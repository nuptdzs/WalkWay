package com.zk.library.common.ui.dialog;

import android.view.View;

/**
 * Created by sea on 16/8/25.
 */
public class ImageDialog extends AlertDialog {

    private View mView;

    @Override
    View middleView() {
        if (mView == null) {
            return super.middleView();
        } else {
            return mView;
        }
    }

    public void setMiddleView(View view) {
        this.mView = view;
        middleView();
    }
}
