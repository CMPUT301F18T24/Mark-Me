/**
 * CMPUT 301 Team 24
 *
 * This is the map selection activity. This activity is prompted when the user wishes to view the
 * map from the record information activity
 *
 * Version 0.1
 *
 * Date: 2018-11-14
 *
 * Copyright Notice
 * @author Curtis Goud
 * @see com.cybersix.markme.fragment.MapFragment
 */
package com.cybersix.markme.actvity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cybersix.markme.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapSelectActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap gmap;
    private LatLng currentLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_select);
        Intent i = getIntent();
        currentLoc = i.getParcelableExtra("startLoc");
        SupportMapFragment map = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.g_map_select);
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

        if(currentLoc != null){
            googleMap.addMarker(new MarkerOptions().position(currentLoc)
                                                    .title("Current Set Location"));
        }

        googleMap.setOnMapLongClickListener(this);

        //Start camera showing uni
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(53.5232,-113.5263)));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        newLocationAlert(latLng);
    }


    private void newLocationAlert(final LatLng latLng){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Location Selected!");
        builder.setMessage("Would you like this to be the location for your record?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent data = new Intent();
                data.putExtra("locations", latLng);
                setResult(RESULT_OK, data);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        builder.show();
    }
}
