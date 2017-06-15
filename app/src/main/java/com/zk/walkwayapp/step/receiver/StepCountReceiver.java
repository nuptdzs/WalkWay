package com.zk.walkwayapp.step.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.zk.walkwayapp.model.bean.Sport;
import com.zk.walkwayapp.model.dao.SportDbUtils;
import com.zk.walkwayapp.step.service.StepService;

/**
 * Created by dzs on 2016/11/24.
 */
public class StepCountReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final Intent data = intent;
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Sport sport = data.getParcelableExtra(StepService.SPORT);
                saveStep(sport);
            }
        });

    }

    /**
     * 因为跨进程操作数据库导致了数据库死锁 所以放到主进程更新步数
     */
    private void saveStep(Sport sport){
        SportDbUtils.getInstance().insert(sport);
    }
}
