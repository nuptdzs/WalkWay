//package com.zk.walkwayapp.view.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.util.Log;
//import android.view.View;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.amap.api.services.route.DrivePath;
//import com.amap.api.services.route.DriveRouteResult;
//import com.amap.api.services.route.DriveStep;
//import com.amap.api.services.route.TMC;
//import com.zk.library.common.mvp.BaseActivity;
//import com.zk.library.common.mvp.IMvpPresenter;
//import com.zk.walkwayapp.R;
//import com.zk.walkwayapp.adapter.DriveSegmentListAdapter;
//import com.zk.walkwayapp.utils.AMapUtil;
//
//import java.util.List;
//
///**
// * 驾车路线详情
// */
//public class DriveRouteDetailActivity extends BaseActivity {
//	private DrivePath mDrivePath;
//	private DriveRouteResult mDriveRouteResult;
//	private TextView mTitle, mTitleDriveRoute, mDesDriveRoute;
//	private ListView mDriveSegmentList;
//	private DriveSegmentListAdapter mDriveSegmentListAdapter;
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_route_detail);
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
//	private Toolbar toolbar;
//	private void init() {
//		toolbar = (Toolbar) findViewById(R.id.toolbar);
//		toolbar.setTitle("DriveRouteDetail");
//		setSupportActionBar(toolbar);
//		toolbar.setNavigationIcon(R.mipmap.icon_back);
//		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//			}
//		});
//		mTitleDriveRoute = (TextView) findViewById(R.id.firstline);
//		mDesDriveRoute = (TextView) findViewById(R.id.secondline);
//		String dur = AMapUtil.getFriendlyTime((int) mDrivePath.getDuration());
//		String dis = AMapUtil.getFriendlyLength((int) mDrivePath
//				.getDistance());
//		mTitleDriveRoute.setText(dur + "(" + dis + ")");
//		int taxiCost = (int) mDriveRouteResult.getTaxiCost();
//		mDesDriveRoute.setText("打车约"+taxiCost+"元");
//		mDesDriveRoute.setVisibility(View.VISIBLE);
//		configureListView();
//	}
//
//	private void configureListView() {
//		mDriveSegmentList = (ListView) findViewById(R.id.bus_segment_list);
//		mDriveSegmentListAdapter = new DriveSegmentListAdapter(
//				this.getApplicationContext(), mDrivePath.getSteps());
//		mDriveSegmentList.setAdapter(mDriveSegmentListAdapter);
//	}
//
//	private void getIntentData() {
//		Intent intent = getIntent();
//		if (intent == null) {
//			return;
//		}
//		mDrivePath = intent.getParcelableExtra("drive_path");
//		mDriveRouteResult = intent.getParcelableExtra("drive_result");
//		for (int i = 0; i < mDrivePath.getSteps().size(); i++) {
//			DriveStep step = mDrivePath.getSteps().get(i);
//			List<TMC> tmclist = step.getTMCs();
//			for (int j = 0; j < tmclist.size(); j++) {
//				String s = ""+tmclist.get(j).getPolyline().size();
//				Log.i("MY", s+tmclist.get(j).getStatus()
//						+tmclist.get(j).getDistance()
//						+tmclist.get(j).getPolyline().toString());
//			}
//		}
//	}
//
//	public void onBackClick(View view) {
//		this.finish();
//	}
//}
