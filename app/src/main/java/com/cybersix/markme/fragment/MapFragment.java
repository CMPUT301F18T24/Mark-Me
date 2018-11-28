package com.cybersix.markme.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybersix.markme.R;
import com.cybersix.markme.controller.RecordController;
import com.cybersix.markme.model.RecordModel;
import com.cybersix.markme.controller.NavigationController;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;






//Credit: http://www.zoftino.com/android-mapview-tutorial
public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private GoogleMap gmap;
    private RecordController recordController = RecordController.getInstance();
    private MapView g_map;


    /*
        Credit for implementation of Google Maps with Fragments:
        http://androiddhina.blogspot.com/2017/11/how-to-use-google-map-in-fragment.html
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View root = inflater.inflate(R.layout.activity_map, container, false);
        g_map = root.findViewById(R.id.g_map);
        g_map.onCreate(savedInstanceState);
        g_map.onResume();
        recordController.addRecordLists();

        try{
            MapsInitializer.initialize(root.getContext());
            g_map.getMapAsync(this);

        } catch (Exception e){
            e.printStackTrace();
        }

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        com.google.android.gms.maps.MapFragment map = (com.google.android.gms.maps.MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.g_map);
        try{
            map.getMapAsync(this);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap = googleMap;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setZoomGesturesEnabled(true);
        googleMap.getUiSettings().setRotateGesturesEnabled(true);
        googleMap.setMinZoomPreference(12);

        //Set click listener
        googleMap.setOnMarkerClickListener(this);

        //Start camera showing uni
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(53.5232,-113.5263)));

        //Add markers for all records
        int idx = 0;
        for(RecordModel r : recordController.records)
        {
            //Only if they have a location
            if(r.getMapLocation() != null){
                LatLng ln = new LatLng(r.getMapLocation().latitude,r.getMapLocation().longitude);

                //Tag here is used to get marker data
                googleMap.addMarker(new MarkerOptions().position(ln)
                        .title(r.getTitle())
                        .snippet(r.getDescription())).setTag(idx);
            }

            idx++;
        }
    }

    //Handles marker clicks
    @Override
    public boolean onMarkerClick(final Marker marker){
        //Tag is idx of selected record
        int idx = (int) marker.getTag();
        Bundle b = new Bundle();
        b.putInt("MapRecordIdx", idx);
        NavigationController.getInstance().switchToFragment(RecordInfoFragment.class,b);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        g_map.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        g_map.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        g_map.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        g_map.onLowMemory();
    }

}
