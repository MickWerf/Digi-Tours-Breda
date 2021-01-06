package com.mickwerf.digi_tours_breda.data.relations;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.RouteStep;

import java.util.List;

public class RouteWithSteps {
    @Embedded
    private Route route;

    @Relation(
            parentColumn = "routeName",
            entityColumn = "route_name"
    )
    private List<RouteStep> routeSteps;

    public RouteWithSteps(Route route, List<RouteStep> routeSteps) {
        this.route = route;
        this.routeSteps = routeSteps;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<RouteStep> getRouteSteps() {
        return routeSteps;
    }

    public void setRouteSteps(List<RouteStep> routeSteps) {
        this.routeSteps = routeSteps;
    }
}
