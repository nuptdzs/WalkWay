package com.zk.walkwayapp.step.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.zk.walkwayapp.step.service.StepService;


/**
 * Created by dzs on 2016/11/23.
 * 网络连接启动
 */
public class ConnectionChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        final Context mContext = context;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                //计步服务不存在时，拉取计步服务
                if (!DeamonAppUtils.getInstance().isServiceWork(mContext, "com.lakala.platform.step.service.StepService")) {
//            LogUtil.printE("DBTTEST", "start StepService  ");
                    mContext.startService(new Intent(mContext, StepService.class));
                }
            }
        });

    }
}
