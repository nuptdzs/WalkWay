package com.zk.walkwayapp.route;


/**
 * Created by dzs on 2017/8/6.
 */

public class Bound {
    private LatLng northeast;
    private LatLng southwest;

    public LatLng getNortheast() {
        return northeast;
    }

    public void setNortheast(LatLng northeast) {
        this.northeast = northeast;
    }

    public LatLng getSouthwest() {
        return southwest;
    }

    public void setSouthwest(LatLng southwest) {
        this.southwest = southwest;
    }
}
