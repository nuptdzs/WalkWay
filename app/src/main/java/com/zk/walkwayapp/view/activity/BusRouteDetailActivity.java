//package com.zk.walkwayapp.view.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.LinearLayout;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.amap.api.maps.AMap;
//import com.amap.api.maps.AMap.InfoWindowAdapter;
//import com.amap.api.maps.AMap.OnInfoWindowClickListener;
//import com.amap.api.maps.AMap.OnMapClickListener;
//import com.amap.api.maps.AMap.OnMapLoadedListener;
//import com.amap.api.maps.AMap.OnMarkerClickListener;
//import com.amap.api.maps.MapView;
//import com.amap.api.maps.model.LatLng;
//import com.amap.api.maps.model.Marker;
//import com.amap.api.services.route.BusPath;
//import com.amap.api.services.route.BusRouteResult;
//import com.zk.library.common.mvp.BaseActivity;
//import com.zk.library.common.mvp.ContentView;
//import com.zk.library.common.mvp.IMvpPresenter;
//import com.zk.walkwayapp.R;
//import com.zk.walkwayapp.adapter.BusSegmentListAdapter;
//import com.zk.walkwayapp.overlay.BusRouteOverlay;
//import com.zk.walkwayapp.utils.AMapUtil;
//
//@ContentView(R.layout.activity_route_detail)
//public class BusRouteDetailActivity extends BaseActivity implements OnMapLoadedListener,
//        OnMapClickListener, InfoWindowAdapter, OnInfoWindowClickListener, OnMarkerClickListener {
//	private AMap aMap;
//	private MapView mapView;
//	private BusPath mBuspath;
//	private TextView tvMap;
//	private BusRouteResult mBusRouteResult;
//	private TextView mTitle, mTitleBusRoute, mDesBusRoute;
//	private ListView mBusSegmentList;
//	private BusSegmentListAdapter mBusSegmentListAdapter;
//	private LinearLayout mBuspathview;
//	private BusRouteOverlay mBusrouteOverlay;
//	private Toolbar toolbar;
//	private boolean isMap = false;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		toolbar = (Toolbar) findViewById(R.id.toolbar);
//		toolbar.setTitle("BusRouteDetail");
//		setSupportActionBar(toolbar);
//		toolbar.setNavigationIcon(R.mipmap.icon_back);
//		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//		tvMap = (TextView) findViewById(R.id.tv_map);
//		tvMap.setVisibility(View.VISIBLE);
//		tvMap.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (isMap){
//					isMap = false;
//					mBuspathview.setVisibility(View.VISIBLE);
//					mapView.setVisibility(View.GONE);
//					tvMap.setText("map");
//				}else {
//					isMap = true;
//					tvMap.setText("detail");
//					mBuspathview.setVisibility(View.GONE);
//					mapView.setVisibility(View.VISIBLE);
//					aMap.clear();// 清理地图上的所有覆盖物
//					mBusrouteOverlay = new BusRouteOverlay(getContext(), aMap,
//							mBuspath, mBusRouteResult.getStartPos(),
//							mBusRouteResult.getTargetPos());
//					mBusrouteOverlay.removeFromMap();
//					mBusrouteOverlay.addToMap();
//					mBusrouteOverlay.zoomToSpan();
//				}
//			}
//		});
//		mapView = (MapView) findViewById(R.id.route_map);
//		mapView.onCreate(savedInstanceState);// 此方法必须重写
//		getIntentData();
//		init();
//	}
//
//	@Override
//	protected IMvpPresenter getPresenter() {
//		return null;
//	}
//
//	@Override
//	protected void addEvent() {
//
//	}
//
//	@Override
//	protected void loadData() {
//
//	}
//
//	private void getIntentData() {
//		Intent intent = getIntent();
//		if (intent != null) {
//			mBuspath = intent.getParcelableExtra("bus_path");
//			mBusRouteResult = intent.getParcelableExtra("bus_result");
//		}
//	}
//
//	private void init() {
//		if (aMap == null) {
//			aMap = mapView.getMap();
//		}
//		registerListener();
//
//		mTitleBusRoute = (TextView) findViewById(R.id.firstline);
//		mDesBusRoute = (TextView) findViewById(R.id.secondline);
//		String dur = AMapUtil.getFriendlyTime((int) mBuspath.getDuration());
//		String dis = AMapUtil.getFriendlyLength((int) mBuspath.getDistance());
//		mTitleBusRoute.setText(dur + "(" + dis + ")");
//		int taxiCost = (int) mBusRouteResult.getTaxiCost();
//		mDesBusRoute.setText("打车约"+taxiCost+"元");
//		mDesBusRoute.setVisibility(View.VISIBLE);
//		mBuspathview = (LinearLayout)findViewById(R.id.bus_path);
//		configureListView();
//	}
//
//	private void registerListener() {
//		aMap.setOnMapLoadedListener(this);
//		aMap.setOnMapClickListener(this);
//		aMap.setOnMarkerClickListener(this);
//		aMap.setOnInfoWindowClickListener(this);
//		aMap.setInfoWindowAdapter(this);
//	}
//
//	private void configureListView() {
//		mBusSegmentList = (ListView) findViewById(R.id.bus_segment_list);
//		mBusSegmentListAdapter = new BusSegmentListAdapter(
//				this.getApplicationContext(), mBuspath.getSteps());
//		mBusSegmentList.setAdapter(mBusSegmentListAdapter);
//
//	}
//
//	public void onBackClick(View view) {
//		this.finish();
//	}
//
//	public void onMapClick(View view) {
//		mBuspathview.setVisibility(View.GONE);
//		mapView.setVisibility(View.VISIBLE);
//		aMap.clear();// 清理地图上的所有覆盖物
//		mBusrouteOverlay = new BusRouteOverlay(this, aMap,
//				mBuspath, mBusRouteResult.getStartPos(),
//				mBusRouteResult.getTargetPos());
//		mBusrouteOverlay.removeFromMap();
//
//	}
//
//	@Override
//	public void onMapLoaded() {
//		if (mBusrouteOverlay != null) {
//			mBusrouteOverlay.addToMap();
//			mBusrouteOverlay.zoomToSpan();
//		}
//	}
//
//	@Override
//	public void onMapClick(LatLng arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public View getInfoContents(Marker arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public View getInfoWindow(Marker arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void onInfoWindowClick(Marker arg0) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public boolean onMarkerClick(Marker arg0) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//
//}
