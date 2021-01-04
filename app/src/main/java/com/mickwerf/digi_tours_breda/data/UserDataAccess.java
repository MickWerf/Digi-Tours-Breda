package com.mickwerf.digi_tours_breda.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;
import com.mickwerf.digi_tours_breda.data.relations.LocationCoordinate;
import com.mickwerf.digi_tours_breda.data.relations.LocationElements;
import com.mickwerf.digi_tours_breda.data.relations.RouteWithLocations;

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
    RouteWithLocations getRouteWithLocations(String routeName);

    @Update
    void updateRoute(Route... routes);
    //endregion

    //region Location & DataElement
    @Query("SELECT * FROM location WHERE is_sight_seeing_location LIKE 1")
    List<Location> getAllSightSeeingLocations();

    @Transaction
    @Query("SELECT * FROM location WHERE locationName LIKE :locationName")
    LocationCoordinate getLocationCoordinate(String locationName);

    @Transaction
    @Query("SELECT * FROM location")
    List<LocationCoordinate> getLocationCoordinates();

    @Transaction
    @Query("SELECT * " +
            "FROM location, language, data_element " +
            "WHERE data_element.location == location.locationName AND " +
            "data_element.language == language.languageName AND " +
            "locationName LIKE :locationName AND " +
            "language.languageName LIKE :language")
    List<LocationElements> getLocationElementsFromLanguage(String locationName, String language);

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
