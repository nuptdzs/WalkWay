package com.zk.walkwayapp.route;

/**
 * Created by dzs on 2017/8/6.
 */

public class TransitAgency {
    private String name;//：其中包含公交机构的名称
    private String url;//：其中包含公交机构的 URL
    private String phone;// 包含公共交通运营机构的电话号码

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
