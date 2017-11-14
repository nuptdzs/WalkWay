package com.zk.walkwayapp.step.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.avos.avoscloud.AVDeviceUtils;
import com.avos.avoscloud.AVUser;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.model.bean.Sport;
import com.zk.walkwayapp.model.dao.SportDbUtils;
import com.zk.walkwayapp.step.StepServiceFlagController;
import com.zk.walkwayapp.step.sensor.StepInAcceleration;
import com.zk.walkwayapp.step.sensor.StepInPedometer;
import com.zk.walkwayapp.step.sensor.StepMode;
import com.zk.walkwayapp.view.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 后台计步器启动的service 需要设置长存状态 计步器通过该service发出相关信息
 * @author dzs
 * @version 1.0
 */
public class StepService extends Service implements StepMode.StepCountListener {
    private final String TAG = "StepService";
    /**其它组件主动拉取步数的Messenger**/
    private Messenger messenger = new Messenger(new MessenerHandler());
    /**对于系统广播的监听 主要做保存和存储时间间隔的调整*/
    private BroadcastReceiver mBatInfoReceiver;
    /**当天的日期*/
    private String CURRENTDATE = "";
    /**当前的日期小时**/
    private int CURRENTDATEHOUR = 0;
    /**计步触发的Action 广播接收后做处理**/
    public static String ACTION_STEP_COUNT = "com.landicorp.action.step.count";
    /**设备不支持计步的广播ACTION**/
    public static String ACTION_SENSOR_NOT_SUPPORTED = "com.landicorp.action.sensor.notsupported";
    /****/
    public final static String STEP_COUNT = "step_count";
    public final static String SPORT = "sport";
    /**默认为30秒一存储*/
    private long saveSeconds = 30000;

    @Override
    public void onCreate() {
        super.onCreate();
        //后面替换为对应的数据库操作
        dbUtils = SportDbUtils.getInstance();
        //注册广播
        initBroadcastReceiver();
        //开启记步
        startStep();
        //开启计时器
        startTimeCount();
    }

    /**
     * 开启记步,这里根据需求 因为需要分时计算 所以采用加速度传感器
     */
    private void startStep(){
        StepMode mode;
        mode = new StepInPedometer(this,this);
        boolean isSupportPedometer = mode.getStep();
        if(isSupportPedometer){
            Log.e("step","PEDOMETER IS AVAILABLE");
        }else {
            Log.e("step","StepInAcceleration IS AVAILABLE");
            mode = new StepInAcceleration(this, this);
            boolean isAvailable = mode.getStep();
            if (isAvailable){
                StepServiceFlagController.setStepServiceIsSupported(this,true);
            }else{
                StepServiceFlagController.setStepServiceIsSupported(this,false);
                sendBroadcast(new Intent(ACTION_SENSOR_NOT_SUPPORTED));
                return;
            }
        }

        //初始化当天数据
        initTodayData();
        //初始化当小时数据
        initHourData();
        onStepCount(StepMode.CURRENT_STEP);
    }

    /**
     * 步数记录回调
     * @param stepCount
     */
    @Override
    public void onStepCount(int stepCount) {
        hasStepChanaged = true;
        Log.e("step","step count is "+stepCount);
        updateNotification("Today's Step is " + stepCount);
    }

    private static final int MSG_FROM_CLIENT = 90;
    private static final int MSG_FROM_SERVER = 91;
    private static class MessenerHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_FROM_CLIENT:
                    try {
                        Messenger messenger = msg.replyTo;
                        Message replyMsg = Message.obtain(null, MSG_FROM_SERVER);
                        Bundle bundle = new Bundle();
                        bundle.putInt("step", StepMode.CURRENT_STEP);
                        replyMsg.setData(bundle);
                        messenger.send(replyMsg);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    private void initBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //日期修改
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        //关机广播
        filter.addAction(Intent.ACTION_SHUTDOWN);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();

                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    saveSeconds = 30000;
                    save();
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    saveSeconds = 60000;
                    save();
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    saveSeconds = 30000;
                    save();
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    save();
                } else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
                    save();
                } else if (Intent.ACTION_DATE_CHANGED.equals(intent.getAction())) {
                    save();
                    //隔日清零
                    initTodayData();
                    initHourData();
                    onStepCount(StepMode.CURRENT_STEP);
                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return messenger.getBinder();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_STICKY;
    }

    //数据库存储工具的配置
    private SportDbUtils dbUtils;
    /**
     * 初始化当天数据
     */
    private void initTodayData(){
        CURRENTDATE = getTodayDate();
        if(dbUtils == null){
            return;
        }
        //获取当天的数据，用于展示
        List<Sport> list = dbUtils.querySportData(getUserId(),CURRENTDATE, -1);
        if (list == null||list.size() == 0 || list.isEmpty()) {
            StepMode.CURRENT_STEP = 0;
            StepMode.WALK_TIME = 0;
        } else if (list.size() == 1) {
            StepMode.CURRENT_STEP = list.get(0).getWalkCount();
            StepMode.WALK_TIME = list.get(0).getWalkTime();
            if(StepMode.WALK_TIME<0){
                StepMode.WALK_TIME = 0;
            }
        }
    }

    /**
     * 初始化小时记录
     */
    private void initHourData(){
        CURRENTDATEHOUR = getTimeHour();
        if(dbUtils == null){
            return;
        }
        List<Sport> list = dbUtils.querySportData(getUserId(),CURRENTDATE,CURRENTDATEHOUR);
        if (list == null||list.size() == 0 || list.isEmpty()) {
            StepMode.CURRENT_STEP_HOUR = 0;
            StepMode.WALK_TIME_HOUR = 0;
        } else if (list.size() == 1) {
            StepMode.CURRENT_STEP_HOUR = list.get(0).getWalkCount();
            StepMode.WALK_TIME_HOUR = list.get(0).getWalkTime();
            if(StepMode.WALK_TIME_HOUR<0){
                StepMode.WALK_TIME_HOUR = 0;
            }
        } else {
        }
    }
    /**
     * 是否有步数的变化
     */
    private boolean hasStepChanaged = false;
    private TimeCount time;
    /**
     * 开启步数的保存计时器
     */
    private void startTimeCount() {
        time = new TimeCount(saveSeconds, 1000);
        time.start();
    }
    /**
     * 如果有步数更新则启动30s依次的保存
     */
    private class TimeCount extends CountDownTimer {
        TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }
        @Override
        public void onFinish() {
            // 如果计时器正常结束，则开始计步
            time.cancel();
            save();
            startTimeCount();
        }
        @Override
        public void onTick(long millisUntilFinished) {
        }
    }

    /**
     * 当天总步数记录的保存
     */
    private void save() {
        //如果用户未打开就不进行保存
        if(!getServiceFlag()){
            return;
        }
        //如果未发生步数变化也不进行保存
        if(!hasStepChanaged){
            return;
        }

        int tempStep = StepMode.CURRENT_STEP;
        int tempTime = StepMode.WALK_TIME;
        if(!CURRENTDATE.equals(getTodayDate())){
            Sport data = formatData4Save(true,tempStep,tempTime);
            dbUtils.insert(data);
            StepMode.CURRENT_STEP = 0;
            StepMode.WALK_TIME = 0;
            CURRENTDATE = getTodayDate();
        }
        tempStep = StepMode.CURRENT_STEP;
        tempTime = StepMode.WALK_TIME;
        Sport data = formatData4Save(true,tempStep,tempTime);
        dbUtils.insert(data);
        saveHour();
        Log.e(TAG,"SAVE step:"+tempStep+"time:"+tempTime);
        hasStepChanaged = false;
    }

    /**
     * @return 服务是否可用
     */
    private boolean getServiceFlag(){
        return StepServiceFlagController.getStepServiceFlag(this.getApplicationContext());
    }

    /**
     * 每个小时的数据保存
     */
    private void saveHour(){
        int tempStep = StepMode.CURRENT_STEP_HOUR;
        //本小时步行时间
        int walkTime = StepMode.WALK_TIME_HOUR;
        //如果当前小时数改变，则需要更改步数记录，先保存上个小时的状态。
        if(CURRENTDATEHOUR!=getTimeHour()||!CURRENTDATE.equals(getTodayDate())){
            List<Sport> list = dbUtils.querySportData(getUserId(),CURRENTDATE, CURRENTDATEHOUR);
            if (list == null||list.size() == 0 || list.isEmpty()){
                Sport data = formatData4Save(false,tempStep,walkTime);
                sendSaveBroadcast(data);
            } else if (list.size() == 1){
                sendSaveBroadcast(formatData4Save(false,tempStep,walkTime));
            }
            StepMode.CURRENT_STEP_HOUR = 0;
            StepMode.WALK_TIME_HOUR = 0;
            CURRENTDATEHOUR = getTimeHour();
            CURRENTDATE = getTodayDate();
        }
        tempStep = StepMode.CURRENT_STEP_HOUR;
        walkTime = StepMode.WALK_TIME_HOUR;
        //根据当前小时数查询出该小时步数记录
        List<Sport> list = dbUtils.querySportData(getUserId(),CURRENTDATE, CURRENTDATEHOUR);
        if (list == null||list.size() == 0 || list.isEmpty()){
            Sport data = formatData4Save(false,tempStep,walkTime);
            sendSaveBroadcast(data);
        } else if (list.size() == 1){
            sendSaveBroadcast(formatData4Save(false,tempStep,walkTime));
        }
    }

    /**
     * 发送保存步数的广播
     * @param sport
     */
    private void sendSaveBroadcast(Sport sport){
        SportDbUtils.getInstance().insert(sport);
    }

    /**
     * 构造用于保存的记步数据
     * @param stepCount 步数
     * @param walkTime 走路时间
     * @return
     */
    private Sport formatData4Save(boolean isSum,int stepCount, int walkTime){
        Sport data = new Sport();
        data.setUserId(getUserId());
        data.setTerminalId(getTerminalId());
        data.setWalkCount(stepCount);
        //由毫秒转换为分
        data.setWalkTime(walkTime);
        data.setDate(CURRENTDATE);
        //保存时刷新同步标示为，数据更新后需要同步至服务器
        data.setUpload(false);
        data.setIsSum(isSum?1:0);
        if(!isSum){
            data.setTime(CURRENTDATEHOUR);
        }else {
            data.setTime(-1);
        }
        data.setWalkDistance(getDistance(stepCount));
        data.setCalorie(getCalorie(0,stepCount));
        return data;
    }

    /**
     * 根据步数计算运动距离
     * @param stepCount
     * @return
     */
    private double getDistance(int stepCount) {
        double distance = 0;
        AVUser user = AVUser.getCurrentUser();
        if (user == null){
            user = new AVUser();
        }
        double height  = user.getDouble("height");
        double weight  = user.getDouble("weight");
        int sex = user.getInt("sex");
        distance = getDistance(sex,stepCount,height);
        return distance;
    }

    /**
     * 根据性别身高计算运动距离
     * @param sex
     * @param stepCount
     * @param height
     * 男 0 女1
     * @return 长度 单位M
     */
    public double getDistance(int sex,int stepCount,double height){
        if(height==0){
            height = 170;
        }
        double distance;
        if(sex==0){
            distance = stepCount*height*0.45/100;
        }else {
            distance = stepCount*height*0.45*0.9/100;
        }
        return distance;
    }

    /**
     * 获得卡路里
     * @param runCount
     * @param walkCount
     * @return
     */
    public int getCalorie(int runCount,int walkCount){
        AVUser user = AVUser.getCurrentUser();
        if (user == null){
            return 0;
        }
        int height  = user.getInt("height");
        int weight  = user.getInt("weight");
        int sex = user.getInt("sex");
        if (height==0)height=170;
        if (weight==0)weight=60;
        return (int) getCalorie(sex,height,weight,0,runCount,walkCount);
//        int sex     = 0;
//        double height  = 0;
//        double weight  = 0;
//        User user = getUser();
//        if(user == null){
//            user = new User();
//        }
//        String weightStr = user.getWeight().contains("kg") ? user.getWeight().substring(0,user.getWeight().length()-2): user.getWeight();
//        String heightStr = user.getHeight().contains("cm") ? user.getHeight().substring(0,user.getHeight().length()-2) : user.getHeight();
//        if (StringUtil.isNotEmpty(weightStr)){
//            try {
//                weight = Double.parseDouble(weightStr);
//                height = Double.parseDouble(heightStr);
//                sex    = Integer.parseInt(user.getSex());
//            }catch (Exception e){
//                LogUtil.print(e.getMessage());
//            }
//        }
//
//        calorie = DeviceManger.getInstance().getCalorie(sex,(int)height,(int)weight,20,runCount,walkCount);
//        return calorie;
    }

    private long getCalorie(int sex, int height, int weight, int age, int runsteps, int walksteps) {
        long calorie;
        if(sex ==0) {
            calorie = (long)((double)((long)walksteps * 40L * (long)weight * (long)height / 170L / 60L) + (double)runsteps * 1.3D * 40.0D * (double)weight * (double)height / 170.0D / 60.0D);
        } else {
            calorie = (long)(((double)((long)walksteps * 40L * (long)weight * (long)height / 160L / 60L) + (double)runsteps * 1.3D * 40.0D * (double)weight * (double)height / 160.0D / 60.0D) * 0.9D);
        }

        return calorie;
    }

    private AVUser getUser() {
        return AVUser.getCurrentUser();
    }
    private String getUserId(){
        AVUser user = getUser();
        if(user == null){
            return "";
        }else {
            return user.getObjectId();
        }
    }

    private String getTerminalId(){
        return AVDeviceUtils.getAndroidId();
    }
    /**
     * 界面退出时保存一下
     * @param conn
     */
    @Override
    public void unbindService(ServiceConnection conn) {
        save();
        super.unbindService(conn);
    }
    @Override
    public boolean onUnbind(Intent intent){
        save();
        return super.onUnbind(intent);
    }
    @Override
    public void onDestroy() {
        //取消前台进程
        stopForeground(true);
        unregisterReceiver(mBatInfoReceiver);
        sendBroadcast(new Intent());
        Intent intent = new Intent(this, StepService.class);
        startService(intent);
        super.onDestroy();
    }

    private NotificationManager nm;
    private NotificationCompat.Builder builder;
    /**
     * update notification
     */
    private void updateNotification(String content) {
        builder = new NotificationCompat.Builder(this);
        builder.setPriority(Notification.PRIORITY_MIN);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), 0);
        builder.setContentIntent(contentIntent);
        builder.setSmallIcon(R.mipmap.plusbutton_gps_selected);
        builder.setTicker("WalkWay");
        builder.setContentTitle("WalkWay");
        //设置不可清除
        builder.setOngoing(true);
        builder.setContentText(content);
        Notification notification = builder.build();
        startForeground(0, notification);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        nm.notify(R.string.app_name, notification);
    }

    /**
     * 获取当前日期
     * @return
     */
    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    /**
     * 获取当前小时
     * @return
     */
    public  int getTimeHour(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        return calendar.get(Calendar.HOUR_OF_DAY);
    }
}
