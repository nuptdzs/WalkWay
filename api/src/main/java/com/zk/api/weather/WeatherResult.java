package com.zk.api.weather;

/**
 * the result of the API for getting WeatherInfo
 */
public class WeatherResult {
    private WeatherLocation basic;
    private WeatherInfo now;
    private String status;

    public WeatherLocation getBasic() {
        return basic;
    }

    public void setBasic(WeatherLocation basic) {
        this.basic = basic;
    }

    public WeatherInfo getNow() {
        return now;
    }

    public void setNow(WeatherInfo now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "WeatherResult{" +
                "basic=" + basic +
                ", now=" + now +
                ", status='" + status + '\'' +
                '}';
    }
}
