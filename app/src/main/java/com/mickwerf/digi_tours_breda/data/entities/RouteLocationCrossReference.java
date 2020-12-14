package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Entity;

@Entity(tableName = "route_location_cross_reference", primaryKeys = {"routeName", "longitude", "latitude"})
public class RouteLocationCrossReference {
    @NonNull
    private String routeName;
    private double longitude;
    private double latitude;

    public RouteLocationCrossReference(@NonNull String routeName, double longitude, double latitude) {
        this.routeName = routeName;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @NonNull
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(@NonNull String routeName) {
        this.routeName = routeName;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
