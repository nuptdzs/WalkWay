package com.zk.walkwayapp.route;

/**
 * Created by dzs on 2017/8/6.
 */

public class TransitDetail {
    private NameLocation arrival_stop;
    private NameLocation departure_stop;
    private String headsign;
    private String headway;
    private int num_stops;
    private Line line;

    @Override
    public String toString() {
        return "TransitDetail{" +
                "arrival_stop=" + arrival_stop +
                ", departure_stop=" + departure_stop +
                ", headsign='" + headsign + '\'' +
                ", headway='" + headway + '\'' +
                ", num_stops=" + num_stops +
                ", line=" + line +
                '}';
    }

    public NameLocation getArrival_stop() {
        return arrival_stop;
    }

    public void setArrival_stop(NameLocation arrival_stop) {
        this.arrival_stop = arrival_stop;
    }

    public NameLocation getDeparture_stop() {
        return departure_stop;
    }

    public void setDeparture_stop(NameLocation departure_stop) {
        this.departure_stop = departure_stop;
    }

    public String getHeadsign() {
        return headsign;
    }

    public void setHeadsign(String headsign) {
        this.headsign = headsign;
    }

    public String getHeadway() {
        return headway;
    }

    public void setHeadway(String headway) {
        this.headway = headway;
    }

    public int getNum_stops() {
        return num_stops;
    }

    public void setNum_stops(int num_stops) {
        this.num_stops = num_stops;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }
}
