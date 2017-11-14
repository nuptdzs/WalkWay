package com.zk.walkwayapp.route;

/**
 * Created by dzs on 2017/8/6.
 */

public class Vehicle {
    private String name;//：其中包含该线路上交通工具的名称。例如：“地铁”。
    private String type;// 包含在该线路上运行的车辆类型。有关受支持值的完整列表，请参阅交通工具类型文档
    private String icon;// 包含与该车辆类型关联的图标的 URL。
    private String local_icon;// 包含与该交通工具类型关联的图标的网址，取决于当地交通标志。

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLocal_icon() {
        return local_icon;
    }

    public void setLocal_icon(String local_icon) {
        this.local_icon = local_icon;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                ", local_icon='" + local_icon + '\'' +
                '}';
    }
}
