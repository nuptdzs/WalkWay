package com.zk.walkwayapp.route;

import java.util.List;

/**
 * Created by dzs on 2017/8/6.
 */

public class Line {
    private String name;// 包含该公共交通线路的全名，例如：“第 7 大道快线”。
    private String short_name;// 包含该公共交通线路的简称。这通常是线路编号，如“M7”或“355”。
    private String color;// 包含该公共交通线路标牌中常用的颜色。颜色以十六进制字符串形式指定，例如：#FF0033
    private List<TransitAgency> agencies;// 包含一个 TransitAgency 对象数组，其中的每个对象都提供有关线路运营商的信息，包括下列属性：
    private String url;// 包含由公共交通运营机构提供的该公共交通线路的 URL。
    private String icon;// 包含与该线路关联的图标的 URL。
    private String text_color;//：其中包含该线路站牌上常用的文字颜色。颜色以十六进制字符串形式指定
    private List<Vehicle> vehicle;// 包含该线路使用的车辆类型。这可能包括下列属性：

    @Override
    public String toString() {
        return "Line{" +
                "name='" + name + '\'' +
                ", short_name='" + short_name + '\'' +
                ", color='" + color + '\'' +
                ", agencies=" + agencies +
                ", url='" + url + '\'' +
                ", icon='" + icon + '\'' +
                ", text_color='" + text_color + '\'' +
                ", vehicle=" + vehicle +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public List<TransitAgency> getAgencies() {
        return agencies;
    }

    public void setAgencies(List<TransitAgency> agencies) {
        this.agencies = agencies;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getText_color() {
        return text_color;
    }

    public void setText_color(String text_color) {
        this.text_color = text_color;
    }

    public List<Vehicle> getVehicle() {
        return vehicle;
    }

    public void setVehicle(List<Vehicle> vehicle) {
        this.vehicle = vehicle;
    }
}
