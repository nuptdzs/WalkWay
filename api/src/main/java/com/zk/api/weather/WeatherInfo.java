package com.zk.api.weather;

/**
 * 天气信息
 *"cond": {  //天气状况
 "code": "104",  //天气状况代码
 "txt": "阴"  //天气状况描述
 },
 "fl": "11",  //体感温度
 "hum": "31",  //相对湿度（%）
 "pcpn": "0",  //降水量（mm）
 "pres": "1025",  //气压
 "tmp": "13",  //温度
 "vis": "10",  //能见度（km）
 "wind": {  //风力风向
 "deg": "40",  //风向（360度）
 "dir": "东北风",  //风向
 "sc": "4-5",  //风力
 "spd": "24"  //风速（kmph）
 }
 */
public class WeatherInfo {
    /**
     * 天气情况
     */
    private Cond cond;
    /**
     * 体感温度
     */
    private String fl;
    /**
     * 相对湿度
     */
    private String hum;
    /**
     * 降水量
     */
    private String pcpn;
    /**
     * 气压
     */
    private String pres;
    /**
     * 温度
     */
    private String tmp;
    /**
     * 能见度
     */
    private String vis;
    /**
     * 风况
     */
    private Wind wind;

    public Cond getCond() {
        return cond;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public String getFl() {
        return fl;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getVis() {
        return vis;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                "cond=" + cond +
                ", fl='" + fl + '\'' +
                ", hum='" + hum + '\'' +
                ", pcpn='" + pcpn + '\'' +
                ", pres='" + pres + '\'' +
                ", tmp='" + tmp + '\'' +
                ", vis='" + vis + '\'' +
                ", wind=" + wind +
                '}';
    }

    public class Cond{
        private String code;
        private String txt;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        @Override
        public String toString() {
            return "Cond{" +
                    "code='" + code + '\'' +
                    ", txt='" + txt + '\'' +
                    '}';
        }
    }

    public class Wind{
        private String deg;
        private String dir;
        private String sc;
        private String spd;

        public String getDeg() {
            return deg;
        }

        public void setDeg(String deg) {
            this.deg = deg;
        }

        public String getDir() {
            return dir;
        }

        public void setDir(String dir) {
            this.dir = dir;
        }

        public String getSc() {
            return sc;
        }

        public void setSc(String sc) {
            this.sc = sc;
        }

        public String getSpd() {
            return spd;
        }

        public void setSpd(String spd) {
            this.spd = spd;
        }

        @Override
        public String toString() {
            return "Wind{" +
                    "deg='" + deg + '\'' +
                    ", dir='" + dir + '\'' +
                    ", sc='" + sc + '\'' +
                    ", spd='" + spd + '\'' +
                    '}';
        }
    }
//    private String text;
//    private String code;
//    private String temperature;
//    private String feels_like;
//    private String pressure;
//    private String humidity;
//    private String visibility;
//    private String wind_direct;
//    private String wind_direction_degree;
//    private String wind_speed;
//    private String wind_scale;
//    private String clouds;
//    private String dew_point;
//
//    public String getText() {
//        return text;
//    }
//
//    public void setText(String text) {
//        this.text = text;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }
//
//    public String getTemperature() {
//        return temperature;
//    }
//
//    public void setTemperature(String temperature) {
//        this.temperature = temperature;
//    }
//
//    public String getFeels_like() {
//        return feels_like;
//    }
//
//    public void setFeels_like(String feels_like) {
//        this.feels_like = feels_like;
//    }
//
//    public String getPressure() {
//        return pressure;
//    }
//
//    public void setPressure(String pressure) {
//        this.pressure = pressure;
//    }
//
//    public String getHumidity() {
//        return humidity;
//    }
//
//    public void setHumidity(String humidity) {
//        this.humidity = humidity;
//    }
//
//    public String getVisibility() {
//        return visibility;
//    }
//
//    public void setVisibility(String visibility) {
//        this.visibility = visibility;
//    }
//
//    public String getWind_direct() {
//        return wind_direct;
//    }
//
//    public void setWind_direct(String wind_direct) {
//        this.wind_direct = wind_direct;
//    }
//
//    public String getWind_direction_degree() {
//        return wind_direction_degree;
//    }
//
//    public void setWind_direction_degree(String wind_direction_degree) {
//        this.wind_direction_degree = wind_direction_degree;
//    }
//
//    public String getWind_speed() {
//        return wind_speed;
//    }
//
//    public void setWind_speed(String wind_speed) {
//        this.wind_speed = wind_speed;
//    }
//
//    public String getWind_scale() {
//        return wind_scale;
//    }
//
//    public void setWind_scale(String wind_scale) {
//        this.wind_scale = wind_scale;
//    }
//
//    public String getClouds() {
//        return clouds;
//    }
//
//    public void setClouds(String clouds) {
//        this.clouds = clouds;
//    }
//
//    public String getDew_point() {
//        return dew_point;
//    }
//
//    public void setDew_point(String dew_point) {
//        this.dew_point = dew_point;
//    }
}
