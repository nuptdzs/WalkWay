package com.zk.api.weather;

/**
 * the result of the API for getting WeatherInfo
 */
public class WeatherResult {
    private WeatherLocation location;
    private WeatherInfo now;
    private String last_update;

    public WeatherLocation getLocation() {
        return location;
    }

    public void setLocation(WeatherLocation location) {
        this.location = location;
    }

    public WeatherInfo getNow() {
        return now;
    }

    public void setNow(WeatherInfo now) {
        this.now = now;
    }

    public String getLast_update() {
        return last_update;
    }

    public void setLast_update(String last_update) {
        this.last_update = last_update;
    }
}
