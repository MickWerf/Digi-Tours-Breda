package com.mickwerf.digi_tours_breda.data.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room entity which describes a location.
 */
@Entity(tableName = "location")
public class Location {
    @NonNull
    @PrimaryKey
    private String locationName;

    @ColumnInfo(name = "visited")
    private boolean visited;

    @ColumnInfo(name = "is_sight_seeing_location")
    private boolean isSightSeeingLocation;

    public Location(@NonNull String locationName, boolean visited, boolean isSightSeeingLocation) {
        this.locationName = locationName;
        this.visited = visited;
        this.isSightSeeingLocation = isSightSeeingLocation;
    }

    @NonNull
    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(@NonNull String locationName) {
        this.locationName = locationName;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public boolean isSightSeeingLocation() {
        return isSightSeeingLocation;
    }

    public void setSightSeeingLocation(boolean sightSeeingLocation) {
        isSightSeeingLocation = sightSeeingLocation;
    }
}
