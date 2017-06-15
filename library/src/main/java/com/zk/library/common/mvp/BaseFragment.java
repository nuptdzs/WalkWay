package com.zk.library.common.mvp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView{
    private static final String TAG = "BaseFragment";
    private BaseActivity baseActivity;
    private View rootView;
    protected P mPresenter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            ContentView contentView = getClass().getAnnotation(ContentView.class);
            if(contentView == null){
                Log.e(TAG,"you must add the Annotation called ContentView before using this BaseFragment");
                return null;
            }
            int layoutId = contentView.value();
            rootView = inflater.inflate(layoutId, container, false);
        }
        ButterKnife.bind(this, rootView);
        load();
        return rootView;
    }

    protected abstract void load();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        rootView = null;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        baseActivity = (BaseActivity) getActivity();
        mPresenter = getPresenter();
    }

    protected abstract P getPresenter();

    @Override
    public void onDetach() {
        super.onDetach();
        mPresenter.detachView();
        baseActivity = null;
    }

    @Override
    public void showToast(String msg) {
        if(baseActivity!=null){
            baseActivity.showToast(msg);
        }else {
            Log.e(TAG,"showToast is useless because activity is null");
        }
    }

    @Override
    public void showLoading(String msg) {
        if(baseActivity!=null){
            baseActivity.showLoading(msg);
        }else {
            Log.e(TAG,"showLoading is useless because activity is null");
        }
    }

    @Override
    public void hideLoading() {
        if(baseActivity!=null){
            baseActivity.hideLoading();
        }else {
            Log.e(TAG,"hideLoading is useless because activity is null");
        }
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public Intent _getIntent() {
        if(baseActivity == null){
            return null;
        }
        return getActivity().getIntent();
    }
}
