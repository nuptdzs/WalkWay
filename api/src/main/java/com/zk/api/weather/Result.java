package com.zk.api.weather;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Result<T> {
    private String status;
    private String status_code;
    @SerializedName("HeWeather data service 3.0")
    private ArrayList<T> HeWeather5;
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus_code() {
        return status_code;
    }

    public void setStatus_code(String status_code) {
        this.status_code = status_code;
    }

    public ArrayList<T> getHeWeather5() {
        return HeWeather5;
    }

    public void setHeWeather5(ArrayList<T> heWeather5) {
        HeWeather5 = heWeather5;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status='" + status + '\'' +
                ", status_code='" + status_code + '\'' +
                ", HeWeather5=" + HeWeather5 +
                '}';
    }
}
