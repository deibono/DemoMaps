package com.techacademy.demomaps.Defi;


import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.techacademy.demomaps.AndroidGPSTrackingActivity;
import com.techacademy.demomaps.GPSTracker;
import com.techacademy.demomaps.R;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap map) {

        // Add a marker in Sydney, Australia, and move the camera.
        GPSTracker gps = new GPSTracker(MapsActivity.this);

        if (gps.canGetLocation()) {//check if the gps function is activate on the mobile
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            LatLng user = new LatLng(latitude,longitude);
            LatLng TourE = new LatLng(48.8583701, 2.2944813);

            map.addMarker(new MarkerOptions().position(user).title("Vous etes ici"));
            map.addMarker(new MarkerOptions().position(TourE).title(AndroidGPSTrackingActivity.toureiffel).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

            float zoomLevel = 11; //This goes up to 21
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(TourE, zoomLevel));
        }
    }


}