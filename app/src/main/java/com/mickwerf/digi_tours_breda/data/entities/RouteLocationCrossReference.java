package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.ForeignKey;

/**
 * Room entity which describes a cross reference (more to more relation) between route and location tables.
 */
@Entity(tableName = "route_location_cross_reference", primaryKeys = {"routeName", "locationName"})
public class RouteLocationCrossReference {
    @ForeignKey(
            entity = Route.class,
            parentColumns = "routeName",
            childColumns = "routeName"
    )
    @NonNull
    private String routeName;

    @ForeignKey(
            entity = Location.class,
            parentColumns = "locationName",
            childColumns = "locationName"
    )
    @NonNull
    private String locationName;

    public RouteLocationCrossReference(@NonNull String routeName, @NonNull String locationName) {
        this.routeName = routeName;
        this.locationName = locationName;
    }

    @NonNull
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(@NonNull String routeName) {
        this.routeName = routeName;
    }

    @NonNull
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(@NonNull String locationName) {
        this.locationName = locationName;
    }
}
