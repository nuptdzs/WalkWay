//package com.zk.walkwayapp.view.fragment;
//
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//
//import com.amap.api.maps.AMap;
//import com.amap.api.maps.MapView;
//import com.amap.api.maps.model.BitmapDescriptorFactory;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.Marker;
//import com.amap.api.maps.model.MarkerOptions;
//import com.amap.api.services.core.AMapException;
//import com.amap.api.services.core.LatLonPoint;
//import com.amap.api.services.route.BusRouteResult;
//import com.amap.api.services.route.DrivePath;
//import com.amap.api.services.route.DriveRouteResult;
//import com.amap.api.services.route.RideRouteResult;
//import com.amap.api.services.route.RouteSearch;
//import com.amap.api.services.route.WalkPath;
//import com.amap.api.services.route.WalkRouteResult;
//import com.avos.avoscloud.AVUser;
//import com.zk.library.common.mvp.BaseFragment;
//import com.zk.library.common.mvp.ContentView;
//import com.zk.library.common.mvp.IMvpPresenter;
//import com.zk.walkwayapp.R;
//import com.zk.walkwayapp.adapter.BusResultListAdapter;
//import com.zk.walkwayapp.model.bean.UserTag;
//import com.zk.walkwayapp.overlay.DrivingRouteOverlay;
//import com.zk.walkwayapp.overlay.WalkRouteOverlay;
//import com.zk.walkwayapp.utils.AMapUtil;
//import com.zk.walkwayapp.utils.ToastUtil;
//import com.zk.walkwayapp.view.activity.DriveRouteDetailActivity;
//import com.zk.walkwayapp.view.activity.WalkRouteDetailActivity;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//
//import static com.avos.avoscloud.AVUser.getCurrentUser;
//
//
///**
// * 地图规划界面
// */
//@ContentView(R.layout.fragment_map)
//public class MapFragment extends BaseFragment implements AMap.OnMapClickListener,
//        AMap.OnMarkerClickListener, AMap.OnInfoWindowClickListener, AMap.InfoWindowAdapter, RouteSearch.OnRouteSearchListener {
//    //路径导航的不同交通类型
//    private final int ROUTE_TYPE_BUS = 1;
//    private final int ROUTE_TYPE_DRIVE = 2;
//    private final int ROUTE_TYPE_WALK = 3;
//    private final int ROUTE_TYPE_CROSSTOWN = 4;
//
//    //界面控件ID绑定
//    @Bind(R.id.mapView)
//    MapView mapView;
//    @Bind(R.id.route_drive)
//    ImageView routeDrive;
//    @Bind(R.id.route_bus)
//    ImageView routeBus;
//    @Bind(R.id.route_walk)
//    ImageView routeWalk;
//    @Bind(R.id.routemap_choose)
//    LinearLayout routemapChoose;
//    @Bind(R.id.routemap_header)
//    RelativeLayout routemapHeader;
//    @Bind(R.id.ll_way)
//    LinearLayout llWay;
//    @Bind(R.id.bus_result_list)
//    ListView busResultList;
//    @Bind(R.id.bus_result)
//    LinearLayout busResult;
//    @Bind(R.id.firstline)
//    TextView firstline;
//    @Bind(R.id.secondline)
//    TextView secondline;
//    @Bind(R.id.detail)
//    LinearLayout detail;
//    @Bind(R.id.bottom_layout)
//    RelativeLayout bottomLayout;
//    @Bind(R.id.onDriveClick)
//    RelativeLayout onDriveClick;
//    @Bind(R.id.onBusClick)
//    RelativeLayout onBusClick;
//    @Bind(R.id.onWalkClick)
//    RelativeLayout onWalkClick;
//    @Bind(R.id.rl_close)
//    RelativeLayout rlClose;
//    @Bind(R.id.btn_gohome)
//    Button btnGohome;
//    @Bind(R.id.btn_gocompany)
//    Button btnGocompany;
//    //开车
//    private DriveRouteResult mDriveRouteResult;
//    //公交地铁
//    private BusRouteResult mBusRouteResult;
//    //步行
//    private WalkRouteResult mWalkRouteResult;
//    private LatLonPoint mStartPoint = new LatLonPoint(39.942295, 116.335891);//起点，116.335891,39.942295
//    private LatLonPoint mEndPoint = new LatLonPoint(39.995576, 116.481288);//终点，116.481288,39.995576
//    private LatLonPoint mStartPoint_bus = new LatLonPoint(40.818311, 111.670801);//起点，111.670801,40.818311
//    private LatLonPoint mEndPoint_bus = new LatLonPoint(44.433942, 125.184449);//终点，
//    private LatLonPoint homePoint;
//    private LatLonPoint companyPoint;
//    private String mCurrentCityName = "北京";
//    //路径规划搜索器
//    private RouteSearch mRouteSearch;
//
//    public MapFragment() {
//        // Required empty public constructor
//    }
//
//    private AMap aMap;
//
//    @Override
//    protected void load() {
//        mapView.onCreate(null);
//        aMap = mapView.getMap();
//        aMap.setMyLocationEnabled(true);
//        init();
//        if (getCurrentUser()!=null){
//            double homelat = AVUser.getCurrentUser().getDouble(UserTag.HOME_LAT);
//            double homelng = AVUser.getCurrentUser().getDouble(UserTag.HOME_LNG);
//            homePoint = new LatLonPoint(homelat,homelng);
//            double companylat = AVUser.getCurrentUser().getDouble(UserTag.COMPANY_LAT);
//            double companylng = AVUser.getCurrentUser().getDouble(UserTag.COMPANY_LNG);
//            companyPoint = new LatLonPoint(companylat,companylng);
//        }
//
//        setfromandtoMarker();
//    }
//
//    @Override
//    protected IMvpPresenter getPresenter() {
//        return null;
//    }
//
//    /**
//     * 初始化AMap对象
//     */
//    private void init() {
//        if (aMap == null) {
//            aMap = mapView.getMap();
//        }
//        registerListener();
//        mRouteSearch = new RouteSearch(getContext());
//        mRouteSearch.setRouteSearchListener(this);
//    }
//
//    /**
//     * 注册监听
//     */
//    private void registerListener() {
//        aMap.setOnMapClickListener(this);
//        aMap.setOnMarkerClickListener(this);
//        aMap.setOnInfoWindowClickListener(this);
//        aMap.setInfoWindowAdapter(this);
//
//    }
//
//    /**
//     * 添加地图定位点
//     */
//    private void setfromandtoMarker() {
//        aMap.addMarker(new MarkerOptions()
//                .position(AMapUtil.convertToLatLng(homePoint))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.start)));
//        aMap.addMarker(new MarkerOptions()
//                .position(AMapUtil.convertToLatLng(companyPoint))
//                .icon(BitmapDescriptorFactory.fromResource(R.drawable.end)));
//    }
//
//    @Override
//    public View getInfoContents(Marker arg0) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public View getInfoWindow(Marker arg0) {
//        // TODO Auto-generated method stub
//        return null;
//    }
//
//    @Override
//    public void onInfoWindowClick(Marker arg0) {
//        // TODO Auto-generated method stub
//    }
//
//    @Override
//    public boolean onMarkerClick(Marker arg0) {
//        // TODO Auto-generated method stub
//        return false;
//    }
//
//    @Override
//    public void onMapClick(LatLng arg0) {
//        // TODO Auto-generated method stub
//
//    }
//
//
//    //关闭导航结果
//    public void close(View view) {
//        routemapHeader.setVisibility(View.GONE);
//        bottomLayout.setVisibility(View.GONE);
//        busResult.setVisibility(View.GONE);
//        aMap.clear();
//        aMap.setMyLocationEnabled(true);
//    }
//
//    /**
//     * 回家  默认都是公交规划
//     * @param view
//     */
//    public void goHome(View view) {
//        mStartPoint = companyPoint;
//        mEndPoint = homePoint;
//        routemapHeader.setVisibility(View.VISIBLE);
//        searchRoute(ROUTE_TYPE_BUS, RouteSearch.BusDefault, R.drawable.route_drive_normal, R.drawable.route_bus_select, R.drawable.route_walk_normal, View.GONE, View.VISIBLE);
//
//    }
//
//    /**
//     * 上班
//     * @param view
//     */
//    public void goCompany(View view) {
//        mEndPoint = companyPoint;
//        mStartPoint = homePoint;
//        routemapHeader.setVisibility(View.VISIBLE);
//        searchRoute(ROUTE_TYPE_BUS, RouteSearch.BusDefault, R.drawable.route_drive_normal, R.drawable.route_bus_select, R.drawable.route_walk_normal, View.GONE, View.VISIBLE);
//    }
//
//    /**
//     * 搜索线路
//     * @param route_type_bus 交通工具
//     * @param busDefault
//     * @param route_drive_normal
//     * @param route_bus_select
//     * @param route_walk_normal
//     * @param gone
//     * @param visible
//     */
//    private void searchRoute(int route_type_bus, int busDefault, int route_drive_normal, int route_bus_select, int route_walk_normal, int gone, int visible) {
//        aMap.setMyLocationEnabled(false);
//        searchRouteResult(route_type_bus, busDefault);
//        routeDrive.setImageResource(route_drive_normal);
//        routeBus.setImageResource(route_bus_select);
//        routeWalk.setImageResource(route_walk_normal);
//        mapView.setVisibility(gone);
//        busResult.setVisibility(visible);
//    }
//
//    /**
//     * 公交路线搜索
//     */
//    public void onBusClick() {
//        searchRoute(ROUTE_TYPE_BUS, RouteSearch.BusDefault, R.drawable.route_drive_normal, R.drawable.route_bus_select, R.drawable.route_walk_normal, View.GONE, View.VISIBLE);
//    }
//
//    /**
//     * 驾车路线搜索
//     */
//    public void onDriveClick() {
//        searchRoute(ROUTE_TYPE_DRIVE, RouteSearch.DrivingDefault, R.drawable.route_drive_select, R.drawable.route_bus_normal, R.drawable.route_walk_normal, View.VISIBLE, View.GONE);
//    }
//
//    /**
//     * 步行路线搜索
//     */
//    public void onWalkClick() {
//        searchRoute(ROUTE_TYPE_WALK, RouteSearch.WalkDefault, R.drawable.route_drive_normal, R.drawable.route_bus_normal, R.drawable.route_walk_select, View.VISIBLE, View.GONE);
//    }
//
//    /**
//     * 跨城公交路线搜索
//     */
//    public void onCrosstownBusClick() {
//        searchRoute(ROUTE_TYPE_CROSSTOWN, RouteSearch.BusDefault, R.drawable.route_drive_normal, R.drawable.route_bus_normal, R.drawable.route_walk_normal, View.GONE, View.VISIBLE);
//    }
//
//    /**
//     * 开始搜索路径规划方案
//     */
//    public void searchRouteResult(int routeType, int mode) {
//        if (mStartPoint == null) {
//            ToastUtil.show(getContext(), "起点未设置");
//            return;
//        }
//        if (mEndPoint == null) {
//            ToastUtil.show(getContext(), "终点未设置");
//        }
//        showProgressDialog();
//        final RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(
//                mStartPoint, mEndPoint);
//        if (routeType == ROUTE_TYPE_BUS) {// 公交路径规划
//            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo, mode,
//                    mCurrentCityName, 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
//            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
//        } else if (routeType == ROUTE_TYPE_DRIVE) {// 驾车路径规划
//            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, mode, null,
//                    null, "");// 第一个参数表示路径规划的起点和终点，第二个参数表示驾车模式，第三个参数表示途经点，第四个参数表示避让区域，第五个参数表示避让道路
//            mRouteSearch.calculateDriveRouteAsyn(query);// 异步路径规划驾车模式查询
//        } else if (routeType == ROUTE_TYPE_WALK) {// 步行路径规划
//            RouteSearch.WalkRouteQuery query = new RouteSearch.WalkRouteQuery(fromAndTo, mode);
//            mRouteSearch.calculateWalkRouteAsyn(query);// 异步路径规划步行模式查询
//        } else if (routeType == ROUTE_TYPE_CROSSTOWN) {
//            RouteSearch.FromAndTo fromAndTo_bus = new RouteSearch.FromAndTo(
//                    mStartPoint_bus, mEndPoint_bus);
//            RouteSearch.BusRouteQuery query = new RouteSearch.BusRouteQuery(fromAndTo_bus, mode,
//                    "呼和浩特市", 0);// 第一个参数表示路径规划的起点和终点，第二个参数表示公交查询模式，第三个参数表示公交查询城市区号，第四个参数表示是否计算夜班车，0表示不计算
//            query.setCityd("农安县");
//            mRouteSearch.calculateBusRouteAsyn(query);// 异步路径规划公交模式查询
//        }
//    }
//
//    /**
//     * 公交路线搜索结果方法回调
//     */
//    @Override
//    public void onBusRouteSearched(BusRouteResult result, int errorCode) {
//        dissmissProgressDialog();
//        bottomLayout.setVisibility(View.GONE);
//        aMap.clear();// 清理地图上的所有覆盖物
//        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
//            if (result != null && result.getPaths() != null) {
//                if (result.getPaths().size() > 0) {
//                    mBusRouteResult = result;
//                    BusResultListAdapter routeBusResultListAdapter = new BusResultListAdapter(getContext(), mBusRouteResult);
//                    busResultList.setAdapter(routeBusResultListAdapter);
//                } else if (result != null && result.getPaths() == null) {
//                    ToastUtil.show(getContext(), R.string.no_result);
//                }
//            } else {
//                ToastUtil.show(getContext(), R.string.no_result);
//            }
//        } else {
//            ToastUtil.showerror(getContext().getApplicationContext(), errorCode);
//        }
//    }
//
//    /**
//     * 驾车路线搜索结果方法回调
//     */
//    @Override
//    public void onDriveRouteSearched(DriveRouteResult result, int errorCode) {
//        dissmissProgressDialog();
//        aMap.clear();// 清理地图上的所有覆盖物
//        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
//            if (result != null && result.getPaths() != null) {
//                if (result.getPaths().size() > 0) {
//                    mDriveRouteResult = result;
//                    final DrivePath drivePath = mDriveRouteResult.getPaths()
//                            .get(0);
//                    DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
//                            getContext(), aMap, drivePath,
//                            mDriveRouteResult.getStartPos(),
//                            mDriveRouteResult.getTargetPos(), null);
//                    drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
//                    drivingRouteOverlay.setIsColorfulline(true);//是否用颜色展示交通拥堵情况，默认true
//                    drivingRouteOverlay.removeFromMap();
//                    drivingRouteOverlay.addToMap();
//                    drivingRouteOverlay.zoomToSpan();
//                    bottomLayout.setVisibility(View.VISIBLE);
//                    int dis = (int) drivePath.getDistance();
//                    int dur = (int) drivePath.getDuration();
//                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
//                    firstline.setText(des);
//                    detail.setVisibility(View.VISIBLE);
//                    int taxiCost = (int) mDriveRouteResult.getTaxiCost();
//                    secondline.setText("taxi cost" + taxiCost + "yuan");
//                    secondline.setVisibility(View.VISIBLE);
//
//                    bottomLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getContext(),
//                                    DriveRouteDetailActivity.class);
//                            intent.putExtra("drive_path", drivePath);
//                            intent.putExtra("drive_result",
//                                    mDriveRouteResult);
//                            startActivity(intent);
//                        }
//                    });
//                } else if (result != null && result.getPaths() == null) {
//                    ToastUtil.show(getContext(), R.string.no_result);
//                }
//
//            } else {
//                ToastUtil.show(getContext(), R.string.no_result);
//            }
//        } else {
//            ToastUtil.showerror(getContext().getApplicationContext(), errorCode);
//        }
//
//
//    }
//
//    /**
//     * 步行路线搜索结果方法回调
//     */
//    @Override
//    public void onWalkRouteSearched(WalkRouteResult result, int errorCode) {
//        dissmissProgressDialog();
//        aMap.clear();// 清理地图上的所有覆盖物
//        if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
//            if (result != null && result.getPaths() != null) {
//                if (result.getPaths().size() > 0) {
//                    mWalkRouteResult = result;
//                    final WalkPath walkPath = mWalkRouteResult.getPaths()
//                            .get(0);
//                    WalkRouteOverlay walkRouteOverlay = new WalkRouteOverlay(
//                            getContext(), aMap, walkPath,
//                            mWalkRouteResult.getStartPos(),
//                            mWalkRouteResult.getTargetPos());
//                    walkRouteOverlay.removeFromMap();
//                    walkRouteOverlay.addToMap();
//                    walkRouteOverlay.zoomToSpan();
//                    bottomLayout.setVisibility(View.VISIBLE);
//                    int dis = (int) walkPath.getDistance();
//                    int dur = (int) walkPath.getDuration();
//                    String des = AMapUtil.getFriendlyTime(dur) + "(" + AMapUtil.getFriendlyLength(dis) + ")";
//                    firstline.setText(des);
//                    detail.setVisibility(View.VISIBLE);
//                    secondline.setVisibility(View.GONE);
//                    bottomLayout.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = new Intent(getContext(),
//                                    WalkRouteDetailActivity.class);
//                            intent.putExtra("walk_path", walkPath);
//                            intent.putExtra("walk_result",
//                                    mWalkRouteResult);
//                            startActivity(intent);
//                        }
//                    });
//                } else if (result != null && result.getPaths() == null) {
//                    ToastUtil.show(getContext(), R.string.no_result);
//                }
//
//            } else {
//                ToastUtil.show(getContext(), R.string.no_result);
//            }
//        } else {
//            ToastUtil.showerror(getContext().getApplicationContext(), errorCode);
//        }
//    }
//
//    @Override
//    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
//
//    }
//
//    /**
//     * 显示进度框
//     */
//    private void showProgressDialog() {
//        showLoading("loading");
//    }
//
//    /**
//     * 隐藏进度框
//     */
//    private void dissmissProgressDialog() {
//        hideLoading();
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    public void onResume() {
//        super.onResume();
//        if (mapView == null) return;
//        mapView.onResume();
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mapView != null) {
//            mapView.onPause();
//        }
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        if (mapView != null) {
//            mapView.onSaveInstanceState(outState);
//        }
//    }
//
//    /**
//     * 方法必须重写
//     */
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        if (mapView == null) return;
//        mapView.onDestroy();
//    }
//
//    //点击事件绑定
//    @OnClick({R.id.onDriveClick, R.id.onBusClick, R.id.onWalkClick, R.id.rl_close, R.id.btn_gohome, R.id.btn_gocompany})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.onDriveClick:
//                onDriveClick();
//                break;
//            case R.id.onBusClick:
//                onBusClick();
//                break;
//            case R.id.onWalkClick:
//                onWalkClick();
//                break;
//            case R.id.rl_close:
//                close(view);
//                break;
//            case R.id.btn_gohome:
//                goHome(view);
//                break;
//            case R.id.btn_gocompany:
//                goCompany(view);
//                break;
//        }
//    }
//}
