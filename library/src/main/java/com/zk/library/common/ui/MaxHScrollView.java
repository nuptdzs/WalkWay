package com.zk.library.common.ui;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.ScrollView;

/**
 * Created by pengmin on 17/5/23.
 */

public class MaxHScrollView extends ScrollView {

    private Context mContext;

    public MaxHScrollView(Context context) {
        super(context);
        init(context);
    }

    public MaxHScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MaxHScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        try {
            //最大高度显示为屏幕内容高度的一半
            Display display = ((Activity) mContext).getWindowManager().getDefaultDisplay();
            DisplayMetrics d = new DisplayMetrics();
            display.getMetrics(d);
            //此处是关键，设置控件高度不能超过屏幕高度一半（d.heightPixels / 2）（在此替换成自己需要的高度）
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(d.heightPixels / 6, MeasureSpec.AT_MOST);
        }catch (Exception e){
            e.printStackTrace();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
