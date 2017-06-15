package com.zk.library.common.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.zk.library.R;


/**
 * 圆形渐变进度条
 */
public class CirclePercentView extends View {

    private Paint percentPaint;
    private Paint percentHighPaint;

    private Paint textPaint;
    private int textSize = 30;

    private int percent;
    private int allLineColor;
    private int percentLineColorLow;
    private int percentLineColorHight;

    private int allLineWidth = 6;
    private int percentLineWidth = 6;
    private int lineHeight = 15;
    private boolean percentTextVisible = true;


    public CirclePercentView(Context context) {
        super(context);
        init(null, 0);
    }

    public CirclePercentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public CirclePercentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CirclePercentView, defStyle, 0);
        percent = a.getInt(R.styleable.CirclePercentView_percent, 0);
        allLineColor = a.getColor(R.styleable.CirclePercentView_allLineColor, Color.GRAY);
        percentLineColorLow = a.getColor(R.styleable.CirclePercentView_percentLineColorLow, Color.GREEN);
        percentLineColorHight = a.getColor(R.styleable.CirclePercentView_percentLineColorHight, Color.RED);
        percentTextVisible = a.getBoolean(R.styleable.CirclePercentView_percentTextVisable, false);
        a.recycle();

        percentPaint = new Paint();
        percentPaint.setAntiAlias(true);
        percentHighPaint = new Paint();
        percentHighPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        int pointX = width / 2;
        int pointY = height / 2;

        float textWidth = textPaint.measureText(percent + "%");
        if (percent < 50) {
            //textPaint.setColor(oxbf3800);  
            textPaint.setColor(Color.BLACK);
        } else {
            //textPaint.setColor(new Color(ox6ec705));  
            textPaint.setColor(Color.RED);
        }
        if (percentTextVisible) {
            canvas.drawText(percent + "%", pointX - textWidth / 2, pointY + textPaint.getTextSize() / 2, textPaint);
        }


        percentPaint.setColor(allLineColor);
        percentPaint.setStrokeWidth(allLineWidth);

        float degrees = (float) (320.0 / 100);

        canvas.save();
        canvas.translate(0, pointY);
        canvas.rotate(-70, pointX, 0);
        for (int i = 0; i < 100; i++) {
            canvas.drawLine(0, 0, lineHeight, 0, percentPaint);
            canvas.rotate(degrees, pointX, 0);
        }
        canvas.restore();

        percentPaint.setColor(percentLineColorLow);
        percentHighPaint.setColor(percentLineColorHight);
        percentPaint.setStrokeWidth(percentLineWidth);
        percentHighPaint.setStrokeWidth(percentLineWidth);
        canvas.save();

        canvas.translate(0, pointY);
        canvas.rotate(-70, pointX, 0);
        for (int i = 0; i < percent; i++) {
            if (i < 40) {
                canvas.drawLine(0, 0, lineHeight, 0, percentPaint);
            } else {
                canvas.drawLine(0, 0, lineHeight, 0, percentHighPaint);
            }
            canvas.rotate(degrees, pointX, 0);
        }
        canvas.restore();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int d = (width >= height) ? height : width;
        setMeasuredDimension(d, d);
    }

    public void setPercent(int percent) {
        this.percent = percent;
        postInvalidate();
    }

    public void setPercentTextVisibility(boolean visibility) {
        percentTextVisible = visibility;
    }

    ;
}