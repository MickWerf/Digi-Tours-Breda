package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "route_step")
public class RouteStep {
    @PrimaryKey(autoGenerate = true)
    private int stepId;

    @ColumnInfo(name = "route_step")
    private int routeStep;

    @ForeignKey(
            entity = Route.class,
            parentColumns = "routeName",
            childColumns = "route_name"
    )
    @NonNull
    @ColumnInfo(name = "route_name")
    private String routeName;

    @ForeignKey(
            entity = Route.class,
            parentColumns = "locationName",
            childColumns = "location_name"
    )
    @NonNull
    @ColumnInfo(name = "location_name")
    private String locationName;

    public RouteStep(int routeStep, @NonNull String routeName, @NonNull String locationName) {
        this.routeStep = routeStep;
        this.routeName = routeName;
        this.locationName = locationName;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public int getRouteStep() {
        return routeStep;
    }

    public void setRouteStep(int routeStep) {
        this.routeStep = routeStep;
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
