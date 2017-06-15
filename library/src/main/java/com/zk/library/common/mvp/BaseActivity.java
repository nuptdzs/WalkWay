package com.zk.library.common.mvp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import butterknife.ButterKnife;


public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IBaseView {

    private static final String TAG = "BaseActivity";
    protected P presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ContentView contentView = getClass().getAnnotation(ContentView.class);
        if(contentView == null){
            Log.e(TAG,"you must add the Annotation called ContentView before using this BaseFragment");
            return;
        }
        int layoutId = contentView.value();
        setContentView(layoutId);
        ButterKnife.bind(this);
        presenter = getPresenter();
        addEvent();
        loadData();
    }
    /**弹窗提示*/
    private Toast toast;
    @Override
    public void showToast(String msg) {
        if(toast == null){
            toast = Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT);
        }else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**加载窗口*/
    private ProgressDialog progressDialog;
    @Override
    public void showLoading(String msg) {
        if(progressDialog == null){
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.setMessage(msg);
        if(progressDialog.isShowing()){

            progressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressDialog==null){
            return;
        }
        progressDialog.hide();
    }

    @Override
    public Context getContext() {
        return getBaseContext();
    }

    @Override
    public Intent _getIntent() {
        return getIntent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.detachView();
        }
        ButterKnife.unbind(this);
    }

    public void nextActivity(Class<?extends BaseActivity> activity){
        Intent intent = new Intent(this,activity);
        startActivity(intent);
    }

    /**获取当前activity的presenter*/
    protected abstract P getPresenter();
    /**给当前activity添加控制事件*/
    protected abstract void addEvent();
    /**加载数据*/
    protected abstract void loadData();

}
