package com.zk.walkwayapp.step.sensor;

import android.content.Context;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by dzs on 2016/11/23.
 */
public abstract class StepMode implements SensorEventListener {
    Context context;
    StepCountListener stepCountListener;
    SensorManager sensorManager;
    /**
     * 当前步数
     */
    public static int CURRENT_STEP = 0;
    /**
     * 走路时间
     */
    public static int WALK_TIME = 0;
    public static int CURRENT_STEP_HOUR = 0;
    public static int WALK_TIME_HOUR = 0;
    boolean isAvailable = false;

    StepMode(Context context, StepCountListener stepCountListener) {
        this.context = context;
        this.stepCountListener = stepCountListener;
    }

    public boolean getStep() {
        prepareSensorManager();
        registerSensor();
        return isAvailable;
    }

    protected abstract void registerSensor();

    private void prepareSensorManager() {
        if (sensorManager != null) {
            sensorManager.unregisterListener(this);
            sensorManager = null;
        }
        sensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
    }

    public interface StepCountListener{
        void onStepCount(int step);
    }

}
