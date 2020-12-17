package com.mickwerf.digi_tours_breda.live_data;

import android.content.Context;
import android.location.LocationManager;

import com.mickwerf.digi_tours_breda.data.Database;
import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;
import com.mickwerf.digi_tours_breda.data.relations.LocationElements;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithLocations;

import java.util.List;

/**
 * Repository is a collection class which converges all backend data streams.
 * This Repository serves as a connection to the DataBase and the GPS Manager.
 */
class Repository {
    private static volatile Repository INSTANCE;

    public static Repository getInstance() {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                INSTANCE = new Repository();
            }
        }
        return INSTANCE;
    }

    public List<Route> getRoutes(Context context) {
        return Database.getInstance(context).userDataAccess().getAllRoutes();
    }

    public RouteWithLocations getActiveRoute(Context context) {
        return Database.getInstance(context).userDataAccess().getRouteWithLocations(getUserSettings(context).getRoute());
    }

    public UserSettings getUserSettings(Context context) {
        return Database.getInstance(context).userDataAccess().getUserSettings();
    }

    public GpsCoordinate getGpsCoordinate() {
        //todo Erwin zou hier naar gaan kijken.
        return null;
    }

    public List<Language> getLanguages(Context context) {
        return Database.getInstance(context).userDataAccess().getLanguages();
    }

    public List<LocationElements> getLocationElements(Context context, Location location, UserSettings settings) {
        //todo query for automatically getting correct language may need to be adjusted.
        return Database.getInstance(context).userDataAccess().getLocationElementsFromLanguage(location.getLocationName(), settings.getLanguage());
    }

    public void setLanguage(Context context, Language language) {
        UserSettings settings = Database.getInstance(context).userDataAccess().getUserSettings();
        settings.setLanguage(language.getLanguageName());
        Database.getInstance(context).userDataAccess().updateCurrentUserSettings(settings);
    }

    public void setActiveRoute(Context context, Route route) {
        UserSettings settings = getUserSettings(context);
        settings.setRoute(route.getRouteName());
        Database.getInstance(context).userDataAccess().updateCurrentUserSettings(settings);
    }

    public void visitLocation(Context context, Location location) {
        location.setVisited(true);
        Database.getInstance(context).userDataAccess().updateLocation(location);
    }
}
