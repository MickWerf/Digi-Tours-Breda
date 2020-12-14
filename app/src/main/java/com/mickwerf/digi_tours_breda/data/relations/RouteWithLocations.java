package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.RouteLocationCrossReference;

import java.util.List;

/**
 * Room relation which relates the route to a list of locations.
 */
public class RouteWithLocations {
    @Embedded
    private Route route;

    @Relation(
            parentColumn = "routeName",
            entityColumn = "locationName",
            associateBy = @Junction(RouteLocationCrossReference.class)
    )
    private List<Location> locations;

    public Route getRoute() {
        return route;
    }

    public List<Location> getLocations() {
        return locations;
    }
}
