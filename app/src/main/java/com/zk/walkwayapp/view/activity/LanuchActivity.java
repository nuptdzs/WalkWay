package com.zk.walkwayapp.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.BasePresenter;
import com.zk.library.common.mvp.ContentView;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.view.MainActivity;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

@ContentView(R.layout.welcome_activity)
public class LanuchActivity extends BaseActivity {
    TimerTask timerTask;
    Timer timer;
    int count;
    int time;
    AdHandler adhandler = new AdHandler(this);
    private static class AdHandler extends Handler {
        private WeakReference<LanuchActivity> welcomeActivityWeakReference;
        AdHandler(LanuchActivity welcomeActivity){
            welcomeActivityWeakReference = new WeakReference<>(welcomeActivity);
        }

        void destory(){
            welcomeActivityWeakReference.clear();
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1101:
                    welcomeActivityWeakReference.get().initAd();
                    welcomeActivityWeakReference.get().tvJump.setVisibility(View.VISIBLE);
                    break;
                case 1102:
                    welcomeActivityWeakReference.get().llTime.setVisibility(View.VISIBLE);
                    welcomeActivityWeakReference.get().tvTime.setVisibility(View.VISIBLE);
                    welcomeActivityWeakReference.get().tvTime.setText(welcomeActivityWeakReference.get().count + "s");
                    welcomeActivityWeakReference.get().count--;
                    if (welcomeActivityWeakReference.get().count == 0) {
                        welcomeActivityWeakReference.get().tvJump.setClickable(false);
                        welcomeActivityWeakReference.get().start();
                    }
                    break;
            }
        }
    }
    /**
     * 广告栏倒计时和跳过功能
     */
    private TextView tvTime, tvJump;
    private ImageView imgAd;
    private LinearLayout llTime;

    //加载广告图片
    private void initAd() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                adhandler.sendEmptyMessage(1102);
            }
        };
        timer = new Timer();
        imgAd = (ImageView) findViewById(R.id.adImage);
        tvTime = (TextView) findViewById(R.id.tvTimeSeconds);
        llTime = (LinearLayout) findViewById(R.id.llTime);
        tvJump = (TextView) findViewById(R.id.tvJump);
        tvJump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startMain();
                finish();
            }
        });
        timer.schedule(timerTask, 0, 1000);

    }

    /**
     * 判断是否是该版本第一次登陆
     *
     * @return
     */
    private boolean isFirstEnter() {
        SharedPreferences setting = getSharedPreferences("Version",
                Activity.MODE_PRIVATE);
        Boolean user_first = setting.getBoolean("FIRST", true);
        if (user_first) {//
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断用户是否第一次登陆
     * 若第一次登陆就跳转引导页面
     */
    public void start() {
        if (isFirstEnter())
            mHandler.sendEmptyMessage(SWITCH_GUIDACTIVITY);
        else
            mHandler.sendEmptyMessage(SWITCH_MAINACTIVITY);
    }

    private final static int SWITCH_MAINACTIVITY = 1000; //
    private final static int SWITCH_GUIDACTIVITY = 1001; //
    private LanuchHandler mHandler = new LanuchHandler(this);

    private static class LanuchHandler  extends Handler {
        public WeakReference<LanuchActivity> welcomeActivityWeakReference;
        public LanuchHandler(LanuchActivity welcomeActivity){
            welcomeActivityWeakReference = new WeakReference<>(welcomeActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SWITCH_MAINACTIVITY:
                    welcomeActivityWeakReference.get().startMain();
                    break;
                case SWITCH_GUIDACTIVITY:
                    welcomeActivityWeakReference.get().startFirst();
                    break;
            }
            super.handleMessage(msg);
        }
        public void destory(){
            if(welcomeActivityWeakReference!=null){
                welcomeActivityWeakReference.clear();
            }
        }
    }

    /**
     * 进入主界面
     */
    private void startMain() {
        timer.cancel();
        timerTask.cancel();
        Intent intent = new Intent();
        if(AVUser.getCurrentUser()==null){
            intent.setClass(LanuchActivity.this, LoginActivity.class);
        }else {
            intent.setClass(LanuchActivity.this, MainActivity.class);
        }
        LanuchActivity.this.startActivity(intent);
        LanuchActivity.this.finish();
    }

    /**
     * 第一次进入界面
     */
    private void startFirst() {
        timer.cancel();
        timerTask.cancel();
        Intent intents = new Intent();
        intents.setClass(LanuchActivity.this, FirstGuideActivity.class);
        LanuchActivity.this.startActivity(intents);
        LanuchActivity.this.finish();
    }

    @Override
    protected void onDestroy() {
        if(timerTask!=null){
            timerTask.cancel();
            timerTask = null;

        }
        if(timer!=null){
            timer.cancel();
            timer = null;
        }

        if(adhandler!=null){
            adhandler.destory();
        }

        if(mHandler!=null){
            mHandler.destory();
        }
        super.onDestroy();
    }

    @Override
    protected BasePresenter getPresenter() {
        return null;
    }

    @Override
    protected void addEvent() {
        count = 3;
        time = 0;
        adhandler.sendEmptyMessageDelayed(1101, 2500);
    }

    @Override
    protected void loadData() {

    }
}
