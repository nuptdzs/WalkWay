package com.zk.walkwayapp.route;

/**
 * Created by dzs on 2017/8/6.
 */
public class NameLocation {
    private String Name;
    private LatLng location;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
}
