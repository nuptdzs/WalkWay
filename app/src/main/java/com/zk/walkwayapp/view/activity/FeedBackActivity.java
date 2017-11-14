package com.zk.walkwayapp.view.activity;

import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.SendCallback;
import com.zk.library.common.mvp.BaseActivity;
import com.zk.library.common.mvp.ContentView;
import com.zk.library.common.mvp.IMvpPresenter;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.utils.ToastUtil;

import butterknife.Bind;

@ContentView(R.layout.activity_feed_back)
public class FeedBackActivity extends BaseActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.et_content)
    EditText etContent;
    @Bind(R.id.btn_feedback)
    Button btnFeedback;

    @Override
    protected IMvpPresenter getPresenter() {
        return null;
    }

    @Override
    protected void addEvent() {
        toolbar.setTitle("FeedBack");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = etContent.getText().toString();
                feedBack(msg);
            }
        });
    }

    @Override
    protected void loadData() {

    }
    private void feedBack(String msg) {
        if (!TextUtils.isEmpty(msg)){
            AVQuery pushQuery = AVInstallation.getQuery();
            String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
            pushQuery.whereEqualTo("installationId", installationId);
            AVPush avPush = new AVPush();
            avPush.setMessage("您的消息已经推送到后台");
            avPush.setPushToAndroid(true);
            avPush.setQuery(pushQuery);
            avPush.sendInBackground(new SendCallback() {
                @Override
                public void done(AVException e) {
                    if (e!=null){
                        ToastUtil.show(FeedBackActivity.this,"反馈成功");
                    }else{
                        ToastUtil.show(FeedBackActivity.this,"反馈失败");

                    }
                }
            });
        }
    }
}
