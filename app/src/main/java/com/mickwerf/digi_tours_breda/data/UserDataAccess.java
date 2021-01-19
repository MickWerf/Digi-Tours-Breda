package com.mickwerf.digi_tours_breda.data;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;
import com.mickwerf.digi_tours_breda.data.relations.LocationCoordinate;
import com.mickwerf.digi_tours_breda.data.relations.LocationElements;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithSteps;

import java.util.List;

/**
 * Data Access Object for user level permissions.
 *
 * The Data Access Object defines the available queries.
 */
@Dao
public interface UserDataAccess {

    //region Route
    @Query("SELECT * FROM route")
    List<Route> getAllRoutes();

    @Transaction
    @Query("SELECT * FROM route WHERE routeName LIKE :routeName")
    RouteWithSteps getRouteWithSteps(String routeName);

    @Query("SELECT * FROM route WHERE routeName Like :routeName")
    Route getRoute(String routeName);

    @Update
    void updateRoute(Route... routes);
    //endregion

    //region Location & DataElement
    @Query("SELECT * FROM location WHERE is_sight_seeing_location LIKE 1")
    List<Location> getAllSightSeeingLocations();

    @Query("SELECT * FROM location WHERE locationName LIKE :locationName")
    Location getLocation(String locationName);

    @Transaction
    @Query("SELECT * FROM location WHERE locationName LIKE :locationName")
    LocationCoordinate getLocationCoordinate(String locationName);

    @Transaction
    @Query("SELECT * FROM gps_coordinate WHERE location LIKE :locationName")
    GpsCoordinate getGpsCoordinate(String locationName);

    @Transaction
    @Query("SELECT * FROM location")
    List<LocationCoordinate> getLocationCoordinates();

    @Transaction
    @Query("SELECT * FROM location WHERE locationName LIKE :locationName")
    LocationElements getLocationElements(String locationName);

    @Update
    void updateLocation(Location... locations);
    //endregion

    //region UserSettings
    @Query("SELECT * FROM user_settings")
    UserSettings getUserSettings();

    @Update
    void updateCurrentUserSettings(UserSettings userSetting);
    //endregion

    //region Language
    @Query("SELECT * FROM language")
    List<Language> getLanguages();
    //endregion
}
