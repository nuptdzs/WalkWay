package com.zk.walkwayapp;

import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVPush;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SendCallback;
import com.zk.api.weather.WeatherInfo;
import com.zk.walkwayapp.model.bean.PushModel;
import com.zk.walkwayapp.model.dao.PushMsgDbUtils;
import com.zk.walkwayapp.step.service.StepService;
import com.zk.walkwayapp.view.activity.MessageActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by dzs on 2017/69.
 */
public class WalkWayApplication extends MultiDexApplication {
    //控制开关 测试数据插入
    public static final boolean TEST = true;

    public static WalkWayApplication application;
    public static final String PLACES_API_KEY = "AIzaSyAKcHQUqbaZfC_IxLrzoW1MNvOF4NFo0Bk";

    private WeatherInfo weatherInfo;

    private static final String AVOS_APPID = "6cNpzgy0zw5b8JYFM1KiWaz2-gzGzoHsz";
    private static final String AOVS_APPKEY = "Sf8yKAGruTNKuJpEKo8cRSSW";

    private static final String USA_APPID = "9I8VycHDD1aqPTWIX6lksKww-MdYXbMMI";
    private static final String USA_APPKEY = "76OG9fc7pMylocqEsl2eWP6m";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApp();
        application = this;
        EventBus.getDefault().register(this);
    }

    PushModel lastpushModel;
    @Subscribe
    public void onEvent(PushModel pushModel){
        if (lastpushModel == null){
            PushMsgDbUtils.insert(pushModel);

        }else {
            if (!lastpushModel.equals(pushModel)){
                PushMsgDbUtils.insert(pushModel);
            }
        }
        lastpushModel = pushModel;
    }


    /**
     * 初始化应用参数
     */
    private void initApp() {
        AVOSCloud.setLogLevel(2);
        AVOSCloud.useAVCloudUS();
        AVOSCloud.initialize(getApplicationContext(),USA_APPID,USA_APPKEY);
//        AVInstallation.getCurrentInstallation().saveInBackground();
        PushService.setDefaultPushCallback(this, MessageActivity.class);
        startService(new Intent(getApplicationContext(), StepService.class));
        Realm.init(this);
//        try {
//            MapsInitializer.initialize(this);
//        } catch (RemoteException e) {
//            e.printStackTrace();
//        }
//        MapsInitializer.loadWorldGridMap(true);
        RealmConfiguration config = new RealmConfiguration.Builder().name("WalkWay.realm").build();
        Realm.setDefaultConfiguration(config);
        lastpushModel = PushMsgDbUtils.findLast();
    }

    public WeatherInfo getWeatherInfo(){
        return weatherInfo;
    }

    public void setWeatherInfo(WeatherInfo weatherInfo){
        AVPush.sendMessageInBackground("the weather is good", AVInstallation.getQuery());
        AVPush push = new AVPush();
        JSONObject object = new JSONObject();
        try {
            object.put("alert", "the weather is good");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        push.setPushToAndroid(true);
        push.setData(object);
        push.sendInBackground(new SendCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    // push successfully.
                    Log.e("weather","推送成功");
                } else {
                    // something wrong.
                    Log.e("weather","推送失败");

                }
            }
        });
        this.weatherInfo = weatherInfo;
        if (Integer.parseInt(getWeatherInfo().getCond().getCode())>204){
            //天气恶劣 不适合运动
            PushModel pushModel = new PushModel();
            pushModel.setMsg("Today's weather is "+getWeatherInfo().getCond().getTxt()+" stay indoor");
            pushModel.setType(1);
            pushModel.setDate(new Date());
            EventBus.getDefault().post(pushModel);
        }else {
            PushModel pushModel = new PushModel();
            pushModel.setMsg("Today's weather is "+getWeatherInfo().getCond().getTxt()+" let's go for a walk");
            pushModel.setType(0);
            pushModel.setDate(new Date());
            EventBus.getDefault().post(pushModel);
        }
    }
}
