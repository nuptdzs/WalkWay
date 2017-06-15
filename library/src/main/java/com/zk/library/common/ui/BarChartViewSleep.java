package com.zk.library.common.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.zk.library.common.common.Dimension;
import com.zk.library.common.ui.module.holographlibrary.Bar;

import java.util.ArrayList;

/**
 * 睡眠日 详情柱状图
 */
public class BarChartViewSleep extends View {

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
    private float barPadding = 15;
    /**
     * 文字大小(标题文字（大）)
     */
    private float hTextSize = 15;

    /**
     * 文字大小（小）
     */
    private float lTextSize = 9;

    /**
     * 单个柱状宽度
     */
    private int barWidth = 10;

    private int width;

    private int barHeight = 16;
    // private boolean isShowTopText = false;
    /**
     * 柱状起始高度
     */
    private int initHeight = 10;

    private ArrayList<Bar> bars = new ArrayList<>();

    public BarChartViewSleep(Context context) {
        super(context);
        init(context);

    }

    public BarChartViewSleep(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BarChartViewSleep(Context context, AttributeSet attrs, int defStyleAttr) {
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
        // margin = mScreenWidth /pageCount - barWidth;

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

        int height = (int) (getHeight());
        Paint mPaint = new Paint();
        Paint mTextPaint0 = new Paint();
        Paint mTextPaint1 = new Paint();
        mPaint.setAntiAlias(true);
        mTextPaint0.setAntiAlias(true);
        mTextPaint1.setAntiAlias(true);

        if (bars == null || bars.size() <= 0)
            return;
        float maxValue = getMax(bars);

        mTextPaint0.setTextSize(hTextSize);
        mTextPaint1.setTextSize(lTextSize);
        // 文本的宽度
        for (int i = 0; i < bars.size(); i++) {
            Bar bar = bars.get(i);

            float left = getLeftPos(i,bars) + getPaddingLeft();
            float right = left + getVauleAdd(bar.getValue(), bars);
            float top = barHeight;
            float bottom = height - barPadding;

            mTextPaint0.setColor(bar.getColor());
            mTextPaint1.setColor(bar.getColor());
            Shader mShader = new LinearGradient(left, bottom, right, top, bar.getStartColor(), bar.getEndColor(),
                    Shader.TileMode.CLAMP); // 一个材质,打造出一个线性梯度沿著一条线。
            mPaint.setShader(mShader);

            // 画圆形矩形 柱状图
            RectF oval3 = new RectF(left, top, right, bottom);// 设置个新的长方形
            canvas.drawRect(oval3, mPaint);

            // 画底部文字
            if (bar.isShowBottomText()) {
                String text2 = bar.getBottomText();
                if (text2 != null && text2.length() > 0) {
                    float textWidth2 = mTextPaint1.measureText(text2);
                    float mLeft = left;
                    if (bar.isTimeLast()) {
                        mLeft = right - textWidth2;
                    }else if(bar.isTimeFirst()){
                        mLeft = left;
                    }

                    canvas.drawText(bar.getBottomText(), mLeft, bottom + barPadding,
                            mTextPaint1);
                }
            }
        }
    }

    /**
     * 获取最大数
     *
     * @param values
     *            一组数据
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

    private float getLeftPos(int pos, ArrayList<Bar> values) {
        float tmp = 0;
        float total = 0;
        float width = getWidth() - getPaddingLeft() -getPaddingRight();
        if (null != values && pos>0) {
            for (int i = 1; i <= pos; i++) {
                tmp = tmp + values.get(i-1).getValue();
            }
        }
        if(null!=values){
            for(int i = 0;i<values.size();i++){
                total = total + values.get(i).getValue();
            }
        }
        return width*(tmp/total);
    }

    private float getVauleAdd(float value,ArrayList<Bar> values){
        float total = 0;
        float width = getWidth() - getPaddingLeft() -getPaddingRight();

        if(null!=values){
            for(int i = 0;i<values.size();i++){
                total = total + values.get(i).getValue();
            }
        }
        return width*(value/total);
    }

}

