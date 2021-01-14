package com.mickwerf.digi_tours_breda.live_data.route_logic;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.gui.activities.MainActivity;
import com.mickwerf.digi_tours_breda.gui.fragments.MapScreenFragment;
import com.mickwerf.digi_tours_breda.services.ForegroundService;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.util.ArrayList;
import java.util.HashMap;

public class GpsLogic implements LocationListener {

    private transient MapScreenFragment mapScreenFragment;
    private ArrayList<Location> locations;
    private ArrayList<GpsCoordinate> LocationCoordinateList;
    private HashMap<Location, Marker> LocationMarkers;
    private Thread checkingThread;
    private Boolean on;
    private int i = 0;
    private GeoPoint nearestLocation;
    private Boolean Opened = false;
    private Context context;
    private LocationManager locationManager;
    private GeoPoint myLocation;
    private MainActivity mainActivity;

    public GpsLogic(MapScreenFragment mapScreenFragment, ArrayList<Location> locations, ArrayList<GpsCoordinate> locationCoordinateList, HashMap<Location, Marker> locationMarkers, MainActivity mainActivity) {
        this.mapScreenFragment = mapScreenFragment;
        this.locations = locations;
        LocationCoordinateList = locationCoordinateList;
        LocationMarkers = locationMarkers;
        this.mainActivity = mainActivity;
        this.on = true;
        this.context = mainActivity.getApplicationContext();
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling ActivityCompat#requestPermissions
            Log.e("GpsLogic", "GPS does not have the required permissions.");
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        this.nearestLocation = new GeoPoint(LocationCoordinateList.get(i).getLatitude(), LocationCoordinateList.get(0).getLongitude());
    }

    public void start() {
        this.checkingThread = new Thread(this::ConstantlyCheck);
        this.checkingThread.start();
    }

    public void stop() {
        this.on = false;
    }

    public void ConstantlyCheck() {
        while (on) {
            try {
                GeoPoint nearestLocation = getNearestLocation();
                double a = myLocation.distanceToAsDouble(nearestLocation);
                if (a <= 15.0) {
                    CreatePopup();
                }
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GeoPoint getNearestLocation() {
        if (locations.get(i).isVisited()) {
            if (i < locations.size() - 1) {
                i++;
                this.nearestLocation = new GeoPoint(LocationCoordinateList.get(i).getLatitude(), LocationCoordinateList.get(i).getLongitude());
            }
        }
        return nearestLocation;
    }

    public void CreatePopup() {
        if (!Opened) {
            this.Opened = true;
            ForegroundService.BuildNotification(locations.get(i).getLocationName(), mainActivity.getApplicationContext());
            this.mapScreenFragment.createPopUp(locations.get(i), this.LocationMarkers.get(locations.get(i)));
        }
    }

    public void setNotOpened(boolean Opened) {
        this.Opened = Opened;
    }

    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        this.myLocation = new GeoPoint(location.getLatitude(), location.getLongitude());
    }
}
