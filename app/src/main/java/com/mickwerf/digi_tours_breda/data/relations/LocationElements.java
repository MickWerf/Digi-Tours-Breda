package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.Location;

import java.util.List;

/**
 * Room relation which relates a location to a list of Data elements.
 */
public class LocationElements {
    @Embedded
    private Location location;

    @Relation(
            parentColumn = "locationName",
            entityColumn = "location"
    )
    private List<LocationElements> elements;

    public Location getLocation() {
        return location;
    }

    public List<LocationElements> getElements() {
        return elements;
    }
}
