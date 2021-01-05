package com.mickwerf.digi_tours_breda.gui.fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.relations.LocationElements;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithLocations;
import com.mickwerf.digi_tours_breda.gui.NextLocationAdapter;
import com.mickwerf.digi_tours_breda.gui.NextLocationItem;
import com.mickwerf.digi_tours_breda.live_data.MainViewModel;
import com.mickwerf.digi_tours_breda.live_data.route_logic.ors.RouteCallGet;
import com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models.Coordinate;

import java.util.LinkedList;

import org.osmdroid.config.Configuration;
import org.osmdroid.library.BuildConfig;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.CustomZoomButtonsController;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import org.osmdroid.views.overlay.Polyline;
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider;
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay;

import java.util.ArrayList;
import java.util.List;


public class MapScreenFragment extends Fragment {

    private final int REQUEST_PERMISSIONS_REQUEST_CODE = 1;

    private MapView mapView;

    private MyLocationNewOverlay locationOverlay;
    private MapController mapController;

    private final LinkedList<NextLocationItem> mLocationList = new LinkedList<>();
    private RecyclerView mRecyclerView;
    private NextLocationAdapter mAdapter;

    private MainViewModel mainViewModel;
    private RouteWithLocations activeRoute;

    private Context context;

    private ArrayList<Coordinate> coordinates;

    private List<Location> locations;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;



    Observer<RouteWithLocations> activeRouteObserver = new Observer<RouteWithLocations>() {
        @Override
        public void onChanged(RouteWithLocations newActiveRoute) {
            activeRoute = newActiveRoute;
        }
    };


    public MapScreenFragment(MainViewModel mainViewModel, Context context) {
        this.mainViewModel = mainViewModel;
        this.context = context;
        this.dialogBuilder = new AlertDialog.Builder(context);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //Configuration.getInstance().load(getActivity().getApplication(), PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()));

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
        this.mapController.zoomTo(5);
        this.mapController.setCenter(locationOverlay.getMyLocation());
        this.mapController.animateTo(locationOverlay.getMyLocation());
//        this.mapController.setCenter(new GeoPoint(4.780642,51.588949));

        this.mainViewModel.getActiveRoute().observe(this,this.activeRouteObserver);
        this.activeRoute = this.mainViewModel.getActiveRoute2();

        Configuration.getInstance().setUserAgentValue(BuildConfig.APPLICATION_ID);



//        Runnable runnable = () -> {
//            this.activeRoute = this.mainViewModel.getActiveRoute().getValue();
//
//        };
//        Thread t = new Thread(runnable);
//        t.start();
//        try {
//            t.join();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }



        if(this.activeRoute != null) {
            mLocationList.clear();
            for (Location location : activeRoute.getLocations()) {
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

        RouteWithLocations route = mainViewModel.getActiveRoute2();

        List<Location> locations = route.getLocations();

        List<GpsCoordinate> LocationCoordinateList = this.mainViewModel.getLocationCoordinates(locations);

//        LocationCoordinateList.clear();
//        LocationCoordinateList.add(new GpsCoordinate(37.421022, -122.086627,"AA"));
//        LocationCoordinateList.add(new GpsCoordinate(37.423834, -122.090104,"BB"));
//        LocationCoordinateList.add(new GpsCoordinate(37.427498, -122.099427,"AA"));


//        System.out.println("SIZE1: "+LocationCoordinateList.size());
//        System.out.println("GPS COORD: "+ LocationCoordinateList.get(0).getLocation().getLocationName());


//        Coordinate start = new Coordinate(-122.086549, 37.421034);
//        Coordinate end = new Coordinate(-122.077987, 37.423411);

        GeoPoint startPoint = new GeoPoint(LocationCoordinateList.get(0).getLatitude(), LocationCoordinateList.get(0).getLongitude());
        DrawWayPoint(startPoint,locations.get(0));

//        GeoPoint start3 = new GeoPoint(LocationCoordinateList.get(1).getLatitude(), LocationCoordinateList.get(1).getLongitude());
//        DrawWayPoint(start3);

        for(int i = 0; i<LocationCoordinateList.size()-1;i++) {

            Coordinate start = new Coordinate(LocationCoordinateList.get(i).getLongitude(), LocationCoordinateList.get(i).getLatitude());
            Coordinate end = new Coordinate(LocationCoordinateList.get(i+1).getLongitude(), LocationCoordinateList.get(i+1).getLatitude());

            GeoPoint point = new GeoPoint(LocationCoordinateList.get(i+1).getLatitude(), LocationCoordinateList.get(i+1).getLongitude());
            DrawWayPoint(point,locations.get(i+1));

            new RouteCallGet.Builder(
                    start,
                    end,
                    this.context
            ).Call(apiResponse -> {
                coordinates = apiResponse.getCoordinates();
                DrawRoute(coordinates);
            });

        }


    }

    public void DrawRoute(ArrayList<Coordinate> coordinates){
        ArrayList<GeoPoint> geoPoints = new ArrayList<>();
        for (Coordinate coordinate : coordinates){
            geoPoints.add(new GeoPoint(coordinate.getLatitude(),coordinate.getLongitude()));
        }

        Polyline line = new Polyline();
        line.setPoints(geoPoints);
        mapView.getOverlayManager().add(line);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public void DrawWayPoint(GeoPoint geoPoint, Location location){
        Marker marker = new Marker(mapView);
        marker.setIcon(getResources().getDrawable(R.drawable.place_icon_gray, context.getTheme()));
        marker.setTitle(location.getLocationName());
        marker.setPosition(geoPoint);
        marker.setAnchor(Marker.ANCHOR_CENTER,Marker.ANCHOR_BOTTOM);

        marker.setOnMarkerClickListener(new Marker.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker, MapView mapView) {
                CreateInfoPopup(location);
                return true;
            }
        });

        mapView.getOverlays().add(marker);
    }

    private TextView infoTV;
    private TextView titleTV;
    private Button okButton;
    private ImageView imageView;

    @SuppressLint("SetTextI18n")
    public void CreateInfoPopup(Location location){
        View popup = getLayoutInflater().inflate(R.layout.info_location_popup,null);

        this.titleTV = (TextView) popup.findViewById(R.id.infoLocationTitle);
        this.titleTV.setText(location.getLocationName());
        this.infoTV = (TextView) popup.findViewById(R.id.infoLocationText);
        this.imageView = (ImageView) popup.findViewById(R.id.infoLocationImage);

        String textPath = this.mainViewModel.getLocationElements(location).getPath();

        String imagePath = this.mainViewModel.getLocationImagePath(location);

        int id = context.getResources().getIdentifier(imagePath, "drawable", context.getPackageName());

        this.imageView.setImageResource(id);


        this.infoTV.setText(textPath + "\n"+imagePath);

        this.okButton = (Button) popup.findViewById(R.id.OkLocationButton);



        dialogBuilder.setView(popup);
        dialog = dialogBuilder.create();

        this.okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private TextView titleTVskipPopup;
    private Button skipButton;

    public void CreateSkipPopup(Location location){
        View popup = getLayoutInflater().inflate(R.layout.skip_location_popup,null);

        this.titleTVskipPopup = (TextView) popup.findViewById(R.id.skipLocationTitle);
        this.titleTVskipPopup.setText(location.getLocationName());
        this.skipButton = (Button) popup.findViewById(R.id.skipLocationButton);



        dialogBuilder.setView(popup);
        dialog = dialogBuilder.create();

        this.skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }
}