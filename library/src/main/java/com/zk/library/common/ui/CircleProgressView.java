package com.zk.library.common.ui;

/**
 * Created by seazhang on 15/3/19.
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.zk.library.R;


/**
 * 弧形的进度条．
 *
 * @author zhou
 *
 */
public class CircleProgressView extends View {

    /** 所代表的进度 0-100 */
    private float mProgress;
    private int mAnimaProgress = -1;

    /** 前景画笔 */
    private Paint mForPain;
    /** 背景画笔 */
    private Paint mBacPain;
    private Paint mTextPain;

    /** 前景色 */
    private int mForColor;
    /** 背景色 */
    private int mBacColor;
    /** 渐变起始色 */
    private int mStatrtColor;
    /** 渐变结束色 */
    private int mEndColor;
    /** 开始角度 */
    private float mStartAngle;
    /** 圆弧扫过的角度 */
    private float mSweepAngle;
    /**中间字体大小*/
    private float mFontSize;
    /** 终点圆点半径    */
    private float pointRadius;

    /** 绘制区域 */
    RectF oval = new RectF();
    /** 文字的宽度 */
    float textWidth;
    private float strokeWidth;

    private boolean withText = false;
    private boolean inAnimation  = false;
    private boolean isAnimationDone  = true;
    private boolean isShowEndPoint = true;

    public CircleProgressView(Context context) {
        this(context, null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        obtionAttrs(context, attrs);
        init();
    }

    /** 获取自定义属性，颜色，角度 */
    private void obtionAttrs(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleProgress);
        mForColor   = a.getColor(R.styleable.CircleProgress_forColor, Color.rgb(10, 20, 220));
        mBacColor   = a.getColor(R.styleable.CircleProgress_bacColor, Color.rgb(200, 200, 200));
        mStatrtColor= a.getColor(R.styleable.CircleProgress_startColor,getContext().getResources().getColor(R.color.color_orange_f95b09));
        mEndColor   = a.getColor(R.styleable.CircleProgress_endColor,getContext().getResources().getColor(R.color.color_orange_ffff0b));
        mStartAngle = a.getFloat(R.styleable.CircleProgress_startAngle, 160);
        mSweepAngle = a.getFloat(R.styleable.CircleProgress_sweepAngle, 540 - 2 * mStartAngle);
        mFontSize   = a.getDimension(R.styleable.CircleProgress_fontSize, 0);
        mProgress   = a.getInt(R.styleable.CircleProgress_progress, 0);
        pointRadius = a.getDimension(R.styleable.CircleProgress_pointRadius, 3);
        float stroke= getResources().getDimension(R.dimen.strokeWidth);
        strokeWidth = a.getDimension(R.styleable.CircleProgress_cStrokeWidth,stroke);
        setWithText(mFontSize != 0);
        a.recycle();
    }

    public void setShowEndPoint(boolean isShowEndPoint) {
        this.isShowEndPoint = isShowEndPoint;
    }

    /** 这里配置字体颜色等界面效果 */
    private void init() {

        mForPain = new Paint();
        mBacPain = new Paint();
        mTextPain = new Paint();
        // BlurMaskFilter filter = new BlurMaskFilter(30, BlurMaskFilter.Blur.OUTER);

        mForPain.setAntiAlias(true);
        mForPain.setStyle(Paint.Style.STROKE);

        int[] colors = {mStatrtColor, mEndColor, mEndColor};
        float Xf = 70f;
        float[] positions = {0,Xf/360f, 1};
        SweepGradient gradient = new SweepGradient(1, 1, colors , positions);
        mForPain.setShader(gradient);
        mForPain.setStrokeCap(Paint.Cap.ROUND);
        mForPain.setStrokeWidth(strokeWidth);

        mBacPain.setAntiAlias(true);
        mBacPain.setStyle(Paint.Style.STROKE);
        mBacPain.setColor(mBacColor);
        mBacPain.setStrokeCap(Paint.Cap.ROUND);
        mBacPain.setStrokeWidth(strokeWidth);
        // mBacPain.setMaskFilter(filter);
        //mBacPain.setShadowLayer(5, 2, 2, Color.GRAY);

        mTextPain.setAntiAlias(true);
        mTextPain.setColor(Color.BLACK);
        //mTextPain.setShadowLayer(5, 2, 2, Color.GRAY);
        mTextPain.setStrokeCap(Paint.Cap.ROUND);
        mTextPain.setTextSize(mFontSize);
        //mTextPain.setTypeface(Typeface.DEFAULT_BOLD);
        textWidth = mTextPain.measureText(mProgress + "%");

        Paint.FontMetrics fontMetrics = mTextPain.getFontMetrics();
        fontTotalHeight = fontMetrics.bottom + fontMetrics.top;
    }

    /**
     * 设置起始渐变色
     * @param mStatrtColor
     * @param mEndColor
     */
    public void setGradientColor(int mStatrtColor,int mEndColor) {

        this.mStatrtColor = mStatrtColor;
        this.mEndColor = mEndColor;

        destroyDrawingCache();
        init();
        invalidate();
    }

    /**
     * 设置渐变色
     * @param colors 围绕中心渲染的颜色数组，至少要有两种颜色值
     */
    public void setGradientColor(int[] colors) {

        mForPain = new Paint();
        mForPain.setAntiAlias(true);
        mForPain.setStyle(Paint.Style.STROKE);
        SweepGradient gradient = new SweepGradient(1, 1, colors , null);
        mForPain.setShader(gradient);
        mForPain.setStrokeCap(Paint.Cap.ROUND);
        mForPain.setStrokeWidth(strokeWidth);
        destroyDrawingCache();
        invalidate();
    }

    float fontTotalHeight;

    public int getProgress() {
        return (int) (mProgress + 0.5f);
    }

    public void setProgress(int mProgress) {
        setProgress(mProgress,false);
    }

    public void setProgress(int mProgress, int mStartAngle) {
        this.mStartAngle = mStartAngle;
        setProgress(mProgress,false);
    }

    private void setProgress(float mProgress) {
        this.mProgress = mProgress;
        textWidth = mTextPain.measureText((int)mProgress + "%");
        invalidate();
    }

    public void setProgress(float progress,boolean withAnim){
        if(withAnim){
            if(inAnimation){
                clearAnimation();
            }
            ProgressAnimation animation = new ProgressAnimation(progress);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    isAnimationDone = false;
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    isAnimationDone = true;
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            this.startAnimation(animation);
            inAnimation = true;
        }else if (progress >= 0) {
            this.mProgress = progress;
            textWidth = mTextPain.measureText(mProgress + "%");
            invalidate();
        }
    }

    /**
     * 是否显示中间文字
     *  在布局中为设置字体大小的属性(CircleProgress_fontSize)时候默认不显示(false).
     * @param withText
     */
    public void setWithText(boolean withText){
        this.withText = withText;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(oval.centerX(), oval.centerY(), getWidth() / 2 - strokeWidth - 4, mBacPain);
//        canvas.drawArc(oval, mStartAngle, mSweepAngle, false, mBacPain);
        canvas.drawArc(oval, mStartAngle, mProgress * 360 / 100, false, mForPain);
        /**
         *
         圆点坐标：(x0,y0)
         半径：r
         角度：a0

         则圆上任一点为：（x1,y1）
         x1   =   x0   +   r   *   cos(ao   *   3.14   /180   )
         y1   =   y0   +   r   *   sin(ao   *   3.14   /180   )
         */

        //画圆弧终点小圆点
        Paint pointPaint = new Paint();
        pointPaint.setColor(getContext().getResources().getColor(R.color.color_gray_aaaa));
        pointPaint.setAntiAlias(true);
        pointPaint.setStyle(Paint.Style.FILL);
        double x0 = oval.centerX()+ (getWidth()/2 - strokeWidth-4)* Math.cos(((mProgress * 360 / 100 + mStartAngle) * Math.PI / 180));
        double y0 = oval.centerX()+ (getWidth()/2 - strokeWidth-4)* Math.sin(((mProgress * 360 / 100 + mStartAngle) * Math.PI / 180));
        if (isAnimationDone && isShowEndPoint && mProgress <100) {
            canvas.drawCircle((float) x0, (float) y0, pointRadius, pointPaint);
        }

        if(withText)
            canvas.drawText((int) mProgress + "%", ( getWidth() - textWidth) / 2, (getWidth() - fontTotalHeight) / 2, mTextPain);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int mode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        if(mode == MeasureSpec.AT_MOST){
            int width = getMeasuredWidth();
            final int measureSpec = MeasureSpec.makeMeasureSpec(width, MeasureSpec.EXACTLY);
            super.onMeasure(widthMeasureSpec, measureSpec);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        int left, right, top, bottom;
        if ((w > 0) && (h > 0) && ((w != oldw) || (h != oldh))) {
            left = getPaddingLeft();
            right = getPaddingRight();
            top = getPaddingTop();
            bottom = getPaddingBottom();
            oval.set(left, top, w - right, w - bottom);
        }
    }

    /**
     * 柱状图动画
     */
    public class ProgressAnimation extends Animation {

        float start,end;

        public ProgressAnimation(float end) {
            start = getProgress();
            this.end = end;
            //时间间隔有点距离才能跑动画．
            //这样可以保证多次动画是匀速的.
            // 动画时间是个二次函数,这样效果比较均匀.
            final float interval = Math.abs(end - start);
            if(interval >= 1)
                setDuration((long) (200* Math.sqrt(interval)));
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            super.applyTransformation(interpolatedTime, t);
            setProgress((start + (end - start) * interpolatedTime));
            if(interpolatedTime==1){
                inAnimation = false;
            }
        }

        @Override
        public void start() {
            super.start();
            inAnimation = true;
        }

        @Override
        public void cancel() {
            super.cancel();
            inAnimation = false;
        }

        @Override
        public boolean willChangeBounds() {
            return false;
        }

    }

}
