package com.zk.library.common.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 垂直ScrollView
 * <p/>
 * 解决内嵌Viewpager 或者水平ScrollView 时滑动经常失效，无法正常滑动问题。
 * <p/>
 * * Created by seazhang on 15/3/13.
 */
public class VerticalScrollView extends ScrollView {

    private GestureDetector mGestureDetector;
    private boolean canScroll;

    public VerticalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mGestureDetector = new GestureDetector(context, new YScrollDetector());
        canScroll = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP)
            canScroll = true;
        return super.onInterceptTouchEvent(ev)
                && mGestureDetector.onTouchEvent(ev);
    }

    class YScrollDetector extends SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            if (canScroll) {
                // 如果我们滚动更接近水平方向,返回false,让子视图来处理它
                if ((Math.abs(distanceY) >= Math.abs(distanceX))) {
                    canScroll = true;
                } else {
                    canScroll = false;
                }
            }

            return canScroll;
        }


    }
}

