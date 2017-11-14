package com.zk.walkwayapp.view.fragment;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.zk.library.common.mvp.BaseFragment;
import com.zk.library.common.mvp.ContentView;
import com.zk.library.common.mvp.IMvpPresenter;
import com.zk.walkwayapp.R;
import com.zk.walkwayapp.model.bean.UserTag;
import com.zk.walkwayapp.view.activity.TransitDirectionActivity;

import butterknife.Bind;
import butterknife.OnClick;

import static com.avos.avoscloud.AVUser.getCurrentUser;

/**
 * A simple {@link Fragment} subclass.
 */
@ContentView(R.layout.fragment_gmap)
public class GMapFragment extends BaseFragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    @Bind(R.id.route_drive)
    ImageView routeDrive;
    @Bind(R.id.onDriveClick)
    RelativeLayout onDriveClick;
    @Bind(R.id.route_bus)
    ImageView routeBus;
    @Bind(R.id.onBusClick)
    RelativeLayout onBusClick;
    @Bind(R.id.route_walk)
    ImageView routeWalk;
    @Bind(R.id.onWalkClick)
    RelativeLayout onWalkClick;
    @Bind(R.id.rl_close)
    RelativeLayout rlClose;
    @Bind(R.id.routemap_choose)
    LinearLayout routemapChoose;
    @Bind(R.id.routemap_header)
    RelativeLayout routemapHeader;
    @Bind(R.id.btn_gohome)
    Button btnGohome;
    @Bind(R.id.btn_gocompany)
    Button btnGocompany;
    @Bind(R.id.ll_way)
    LinearLayout llWay;
    @Bind(R.id.firstline)
    TextView firstline;
    @Bind(R.id.secondline)
    TextView secondline;
    @Bind(R.id.detail)
    LinearLayout detail;
    @Bind(R.id.bottom_layout)
    RelativeLayout bottomLayout;
    @Bind(R.id.bus_result_list)
    ListView busResultList;
    @Bind(R.id.bus_result)
    LinearLayout busResult;
    private LatLng homePoint;
    private LatLng companyPoint;
    private GoogleMap googleMap;
    LatLng camera;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    @Override
    protected void load() {
        if (getCurrentUser()!=null){
            double homelat = getCurrentUser().getDouble(UserTag.HOME_LAT);
            double homelng = getCurrentUser().getDouble(UserTag.HOME_LNG);
            homePoint = new LatLng(homelat,homelng);
            double companylat = getCurrentUser().getDouble(UserTag.COMPANY_LAT);
            double companylng = getCurrentUser().getDouble(UserTag.COMPANY_LNG);
            companyPoint = new LatLng(companylat,companylng);
            camera = new LatLng(homelat,homelng);
        }
        FragmentManager fm = getChildFragmentManager();
        SupportMapFragment mapFragment = (SupportMapFragment) fm.findFragmentByTag("mapFragment");
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            FragmentTransaction ft = fm.beginTransaction();
            ft.add(R.id.mapFragmentContainer, mapFragment, "mapFragment");
            ft.commit();
            fm.executePendingTransactions();
        }

        mapFragment.getMapAsync(this);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    protected IMvpPresenter getPresenter() {
        return null;
    }
    //点击事件绑定
    @OnClick({R.id.onDriveClick, R.id.onBusClick, R.id.onWalkClick, R.id.rl_close, R.id.btn_gohome, R.id.btn_gocompany})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.onDriveClick:
                onDriveClick();
                break;
            case R.id.onBusClick:
                onBusClick();
                break;
            case R.id.onWalkClick:
                onWalkClick();
                break;
            case R.id.rl_close:
                close(view);
                break;
            case R.id.btn_gohome:
                goHome(view);
                break;
            case R.id.btn_gocompany:
                goCompany(view);
                break;
        }
    }
    //关闭导航结果
    public void close(View view) {
        routemapHeader.setVisibility(View.GONE);
        bottomLayout.setVisibility(View.GONE);
    }

    private void goCompany(View view) {
        Intent intent = new Intent(getActivity(), TransitDirectionActivity.class);
        intent.putExtra("orgin",homePoint);
        intent.putExtra("destination",companyPoint);
        startActivity(intent);

    }

    private void goHome(View view) {
        Intent intent = new Intent(getActivity(), TransitDirectionActivity.class);
        intent.putExtra("orgin",companyPoint);
        intent.putExtra("destination",homePoint);
        startActivity(intent);
    }

    private void onWalkClick() {

    }

    private void onBusClick() {

    }

    private void onDriveClick() {

    }
    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if( mGoogleApiClient != null && mGoogleApiClient.isConnected() ) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        setUpMap();
    }

    private void setUpMap() {
        googleMap.setMyLocationEnabled(true);
        LocationAvailability locationAvailability =
                LocationServices.FusedLocationApi.getLocationAvailability(mGoogleApiClient);
        if (null != locationAvailability && locationAvailability.isLocationAvailable()) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            if (mLastLocation != null) {
                LatLng currentLocation = new LatLng(mLastLocation.getLatitude(), mLastLocation
                        .getLongitude());
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 12));
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
