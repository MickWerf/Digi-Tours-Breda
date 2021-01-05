package com.mickwerf.digi_tours_breda.live_data;

import android.content.Context;
import android.location.LocationManager;

import com.mickwerf.digi_tours_breda.data.Database;
import com.mickwerf.digi_tours_breda.data.entities.DataElement;
import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;
import com.mickwerf.digi_tours_breda.data.relations.LocationCoordinate;
import com.mickwerf.digi_tours_breda.data.relations.LocationElements;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithLocations;
import com.mickwerf.digi_tours_breda.live_data.route_logic.ors.models.Coordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository is a collection class which converges all backend data streams.
 * This Repository serves as a connection to the DataBase and the GPS Manager.
 */
class Repository {
    private static volatile Repository INSTANCE;

    private List<Route> routes;
    private RouteWithLocations activeRoute;
    private UserSettings userSettings;
    private List<GpsCoordinate> coordinateList = new ArrayList<>();
    private DataElement dataElement;
    private String imagePath;

    public static Repository getInstance() {
        if (INSTANCE == null) {
            synchronized (Repository.class) {
                INSTANCE = new Repository();
            }
        }
        return INSTANCE;
    }

    public List<Route> getRoutes(Context context) {

        Runnable runnable = () -> {
            routes = Database.getInstance(context).userDataAccess().getAllRoutes();
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return routes;
    }

    public RouteWithLocations getActiveRoute(Context context) {

        Runnable runnable = () -> {
            activeRoute = Database.getInstance(context).userDataAccess().getRouteWithLocations(getUserSettings(context).getRoute());
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return activeRoute;
    }

    public UserSettings getUserSettings(Context context) {

        Runnable runnable = () -> {
            this.userSettings = Database.getInstance(context).userDataAccess().getUserSettings();
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.userSettings;
    }

    public GpsCoordinate getGpsCoordinate() {
        //todo Erwin zou hier naar gaan kijken.
        return null;
    }

    public List<Language> getLanguages(Context context) {
        return Database.getInstance(context).userDataAccess().getLanguages();
    }

    public DataElement getLocationElements(Context context, Location location) {


        Runnable runnable = () -> {

            switch (getUserSettings(context).getLanguage()){

                case "Nederlands":
                    this.dataElement = Database.getInstance(context).userDataAccess().getLocationElements(location.getLocationName()).getElements().get(0);
                    break;
                case "Engels":
                    this.dataElement = Database.getInstance(context).userDataAccess().getLocationElements(location.getLocationName()).getElements().get(1);
                    break;
                case "Duits":
                    this.dataElement = Database.getInstance(context).userDataAccess().getLocationElements(location.getLocationName()).getElements().get(2);
                    break;
            }

        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

       return this.dataElement;

        //todo query for automatically getting correct language may need to be adjusted.
    }

    public String getLocationImagePath(Context context, Location location) {

        Runnable runnable = () -> {
            this.imagePath = Database.getInstance(context).userDataAccess().getLocationElements(location.getLocationName()).getElements().get(3).getPath();
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //todo query for automatically getting correct language may need to be adjusted.
        return imagePath;
    }



    public void setLanguage(Context context, Language language) {
        UserSettings settings = Database.getInstance(context).userDataAccess().getUserSettings();
        settings.setLanguage(language.getLanguageName());
        Database.getInstance(context).userDataAccess().updateCurrentUserSettings(settings);
    }

    public void setActiveRoute(Context context, Route route) {
        Runnable runnable = () -> {
            UserSettings settings = getUserSettings(context);
            settings.setRoute(route.getRouteName());
            Database.getInstance(context).userDataAccess().updateCurrentUserSettings(settings);
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void visitLocation(Context context, Location location) {
        location.setVisited(true);
        Database.getInstance(context).userDataAccess().updateLocation(location);
    }

    public List<GpsCoordinate> getLocationCoordinates(Context context, List<Location> locations) {

        this.coordinateList.clear();
        Runnable runnable = () -> {
            for (Location location : locations){
                this.coordinateList.add(Database.getInstance(context).userDataAccess().getGpsCoordinate(location.getLocationName()));
            }
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return this.coordinateList;
    }
}
