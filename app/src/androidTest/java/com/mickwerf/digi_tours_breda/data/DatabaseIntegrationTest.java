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
    private AdminDataAccess dataAdminAccess;

    @Before
    public void createNewDataBaseObject() {
        this.database = Database.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        this.dataAdminAccess = database.adminDataAccess();
    }

    @Test
    public void testRoutes() {
        List<Route> routes = this.dataAdminAccess.getAllRoutes();

        Assert.assertEquals(20, routes.size());

        Route route = routes.get(0);
        boolean previousStatus = route.isComplete();

        route.setComplete(!previousStatus);
        this.dataAdminAccess.updateRoute(route);

        RouteWithLocations routeWithLocations = this.dataAdminAccess.getRouteWithLocations(route.getRouteName());

        Assert.assertEquals(!previousStatus, routeWithLocations.getRoute().isComplete());
    }

}