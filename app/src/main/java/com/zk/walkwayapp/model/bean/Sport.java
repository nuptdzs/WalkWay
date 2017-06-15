package com.zk.walkwayapp.model.bean;

import io.realm.RealmObject;

/**
 * 运动数据类
 */
public class Sport extends RealmObject{

    /**
     * 用以区分是否为日统计或者小数源数据
     */
    private int isSum;
    //用户id
    private String userId;

    //设备ID
    private String terminalId;

    //日期
    private String date;

    //小时
    private int time;

    //步行计数
    private int walkCount = 0;

    //步行距离
    private double walkDistance = 0;


    //步行时长
    private int walkTime = 0;


    //消耗卡路里
    private double calorie = 0;

    //数据是否上传
    private boolean isUpload = false;

    //数据是否解析
    private boolean isAnalysis = false;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public void setTerminalId(String terminalId) {
        this.terminalId = terminalId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getWalkCount() {
        return walkCount;
    }

    public void setWalkCount(int walkCount) {
        this.walkCount = walkCount;
    }

    public double getWalkDistance() {
        return walkDistance;
    }

    public void setWalkDistance(double walkDistance) {
        this.walkDistance = walkDistance;
    }

    public int getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(int walkTime) {
        this.walkTime = walkTime;
    }

    public double getCalorie() {
        return calorie;
    }

    public void setCalorie(double calorie) {
        this.calorie = calorie;
    }

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean isUpload) {
        this.isUpload = isUpload;
    }

    public boolean isAnalysis() {
        return isAnalysis;
    }

    public void setAnalysis(boolean isAnalysis) {
        this.isAnalysis = isAnalysis;
    }

    public int getIsSum() {
        return isSum;
    }

    public void setIsSum(int isSum) {
        this.isSum = isSum;
    }

    @Override
    public String toString() {
        return "Sport{" +
                "isSum=" + isSum +
                ", userId='" + userId + '\'' +
                ", terminalId='" + terminalId + '\'' +
                ", date='" + date + '\'' +
                ", time=" + time +
                ", walkCount=" + walkCount +
                ", walkDistance=" + walkDistance +
                ", walkTime=" + walkTime +
                ", calorie=" + calorie +
                ", isUpload=" + isUpload +
                ", isAnalysis=" + isAnalysis +
                '}';
    }
}
