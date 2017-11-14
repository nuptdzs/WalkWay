package com.zk.walkwayapp.route;

import java.util.List;

/**
 * Created by dzs on 2017/8/6.
 */

public class RouteJson
{

    /**
     * geocoded_waypoints : [{"geocoder_status":"OK","place_id":"ChIJAyLajdygszURphWwAyLK-Ug","types":["neighborhood","political"]},{"geocoder_status":"OK","place_id":"ChIJIb5eI7OmszURixgKVuFPjX8","types":["establishment","point_of_interest","shopping_mall"]}]
     * routes : [{"bounds":{"northeast":{"lat":31.3449368,"lng":120.6145471},"southwest":{"lat":31.3116216,"lng":120.599384}},"copyrights":"地图数据 ©2017 GS(2011)6020","legs":[{"distance":{"text":"5.0 公里","value":4955},"duration":{"text":"14分钟","value":864},"end_address":"中国江苏省苏州市平江区人民路3188号 邮政编码: 215000","end_location":{"lat":31.3449368,"lng":120.6142309},"start_address":"中国江苏省苏州市姑苏区石路 邮政编码: 215000","start_location":{"lat":31.3118606,"lng":120.6009116},"steps":[{"distance":{"text":"16 米","value":16},"duration":{"text":"1分钟","value":4},"end_location":{"lat":31.3119274,"lng":120.6007595},"html_instructions":"从<b>西民庆里<\/b>向<b>西北<\/b>行驶，到<b>金石街<\/b>","polyline":{"points":"crb~Duza_VA@KZ"},"start_location":{"lat":31.3118606,"lng":120.6009116},"travel_mode":"DRIVING"},{"distance":{"text":"68 米","value":68},"duration":{"text":"1分钟","value":16},"end_location":{"lat":31.3116216,"lng":120.6001459},"html_instructions":"从<b>西民庆里<\/b>向<b>左<\/b>转，进入<b>金石街<\/b>","polyline":{"points":"qrb~Dwya_VBH^nA@@BHBBBDJH"},"start_location":{"lat":31.3119274,"lng":120.6007595},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 公里","value":110},"duration":{"text":"1分钟","value":35},"end_location":{"lat":31.3125971,"lng":120.5999718},"html_instructions":"向<b>右<\/b>急转，进入<b>广济南路<\/b>","maneuver":"turn-sharp-right","polyline":{"points":"spb~D}ua_VsCZo@F"},"start_location":{"lat":31.3116216,"lng":120.6001459},"travel_mode":"DRIVING"},{"distance":{"text":"1.3 公里","value":1321},"duration":{"text":"4分钟","value":243},"end_location":{"lat":31.3231972,"lng":120.6037812},"html_instructions":"继续前行，上<b>广济路<\/b>","polyline":{"points":"wvb~Dyta_V}@LA?kANI@_BRSBaALuARe@Dm@D}@HY@m@Bk@?K?g@Au@?_@A_@ASAUCUGUGOIEAIEYUQSYa@ACUa@GMc@}@OU]g@[]GGQS_@Uc@W[Q[QIC]QECc@UOGm@[g@UWOYQg@Wg@Ma@KWEm@Gy@IGAYE_@IWI]Ma@QAA]WY[{AeBIK"},"start_location":{"lat":31.3125971,"lng":120.5999718},"travel_mode":"DRIVING"},{"distance":{"text":"1.9 公里","value":1938},"duration":{"text":"5分钟","value":290},"end_location":{"lat":31.3399482,"lng":120.6043088},"html_instructions":"继续前行，上<b>广济北路<\/b>","polyline":{"points":"_yd~Dslb_VO]OWoA{Ac@c@[Ca@U_@M{@QaAK}AMoBMGASCg@EgAKeAIUCKAYCUCOC}Aa@{CYy@IWDA?C?A@E?G?GA{@S]KgA[{@QgAOw@GE?]?u@@cA@mAH{@J{@LyCd@y@L_@FOBg@HqC`@WDQ@wC`@c@FQD}@N{@LkARaBXk@Ho@FgCf@w@NiATs@N"},"start_location":{"lat":31.3231972,"lng":120.6037812},"travel_mode":"DRIVING"},{"distance":{"text":"0.9 公里","value":890},"duration":{"text":"2分钟","value":112},"end_location":{"lat":31.340513,"lng":120.6136372},"html_instructions":"向<b>右<\/b>转，进入<b>312国道<\/b>/<b>城北东路<\/b>/<b>G312<\/b>","maneuver":"turn-right","polyline":{"points":"uah~D}ob_VOyDOyCKcC?AQuDI}AGyAI}AAUMoCE{@Ci@Co@A[?C?CCcAAiAAs@?eA?Q@wA@e@DqAFyA"},"start_location":{"lat":31.3399482,"lng":120.6043088},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 公里","value":312},"duration":{"text":"1分钟","value":59},"end_location":{"lat":31.343083,"lng":120.6133588},"html_instructions":"向<b>左<\/b>转，进入<b>人民路<\/b>","maneuver":"turn-left","polyline":{"points":"eeh~Dgjd_VBi@S@uAL_CVi@FOBuDd@k@F"},"start_location":{"lat":31.340513,"lng":120.6136372},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 公里","value":116},"duration":{"text":"1分钟","value":18},"end_location":{"lat":31.3433007,"lng":120.6145471},"html_instructions":"向<b>右<\/b>转，进入<b>莲升路<\/b>","maneuver":"turn-right","polyline":{"points":"guh~Dohd_VMkAQwBE_@EI"},"start_location":{"lat":31.343083,"lng":120.6133588},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 公里","value":184},"duration":{"text":"1分钟","value":87},"end_location":{"lat":31.3449368,"lng":120.6142309},"html_instructions":"向<b>左<\/b>转，进入<b>春分街<\/b><div style=\"font-size:0.9em\">目的地在左侧<\/div>","maneuver":"turn-left","polyline":{"points":"svh~D}od_VkBRmCZQB{@J"},"start_location":{"lat":31.3433007,"lng":120.6145471},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]}],"overview_polyline":{"points":"crb~Duza_VM\\b@xALTJHsCZmBTwDd@qEj@sDTuCA_ACi@Ek@OUKc@[k@u@cAqBm@}@c@e@q@i@_Ai@iAk@iCoAq@a@g@Wg@My@QgBQa@Gw@S_A_@_@YuBaCYi@OWoA{Ac@c@[CaAc@{@QaAKmE[[EkE_@kAM}Aa@{CYy@IWDE?G@OAyA_@cCm@_CW}CBiCTuEr@qCb@sIjAoDj@mDl@{AP}H|Ak@yMm@kM_@_JEmCAyBHaFJcCiBNoJhAk@FMkAWwCEIkBR_D^{@J"},"summary":"广济路和广济北路","warnings":[],"waypoint_order":[]}]
     * status : OK
     */

    private String status;
    private List<GeocodedWaypointsBean> geocoded_waypoints;
    private List<RoutesBean> routes;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeocodedWaypointsBean> getGeocoded_waypoints() {
        return geocoded_waypoints;
    }

    public void setGeocoded_waypoints(List<GeocodedWaypointsBean> geocoded_waypoints) {
        this.geocoded_waypoints = geocoded_waypoints;
    }

    public List<RoutesBean> getRoutes() {
        return routes;
    }

    public void setRoutes(List<RoutesBean> routes) {
        this.routes = routes;
    }

    public static class GeocodedWaypointsBean {
        /**
         * geocoder_status : OK
         * place_id : ChIJAyLajdygszURphWwAyLK-Ug
         * types : ["neighborhood","political"]
         */

        private String geocoder_status;
        private String place_id;
        private List<String> types;

        public String getGeocoder_status() {
            return geocoder_status;
        }

        public void setGeocoder_status(String geocoder_status) {
            this.geocoder_status = geocoder_status;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }
    }

    public static class RoutesBean {
        /**
         * bounds : {"northeast":{"lat":31.3449368,"lng":120.6145471},"southwest":{"lat":31.3116216,"lng":120.599384}}
         * copyrights : 地图数据 ©2017 GS(2011)6020
         * legs : [{"distance":{"text":"5.0 公里","value":4955},"duration":{"text":"14分钟","value":864},"end_address":"中国江苏省苏州市平江区人民路3188号 邮政编码: 215000","end_location":{"lat":31.3449368,"lng":120.6142309},"start_address":"中国江苏省苏州市姑苏区石路 邮政编码: 215000","start_location":{"lat":31.3118606,"lng":120.6009116},"steps":[{"distance":{"text":"16 米","value":16},"duration":{"text":"1分钟","value":4},"end_location":{"lat":31.3119274,"lng":120.6007595},"html_instructions":"从<b>西民庆里<\/b>向<b>西北<\/b>行驶，到<b>金石街<\/b>","polyline":{"points":"crb~Duza_VA@KZ"},"start_location":{"lat":31.3118606,"lng":120.6009116},"travel_mode":"DRIVING"},{"distance":{"text":"68 米","value":68},"duration":{"text":"1分钟","value":16},"end_location":{"lat":31.3116216,"lng":120.6001459},"html_instructions":"从<b>西民庆里<\/b>向<b>左<\/b>转，进入<b>金石街<\/b>","polyline":{"points":"qrb~Dwya_VBH^nA@@BHBBBDJH"},"start_location":{"lat":31.3119274,"lng":120.6007595},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 公里","value":110},"duration":{"text":"1分钟","value":35},"end_location":{"lat":31.3125971,"lng":120.5999718},"html_instructions":"向<b>右<\/b>急转，进入<b>广济南路<\/b>","maneuver":"turn-sharp-right","polyline":{"points":"spb~D}ua_VsCZo@F"},"start_location":{"lat":31.3116216,"lng":120.6001459},"travel_mode":"DRIVING"},{"distance":{"text":"1.3 公里","value":1321},"duration":{"text":"4分钟","value":243},"end_location":{"lat":31.3231972,"lng":120.6037812},"html_instructions":"继续前行，上<b>广济路<\/b>","polyline":{"points":"wvb~Dyta_V}@LA?kANI@_BRSBaALuARe@Dm@D}@HY@m@Bk@?K?g@Au@?_@A_@ASAUCUGUGOIEAIEYUQSYa@ACUa@GMc@}@OU]g@[]GGQS_@Uc@W[Q[QIC]QECc@UOGm@[g@UWOYQg@Wg@Ma@KWEm@Gy@IGAYE_@IWI]Ma@QAA]WY[{AeBIK"},"start_location":{"lat":31.3125971,"lng":120.5999718},"travel_mode":"DRIVING"},{"distance":{"text":"1.9 公里","value":1938},"duration":{"text":"5分钟","value":290},"end_location":{"lat":31.3399482,"lng":120.6043088},"html_instructions":"继续前行，上<b>广济北路<\/b>","polyline":{"points":"_yd~Dslb_VO]OWoA{Ac@c@[Ca@U_@M{@QaAK}AMoBMGASCg@EgAKeAIUCKAYCUCOC}Aa@{CYy@IWDA?C?A@E?G?GA{@S]KgA[{@QgAOw@GE?]?u@@cA@mAH{@J{@LyCd@y@L_@FOBg@HqC`@WDQ@wC`@c@FQD}@N{@LkARaBXk@Ho@FgCf@w@NiATs@N"},"start_location":{"lat":31.3231972,"lng":120.6037812},"travel_mode":"DRIVING"},{"distance":{"text":"0.9 公里","value":890},"duration":{"text":"2分钟","value":112},"end_location":{"lat":31.340513,"lng":120.6136372},"html_instructions":"向<b>右<\/b>转，进入<b>312国道<\/b>/<b>城北东路<\/b>/<b>G312<\/b>","maneuver":"turn-right","polyline":{"points":"uah~D}ob_VOyDOyCKcC?AQuDI}AGyAI}AAUMoCE{@Ci@Co@A[?C?CCcAAiAAs@?eA?Q@wA@e@DqAFyA"},"start_location":{"lat":31.3399482,"lng":120.6043088},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 公里","value":312},"duration":{"text":"1分钟","value":59},"end_location":{"lat":31.343083,"lng":120.6133588},"html_instructions":"向<b>左<\/b>转，进入<b>人民路<\/b>","maneuver":"turn-left","polyline":{"points":"eeh~Dgjd_VBi@S@uAL_CVi@FOBuDd@k@F"},"start_location":{"lat":31.340513,"lng":120.6136372},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 公里","value":116},"duration":{"text":"1分钟","value":18},"end_location":{"lat":31.3433007,"lng":120.6145471},"html_instructions":"向<b>右<\/b>转，进入<b>莲升路<\/b>","maneuver":"turn-right","polyline":{"points":"guh~Dohd_VMkAQwBE_@EI"},"start_location":{"lat":31.343083,"lng":120.6133588},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 公里","value":184},"duration":{"text":"1分钟","value":87},"end_location":{"lat":31.3449368,"lng":120.6142309},"html_instructions":"向<b>左<\/b>转，进入<b>春分街<\/b><div style=\"font-size:0.9em\">目的地在左侧<\/div>","maneuver":"turn-left","polyline":{"points":"svh~D}od_VkBRmCZQB{@J"},"start_location":{"lat":31.3433007,"lng":120.6145471},"travel_mode":"DRIVING"}],"traffic_speed_entry":[],"via_waypoint":[]}]
         * overview_polyline : {"points":"crb~Duza_VM\\b@xALTJHsCZmBTwDd@qEj@sDTuCA_ACi@Ek@OUKc@[k@u@cAqBm@}@c@e@q@i@_Ai@iAk@iCoAq@a@g@Wg@My@QgBQa@Gw@S_A_@_@YuBaCYi@OWoA{Ac@c@[CaAc@{@QaAKmE[[EkE_@kAM}Aa@{CYy@IWDE?G@OAyA_@cCm@_CW}CBiCTuEr@qCb@sIjAoDj@mDl@{AP}H|Ak@yMm@kM_@_JEmCAyBHaFJcCiBNoJhAk@FMkAWwCEIkBR_D^{@J"}
         * summary : 广济路和广济北路
         * warnings : []
         * waypoint_order : []
         */

        private BoundsBean bounds;
        private String copyrights;
        private OverviewPolylineBean overview_polyline;
        private String summary;
        private List<LegsBean> legs;
        private List<?> warnings;
        private List<?> waypoint_order;

        public BoundsBean getBounds() {
            return bounds;
        }

        public void setBounds(BoundsBean bounds) {
            this.bounds = bounds;
        }

        public String getCopyrights() {
            return copyrights;
        }

        public void setCopyrights(String copyrights) {
            this.copyrights = copyrights;
        }

        public OverviewPolylineBean getOverview_polyline() {
            return overview_polyline;
        }

        public void setOverview_polyline(OverviewPolylineBean overview_polyline) {
            this.overview_polyline = overview_polyline;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public List<LegsBean> getLegs() {
            return legs;
        }

        public void setLegs(List<LegsBean> legs) {
            this.legs = legs;
        }

        public List<?> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<?> warnings) {
            this.warnings = warnings;
        }

        public List<?> getWaypoint_order() {
            return waypoint_order;
        }

        public void setWaypoint_order(List<?> waypoint_order) {
            this.waypoint_order = waypoint_order;
        }

        public static class BoundsBean {
            /**
             * northeast : {"lat":31.3449368,"lng":120.6145471}
             * southwest : {"lat":31.3116216,"lng":120.599384}
             */

            private NortheastBean northeast;
            private SouthwestBean southwest;

            public NortheastBean getNortheast() {
                return northeast;
            }

            public void setNortheast(NortheastBean northeast) {
                this.northeast = northeast;
            }

            public SouthwestBean getSouthwest() {
                return southwest;
            }

            public void setSouthwest(SouthwestBean southwest) {
                this.southwest = southwest;
            }

            public static class NortheastBean {
                /**
                 * lat : 31.3449368
                 * lng : 120.6145471
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class SouthwestBean {
                /**
                 * lat : 31.3116216
                 * lng : 120.599384
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }
        }

        public static class OverviewPolylineBean {
            /**
             * points : crb~Duza_VM\b@xALTJHsCZmBTwDd@qEj@sDTuCA_ACi@Ek@OUKc@[k@u@cAqBm@}@c@e@q@i@_Ai@iAk@iCoAq@a@g@Wg@My@QgBQa@Gw@S_A_@_@YuBaCYi@OWoA{Ac@c@[CaAc@{@QaAKmE[[EkE_@kAM}Aa@{CYy@IWDE?G@OAyA_@cCm@_CW}CBiCTuEr@qCb@sIjAoDj@mDl@{AP}H|Ak@yMm@kM_@_JEmCAyBHaFJcCiBNoJhAk@FMkAWwCEIkBR_D^{@J
             */

            private String points;

            public String getPoints() {
                return points;
            }

            public void setPoints(String points) {
                this.points = points;
            }
        }

        public static class LegsBean {
            /**
             * distance : {"text":"5.0 公里","value":4955}
             * duration : {"text":"14分钟","value":864}
             * end_address : 中国江苏省苏州市平江区人民路3188号 邮政编码: 215000
             * end_location : {"lat":31.3449368,"lng":120.6142309}
             * start_address : 中国江苏省苏州市姑苏区石路 邮政编码: 215000
             * start_location : {"lat":31.3118606,"lng":120.6009116}
             * steps : [{"distance":{"text":"16 米","value":16},"duration":{"text":"1分钟","value":4},"end_location":{"lat":31.3119274,"lng":120.6007595},"html_instructions":"从<b>西民庆里<\/b>向<b>西北<\/b>行驶，到<b>金石街<\/b>","polyline":{"points":"crb~Duza_VA@KZ"},"start_location":{"lat":31.3118606,"lng":120.6009116},"travel_mode":"DRIVING"},{"distance":{"text":"68 米","value":68},"duration":{"text":"1分钟","value":16},"end_location":{"lat":31.3116216,"lng":120.6001459},"html_instructions":"从<b>西民庆里<\/b>向<b>左<\/b>转，进入<b>金石街<\/b>","polyline":{"points":"qrb~Dwya_VBH^nA@@BHBBBDJH"},"start_location":{"lat":31.3119274,"lng":120.6007595},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 公里","value":110},"duration":{"text":"1分钟","value":35},"end_location":{"lat":31.3125971,"lng":120.5999718},"html_instructions":"向<b>右<\/b>急转，进入<b>广济南路<\/b>","maneuver":"turn-sharp-right","polyline":{"points":"spb~D}ua_VsCZo@F"},"start_location":{"lat":31.3116216,"lng":120.6001459},"travel_mode":"DRIVING"},{"distance":{"text":"1.3 公里","value":1321},"duration":{"text":"4分钟","value":243},"end_location":{"lat":31.3231972,"lng":120.6037812},"html_instructions":"继续前行，上<b>广济路<\/b>","polyline":{"points":"wvb~Dyta_V}@LA?kANI@_BRSBaALuARe@Dm@D}@HY@m@Bk@?K?g@Au@?_@A_@ASAUCUGUGOIEAIEYUQSYa@ACUa@GMc@}@OU]g@[]GGQS_@Uc@W[Q[QIC]QECc@UOGm@[g@UWOYQg@Wg@Ma@KWEm@Gy@IGAYE_@IWI]Ma@QAA]WY[{AeBIK"},"start_location":{"lat":31.3125971,"lng":120.5999718},"travel_mode":"DRIVING"},{"distance":{"text":"1.9 公里","value":1938},"duration":{"text":"5分钟","value":290},"end_location":{"lat":31.3399482,"lng":120.6043088},"html_instructions":"继续前行，上<b>广济北路<\/b>","polyline":{"points":"_yd~Dslb_VO]OWoA{Ac@c@[Ca@U_@M{@QaAK}AMoBMGASCg@EgAKeAIUCKAYCUCOC}Aa@{CYy@IWDA?C?A@E?G?GA{@S]KgA[{@QgAOw@GE?]?u@@cA@mAH{@J{@LyCd@y@L_@FOBg@HqC`@WDQ@wC`@c@FQD}@N{@LkARaBXk@Ho@FgCf@w@NiATs@N"},"start_location":{"lat":31.3231972,"lng":120.6037812},"travel_mode":"DRIVING"},{"distance":{"text":"0.9 公里","value":890},"duration":{"text":"2分钟","value":112},"end_location":{"lat":31.340513,"lng":120.6136372},"html_instructions":"向<b>右<\/b>转，进入<b>312国道<\/b>/<b>城北东路<\/b>/<b>G312<\/b>","maneuver":"turn-right","polyline":{"points":"uah~D}ob_VOyDOyCKcC?AQuDI}AGyAI}AAUMoCE{@Ci@Co@A[?C?CCcAAiAAs@?eA?Q@wA@e@DqAFyA"},"start_location":{"lat":31.3399482,"lng":120.6043088},"travel_mode":"DRIVING"},{"distance":{"text":"0.3 公里","value":312},"duration":{"text":"1分钟","value":59},"end_location":{"lat":31.343083,"lng":120.6133588},"html_instructions":"向<b>左<\/b>转，进入<b>人民路<\/b>","maneuver":"turn-left","polyline":{"points":"eeh~Dgjd_VBi@S@uAL_CVi@FOBuDd@k@F"},"start_location":{"lat":31.340513,"lng":120.6136372},"travel_mode":"DRIVING"},{"distance":{"text":"0.1 公里","value":116},"duration":{"text":"1分钟","value":18},"end_location":{"lat":31.3433007,"lng":120.6145471},"html_instructions":"向<b>右<\/b>转，进入<b>莲升路<\/b>","maneuver":"turn-right","polyline":{"points":"guh~Dohd_VMkAQwBE_@EI"},"start_location":{"lat":31.343083,"lng":120.6133588},"travel_mode":"DRIVING"},{"distance":{"text":"0.2 公里","value":184},"duration":{"text":"1分钟","value":87},"end_location":{"lat":31.3449368,"lng":120.6142309},"html_instructions":"向<b>左<\/b>转，进入<b>春分街<\/b><div style=\"font-size:0.9em\">目的地在左侧<\/div>","maneuver":"turn-left","polyline":{"points":"svh~D}od_VkBRmCZQB{@J"},"start_location":{"lat":31.3433007,"lng":120.6145471},"travel_mode":"DRIVING"}]
             * traffic_speed_entry : []
             * via_waypoint : []
             */

            private DistanceBean distance;
            private DurationBean duration;
            private String end_address;
            private EndLocationBean end_location;
            private String start_address;
            private StartLocationBean start_location;
            private List<StepsBean> steps;
            private List<?> traffic_speed_entry;
            private List<?> via_waypoint;

            public DistanceBean getDistance() {
                return distance;
            }

            public void setDistance(DistanceBean distance) {
                this.distance = distance;
            }

            public DurationBean getDuration() {
                return duration;
            }

            public void setDuration(DurationBean duration) {
                this.duration = duration;
            }

            public String getEnd_address() {
                return end_address;
            }

            public void setEnd_address(String end_address) {
                this.end_address = end_address;
            }

            public EndLocationBean getEnd_location() {
                return end_location;
            }

            public void setEnd_location(EndLocationBean end_location) {
                this.end_location = end_location;
            }

            public String getStart_address() {
                return start_address;
            }

            public void setStart_address(String start_address) {
                this.start_address = start_address;
            }

            public StartLocationBean getStart_location() {
                return start_location;
            }

            public void setStart_location(StartLocationBean start_location) {
                this.start_location = start_location;
            }

            public List<StepsBean> getSteps() {
                return steps;
            }

            public void setSteps(List<StepsBean> steps) {
                this.steps = steps;
            }

            public List<?> getTraffic_speed_entry() {
                return traffic_speed_entry;
            }

            public void setTraffic_speed_entry(List<?> traffic_speed_entry) {
                this.traffic_speed_entry = traffic_speed_entry;
            }

            public List<?> getVia_waypoint() {
                return via_waypoint;
            }

            public void setVia_waypoint(List<?> via_waypoint) {
                this.via_waypoint = via_waypoint;
            }

            public static class DistanceBean {
                /**
                 * text : 5.0 公里
                 * value : 4955
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class DurationBean {
                /**
                 * text : 14分钟
                 * value : 864
                 */

                private String text;
                private int value;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public int getValue() {
                    return value;
                }

                public void setValue(int value) {
                    this.value = value;
                }
            }

            public static class EndLocationBean {
                /**
                 * lat : 31.3449368
                 * lng : 120.6142309
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class StartLocationBean {
                /**
                 * lat : 31.3118606
                 * lng : 120.6009116
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class StepsBean {
                /**
                 * distance : {"text":"16 米","value":16}
                 * duration : {"text":"1分钟","value":4}
                 * end_location : {"lat":31.3119274,"lng":120.6007595}
                 * html_instructions : 从<b>西民庆里</b>向<b>西北</b>行驶，到<b>金石街</b>
                 * polyline : {"points":"crb~Duza_VA@KZ"}
                 * start_location : {"lat":31.3118606,"lng":120.6009116}
                 * travel_mode : DRIVING
                 * maneuver : turn-sharp-right
                 */

                private DistanceBeanX distance;
                private DurationBeanX duration;
                private EndLocationBeanX end_location;
                private String html_instructions;
                private PolylineBean polyline;
                private StartLocationBeanX start_location;
                private String travel_mode;
                private String maneuver;

                public DistanceBeanX getDistance() {
                    return distance;
                }

                public void setDistance(DistanceBeanX distance) {
                    this.distance = distance;
                }

                public DurationBeanX getDuration() {
                    return duration;
                }

                public void setDuration(DurationBeanX duration) {
                    this.duration = duration;
                }

                public EndLocationBeanX getEnd_location() {
                    return end_location;
                }

                public void setEnd_location(EndLocationBeanX end_location) {
                    this.end_location = end_location;
                }

                public String getHtml_instructions() {
                    return html_instructions;
                }

                public void setHtml_instructions(String html_instructions) {
                    this.html_instructions = html_instructions;
                }

                public PolylineBean getPolyline() {
                    return polyline;
                }

                public void setPolyline(PolylineBean polyline) {
                    this.polyline = polyline;
                }

                public StartLocationBeanX getStart_location() {
                    return start_location;
                }

                public void setStart_location(StartLocationBeanX start_location) {
                    this.start_location = start_location;
                }

                public String getTravel_mode() {
                    return travel_mode;
                }

                public void setTravel_mode(String travel_mode) {
                    this.travel_mode = travel_mode;
                }

                public String getManeuver() {
                    return maneuver;
                }

                public void setManeuver(String maneuver) {
                    this.maneuver = maneuver;
                }

                public static class DistanceBeanX {
                    /**
                     * text : 16 米
                     * value : 16
                     */

                    private String text;
                    private int value;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }
                }

                public static class DurationBeanX {
                    /**
                     * text : 1分钟
                     * value : 4
                     */

                    private String text;
                    private int value;

                    public String getText() {
                        return text;
                    }

                    public void setText(String text) {
                        this.text = text;
                    }

                    public int getValue() {
                        return value;
                    }

                    public void setValue(int value) {
                        this.value = value;
                    }
                }

                public static class EndLocationBeanX {
                    /**
                     * lat : 31.3119274
                     * lng : 120.6007595
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class PolylineBean {
                    /**
                     * points : crb~Duza_VA@KZ
                     */

                    private String points;

                    public String getPoints() {
                        return points;
                    }

                    public void setPoints(String points) {
                        this.points = points;
                    }
                }

                public static class StartLocationBeanX {
                    /**
                     * lat : 31.3118606
                     * lng : 120.6009116
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }
    }
}
