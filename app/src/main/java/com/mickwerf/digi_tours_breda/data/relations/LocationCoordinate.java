package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Location;

/**
 * Room relation which relates a location to a gps coordinate.
 */
public class LocationCoordinate {
    @Embedded
    private Location location;

    @Relation(
            parentColumn = "locationName",
            entityColumn = "location"
    )
    private GpsCoordinate gpsCoordinate;

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setGpsCoordinate(GpsCoordinate gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
    }

    public Location getLocation() {
        return location;
    }

    public GpsCoordinate getGpsCoordinate() {
        return gpsCoordinate;
    }
}
