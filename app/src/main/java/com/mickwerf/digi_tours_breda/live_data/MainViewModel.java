package com.mickwerf.digi_tours_breda.live_data;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.mickwerf.digi_tours_breda.data.Database;
import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;
import com.mickwerf.digi_tours_breda.data.relations.LocationElements;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithLocations;
import com.yariksoffice.lingver.Lingver;

import java.util.List;
import java.util.Locale;

/**
 * ViewModel for the Main Activity.
 *
 * Manages all Live Data and serves as a connector class between the front and backend.
 */
public class MainViewModel extends AndroidViewModel {
    private static final Locale LOCALE_DEFAULT = new Locale("nl");

    private final MutableLiveData<UserSettings> userSettings = new MutableLiveData<>();
    private final MutableLiveData<GpsCoordinate> gpsCoordinate = new MutableLiveData<>();
    private final MutableLiveData<RouteWithLocations> activeRoute = new MutableLiveData<>();
    private final MutableLiveData<List<Route>> routes = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
        //TODO: change this to be more good looking V
        try{
            Lingver.init(application);
        }catch (Exception e){

        }
    }

    public MutableLiveData<List<Route>> getRoutes() {
        this.routes.setValue(Repository.getInstance().getRoutes(getApplication().getApplicationContext()));
        return routes;
    }

    public List<Route> getRoutes2(){
        return Repository.getInstance().getRoutes(getApplication().getApplicationContext());
    }

    public UserSettings getUserSettings2(){
        return Repository.getInstance().getUserSettings(getApplication().getApplicationContext());
    }

    public MutableLiveData<UserSettings> getUserSettings() {
        this.userSettings.postValue(Repository.getInstance().getUserSettings(getApplication().getApplicationContext()));
        return userSettings;
    }

    public MutableLiveData<GpsCoordinate> getGpsCoordinate() {
        this.gpsCoordinate.postValue(Repository.getInstance().getGpsCoordinate());
        return gpsCoordinate;
    }

    public List<Language> getLanguages() {
        return Repository.getInstance().getLanguages(getApplication().getApplicationContext());
    }

    public MutableLiveData<RouteWithLocations> getActiveRoute() {
        activeRoute.postValue(Repository.getInstance().getActiveRoute(getApplication().getApplicationContext()));
        return activeRoute;
    }

    public RouteWithLocations getActiveRoute2(){
        return Repository.getInstance().getActiveRoute(getApplication().getApplicationContext());
    }

    public void setActiveRoute(Route newRoute) {
        Repository.getInstance().setActiveRoute(getApplication().getApplicationContext(), newRoute);
    }

    public void setCurrentLanguage(Language language, Locale newLocale) {
        Repository.getInstance().setLanguage(getApplication().getApplicationContext(), language);
        Lingver.getInstance().setLocale(getApplication().getApplicationContext(), newLocale);
    }

    public void visitLocation(Location location) {
        Repository.getInstance().visitLocation(getApplication().getApplicationContext(), location);
        activeRoute.setValue(Repository.getInstance().getActiveRoute(getApplication().getApplicationContext()));
    }

    public List<LocationElements> getLocationElements(Location location) {
        //todo possibly need to change with new query, though it may not be needed.
        UserSettings settings = Repository.getInstance().getUserSettings(getApplication().getApplicationContext());
        return Repository.getInstance().getLocationElements(getApplication().getApplicationContext(), location, settings);
    }

    public Boolean setCurrentRoute(String routeName){
        if(getUserSettings2().getRoute() == null){
            Route activeRoute = Repository.getInstance().getRoute(getApplication().getApplicationContext(), routeName);
            Repository.getInstance().setActiveRoute(getApplication().getApplicationContext(), activeRoute);
            return true;
        }
        return false;

    }
}
