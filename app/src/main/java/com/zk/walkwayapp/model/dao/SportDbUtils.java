package com.zk.walkwayapp.model.dao;


import android.util.Log;

import com.zk.walkwayapp.model.bean.Sport;

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
}
