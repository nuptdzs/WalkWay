//package com.zk.walkwayapp.view.activity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.widget.Toolbar;
//import android.view.View;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.amap.api.services.route.WalkPath;
//import com.zk.library.common.mvp.BaseActivity;
//import com.zk.library.common.mvp.IMvpPresenter;
//import com.zk.walkwayapp.R;
//import com.zk.walkwayapp.adapter.WalkSegmentListAdapter;
//import com.zk.walkwayapp.utils.AMapUtil;
//
//public class WalkRouteDetailActivity extends BaseActivity {
//	private WalkPath mWalkPath;
//	private TextView mTitle,mTitleWalkRoute;
//	private ListView mWalkSegmentList;
//	private WalkSegmentListAdapter mWalkSegmentListAdapter;
//	private Toolbar toolbar;
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_route_detail);
//		getIntentData();
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
//		mTitleWalkRoute = (TextView) findViewById(R.id.firstline);
//		String dur = AMapUtil.getFriendlyTime((int) mWalkPath.getDuration());
//		String dis = AMapUtil
//				.getFriendlyLength((int) mWalkPath.getDistance());
//		mTitleWalkRoute.setText(dur + "(" + dis + ")");
//		mWalkSegmentList = (ListView) findViewById(R.id.bus_segment_list);
//		mWalkSegmentListAdapter = new WalkSegmentListAdapter(
//				this.getApplicationContext(), mWalkPath.getSteps());
//		mWalkSegmentList.setAdapter(mWalkSegmentListAdapter);
//
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
//		if (intent == null) {
//			return;
//		}
//		mWalkPath = intent.getParcelableExtra("walk_path");
//	}
//
//	public void onBackClick(View view) {
//		this.finish();
//	}
//
//}
