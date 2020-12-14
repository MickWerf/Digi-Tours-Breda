package com.mickwerf.digi_tours_breda.data.entities;

import androidx.room.Entity;

@Entity(tableName = "gps_coordinate", primaryKeys = {"latitude", "longitude"})
public class GpsCoordinate {
    private double latitude;
    private double longitude;

    public GpsCoordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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
}
