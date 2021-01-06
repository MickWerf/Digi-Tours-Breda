package com.mickwerf.digi_tours_breda.data;

import androidx.test.platform.app.InstrumentationRegistry;

import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;
import com.mickwerf.digi_tours_breda.data.relations.LocationCoordinate;
import com.mickwerf.digi_tours_breda.data.relations.LocationElements;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DatabaseIntegrationTest {
    private AdminDataAccess dataAdminAccess;

    @Before
    public void createNewDataBaseObject() {
        Database database = Database.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext());
        this.dataAdminAccess = database.adminDataAccess();
    }

    /**
     * Tests all route queries with the database.
     */
    @Test
    public void testRoutes() {
        //Check if the total number of routes is correct.
        List<Route> routes = this.dataAdminAccess.getAllRoutes();
        Assert.assertEquals(7, routes.size());

        //Test updating routes
        Route route = routes.get(0);
        boolean previousStatus = route.isComplete();
        route.setComplete(!previousStatus);
        this.dataAdminAccess.updateRoute(route);
        RouteWithLocations routeWithLocations = this.dataAdminAccess.getRouteWithLocations(route.getRouteName());
        Assert.assertEquals(!previousStatus, routeWithLocations.getRoute().isComplete());
    }

    /**
     * Tests all location queries with the database.
     * Test id: I4.T1
     */
    @Test
    public void testLocations() {
        //Test total number of sight seeing locations.
        List<Location> locations = this.dataAdminAccess.getAllSightSeeingLocations();
        Assert.assertEquals(41, locations.size());

        //Tests the coordinate of a specific location.
        LocationCoordinate coordinate = this.dataAdminAccess.getLocationCoordinate(locations.get(0).getLocationName());
        Assert.assertEquals(51.589031658516780964, coordinate.getGpsCoordinate().getLatitude(), 0.00000000001);
        Assert.assertEquals(4.7756363470046441221, coordinate.getGpsCoordinate().getLongitude(), 0.00000000001);

        //Test the total number of location coordinates.
        List<LocationCoordinate> coordinates = this.dataAdminAccess.getLocationCoordinates();
        Assert.assertEquals(49, coordinates.size());
    }

    /**
     * Tests all user settings queries with the database.
     * Test id: I3.T1
     */
    @Test
    public void testUserSettings() {
        //Tests updating user settings.
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

    /**
     * Tests all language queries with the database.
     */
    @Test
    public void testLanguages() {
        //tests if the correct number of languages are included.
        List<Language> languages = this.dataAdminAccess.getLanguages();
        Assert.assertEquals(4, languages.size());

        //Tests if all all languages are included.
        List<String> languageNames = new ArrayList<>();
        for (Language language : languages) {
            languageNames.add(language.getLanguageName());
        }
        Assert.assertTrue(languageNames.contains("Nederlands"));
        Assert.assertTrue(languageNames.contains("Engels"));
        Assert.assertTrue(languageNames.contains("Duits"));
        Assert.assertTrue(languageNames.contains("Generic"));
    }

    /**
     * Tests all data elements queries with the database.
     * Test id: I4.T2
     */
    @Test
    public void testDataElements() {
        List<LocationElements> locationElements = this.dataAdminAccess.getLocationElementsFromLanguage("Avans Hogeschool", "Nederlands");
        Assert.assertEquals(1, locationElements.size());
    }
}