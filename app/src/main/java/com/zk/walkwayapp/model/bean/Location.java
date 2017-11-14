package com.zk.walkwayapp.model.bean;

import android.support.v4.util.Pair;

/**
 * Created by dzs on 2017/6/20.
 */

public class Location {
    private String address;
    private Pair<Long,Long> latlng;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Pair<Long, Long> getLatlng() {
        return latlng;
    }

    public void setLatlng(Pair<Long, Long> latlng) {
        this.latlng = latlng;
    }
}
