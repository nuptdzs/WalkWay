package com.zk.walkwayapp.step.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.avos.avoscloud.AVUser;
import com.zk.walkwayapp.model.bean.PushModel;
import com.zk.walkwayapp.model.bean.Sport;
import com.zk.walkwayapp.model.bean.UserTag;
import com.zk.walkwayapp.model.dao.SportDbUtils;
import com.zk.walkwayapp.step.service.StepService;

import org.greenrobot.eventbus.EventBus;

import java.util.Date;

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
                if (sport.getWalkCount()> AVUser.getCurrentUser().getInt(UserTag.STEP_GOAL)){
                    //已达目标
                    PushModel pushModel = new PushModel();
                    pushModel.setMsg("congratulation you have archived your goal!");
                    pushModel.setType(3);
                    pushModel.setDate(new Date());
                    EventBus.getDefault().post(pushModel);
                }

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
