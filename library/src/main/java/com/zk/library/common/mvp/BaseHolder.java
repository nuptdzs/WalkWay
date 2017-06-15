package com.zk.library.common.mvp;


import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseHolder<MODEL> extends RecyclerView.ViewHolder {

    private View view;
    public BaseHolder(View itemView) {
        super(itemView);
        view = itemView;
    }

    public abstract void initdata(MODEL model);
    public abstract BaseHolder<MODEL> getInstance();
}
