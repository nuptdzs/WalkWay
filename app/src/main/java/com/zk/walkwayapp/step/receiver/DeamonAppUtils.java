package com.zk.walkwayapp.step.receiver;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by seazhang on 17/1/20.
 */

public class DeamonAppUtils {

    private static class InstanceHolder {

        public static final DeamonAppUtils INSTANCE = new DeamonAppUtils();
    }

    private DeamonAppUtils() {
    }

    public static DeamonAppUtils getInstance() {

        return DeamonAppUtils.InstanceHolder.INSTANCE;
    }

    private boolean isLoadSQLLib = false;
    private boolean isAppConfigInited = false;

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param mContext
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    public boolean isServiceWork(Context mContext, String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(40);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    public boolean isLoadSQLLib() {
        return isLoadSQLLib;
    }

    public void setLoadSQLLib(boolean loadSQLLib) {
        isLoadSQLLib = loadSQLLib;
    }

    public boolean isAppConfigInited() {
        return isAppConfigInited;
    }

    public void setAppConfigInited(boolean appConfigInited) {
        isAppConfigInited = appConfigInited;
    }
}
