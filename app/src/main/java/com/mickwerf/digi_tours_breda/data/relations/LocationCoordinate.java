package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Location;

public class LocationCoordinate {
    @Embedded
    private Location location;

    @Relation(
            parentColumn = "locationName",
            entityColumn = "location"
    )
    private GpsCoordinate gpsCoordinate;
}
