package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

/**
 * Room entity which describes a cross reference (more to more relation) between route and location tables.
 */
@Entity(tableName = "route_location_cross_reference", primaryKeys = {"routeName", "latitude", "longitude"})
public class RouteLocationCrossReference {
    @NonNull
    private String routeName;
    @NonNull
    @Embedded private GpsCoordinate gpsCoordinate;

    public RouteLocationCrossReference(@NonNull String routeName,@NonNull GpsCoordinate gpsCoordinate) {
        this.routeName = routeName;
        this.gpsCoordinate = gpsCoordinate;
    }

    @NonNull
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(@NonNull String routeName) {
        this.routeName = routeName;
    }

    public @NonNull GpsCoordinate getGpsCoordinate() {
        return gpsCoordinate;
    }

    public void setGpsCoordinate(@NonNull GpsCoordinate gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
    }
}
