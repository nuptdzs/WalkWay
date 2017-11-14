package com.zk.walkwayapp.view.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.ContentView;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.contract.RegisterContract;
import com.zk.walkwayapp.presenter.login.RegisterPresenter;

import java.lang.ref.WeakReference;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.OnClick;

@ContentView(R.layout.activity_regist)
public class RegistActivity extends BaseActivity<RegisterContract.IRegisterPresenter> implements RegisterContract.IRegisterView {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.etRegistName)
    EditText etRegistName;
    @Bind(R.id.etRegistPwd)
    EditText etRegistPwd;
    @Bind(R.id.etSmsCode)
    EditText etSmsCode;
    @Bind(R.id.tvSmsCode)
    TextView tvSmsCode;
    @Bind(R.id.btRegister)
    Button btRegister;

    @Override
    protected RegisterContract.IRegisterPresenter getPresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    protected void addEvent() {
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.avoscloud_feedback_thread_actionbar_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void loadData() {

    }

    @OnClick({R.id.tvSmsCode, R.id.btRegister})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSmsCode:
                sendSmsCode();
                break;
            case R.id.btRegister:
                register();
                break;
        }
    }

    private void sendSmsCode(){
        presenter.sendSms();
    }

    @Override
    public String getPhone() {
        return etRegistName.getText().toString().trim();
    }

    @Override
    public String getPwd() {
        return etRegistPwd.getText().toString().trim();
    }

    @Override
    public String getSmsCode() {
        return etSmsCode.getText().toString().trim();
    }

    @Override
    public void register() {
        presenter.register();
    }

    @Override
    public void sendSmsSuccess() {
        tvSmsCode.setClickable(false);
        tvSmsCode.setBackgroundColor(getResources().getColor(R.color.text_color_gray));
        startCount();
    }

    @Override
    public void verfySmsSuccess() {

    }

    @Override
    public void registerSuccess() {
        finish();
    }

    Timer timer;
    CountHandler handler = new CountHandler(this);
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            count--;
            handler.sendEmptyMessage(1111);
        }
    };

    private static class CountHandler extends Handler {
        WeakReference<RegistActivity> weakReference;
        public CountHandler(RegistActivity registActivity){
            weakReference = new WeakReference<>(registActivity);
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1111:
                    int leftSeconds = weakReference.get().count;
                    if (leftSeconds>0){
                        weakReference.get().tvSmsCode.setText(leftSeconds+"s后重新发送");

                    }else {
                        weakReference.get().resetCount();
                    }
                    break;
            }
        }
    }
    int count = 60;
    void resetCount(){
        timer.cancel();
        tvSmsCode.setText("发送验证码");
        tvSmsCode.setClickable(true);
        tvSmsCode.setBackgroundColor(getResources().getColor(R.color.blue));
        count = 60;
    }
    void startCount(){
        timer = new Timer();
        timer.schedule(timerTask,0,1000);
    }
}
