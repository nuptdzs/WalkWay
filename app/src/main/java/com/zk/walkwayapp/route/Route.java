package com.zk.walkwayapp.route;

import java.util.List;

/**
 * Created by dzs on 2017/8/6.
 */

public class Route {
    private String summary;
    private List<Leg> legs;
    private Fare fare;
    private Bound bounds;

    @Override
    public String toString() {
        return "Route{" +
                "summary='" + summary + '\'' +
                ", legs=" + legs +
                ", fare=" + fare +
                ", bounds=" + bounds +
                '}';
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public List<Leg> getLegs() {
        return legs;
    }

    public void setLegs(List<Leg> legs) {
        this.legs = legs;
    }

    public Fare getFare() {
        return fare;
    }

    public void setFare(Fare fare) {
        this.fare = fare;
    }

    public Bound getBounds() {
        return bounds;
    }

    public void setBounds(Bound bounds) {
        this.bounds = bounds;
    }
}
