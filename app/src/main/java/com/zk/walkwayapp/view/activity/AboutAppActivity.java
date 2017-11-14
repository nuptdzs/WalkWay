package com.zk.walkwayapp.view.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zk.walkwayapp.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AboutAppActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.aboutappimage)
    ImageView aboutappimage;
    @Bind(R.id.aboutappversion)
    TextView aboutappversion;
    @Bind(R.id.aboutbunleversion)
    TextView aboutbunleversion;
    @Bind(R.id.aboutappservicenum)
    TextView aboutappservicenum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);
        ButterKnife.bind(this);
        toolbar.setTitle("AboutApp");
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        PackageManager pm = getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String versionName = pi.versionName;
        int versioncode = pi.versionCode;
        aboutappversion.setText("WalkWayApp " +versionName);
        String bundleVersion = String.format("（ BuVer：%s ）", versioncode+"");
        ((TextView) findViewById(R.id.aboutbunleversion)).setText(bundleVersion);
    }
}
