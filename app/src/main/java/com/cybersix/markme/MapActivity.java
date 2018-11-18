package com.cybersix.markme;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import static java.security.AccessController.getContext;


//Credit: http://www.zoftino.com/android-mapview-tutorial
public class MapActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap gmap;
    private RecordController recordController = RecordController.getInstance();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.settings:
                    return true;
                case R.id.gps:
                    return true;
                case R.id.body:
                    return true;
                case R.id.gallery:
                    return true;
                case R.id.list:
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.g_map);
        try{
            map.getMapAsync(this);
        } catch(Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        MapsInitializer.initialize(getApplicationContext());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.setMinZoomPreference(12);

        //Set click listener
        googleMap.setOnMarkerClickListener(this);

        //Start camera showing uni
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(53.5232,-113.5263)));

        //Add markers for all records
        for(RecordModel r : recordController.records)
        {
            LatLng ln = new LatLng(r.getMapLocation().getLatitude(),r.getMapLocation().getLongitude());
            MarkerOptions mo = new MarkerOptions();
            mo.position(ln);
            mo.title(r.getTitle());
            mo.snippet(r.getDescription());
            //Tag here is used to get marker data
            googleMap.addMarker(mo).setTag(r);
        }
    }

    //Handles marker clicks
    @Override
    public boolean onMarkerClick(final Marker marker){
        Log.d("Marker Selected" ,((RecordModel)marker.getTag()).getBodyLocation().getBodyPart().toString());
        return true;
    }


}
