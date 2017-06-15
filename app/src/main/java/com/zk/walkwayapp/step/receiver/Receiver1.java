package com.zk.walkwayapp.step.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by dzs on 2016/11/23.
 * 不需要做事 进程收护挂起
 */
public class Receiver1 extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Landi","receiver1 onReceive");
    }
}
