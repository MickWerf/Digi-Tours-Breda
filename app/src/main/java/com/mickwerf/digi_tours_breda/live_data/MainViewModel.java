package com.mickwerf.digi_tours_breda.live_data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithLocations;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final MutableLiveData<UserSettings> userSettings = new MutableLiveData<>();
    private final MutableLiveData<GpsCoordinate> gpsCoordinate = new MutableLiveData<>();
    private final MutableLiveData<RouteWithLocations> activeRoute = new MutableLiveData<>();
    private final MutableLiveData<List<Route>> routes = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<Route>> getRoutes() {
        return routes;
    }

    public MutableLiveData<UserSettings> getUserSettings() {
        return userSettings;
    }

    public MutableLiveData<GpsCoordinate> getGpsCoordinate() {
        return gpsCoordinate;
    }

    public MutableLiveData<RouteWithLocations> getActiveRoute() {
        return activeRoute;
    }

    public void setActiveRoute(Route newRoute) {

    }

    public void getLanguages() {

    }

    public void setCurrentLanguage(Language language) {

    }

    public void visitLocation(Location location) {

    }

    public void getLocationElements(Location location) {

    }
}
