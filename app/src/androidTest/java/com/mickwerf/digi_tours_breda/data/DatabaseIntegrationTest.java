package com.mickwerf.digi_tours_breda.data;

import androidx.test.platform.app.InstrumentationRegistry;

import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithLocations;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class DatabaseIntegrationTest {

    private Database database;

    @Before
    public void createNewDataBaseObject() {
        this.database = Database.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
    }

    @Test
    public void testRoutes() {
        List<Route> routes = this.database.userDataAccess().getAllRoutes();

        Assert.assertEquals(20, routes.size());

        Route route = routes.get(0);
        boolean previousStatus = route.isComplete();

        route.setComplete(!previousStatus);
        this.database.userDataAccess().updateRoute(route);

        RouteWithLocations routeWithLocations = this.database.userDataAccess().getRouteWithLocations(route.getRouteName());

        Assert.assertEquals(!previousStatus, routeWithLocations.getRoute().isComplete());
    }

}