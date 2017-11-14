package com.zk.walkwayapp.model.bean;

/**
 * Created by dzs on 2017/6/12.
 */
public class UserInfo {
    private String avatorPath;
    private String nickName;
    private double userId;
    private int sex;
    //家庭住址 53.467887,-2.243137 Sir Charles Groves Hall, Booth Street West, Manchester, United Kingdom
    private String homeAddress = "Sir Charles Groves Hall, Booth Street West, Manchester, United Kingdom";
    private double homeLat = (double) 53.467887;
    private double homeLng = (double) -2.243137;
    //公司地址 The University of Manchester Oxford Road Manchester
    //53.4668498,-2.2360724
    private String companyAddress = "The University of Manchester Oxford Road Manchester";
    private double companyLat = (double) 53.4668498;
    private double  companyLng = (double) -2.2360724;
    //工作时间
    private int workStart;
    private int workEnd;

    public String getAvatorPath() {
        return avatorPath;
    }

    public void setAvatorPath(String avatorPath) {
        this.avatorPath = avatorPath;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public double getUserId() {
        return userId;
    }

    public void setUserId(double userId) {
        this.userId = userId;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public double getHomeLat() {
        return homeLat;
    }

    public void setHomeLat(double homeLat) {
        this.homeLat = homeLat;
    }

    public double getHomeLng() {
        return homeLng;
    }

    public void setHomeLng(double homeLng) {
        this.homeLng = homeLng;
    }

    public String getCompanyAddress() {
        return companyAddress;
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public double getCompanyLat() {
        return companyLat;
    }

    public void setCompanyLat(double companyLat) {
        this.companyLat = companyLat;
    }

    public double getCompanyLng() {
        return companyLng;
    }

    public void setCompanyLng(double companyLng) {
        this.companyLng = companyLng;
    }

    public int getWorkStart() {
        return workStart;
    }

    public void setWorkStart(int workStart) {
        this.workStart = workStart;
    }

    public int getWorkEnd() {
        return workEnd;
    }

    public void setWorkEnd(int workEnd) {
        this.workEnd = workEnd;
    }
}
