package com.zk.api.weather;

/**
 * 天气信息
 * {
 "results": [{
 "location": {
 "id": "C23NB62W20TF",
 "name": "西雅图",
 "country": "US",
 "timezone": "America/Los_Angeles",
 "timezone_offset": "-07:00"
 },
 "now": {
 "text": "多云", //天气现象文字
 "code": "4", //天气现象代码
 "temperature": "14", //温度，单位为c摄氏度或f华氏度
 "feels_like": "14", //体感温度，单位为c摄氏度或f华氏度
 "pressure": "1018", //气压，单位为mb百帕或in英寸
 "humidity": "76", //相对湿度，0~100，单位为百分比
 "visibility": "16.09", //能见度，单位为km公里或mi英里
 "wind_direction": "西北", //风向文字
 "wind_direction_degree": "340", //风向角度，范围0~360，0为正北，90为正东，180为正南，270为正西
 "wind_speed": "8.05", //风速，单位为km/h公里每小时或mph英里每小时
 "wind_scale": "2", //风力等级，请参考：http://baike.baidu.com/view/465076.htm
 "clouds": "90", //云量，范围0~100，天空被云覆盖的百分比 #目前不支持中国城市#
 "dew_point": "-12" //露点温度，请参考：http://baike.baidu.com/view/118348.htm #目前不支持中国城市#
 },
 "last_update": "2015-09-25T22:45:00-07:00" //数据更新时间（该城市的本地时间）
 }]
 }
 */
public class WeatherInfo {
    private String text;
    private String code;
    private String temperature;
    private String feels_like;
    private String pressure;
    private String humidity;
    private String visibility;
    private String wind_direct;
    private String wind_direction_degree;
    private String wind_speed;
    private String wind_scale;
    private String clouds;
    private String dew_point;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(String feels_like) {
        this.feels_like = feels_like;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    public String getWind_direct() {
        return wind_direct;
    }

    public void setWind_direct(String wind_direct) {
        this.wind_direct = wind_direct;
    }

    public String getWind_direction_degree() {
        return wind_direction_degree;
    }

    public void setWind_direction_degree(String wind_direction_degree) {
        this.wind_direction_degree = wind_direction_degree;
    }

    public String getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(String wind_speed) {
        this.wind_speed = wind_speed;
    }

    public String getWind_scale() {
        return wind_scale;
    }

    public void setWind_scale(String wind_scale) {
        this.wind_scale = wind_scale;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }

    public String getDew_point() {
        return dew_point;
    }

    public void setDew_point(String dew_point) {
        this.dew_point = dew_point;
    }
}
