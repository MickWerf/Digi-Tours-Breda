package com.mickwerf.digi_tours_breda.gui.fragments;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithLocations;
import com.mickwerf.digi_tours_breda.gui.NextLocationAdapter;
import com.mickwerf.digi_tours_breda.gui.NextLocationItem;

import java.util.LinkedList;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;


public class MapScreenFragment extends Fragment {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private MapView mapView;

    private MyLocationNewOverlay locationOverlay;
    private MapController mapController;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private final LinkedList<NextLocationItem> mLocationList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private NextLocationAdapter mAdapter;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private MutableLiveData<RouteWithLocations> routeData;

    public MapScreenFragment(MutableLiveData<RouteWithLocations> routeData) {
        this.routeData = routeData;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Configuration.getInstance().load(getActivity().getApplication(), PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()));

        requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION
        }, REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map_screen, container, false);

//        this.mapView = getActivity().findViewById(R.id.map_view);
//        this.mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
//        this.mapView.setMultiTouchControls(true);
//        this.mapView.setTileSource(TileSourceFactory.MAPNIK);
//
//        this.locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity().getApplicationContext()), this.mapView);
//        this.locationOverlay.enableMyLocation();
//        this.locationOverlay.enableFollowLocation();
//        this.mapView.getOverlays().add(this.locationOverlay);
//        this.mapController = new MapController(this.mapView);
//        this.mapController.zoomTo(19);
//        this.mapController.setCenter(locationOverlay.getMyLocation());
//        this.mapController.animateTo(locationOverlay.getMyLocation());

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        this.mapView.onResume();
        this.locationOverlay.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.mapView.onPause();
        this.locationOverlay.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        ArrayList<String> permissionsToRequest = new ArrayList<>();
        for (int i = 0; i < grantResults.length; i++) {
            permissionsToRequest.add(permissions[i]);
        }
        if (permissionsToRequest.size() > 0) {
            ActivityCompat.requestPermissions(
                    this.getActivity(),
                    permissionsToRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.mapView = getActivity().findViewById(R.id.map_view);
        this.mapView.getZoomController().setVisibility(CustomZoomButtonsController.Visibility.NEVER);
        this.mapView.setMultiTouchControls(true);
        this.mapView.setTileSource(TileSourceFactory.MAPNIK);

        this.locationOverlay = new MyLocationNewOverlay(new GpsMyLocationProvider(getActivity().getApplicationContext()), this.mapView);
        this.locationOverlay.enableMyLocation();
        this.locationOverlay.enableFollowLocation();
        this.mapView.getOverlays().add(this.locationOverlay);
        this.mapController = new MapController(this.mapView);
        this.mapController.zoomTo(19);
        this.mapController.setCenter(locationOverlay.getMyLocation());
        this.mapController.animateTo(locationOverlay.getMyLocation());

        if(this.routeData.getValue() != null) {
            for (Location location : this.routeData.getValue().getLocations()) {
                mLocationList.add(new NextLocationItem(location.getLocationName()));
            }
        }else {

        mLocationList.add(new NextLocationItem("test1"));
        mLocationList.add(new NextLocationItem("test2"));
        mLocationList.add(new NextLocationItem("test3"));
        mLocationList.add(new NextLocationItem("test4"));
        mLocationList.add(new NextLocationItem("test5"));
        mLocationList.add(new NextLocationItem("test6"));
        mLocationList.add(new NextLocationItem("test7"));
            //TODO: deletetest code ^
        }

        // Create recycler view.
        mRecyclerView = getView().findViewById(R.id.NextLocationRecyclerview);
        // Create an adapter and supply the data to be displayed.
        mAdapter = new NextLocationAdapter(getContext(), mLocationList);
        // Connect the adapter with the recycler view.
        mRecyclerView.setAdapter(mAdapter);
        // Give the recycler view a default layout manager.
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}