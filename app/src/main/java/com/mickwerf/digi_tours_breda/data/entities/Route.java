package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a route.
 */
@Entity(tableName = "route")
public class Route {
    @NonNull
    @PrimaryKey
    private String routeName;

    @ColumnInfo(name = "complete")
    private boolean complete;

    @NonNull
    @ColumnInfo(name = "route_image_path")
    private String routeImagePath;

    public Route(@NonNull String routeName, boolean complete, @NonNull String routeImagePath) {
        this.routeName = routeName;
        this.complete = complete;
        this.routeImagePath = routeImagePath;
    }

    @NonNull
    public String getRouteName() {
        return routeName;
    }

    public void setRouteName(@NonNull String routeName) {
        this.routeName = routeName;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    @NonNull
    public String getRouteImagePath() {
        return routeImagePath;
    }

    public void setRouteImagePath(@NonNull String routeImagePath) {
        this.routeImagePath = routeImagePath;
    }
}
