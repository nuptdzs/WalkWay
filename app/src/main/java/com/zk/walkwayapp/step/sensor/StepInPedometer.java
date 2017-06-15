package com.zk.walkwayapp.step.sensor;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.util.Log;

import com.zk.library.common.utils.PerferenceUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * 4.4以上部分机型支持计步传感器
 */
public class StepInPedometer extends StepMode {
    private final String HOUR_STEP = "hour_step";
    private final String TODAY_STEP = "today_step";
    private final String TAG = "StepInPedometer";
    private int sensorMode = 0;
    private long lastTime = 0;
    /**
     * 当使用count计步的时候 记录本日最初的步数 用当前步数相减获得本日步数
     */
    private int todayStep = 0;
    private int hourStep = 0;

    public StepInPedometer(Context context, StepCountListener stepCountListener) {
        super(context, stepCountListener);
    }

    @Override
    protected void registerSensor() {
        addCountStepListener();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int liveStep = (int) event.values[0];

        if(sensorMode == 0){
            StepMode.CURRENT_STEP += liveStep;
            StepMode.CURRENT_STEP_HOUR += liveStep;
        }else if(sensorMode == 1){
            long currentTime = System.currentTimeMillis();
            Date now = new Date(currentTime);
            long firstTimeToday = PerferenceUtils.getInstance(context).getLongValue("first_time_in_today");
            long firstHourToday = PerferenceUtils.getInstance(context).getLongValue("first_hour_in_today");
            Date first = new Date(firstTimeToday);
            Calendar nowC = Calendar.getInstance();
            nowC.setTime(now);
            Calendar firstC = Calendar.getInstance();
            firstC.setTime(first);
            Calendar firstH = Calendar.getInstance();
            firstH.setTime(new Date(firstHourToday));
            if(nowC.get(Calendar.DAY_OF_MONTH)!=firstC.get(Calendar.DAY_OF_MONTH)){
                //如果日期刷新 步数记录更新
                PerferenceUtils.getInstance(context).setIntValue(TODAY_STEP, liveStep);
                PerferenceUtils.getInstance(context).setLongValue("first_time_in_today",currentTime);
                todayStep = liveStep;
            }else {
                todayStep = PerferenceUtils.getInstance(context).getIntValue(TODAY_STEP);
            }
            if(nowC.get(Calendar.HOUR_OF_DAY)!=firstH.get(Calendar.HOUR_OF_DAY)){
                //如果小时刷新 步数记录 更新
                PerferenceUtils.getInstance(context).setIntValue(HOUR_STEP, liveStep);
                PerferenceUtils.getInstance(context).setLongValue("first_hour_in_today",currentTime);
                hourStep = liveStep;
            }else {
                hourStep = PerferenceUtils.getInstance(context).getIntValue(HOUR_STEP);
            }
            if(currentTime>lastTime&&currentTime - lastTime<10000&&StepMode.CURRENT_STEP<(liveStep-todayStep)){
                StepMode.WALK_TIME +=currentTime-lastTime;
                StepMode.WALK_TIME_HOUR += currentTime - lastTime;
                Log.e(TAG,"Active time is:"+StepMode.WALK_TIME +" hour:"+StepMode.WALK_TIME_HOUR);
            }
            lastTime = currentTime;
            StepMode.CURRENT_STEP_HOUR = liveStep - hourStep;
            StepMode.CURRENT_STEP = liveStep - todayStep;
        }
        Log.d(TAG,"STEP_TODAY is "+CURRENT_STEP+"\nSTEP_CURRENT_HOUR IS:"+CURRENT_STEP_HOUR);
        stepCountListener.onStepCount(StepMode.CURRENT_STEP);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void addCountStepListener() {
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        Sensor countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (countSensor != null) {
            Log.d("step","sensor is countSensor");
            isAvailable = true;
            sensorManager.registerListener(this, countSensor, SensorManager.SENSOR_DELAY_UI);
            sensorMode = 1;
        } else if (detectorSensor != null) {
            Log.d("step","sensor is detectorSensor");
            sensorManager.registerListener(this, detectorSensor, SensorManager.SENSOR_DELAY_UI);
            isAvailable = true;
            sensorMode = 0;
        } else  {
            isAvailable = false;
            Log.v(TAG, "Count sensor not available!");
        }
    }
}
