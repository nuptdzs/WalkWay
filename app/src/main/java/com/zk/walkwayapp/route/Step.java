package com.zk.walkwayapp.route;

import java.util.List;

/**
 * Created by dzs on 2017/8/6.
 */

public class Step{
    private String html_instructions;// 包含该分段的格式化指令，以 HTML 文本字符串形式呈现。
    private ValueText distance;// 包含从该分段至下一分段起点的覆盖距离。（请参阅上文路线段中对该字段的介绍。）如果距离未知，那么此字段可能未定义
    private ValueText duration;// 包含完成该分段至下一分段起点距离通常需要的时间。（请参阅上文路线段中的说明。）如果持续时间未知，该字段可能处于未定义状态。
    private LatLng start_location;// 包含该分段起点的位置，以一组lat 字段和 lng 字段形式表示。
    private LatLng end_location;// 包含该分段终点的位置，以一组lat 字段和 lng 字段形式表示。
    private List<LatLng> polyline;//其中包含一个 points 对象，用于储存以经过编码的折线形式表示的路段。该多段线是分段的近似（平滑）路径。
    private List<Step> steps;// 包含公共交通路线中步行或驾车分段的详细路线。只有在 travel_mode 设置为“transit”时，才会出现分段。内部 steps 数组与 steps 的类型相同。
    private TransitDetail transit_details;// 包含公共交通专属信息。只有在 travel_mode 设置为“transit”时，才会返回该字段。请参阅下文的公共交通详情。

    @Override
    public String toString() {
        return "Step{" +
                "html_instructions='" + html_instructions + '\'' +
                ", distance=" + distance +
                ", duration=" + duration +
                ", start_location=" + start_location +
                ", end_location=" + end_location +
                ", polyline=" + polyline +
                ", steps=" + steps +
                ", transit_details=" + transit_details +
                '}';
    }

    public String getHtml_instructions() {
        return html_instructions;
    }

    public void setHtml_instructions(String html_instructions) {
        this.html_instructions = html_instructions;
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

    public List<LatLng> getPolyline() {
        return polyline;
    }

    public void setPolyline(List<LatLng> polyline) {
        this.polyline = polyline;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    public TransitDetail getTransit_details() {
        return transit_details;
    }

    public void setTransit_details(TransitDetail transit_details) {
        this.transit_details = transit_details;
    }
}
