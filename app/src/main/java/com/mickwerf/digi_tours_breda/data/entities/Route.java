package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "route")
public class Route {
    @NonNull
    @PrimaryKey
    private String name;

    @ColumnInfo(name = "complete")
    private boolean complete;

    @NonNull
    @ColumnInfo(name = "route_image_path")
    private String routeImagePath;

    public Route(@NonNull String name, boolean complete, @NonNull String routeImagePath) {
        this.name = name;
        this.complete = complete;
        this.routeImagePath = routeImagePath;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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
