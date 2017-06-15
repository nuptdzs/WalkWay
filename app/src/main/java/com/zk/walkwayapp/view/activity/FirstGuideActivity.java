package com.zk.walkwayapp.view.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v4.view.ViewPager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.library.common.mvp.ContentView;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.view.MainActivity;

import butterknife.Bind;
import butterknife.OnClick;

@ContentView(R.layout.activity_first_guide)
public class FirstGuideActivity extends BaseActivity {

    @Bind(R.id.viewpager)
    ViewPager viewpager;
    @Bind(R.id.btEnter)
    Button btEnter;
    @Bind(R.id.llContainer)
    LinearLayout llContainer;

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void addEvent() {

    }

    @Override
    protected void loadData() {
        SharedPreferences setting = getSharedPreferences("Version",
                Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putBoolean("FIRST", false).apply();
        getPics();
    }

    /**
     * 添加引导页图片资源
     */
    private void getPics() {

    }

    @OnClick(R.id.btEnter)
    public void onClick() {
        nextActivity(MainActivity.class);
        finish();
    }

}
