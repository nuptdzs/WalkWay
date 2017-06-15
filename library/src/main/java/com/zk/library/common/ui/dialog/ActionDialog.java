package com.zk.library.common.ui.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zk.library.R;


public class ActionDialog extends Dialog {

    private Context context;//上下文

    private View view;//自定义view

    /**
     * 构造方法
     *
     * @param context 上下文
     */
    public ActionDialog(Context context, View view) {
        super(context, R.style.dialog_normal);
        this.context = context;
        this.view = view;
        setContentView(getView());
    }


    /**
     * 获取自定义view
     *
     * @return
     */
    View getView() {
        Window mWindow = getWindow();
        mWindow.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        mWindow.setGravity(Gravity.BOTTOM);
        mWindow.setLayout(getScreen(context).widthPixels, ViewGroup.LayoutParams.WRAP_CONTENT);
        //添加动画
        mWindow.setWindowAnimations(R.style.dialogAnim_popup);
        mWindow.setAttributes(lp);
        return view;
    }

    /**
     * 获取屏幕分辨率宽
     */
    public DisplayMetrics getScreen(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }


}
