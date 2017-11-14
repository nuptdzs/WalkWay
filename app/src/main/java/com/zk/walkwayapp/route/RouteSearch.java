//package com.zk.walkwayapp.route;
//
//import android.content.Context;
//
//import com.akexorcist.googledirection.DirectionCallback;
//import com.akexorcist.googledirection.GoogleDirection;
//import com.akexorcist.googledirection.constant.TransportMode;
//import com.akexorcist.googledirection.model.Direction;
//import com.amap.api.services.core.LatLonPoint;
//import com.amap.api.services.route.BusRouteResult;
//import com.amap.api.services.route.DriveRouteResult;
//import com.amap.api.services.route.RideRouteResult;
//import com.amap.api.services.route.WalkRouteResult;
//import com.zk.walkwayapp.WalkWayApplication;
//
///**
// * Created by dzs on 2017/8/6.
// */
//public class RouteSearch {
//    public static int BusDefault = 0;
//    public static int DrivingDefault = 1;
//    public static int WalkDefault = 2;
//    private OnRouteSearchListener routeSearchListener;
//
//    public RouteSearch(Context context){
//
//    }
//
//    public void setRouteSearchListener(OnRouteSearchListener routeSearchListener) {
//        this.routeSearchListener = routeSearchListener;
//    }
//
//    public void calculateBusRouteAsyn(BusRouteQuery query) {
//        com.google.android.gms.maps.model.LatLng start = new com.google.android.gms.maps.model.LatLng(query.getFromAndTo().getmStartPoint().getLatitude(),query.getFromAndTo().getmStartPoint().getLongitude());
//        com.google.android.gms.maps.model.LatLng end = new com.google.android.gms.maps.model.LatLng(query.getFromAndTo().getmStartPoint().getLatitude(),query.getFromAndTo().getmStartPoint().getLongitude());
//
//        GoogleDirection.withServerKey(WalkWayApplication.PLACES_API_KEY)
//                .from(start)
//                .to(end)
//                .transportMode(TransportMode.TRANSIT)
//                .execute(new DirectionCallback() {
//                    @Override
//                    public void onDirectionSuccess(Direction direction, String rawBody) {
//
//                    }
//
//                    @Override
//                    public void onDirectionFailure(Throwable t) {
//
//                    }
//                });
//    }
//
//    public void calculateDriveRouteAsyn(DriveRouteQuery query) {
//        com.google.android.gms.maps.model.LatLng start = new com.google.android.gms.maps.model.LatLng(query.getFromAndTo().getmStartPoint().getLatitude(),query.getFromAndTo().getmStartPoint().getLongitude());
//        com.google.android.gms.maps.model.LatLng end = new com.google.android.gms.maps.model.LatLng(query.getFromAndTo().getmStartPoint().getLatitude(),query.getFromAndTo().getmStartPoint().getLongitude());
//
//        GoogleDirection.withServerKey(WalkWayApplication.PLACES_API_KEY)
//                .from(start)
//                .to(end)
//                .transportMode(TransportMode.DRIVING)
//                .execute(new DirectionCallback() {
//                    @Override
//                    public void onDirectionSuccess(Direction direction, String rawBody) {
//                        DriveRouteResult result = new DriveRouteResult();
//                        routeSearchListener.onDriveRouteSearched(
//                                result,0);
//                    }
//
//                    @Override
//                    public void onDirectionFailure(Throwable t) {
//
//                    }
//                });
//    }
//
//    public void calculateWalkRouteAsyn(WalkRouteQuery query) {
//        com.google.android.gms.maps.model.LatLng start = new com.google.android.gms.maps.model.LatLng(query.getFromAndTo().getmStartPoint().getLatitude(),query.getFromAndTo().getmStartPoint().getLongitude());
//        com.google.android.gms.maps.model.LatLng end = new com.google.android.gms.maps.model.LatLng(query.getFromAndTo().getmStartPoint().getLatitude(),query.getFromAndTo().getmStartPoint().getLongitude());
//
//        GoogleDirection.withServerKey(WalkWayApplication.PLACES_API_KEY)
//                .from(start)
//                .to(end)
//                .transportMode(TransportMode.WALKING)
//                .execute(new DirectionCallback() {
//                    @Override
//                    public void onDirectionSuccess(Direction direction, String rawBody) {
//                    }
//
//                    @Override
//                    public void onDirectionFailure(Throwable t) {
//
//                    }
//                });
//    }
//
//    public interface OnRouteSearchListener {
//        void onBusRouteSearched(BusRouteResult result, int errorCode);
//        void onDriveRouteSearched(DriveRouteResult result, int errorCode);
//        void onWalkRouteSearched(WalkRouteResult result, int errorCode);
//        void onRideRouteSearched(RideRouteResult rideRouteResult, int i);
//    }
//
//    public static class FromAndTo {
//        private final LatLonPoint mStartPoint;
//        private final LatLonPoint mEndPoint;
//
//        public FromAndTo(LatLonPoint mStartPoint, LatLonPoint mEndPoint) {
//
//            this.mStartPoint = mStartPoint;
//            this.mEndPoint = mEndPoint;
//        }
//
//        public LatLonPoint getmEndPoint() {
//            return mEndPoint;
//        }
//
//        public LatLonPoint getmStartPoint() {
//            return mStartPoint;
//        }
//    }
//
//    public static class BusRouteQuery {
//        private final FromAndTo fromAndTo;
//        private final int mode;
//        private final String mCurrentCityName;
//        private final int i;
//        private String cityd;
//
//        public FromAndTo getFromAndTo() {
//            return fromAndTo;
//        }
//
//        public int getMode() {
//            return mode;
//        }
//
//        public String getmCurrentCityName() {
//            return mCurrentCityName;
//        }
//
//        public int getI() {
//            return i;
//        }
//
//        public String getCityd() {
//            return cityd;
//        }
//
//        public BusRouteQuery(FromAndTo fromAndTo, int mode, String mCurrentCityName, int i) {
//
//            this.fromAndTo = fromAndTo;
//            this.mode = mode;
//            this.mCurrentCityName = mCurrentCityName;
//            this.i = i;
//        }
//
//        public void setCityd(String cityd) {
//            this.cityd = cityd;
//        }
//    }
//
//    public static class DriveRouteQuery {
//        private final FromAndTo fromAndTo;
//        private final int mode;
//        private final Object o;
//
//        public FromAndTo getFromAndTo() {
//            return fromAndTo;
//        }
//
//        public int getMode() {
//            return mode;
//        }
//
//        public Object getO() {
//            return o;
//        }
//
//        public Object getO1() {
//            return o1;
//        }
//
//        public String getS() {
//            return s;
//        }
//
//        private final Object o1;
//        private final String s;
//
//        public DriveRouteQuery(FromAndTo fromAndTo, int mode, Object o, Object o1, String s) {
//
//            this.fromAndTo = fromAndTo;
//            this.mode = mode;
//            this.o = o;
//            this.o1 = o1;
//            this.s = s;
//        }
//    }
//
//    public static class WalkRouteQuery {
//        private final FromAndTo fromAndTo;
//        private final int mode;
//
//        public FromAndTo getFromAndTo() {
//            return fromAndTo;
//        }
//
//        public int getMode() {
//            return mode;
//        }
//
//        public WalkRouteQuery(FromAndTo fromAndTo, int mode) {
//
//            this.fromAndTo = fromAndTo;
//            this.mode = mode;
//        }
//    }
//}
