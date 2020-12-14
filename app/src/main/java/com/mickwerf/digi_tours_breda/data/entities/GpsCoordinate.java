package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "gps_coordinate", primaryKeys = {"latitude, longitude"})
public class GpsCoordinate {
    private double latitude;
    private double longitude;
    @NonNull
    private String location;

    public GpsCoordinate(double latitude, double longitude, @NonNull String location) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @NonNull
    public String getLocation() {
        return location;
    }

    public void setLocation(@NonNull String location) {
        this.location = location;
    }
}
