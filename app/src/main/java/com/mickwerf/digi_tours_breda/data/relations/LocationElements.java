package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.Location;

import java.util.List;

public class LocationElements {
    @Embedded
    private Location location;

    @Relation(
            parentColumn = "locationName",
            entityColumn = "location"
    )
    private List<LocationElements> elements;
}
