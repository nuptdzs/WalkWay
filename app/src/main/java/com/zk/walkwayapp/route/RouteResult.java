package com.zk.walkwayapp.route;

import java.util.List;

/**
 * Created by dzs on 2017/8/6.
 */

public class RouteResult {
    private String status;
    private List<WayPoint> geocoded_waypoints;
    private List<Route> routes;
    private List<AvailableTravelMode> available_travel_modes;

    @Override
    public String toString() {
        return "RouteResult{" +
                "status='" + status + '\'' +
                ", geocoded_waypoints=" + geocoded_waypoints +
                ", routes=" + routes +
                ", available_travel_modes=" + available_travel_modes +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<WayPoint> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<WayPoint> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }

    public List<AvailableTravelMode> getAvailable_travel_modes() {
        return available_travel_modes;
    }

    public void setAvailable_travel_modes(List<AvailableTravelMode> available_travel_modes) {
        this.available_travel_modes = available_travel_modes;
    }
}
