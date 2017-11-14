package com.zk.walkwayapp.model.dao;


import android.util.Log;

import com.avos.avoscloud.AVDeviceUtils;
import com.avos.avoscloud.AVUser;
import com.zk.walkwayapp.WalkWayApplication;
import com.zk.walkwayapp.model.bean.Sport;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by dzs on 2016/11/30.
 */
public class SportDbUtils{



    private static class InstanceHolder {

        public static final SportDbUtils INSTANCE = new SportDbUtils();
    }

    private SportDbUtils() {
    }

    public static SportDbUtils getInstance() {

        return SportDbUtils.InstanceHolder.INSTANCE;
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


    public void testData(String date){
        if (!WalkWayApplication.TEST){
            return;
        }
        Sport total = new Sport();
        total.setDate(date);
        total.setTime(-1);
        total.setIsSum(1);
        total.setUserId(getUserId());
        for (int i =6;i<22;i++){
            Sport sport = new Sport();
            sport.setUserId(getUserId());
            sport.setDate(date);
            sport.setWalkTime(200);
            total.setWalkTime(total.getTime()+200);
            sport.setTime(i);
            sport.setWalkCount((int) (Math.random()*1000));
            total.setWalkCount(total.getWalkCount()+sport.getWalkCount());
            sport.setCalorie(getCalorie(sport.getWalkCount(),0));
            total.setCalorie(total.getCalorie()+sport.getCalorie());
            sport.setWalkDistance(getDistance(sport.getWalkCount()));
            total.setWalkDistance(total.getWalkDistance()+sport.getWalkDistance());
            insert(sport);
        }
        insert(total);
    }

    public synchronized List<Sport> querySportDetailsByDay(String userId,String date){
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sport> results;
        results = realm.where(Sport.class).equalTo("userId",userId)
                .equalTo("date",date)
                .notEqualTo("time",-1).findAll();
        List<Sport> sports =  realm.copyFromRealm(results);
        return sports;
    }
    /**
     * @param userId 用户id
     * @param date 日期
     * @param time 时间 如果传-1则表示为日数据表 否则为小时数据表
     * @return
     */
    public synchronized List<Sport> querySportData(String userId, String date, int time) {

        Realm realm = Realm.getDefaultInstance();
        RealmResults<Sport> sport;
        sport = realm.where(Sport.class).
                    equalTo("userId",userId).
                    equalTo("date",date).
                    equalTo("time",time).
                    findAll();
        List<Sport> sports = realm.copyFromRealm(sport);
        if (!date.equals(getTodayDate())){
            if (sports==null||sports.size()==0||sports.get(0).getWalkCount()==0){
                testData(date);
            }
        }

//        if (!date.equals(getTodayDate())){
//            if (sports==null||sports.size()==0||sports.get(0).getWalkCount()==0){
//                testData(date);
//            }
//        }
        return sports;
    }

    /**
     * 更新运动数据
     * @param data
     */
    public synchronized void insert(Sport data) {
        Log.e("DB","SAVE"+data.toString());
        Realm realm = Realm.getDefaultInstance();
        Sport sport = realm.where(Sport.class).
                equalTo("userId",data.getUserId()).
                equalTo("date",data.getDate()).
                equalTo("time",data.getTime()).
                findFirst();
        if(sport!=null){
            realm.beginTransaction();
            sport.setUserId(data.getUserId());
            sport.setDate(data.getDate());
            sport.setTime(data.getTime());
            sport.setWalkDistance(data.getWalkDistance());
            sport.setCalorie(data.getCalorie());
            sport.setWalkCount(data.getWalkCount());
            sport.setIsSum(data.getIsSum());
            sport.setWalkTime(data.getWalkTime());
            realm.commitTransaction();
        }else {
            realm.beginTransaction();
            realm.copyToRealm(data);
            realm.commitTransaction();
        }

//        String tableName = TABLE_DAY;
//        if(data.getTime()>0){
//            tableName = TABLE_ORIGINAL;
//        }
//        HealthSportDao.getInstance().insertSportData(tableName,data);
    }

    public String queryMinDate(String userId) {
        return "2017-01-01";
    }

}
