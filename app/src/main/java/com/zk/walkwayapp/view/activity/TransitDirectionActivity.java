package com.zk.walkwayapp.view.activity;

import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Step;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.zk.walkwayapp.R;

import java.util.ArrayList;
import java.util.List;

public class TransitDirectionActivity extends AppCompatActivity implements OnMapReadyCallback, View.OnClickListener, DirectionCallback {
    private GoogleMap googleMap;
    private String serverKey = "AIzaSyAKcHQUqbaZfC_IxLrzoW1MNvOF4NFo0Bk";
    private LatLng origin = new LatLng(13.7371063, 100.5642539);
    private LatLng destination = new LatLng(13.7604896, 100.5594266);
    private TextView tvDetail;
    private TextView tvShowDetail;
    private Toolbar toolbar;
    boolean isShowing = true;
    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transit_direction);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        toolbar.setTitle("TransitDirection");
        tvDetail = (TextView) findViewById(R.id.tvDetail);
        tvDetail.setMovementMethod(ScrollingMovementMethod.getInstance());
        tvShowDetail = (TextView) findViewById(R.id.showDetail);
        tvShowDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isShowing){
                    tvShowDetail.setText("show detail");
                    isShowing = false;
                    tvDetail.setVisibility(View.GONE);
                }else {
                    tvShowDetail.setText("close detail");
                    isShowing = true;
                    tvDetail.setVisibility(View.VISIBLE);
                }
            }
        });
        origin = getIntent().getParcelableExtra("orgin");
        destination = getIntent().getParcelableExtra("destination");
        ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMapAsync(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(origin, 14));
        // 1
        requestDirection();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_request_direction) {
            requestDirection();
        }
    }

    public void requestDirection() {
        GoogleDirection.withServerKey(serverKey)
                .from(origin)
                .to(destination)
                .transportMode(TransportMode.TRANSIT)
                .execute(this);
    }

    @Override
    public void onDirectionSuccess(Direction direction, String rawBody) {
        if (direction.isOK()) {
            ArrayList<LatLng> sectionPositionList =
                    direction.getRouteList().get(0).getLegList().get(0).getSectionPoint();
            for (LatLng position : sectionPositionList) {
                googleMap.addMarker(new MarkerOptions().position(position));
            }
            List<Step> stepList = direction.getRouteList().get(0).getLegList().get(0).getStepList();
            StringBuffer buffer = new StringBuffer();
            for (Step step:stepList){
                buffer.append(step.getHtmlInstruction()+"\n");
                if (step.getStepList()!=null){
                    for (Step step1:step.getStepList()){
                        buffer.append(step1.getHtmlInstruction()+"\n");
                   }
                }
            }
            tvDetail.setText(Html.fromHtml(buffer.toString()));
            ArrayList<PolylineOptions> polylineOptionList =
                    DirectionConverter.createTransitPolyline(this, stepList, 5, Color.RED, 3, Color.BLUE);
            for (PolylineOptions polylineOption : polylineOptionList) {
                googleMap.addPolyline(polylineOption);
            }

        }
    }

    @Override
    public void onDirectionFailure(Throwable t) {
    }
}
