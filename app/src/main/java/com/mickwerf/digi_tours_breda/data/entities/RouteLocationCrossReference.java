package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;

/**
 * Room entity which describes a cross reference (more to more relation) between route and location tables.
 */
@Entity(tableName = "route_location_cross_reference", primaryKeys = {"routeName", "locationName"})
public class RouteLocationCrossReference {
    @NonNull
    private String routeName;
    @NonNull
    @Embedded private Location location;

    public RouteLocationCrossReference(@NonNull String routeName,@NonNull Location location) {
        this.routeName = routeName;
        this.location = location;
    }

    @NonNull
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(@NonNull String routeName) {
        this.routeName = routeName;
    }

    @NonNull
    public Location getLocation() {
        return location;
    }

    public void setLocation(@NonNull Location location) {
        this.location = location;
    }
}
