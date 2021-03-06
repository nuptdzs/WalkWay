package com.zk.library.common.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.zk.library.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 折线图
 * <p/>
 * (原点)左上角为坐标原点（0，0）
 * 。
 * |
 * |
 * |
 * |
 * |
 * |
 * |------------------->
 * (0,yori)
 */
public class LineChartView extends View {
    float startX = 0;//滑动时候，上一次手指的x坐标
    private int xori;//圆点x坐标
    private int yori;//圆点y坐标
    private int xinit;//第一个点x坐标
    private int minXinit;//在移动时，第一个点允许最小的x坐标
    private int maxXinit;//在移动时，第一个点允许允许最大的x坐标
    private int xylinecolor;//xy坐标轴颜色
    private int xylinewidth;//xy坐标轴大小
    private int xytextcolor;//xy坐标轴文字颜色
    private int xytextsize;//xy坐标轴文字大小
    private int linecolor;//折线的颜色
    private int interval;//坐标间的间隔
    private int yInterval;//坐标y间的间隔
    private int bgColor;//背景颜色
    private List<String> x_coords;//x坐标点的值
    private List<String> x_coord_values;//每个点状态值
    private int width;//控件宽度
    private int heigth;//控件高度
    private float textwidth;//y轴文字的宽度
    private float pointRadius = 5;//点心圆半径

    public LineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineChartView);
        xylinecolor = typedArray.getColor(R.styleable.LineChartView_xylinecolor, Color.GRAY);
        xylinewidth = typedArray.getInt(R.styleable.LineChartView_xylinewidth, 2);
        xytextcolor = typedArray.getColor(R.styleable.LineChartView_xytextcolor, Color.BLACK);
        xytextsize = typedArray.getLayoutDimension(R.styleable.LineChartView_xytextsize, 20);
        linecolor = typedArray.getColor(R.styleable.LineChartView_linecolor, Color.GRAY);
        interval = typedArray.getLayoutDimension(R.styleable.LineChartView_interval, 100);
        yInterval = typedArray.getLayoutDimension(R.styleable.LineChartView_yinterval, 100);
        bgColor = typedArray.getColor(R.styleable.LineChartView_bgcolor, Color.WHITE);
        typedArray.recycle();
        x_coords = new ArrayList<String>();
        x_coord_values = new ArrayList<String>();
    }


    public LineChartView(Context context) {
        super(context);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        if (changed) {
            width = getWidth();
            heigth = getHeight();
            Paint paint = new Paint();
            paint.setTextSize(xytextsize);
            textwidth = paint.measureText("ABC");
            xori = (int) (textwidth + 6 + 2 * xylinewidth);//6 为与y轴的间隔
            yori = heigth - xytextsize - 2 * xylinewidth - 3;
//            xinit = interval / 2 + xori;
            xinit = xori;
            minXinit = width - xori - x_coords.size() * interval;
            maxXinit = xinit;
            setBackgroundColor(bgColor);
        }
        super.onLayout(changed, left, top, right, bottom);
    }


    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        drawX(canvas);
        drawXY(canvas);
        drawY(canvas);
    }


    //画坐标轴
    private void drawXY(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(xylinecolor);
        paint.setStrokeWidth(xylinewidth);
//        canvas.drawLine(xori, 0, xori, yori, paint);
        canvas.drawLine(xori, yori, width, yori, paint);
    }

    //画Y轴坐标点
    private void drawY(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(xylinecolor);
        paint.setStyle(Paint.Style.FILL);
        for (int i = 1; i < 5; i++) {
            canvas.drawCircle(xori, yori - (i * yInterval / 2), xylinewidth, paint);
            canvas.drawLine(xori, yori - (i * yInterval / 2), getWidth(), yori - (i * yInterval / 2), paint);
        }

        paint.setTextSize(xytextsize);
        paint.setColor(xytextcolor);
        canvas.drawText("1W", xori - textwidth - 6 - xylinewidth, yori - (2 * yInterval) + xytextsize / 2, paint);
        canvas.drawText("7.5k", xori - textwidth - 6 - xylinewidth, (float) (yori - (1.5 * yInterval) + xytextsize / 2), paint);
        canvas.drawText("5k", xori - textwidth - 6 - xylinewidth, yori - yInterval + xytextsize / 2, paint);
        canvas.drawText("2.5k", xori - textwidth - 6 - xylinewidth, (float) (yori - (0.5 * yInterval) + xytextsize / 2), paint);
        canvas.drawText("0k", xori - textwidth - 6 - xylinewidth, (float) yori, paint);

    }

    //画X轴坐标点，折线，表情
    @SuppressLint("ResourceAsColor")
    private void drawX(Canvas canvas) {
        Paint x_coordPaint = new Paint();

        Log.i("xinit", xinit + "");
        x_coordPaint.setTextSize(xytextsize);
        x_coordPaint.setStyle(Paint.Style.FILL);
        Path path = new Path();
        //画坐标点
        for (int i = 0; i < x_coords.size(); i++) {
            int x = i * interval + xinit;
            if (i == 0) {

                path.moveTo(0, yori);
                path.lineTo(x, getYValue(x_coord_values.get(i)));

            } else {
                path.lineTo(x, getYValue(x_coord_values.get(i)));
            }

            if (i == x_coords.size() - 1) {
                path.lineTo(x, yori);
            }


            x_coordPaint.setColor(xylinecolor);
            canvas.drawCircle(x, yori, xylinewidth, x_coordPaint);
            String text = x_coords.get(i);
            x_coordPaint.setColor(xytextcolor);

            canvas.drawText(text, x - x_coordPaint.measureText(text) / 2 + 20, yori + xytextsize + xylinewidth * 2, x_coordPaint);
        }
        path.close();

        x_coordPaint.setStyle(Paint.Style.FILL);
        x_coordPaint.setStrokeWidth(xylinewidth);
        x_coordPaint.setColor(linecolor);
//        x_coordPaint.setColor(Color.BLUE);
        Shader mShader = new LinearGradient(0, 0, 0, getHeight(), new int[]{
                Color.argb(100, 0, 255, 255), Color.argb(45, 0, 255, 255),
                Color.argb(10, 0, 255, 255)}, null, Shader.TileMode.CLAMP);
        x_coordPaint.setShader(mShader);
        //画折线
        canvas.drawPath(path, x_coordPaint);


        //画点
        Paint circlePaint = new Paint();
        circlePaint.setColor(Color.CYAN);
        circlePaint.setStyle(Paint.Style.STROKE);
        for (int i = 0; i < x_coords.size(); i++) {
            int x = i * interval + xinit;
//            canvas.drawBitmap(getYBitmap(x_coord_values.get(i)), x - imageWidth / 2, getYValue(x_coord_values.get(i)) - imageWidth / 2, x_coordPaint);
            canvas.drawCircle(x,getYValue(x_coord_values.get(i)),pointRadius,circlePaint);
        }


        //将折线超出x轴坐标的部分截取掉
        Paint rectfPaint = new Paint();
        rectfPaint.setStyle(Paint.Style.FILL);
        rectfPaint.setColor(bgColor);
        rectfPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        RectF rectF = new RectF(0, 0, xori, heigth);
        canvas.drawRect(rectF, rectfPaint);

    }


    //得到y坐标
//    private float getYValue(String value) {
//        if (value.equalsIgnoreCase("A")) {
//            return yori - yInterval / 2;
//        } else if (value.equalsIgnoreCase("B")) {
//            return yori - yInterval;
//        } else if (value.equalsIgnoreCase("C")) {
//            return (float) (yori - yInterval * 1.5);
//        } else if (value.equalsIgnoreCase("D")) {
//            return yori - yInterval * 2;
//        } else {
//            return yori;
//        }
//    }

    private float getYValue(String value) {
        BigDecimal bigDecimal = new BigDecimal(2);
        //  4*yInterval*value/10000;
        float yValue = bigDecimal.multiply(new BigDecimal(yInterval))
                .multiply(new BigDecimal(value))
                .divide(new BigDecimal(10000))
                .floatValue();

        return yori - yValue;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        //如果不用滑动就可以展示所有数据，就不让滑动
        if (interval * x_coord_values.size() <= width - xori) {
            return false;
        }

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;

            case MotionEvent.ACTION_MOVE:
                float dis = event.getX() - startX;
                startX = event.getX();
                if (xinit + dis > maxXinit) {
                    xinit = maxXinit;
                } else if (xinit + dis < minXinit) {
                    xinit = minXinit;
                } else {
                    xinit = (int) (xinit + dis);
                }
                invalidate();

                break;
        }
        return true;
    }


    public int getXori() {
        return xori;
    }


    public void setXori(int xori) {
        this.xori = xori;
    }


    public int getYori() {
        return yori;
    }


    public void setYori(int yori) {
        this.yori = yori;
    }


    public int getXinit() {
        return xinit;
    }


    public void setXinit(int xinit) {
        this.xinit = xinit;
    }


    public int getMinXinit() {
        return minXinit;
    }


    public void setMinXinit(int minXinit) {
        this.minXinit = minXinit;
    }


    public int getMaxXinit() {
        return maxXinit;
    }


    public void setMaxXinit(int maxXinit) {
        this.maxXinit = maxXinit;
    }


    public int getXylinecolor() {
        return xylinecolor;
    }


    public void setXylinecolor(int xylinecolor) {
        this.xylinecolor = xylinecolor;
    }


    public int getXylinewidth() {
        return xylinewidth;
    }


    public void setXylinewidth(int xylinewidth) {
        this.xylinewidth = xylinewidth;
    }


    public int getXytextcolor() {
        return xytextcolor;
    }


    public void setXytextcolor(int xytextcolor) {
        this.xytextcolor = xytextcolor;
    }


    public int getXytextsize() {
        return xytextsize;
    }


    public void setXytextsize(int xytextsize) {
        this.xytextsize = xytextsize;
    }


    public int getLinecolor() {
        return linecolor;
    }


    public void setLinecolor(int linecolor) {
        this.linecolor = linecolor;
    }


    public int getInterval() {
        return interval;
    }


    public void setInterval(int interval) {
        this.interval = interval;
    }


    public int getBgColor() {
        return bgColor;
    }


    public void setBgColor(int bgColor) {
        this.bgColor = bgColor;
    }


    public List<String> getX_coords() {
        return x_coords;
    }


    public List<String> getX_coord_values() {
        return x_coord_values;
    }


    /**
     * 设置坐标折线图值
     *
     * @param x_coords       横坐标坐标点
     * @param x_coord_values 每个点的值
     */
    public void setValue(List<String> x_coords, List<String> x_coord_values) {
        if (x_coord_values.size() != x_coords.size()) {
            throw new IllegalArgumentException("坐标轴点和坐标轴点的值的个数必须一样!");
        }
        this.x_coord_values = x_coord_values;
        this.x_coords = x_coords;
        invalidate();
    }


    /**
     * 设置坐标折线图值
     *
     * @param coordValues 坐标点
     */
    public void setValue(List<HashMap<String, String>> coordValues) {
        if (coordValues == null) return;

        for (int i = 0; i < coordValues.size(); i++) {
            HashMap<String, String> coord = coordValues.get(i);
            this.x_coords.add(coord.get("xcoord"));
            this.x_coord_values.add(coord.get("ycoord"));

        }
        invalidate();
    }

    public void clearLine(){
        x_coords.clear();
        x_coord_values.clear();
        invalidate();
    }
}
