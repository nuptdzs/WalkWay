package com.zk.walkwayapp;

import android.app.Application;
import android.content.Intent;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;
import com.zk.walkwayapp.step.service.StepService;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dzs on 2017/6/9.
 */
public class WalkWayApplication extends Application {

    private static final String AVOS_APPID = "6cNpzgy0zw5b8JYFM1KiWaz2-gzGzoHsz";
    private static final String AOVS_APPKEY = "Sf8yKAGruTNKuJpEKo8cRSSW";
    @Override
    public void onCreate() {
        super.onCreate();
        AVOSCloud.setLogLevel(2);
        AVOSCloud.initialize(getApplicationContext(),AVOS_APPID,AOVS_APPKEY);
        startService(new Intent(getApplicationContext(), StepService.class));
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("WalkWay.realm").build();
        Realm.setDefaultConfiguration(config);
        Stetho.initialize(//Stetho初始化
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build()
        );
    }
}
