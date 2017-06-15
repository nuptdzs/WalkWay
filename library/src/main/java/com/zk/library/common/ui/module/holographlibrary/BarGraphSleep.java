package com.zk.library.common.ui.module.holographlibrary;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zk.library.R;
import com.zk.library.common.common.Dimension;
import com.zk.library.common.ui.module.booheescrollview.BooheeScrollView;

import java.util.ArrayList;

/**
 * 绘制柱状图
 * 睡眠（周）
 */
public class BarGraphSleep extends LinearLayout {

    private LinearLayout mRootLayout;
    private boolean isShowSelect = false;
    private int mScreenHeight;
    private int count;
    /**
     * 默认一屏显示柱状个数
     */
    private int pageCount = 7;
    private float margin;
    /**
     * 单个柱状宽度
     */
    private float barWidth = 10;
    /**
     * 选中子项目的宽度
     */
    private float selectWidth = 40;
    //起始柱状高度
    private float initHeight = 0;
    private int centerStart;
    private RelativeLayout selectIndexLayout;
    private RelativeLayout selectLayout;
    private ImageView selectImage;
    private TextView selectText;
    private BooheeScrollView horizontalScrollView;
    private OnBarEventListener barEventListener;
    private int currentSelectedIndex = -1;
    private int bottomTextColor = getResources().getColor(R.color.blue_homebackground_4688f1);
    private int topTextColor = getResources().getColor(R.color.blue_homebackground_4688f1);
    private int itemTextColor = getResources().getColor(R.color.white);
    private Typeface face;

    public BarGraphSleep(Context context) {
        this(context, null);

        initUI(context);
    }

    public BarGraphSleep(Context context, AttributeSet attrs) {
        super(context, attrs);

        initUI(context);
    }


    public void setTopTextColor(int topTextColor) {
        this.topTextColor = topTextColor;
    }

    public void setBottomTextColor(int bottomTextColor) {
        this.bottomTextColor = bottomTextColor;
    }

    public void setItemTextColor(int itemTextColor) {
        this.itemTextColor = itemTextColor;
    }

    /**
     * 设置默认一屏显示柱状个数
     * @param pageCount
     */
    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
        centerStart = isEven(pageCount) ? pageCount/2 - 1 : pageCount/2;

    }

    /**
     * 设置柱状宽度 （单位dp）
     * @param barWidth
     */
    public void setBarWidth(int barWidth) {
        this.barWidth = barWidth;
        this.barWidth = Dimension.dip2px(barWidth,getContext());
    }

    /**
     * 显示选中某选中项时显示背景
     * @param isShowSelect
     */
    public void setShowSelect(boolean isShowSelect) {
        this.isShowSelect = isShowSelect;
    }

    public void setBarEventListener(OnBarEventListener barEventListener) {
        this.barEventListener = barEventListener;
    }

    private int textMargin = 1;
    private void initUI(Context context) {
        barWidth    = Dimension.dip2pxF(barWidth, context);
        selectWidth = Dimension.dip2pxF(selectWidth,context);
        centerStart = isEven(pageCount) ? pageCount/2 - 1 : pageCount/2;
        face                             = Typeface.createFromAsset(getContext().getAssets(), "fonts/AvenirNextLTPro-Regular.ttf");
        mScreenHeight                    = ((Activity)context).getWindowManager().getDefaultDisplay().getHeight();
        RelativeLayout mParentRootLayout = (RelativeLayout) LayoutInflater.from(context).inflate(R.layout.ui_bar_graph, null);
        horizontalScrollView 	         = (BooheeScrollView) mParentRootLayout.findViewById(R.id.ui_id_bar_graph_scroll_view);
        mRootLayout 			         = (LinearLayout) mParentRootLayout.findViewById(R.id.ui_id_bar_graph_content_view);
        selectLayout                     = (RelativeLayout) mParentRootLayout.findViewById(R.id.select_layout);
        selectIndexLayout                = (RelativeLayout) mParentRootLayout.findViewById(R.id.select_index_layout);
        selectImage                      = (ImageView) mParentRootLayout.findViewById(R.id.select_iamge);
        selectText                       = (TextView) mParentRootLayout.findViewById(R.id.select_text);
        selectText.setTypeface(face);
        addView(mParentRootLayout);
//        mRootLayout.setOnClickListener(new OnClickListener() {
//            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
//            @Override
//            public void onClick(View v) {
//                if (barEventListener !=null){
//                    Log.e("123",v.getX()+" "+v.getScaleX()+" "+getWidth());
//                    int pos = (int) (getWidth()/v.getX());
////                    barEventListener.onBarItemClick(pos);
//                }
//            }
//        });
        mRootLayout.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (barEventListener !=null && event.getAction() == MotionEvent.ACTION_UP){
                    float perWid = getWidth()/7;
                    int pos = (int) (event.getX()/perWid);
                    barEventListener.onBarItemClick(pos);
                }
                return true;
            }
        });
    }


    public void setBars(final ArrayList<Bar> bars) {
        //View绘制完成监听
        mRootLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    mRootLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                } else {
                    mRootLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }

                // get width and height of the view
                if (mRootLayout.getHeight() != 0){
                    setBarsView(bars);
                }
            }
        });
    }

    /**
     * 清空视图
     */
    public void clearBar(){
        if (mRootLayout!=null){
            mRootLayout.removeAllViews();
            invalidate();
        }
    }

    public void setTextMargin(int margin){
        textMargin = margin;
    }
    private void setBarsView(ArrayList<Bar> bars) {

        if (bars == null || bars.size() == 0) return;
        View[] barViews = new View[bars.size()];
        count = bars.size();
        if (pageCount >= bars.size()) pageCount = bars.size();

        int width   = getWidth();
        margin      =  (width - barWidth * pageCount)/pageCount;
        final float maxValue = getMax(bars);

        int mTextMargin = 0;
        for (int i = 0; i < bars.size(); i++) {
            RelativeLayout layout =
                    (RelativeLayout) LayoutInflater.from(getContext()).inflate(R.layout.ui_rect_item_view, mRootLayout, false);
            final TextView itemTopText  = (TextView) layout.findViewById(R.id.ui_id_item_top_text);
            final TextView topText      = (TextView) layout.findViewById(R.id.ui_id_top_text);
            final View rectView         = layout.findViewById(R.id.ui_id_item_center_rect);
            final TextView bottomText   = (TextView) layout.findViewById(R.id.ui_id_item_bottom_text);

            final Bar bar = bars.get(i);
            if (bar.isShowBarText()){
                itemTopText.setVisibility(View.VISIBLE);
                itemTopText.setText(String.valueOf(bar.getItemText()));
                itemTopText.setTextColor(itemTextColor);
            }else {
                itemTopText.setVisibility(View.GONE);
            }
            if (bar.isShowTopText()){
                topText.setVisibility(View.VISIBLE);
                topText.setText(String.valueOf(bar.getTopText()));
                topText.setTextColor(topTextColor);
                topText.setTypeface(face);

            }else {
                topText.setVisibility(View.INVISIBLE);
            }

            if (bar.isShowBottomText()){
                bottomText.setText(bar.getBottomText());
                bottomText.setVisibility(View.VISIBLE);
                bottomText.setTextColor(bottomTextColor);

            }else {
                bottomText.setVisibility(View.GONE);
            }
            if(mTextMargin<textMargin){
                topText.setText("");
            }else {
                mTextMargin = 0;
            }
            if(i == 0){
                topText.setText(String.valueOf(bar.getTopText()));
                mTextMargin++;
            }
            mTextMargin++;
            if (bar.getColor()!=0){
                rectView.setBackgroundColor(bar.getColor());
            }
            if (bar.getDrawable() !=0){
                rectView.setBackgroundResource(bar.getDrawable());
            }
            rectView.setBackgroundResource(R.drawable.ui_bargraph_bg_white);
            float height;
            //计算柱状图的高度
            if (maxValue <=0) {
                height = 0;
            }else {
                height = bar.getValue() / maxValue * (mRootLayout.getHeight() - Dimension.dip2px(30, getContext()) - Dimension.dip2px(mScreenHeight * 40 / mScreenHeight, getContext()));
            }
            LayoutParams params = new LayoutParams((int)barWidth,(int)(height+initHeight));

            float marginLeft  = margin / 2;
            float marginRight = margin / 2;
            if (i == 0){
                marginLeft = marginLeft ;
            }

            if (i == bars.size() -1){
                marginRight = marginRight ;
            }
            params.setMargins((int) marginLeft, 0, (int) marginRight, 0);
            params.width = (int) barWidth;
            rectView.setPadding((int)marginLeft,0,(int)marginRight,0);
            rectView.setLayoutParams(params);
            barViews[i] = layout;
            mRootLayout.addView(layout);
        }
        horizontalScrollView.setChildViews(barViews);

        if (isShowSelect) {
            setIndexLayoutMargin();
        }
    }

    /**
     * 设置选中索引文本
     * @param text
     */
    public void setIndexInfo(final String text){
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                selectText.setText(text);
            }
        });
    }

    /**
     * 设置选中索引图片
     * @param drawable
     */
    public void setIndexImage(int drawable){
        selectImage.setImageResource(drawable);
    }

    /**
     * 设置index布局边距
     */
    public void setIndexLayoutMargin(){

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,mRootLayout.getHeight());
        //选中项距屏幕左边距
        float marginLeft = 4*barWidth + 9*margin/2 - (selectWidth/3 -barWidth/3);
        lp.setMargins((int)marginLeft,0,0,0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        selectLayout.setLayoutParams(lp);
        selectLayout.setVisibility(View.VISIBLE);
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

    /**
     * 滚动到指定位置
     * @param index
     */
    public void scrollToIndexPosition(int index,int offset,boolean isSmoothScroll){

        ViewGroup group = (ViewGroup)getChildAt(0);
        BooheeScrollView scrollview = (BooheeScrollView) group.getChildAt(1);
        ViewGroup group2 = (ViewGroup) scrollview.getChildAt(0);
        View view = group2.getChildAt(index);
        if (view !=null) {
            int viewLeft = view.getLeft();
            if (isSmoothScroll){

                scrollview.smoothScrollTo(viewLeft + offset, 0);
            }else {
                scrollview.scrollTo(viewLeft + offset, 0);
            }
        }
    }

    /**
     * 滚动到指定位置
     * @param index
     */
    public void scrollToIndexPosition(int index,boolean isSmoothScroll){

        scrollToIndexPosition(index,0,isSmoothScroll);
    }

    //奇偶数判断
    private boolean isEven(int count){
        return count%2==0;
    }

    /**
     * bargraph事件监听器
     */
    public interface OnBarEventListener {
        /**
         * 子项目点击
         */
        void onBarItemClick(int pos);

    }

}