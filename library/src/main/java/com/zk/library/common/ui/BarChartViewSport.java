package com.zk.library.common.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.zk.library.R;
import com.zk.library.common.common.Dimension;
import com.zk.library.common.ui.module.holographlibrary.Bar;

import java.util.ArrayList;

/**
 * 自定义柱状图
 */
public class BarChartViewSport extends View {

    /**
     * 屏幕宽度
     */
    private int mScreenWidth;

    /**
     * 默认一屏显示柱状个数
     */
    private int pageCount = 7;
    /**
     * 柱状之间的间隔
     */
    private int margin;
    /**
     * 文字padding
     */
    private float barPadding = 25;
    /**
     * 文字大小(标题文字（大）)
     */
    private float hTextSize = 10;

    /**
     * 文字大小（小）
     */
    private float lTextSize = 9;

    /**
     * 单个柱状宽度
     */
    private int barWidth = 10;

    private int width;
    private boolean isShowTopText = false;
    /**
     * 柱状起始高度
     */
    private int initHeight = 10;

    private ArrayList<Bar> bars = new ArrayList<>();

    public BarChartViewSport(Context context) {
        super(context);
        init(context);

    }

    public BarChartViewSport(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BarChartViewSport(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    /**
     * view 初始化操作
     *
     * @param context
     */
    private void init(Context context) {
        mScreenWidth = Dimension.getPhoneResolution(context).widthPixels - getPaddingLeft() - getPaddingRight();
        barWidth = Dimension.dip2px(barWidth, context);
        margin = mScreenWidth / pageCount - barWidth;
        hTextSize = Dimension.sp2px(hTextSize, context);
        lTextSize = Dimension.sp2px(lTextSize, context);
        barPadding = Dimension.dip2px(barPadding, context);

    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
        margin = mScreenWidth / pageCount - barWidth;

    }

    public int getMargin() {
        return margin;
    }

    public int getBarWidth() {
        return barWidth;
    }

    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        this.barWidth = Dimension.dip2px(barWidth, getContext());
        margin = mScreenWidth / pageCount - this.barWidth;
    }

    public void setBarInitHeight(int barHeight) {
        this.initHeight = barHeight;
        this.initHeight = Dimension.dip2px(barHeight, getContext());
    }

    public float getlTextSize() {
        return lTextSize;
    }

    public void setlTextSize(float lTextSize) {
        this.lTextSize = lTextSize;
        this.lTextSize = Dimension.sp2px(lTextSize, getContext());
    }

    public float gethTextSize() {
        return hTextSize;
    }

    public void sethTextSize(float hTextSize) {
        this.hTextSize = hTextSize;
        this.hTextSize = Dimension.sp2px(hTextSize, getContext());
    }

    public boolean isShowTopText() {
        return isShowTopText;
    }

    public void setShowTopText(boolean isShowTopText) {
        this.isShowTopText = isShowTopText;
    }

    /**
     * 添加柱状图数据
     *
     * @param bars
     */
    public void setBars(ArrayList<Bar> bars) {
        this.bars = bars;
        postInvalidate();
        requestLayout();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        // 获取小三角的高度
        BitmapFactory.Options o = new BitmapFactory.Options();//实例化一个对象...
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_sport_green, o);
        int picHeight = o.outHeight;
        int height = (int) (getHeight());
        Paint mPaint = new Paint();
        Paint mTextPaint0 = new Paint();
        Paint mTextPaint1 = new Paint();
        mPaint.setAntiAlias(true);
        mTextPaint0.setAntiAlias(true);
        mTextPaint1.setAntiAlias(true);

        if (bars == null || bars.size() <=0) return;
        float maxValue = getMax(bars);

        mTextPaint0.setTextSize(hTextSize);
        mTextPaint1.setTextSize(lTextSize);
        Paint.FontMetrics fm0 = mTextPaint0.getFontMetrics();
        Paint.FontMetrics fm1 = mTextPaint1.getFontMetrics();
        //文本的宽度
        for (int i = 0; i <bars.size(); i++) {
            Bar bar       = bars.get(i);
            float iHeight = 0;
            if (maxValue <= 0){
                iHeight = 0;
            }else {
                iHeight = bar.getValue() / maxValue * Math.abs(height - barPadding * 2);
            }

            float left    = margin /2 + margin * i + barWidth * i + getPaddingLeft();
            float right   = left + barWidth;
            float top     = height - iHeight - barPadding/2 -picHeight ;
            float bottom  = height - barPadding/2 -picHeight;
            if (top == bottom) {
                top -= initHeight;
            }else if (top - bottom > getPaddingTop() && top - bottom > iHeight){
                top -= getPaddingTop();
            }else if (top - bottom < initHeight){
                top -= initHeight;
            }
            mTextPaint0.setColor(bar.getColor());
            mTextPaint1.setColor(bar.getColor());
            Shader mShader = new LinearGradient(left, bottom, right, top,bar.getStartColor(),bar.getEndColor(), Shader.TileMode.CLAMP); // 一个材质,打造出一个线性梯度沿著一条线。
            mPaint.setShader(mShader);

            //画圆形矩形 柱状图
            RectF oval3 = new RectF(left, top, right, bottom);// 设置个新的长方形
            int radius = barWidth/2;
            canvas.drawRoundRect(oval3, radius, radius, mPaint);//第二个参数是x半径，第三个参数是y半径

            //画顶部文字
            if (bar.isShowTopText()) {
                String text0 = bar.getTopText();
                if (text0 !=null || text0.length() > 0) {
                    if (text0.contains("\n")){
                        String[] texts = text0.split("\n");
                        for (int j =0; j < texts.length ; j++){
                            float textwidth = mTextPaint0.measureText(texts[j]);
                            canvas.drawText(texts[j], left - (textwidth - barWidth) / 2, barPadding*3/2 -picHeight+ (fm0.bottom - fm0.top) * j, mTextPaint0);
                        }
                    }else {
                        float textWidth0 = mTextPaint0.measureText(text0);
                        canvas.drawText(text0, left - (textWidth0 - barWidth) / 2, barPadding-picHeight, mTextPaint0);
                    }
                }
                if (i == bars.size() -1) width = (int)(right + margin/2);
            }

            //绘制柱状紧上面的文字
            if (bar.isShowBarText()) {
                String text1 = bar.getItemText();
                if (text1 !=null || text1.length() > 0) {
                    float textWidth1 = mTextPaint1.measureText(text1);
                    canvas.drawText(text1, left - (textWidth1 - barWidth) / 2, top - barPadding, mTextPaint1);
                }
            }

            //画底部文字
            if (bar.isShowBottomText()) {
                String text2 = bar.getBottomText();
                if (text2 !=null || text2.length() > 0) {
                    float textWidth2 = mTextPaint1.measureText(text2);
                    canvas.drawText(bar.getBottomText(), left - (textWidth2 - barWidth) / 2, bottom + barPadding/2, mTextPaint1);
                }
            }
            // 画当前时间图片
            if (bar.isCurrentTime()) {

                Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.icon_sport_green);
                int bitmapWidth = bitmap.getWidth();
                int h = bitmap.getHeight();
                canvas.drawBitmap(bitmap, left - (bitmapWidth - barWidth) / 2, height - picHeight, mPaint);
            }
        }
    }

    /**
     * @return 返回指定的文字高度
     */
    public float getFontHeight(Paint paint) {
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文字基准线的下部距离-文字基准线的上部距离 = 文字高度
        return fm.descent - fm.ascent;
    }

    /**
     * 获取最大数
     *
     * @param values 一组数据
     * @return
     */
    private float getMax(ArrayList<Bar> values) {
        float tmp = Float.MIN_VALUE;
        if (null != values) {
            tmp = values.get(0).getValue();
            for (int i = 0; i < values.size(); i++) {
                if (tmp < values.get(i).getValue()) {
                    tmp = values.get(i).getValue();
                }
            }
        }
        return tmp;
    }

}
