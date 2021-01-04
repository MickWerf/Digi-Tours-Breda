package com.mickwerf.digi_tours_breda.data;

import androidx.test.platform.app.InstrumentationRegistry;

import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;
import com.mickwerf.digi_tours_breda.data.relations.LocationCoordinate;
import com.mickwerf.digi_tours_breda.data.relations.LocationElements;
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

    //New integration test (not in integration document)
    @Test
    public void testRoutes() {
        List<Route> routes = this.dataAdminAccess.getAllRoutes();

        Assert.assertEquals(6, routes.size());

        Route route = routes.get(0);
        boolean previousStatus = route.isComplete();

        route.setComplete(!previousStatus);
        this.dataAdminAccess.updateRoute(route);

        RouteWithLocations routeWithLocations = this.dataAdminAccess.getRouteWithLocations(route.getRouteName());

        Assert.assertEquals(!previousStatus, routeWithLocations.getRoute().isComplete());
    }

    //Test I3.T1
    @Test
    public void testSettingLanguage() {
        UserSettings oldUserSettings = this.dataAdminAccess.getUserSettings();

        String newLanguage = "";
        switch (oldUserSettings.getLanguage()) {
            case "Nederlands":
                newLanguage = "Engels";
                break;
            case "Engels":
                newLanguage = "Nederlands";
                break;
        }

        oldUserSettings.setLanguage(newLanguage);
        this.dataAdminAccess.updateCurrentUserSettings(oldUserSettings);


        UserSettings newUserSettings = this.dataAdminAccess.getUserSettings();
        Assert.assertEquals(newLanguage, newUserSettings.getLanguage());
    }

    //Test I4.T1
    @Test
    public void testGetAllCoordinates() {
        List<LocationCoordinate> locationCoordinates = this.dataAdminAccess.getLocationCoordinates();

        Assert.assertEquals(30, locationCoordinates.size());
    }

    //Test I4.T2
    @Test
    public void testLocationElements() {
        List<LocationElements> locationElements = this.dataAdminAccess.getLocationElementsFromLanguage("Avans Hogeschool", "Nederlands");

        Assert.assertEquals(1, locationElements.size());
    }
}