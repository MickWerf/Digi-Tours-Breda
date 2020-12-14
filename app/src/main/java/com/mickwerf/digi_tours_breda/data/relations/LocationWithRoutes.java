package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.RouteLocationCrossReference;

import java.util.List;

public class LocationWithRoutes {
    @Embedded
    private Location location;

    @Relation(
            parentColumn = "locationName",
            entityColumn = "routeName",
            associateBy = @Junction(RouteLocationCrossReference.class)
    )
    private List<Route> routes;
}
