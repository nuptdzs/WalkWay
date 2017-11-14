package com.zk.walkwayapp.route;

import java.util.List;

/**
 * Created by dzs on 2017/8/6.
 */

public class Leg {
    private List<Step> steps;
    private ValueText distance;
    private ValueText duration;
    private ValueText duration_in_traffic;
    private LatLng start_location;
    private LatLng end_location;
    private String start_address;
    private String end_address;

    @Override
    public String toString() {
        return "Leg{" +
                "steps=" + steps +
                ", distance=" + distance +
                ", duration=" + duration +
                ", duration_in_traffic=" + duration_in_traffic +
                ", start_location=" + start_location +
                ", end_location=" + end_location +
                ", start_address='" + start_address + '\'' +
                ", end_address='" + end_address + '\'' +
                '}';
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public ValueText getDistance() {
        return distance;
    }

    public void setDistance(ValueText distance) {
        this.distance = distance;
    }

    public ValueText getDuration() {
        return duration;
    }

    public void setDuration(ValueText duration) {
        this.duration = duration;
    }

    public ValueText getDuration_in_traffic() {
        return duration_in_traffic;
    }

    public void setDuration_in_traffic(ValueText duration_in_traffic) {
        this.duration_in_traffic = duration_in_traffic;
    }

    public LatLng getStart_location() {
        return start_location;
    }

    public void setStart_location(LatLng start_location) {
        this.start_location = start_location;
    }

    public LatLng getEnd_location() {
        return end_location;
    }

    public void setEnd_location(LatLng end_location) {
        this.end_location = end_location;
    }

    public String getStart_address() {
        return start_address;
    }

    public void setStart_address(String start_address) {
        this.start_address = start_address;
    }

    public String getEnd_address() {
        return end_address;
    }

    public void setEnd_address(String end_address) {
        this.end_address = end_address;
    }
}
