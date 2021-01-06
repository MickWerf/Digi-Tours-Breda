package com.mickwerf.digi_tours_breda.live_data.route_logic;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.gui.fragments.MapScreenFragment;

import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.overlay.Marker;

import java.util.HashMap;
import java.util.List;

public class GpsLogic {

    private MapScreenFragment mapScreenFragment;
    private List<Location> locations;
    private List<GpsCoordinate> LocationCoordinateList;
    private HashMap<Location, Marker> LocationMarkers;
    private Thread checkingThread;
    private boolean on;
    private int i = 0;
    private GeoPoint nearestLocation;
    private boolean Opened = false;

    public GpsLogic(MapScreenFragment mapScreenFragment, List<Location> locations, List<GpsCoordinate> locationCoordinateList, HashMap<Location, Marker> locationMarkers) {
        this.mapScreenFragment = mapScreenFragment;
        this.locations = locations;
        LocationCoordinateList = locationCoordinateList;
        LocationMarkers = locationMarkers;
        this.checkingThread = new Thread(this::ConstantlyCheck);
        this.on = true;
        this.nearestLocation = new GeoPoint(LocationCoordinateList.get(i).getLatitude(),LocationCoordinateList.get(0).getLongitude());
    }

    public void start(){
        this.checkingThread.start();
    }

    public void stop(){
        this.on = false;
    }

    public void ConstantlyCheck(){
        while (on) {
            try {
                GeoPoint myLocation = mapScreenFragment.getLocationOverlay().getMyLocation();
//                myLocation = new GeoPoint(37.422066, -122.083975);
                GeoPoint nearestLocation = getNearestLocation();

                double a = myLocation.distanceToAsDouble(nearestLocation);
                if(a <= 15.0){
                    CreatePopup();
                }
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public GeoPoint getNearestLocation(){
        while (true) {
            if (locations.get(i).isVisited()) {
                if(i<locations.size()-1){
                    i++;
                    this.nearestLocation = new GeoPoint(LocationCoordinateList.get(i).getLatitude(),LocationCoordinateList.get(i).getLongitude());
                }
            }
            return nearestLocation;
        }

    }

    public void CreatePopup() {
        if(!Opened) {
            this.Opened = true;
            this.mapScreenFragment.createPopUp(locations.get(i), this.LocationMarkers.get(locations.get(i)));
        }
    }

    public void setNotOpened(boolean Opened){
        this.Opened = Opened;
    }





}
