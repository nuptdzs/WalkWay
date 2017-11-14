package com.zk.walkwayapp.step;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_MULTI_PROCESS;

/**
 */
public class StepServiceFlagController {

    private static final String NAME = "STEP_SERVICE_FLAG";
    private static final int VERSION = 1;
    private static final String KEY_STEP_FLAG = "FLAG";
    private static final String KEY_STEP_SUPPORTED = "STEP_SUPPORTED";
    /**
     * 设置手机记步服务开关
     * @param context
     * @param flag
     */
    public static void setStepServiceFlag(Context context, boolean flag){
//        LklPreferences.getInstance().putBoolean(KEY_STEP_FLAG,flag);
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_STEP_FLAG,flag);
        editor.apply();
    }

    /**
     * 获取记步服务开关
     * @param context
     * @return
     */
    public static boolean getStepServiceFlag(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,MODE_MULTI_PROCESS);
        //默认关闭
        boolean l = sharedPreferences.getBoolean(KEY_STEP_FLAG,true);
        return l;
    }


    /**
     * 设置手机计步服务是否可用
     * @param context
     * @param flag
     */
    public static void setStepServiceIsSupported(Context context, boolean flag){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,MODE_MULTI_PROCESS);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(KEY_STEP_SUPPORTED,flag);
        editor.apply();
    }

    /**
     * 获取手机是否支持计步
     * @param context
     * @return
     */
    public static boolean getStepServiceIsSupported(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(NAME,MODE_MULTI_PROCESS);
        //默认关闭
        boolean l = sharedPreferences.getBoolean(KEY_STEP_SUPPORTED,false);
        return l;
//        return LklPreferences.getInstance().getBoolean(KEY_STEP_SUPPORTED,false);
    }
}
