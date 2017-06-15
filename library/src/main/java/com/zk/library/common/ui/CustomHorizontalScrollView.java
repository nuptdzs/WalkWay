package com.zk.library.common.ui;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.HorizontalScrollView;
import android.widget.Scroller;

/**
 * 自定义HorizontalScrollView
 * <br>修改滑动速度，添加lock center，添加滑动监听
 * @author sea 2015/04/26
 *
 */
public  class CustomHorizontalScrollView extends HorizontalScrollView implements Handler.Callback {
    private static final int MSG_SCROLLING = 0;
    private static final int MSG_FILING = 1;
    private static final int MSG_SCROLL_TO = 2;

    private Scroller mScroller;
    private Handler mHandler;
    private ScrollViewListener scrollViewListener;
    //当前滚动状态
    private ScrollType mScrollType = ScrollType.IDLE;
    private int mWillScrollTo = -1;
    private int selectPosition= -1;

    public CustomHorizontalScrollView(Context context) {
        super(context);
        init();
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch(action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_MOVE:
                mScrollType = ScrollType.TOUCH_SCROLL;
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mScrollType = ScrollType.IDLE;

                //滑动结束，将卷动值对齐到中心。
                int center = getScrollX() + getWidth() / 2;
                mWillScrollTo = getScrollX() + modifyToCenter(center) - center;
                if (mWillScrollTo < 0){
                    mWillScrollTo = 0;
                }
                mHandler.sendEmptyMessage(MSG_SCROLL_TO);
                break;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Message msg = new Message();
        msg.what = MSG_SCROLLING;
        msg.arg1 = l;
        mHandler.sendMessage(msg);
    }

    @Override
    public void fling(int velocityX) {
        //重写飞滑，改成平滑卷动并对齐到某个条目的中心
        //velocityX < 0 向右，velocityX >0 向左
        int currentX = getScrollX();
        int maxScrollX = 0;
        float decelerate = 3000; //减速度
        float t = (float)velocityX / decelerate;
        int officeX = (int)(500 * t * t) * (velocityX > 0 ? 1 : -1);

        View view = getChildAt(0);
        if (view != null){
            int sw = view.getWidth();
            int w = getWidth();
            if (sw > w){
                maxScrollX = sw - w;
            }
        }

        if (maxScrollX < officeX + currentX){
            officeX = maxScrollX - currentX;
        }

        //按现有速度要滑动的中心距离
        int center = currentX + officeX + getWidth() / 2;
        //补正后的中心距离
        int modifyCenter = modifyToCenter(center);
        //补正后需要滑动的距离
        officeX += modifyCenter - center;

        mScroller.startScroll(currentX, 0, officeX, 0);

        //停止 MSG_SCROLL_TO 消息的对齐操作
        mWillScrollTo = -1;

        postInvalidate();
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what){
            case MSG_SCROLLING:
                if (scrollViewListener != null) {
//                    scrollViewListener.onScrollChanged(ScrollType.TOUCH_SCROLL,msg.arg1);
                }
                break;

            case MSG_SCROLL_TO:
                if (mWillScrollTo >= 0){
                    smoothScrollTo(mWillScrollTo,getScrollY());
                    mWillScrollTo = -1;
                }
                if (mWillScrollTo == -1){
                    if (scrollViewListener != null) {
                        scrollViewListener.onItemSelected(selectPosition);
                    }
                }

                break;
        }
        return false;
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            //这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            //必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();
        }
        else {
            super.computeScroll();
        }
    }

    /**
     * 必须先调用这个方法设置Handler 不然会出错   must set Handler first  befo\若俄   t scroll state listener
     */
    public void setHandler(Handler handler, int directChildId) {

    }

    /**
     * 必须先调用这个方法设置Handler 不然会出错   must set Handler first  before set scroll state listener
     */
    public void setHandler(Handler handler) {

    }

    /**
     * 设置滚动监听 add scroll state change listener
     */
    public void setOnScrollStateChangedListener(ScrollViewListener listener) {
        this.scrollViewListener = listener;
    }

    private int modifyToCenter(int x){

        ViewGroup group = (ViewGroup)getChildAt(0);
        int chilrenNum = group.getChildCount();
        for (int i = 0; i < chilrenNum; i++) {
            View v = group.getChildAt(i);

            int viewLeft = v.getLeft();
            int viewWidth = v.getWidth();
            if (x >= viewLeft && x <= viewLeft + viewWidth) {
                selectPosition = i;
                return (viewLeft + viewWidth / 2);
            }
        }

        return x;
    }

    private void init(){
        mScroller = new Scroller(getContext(),new DecelerateInterpolator());
        mHandler = new Handler(this);
    }


    public interface ScrollViewListener {
        void onScrollChanged(ScrollType scrollType, int scrollX);
        void onItemSelected(int position);

    }

    /**
     * 滚动状态 IDLE 滚动停止 TOUCH_SCROLL 手指拖动滚动 FLING滚动
     */
    public enum ScrollType {
        IDLE, TOUCH_SCROLL, FLING
    }
}