package com.mickwerf.digi_tours_breda.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.mickwerf.digi_tours_breda.data.entities.DataElement;
import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.RouteLocationCrossReference;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;

/**
 * Data Access Object for Admin level permissions.
 *
 * The Data Access Object defines the available queries.
 */
@Dao
public interface AdminDataAccess extends UserDataAccess {

    //region Insert
    @Insert
    void insertRoutes(Route... routes);

    @Insert
    void insertLocations(Location... locations);

    @Insert
    void insertLanguages(Language... languages);

    @Insert
    void insertCoordinates(GpsCoordinate... coordinates);

    @Insert
    void insertDataElement(DataElement... dataElements);

    @Insert
    void insertUserSettings(UserSettings... userSettings);

    @Insert
    void insertRouteLocationCross(RouteLocationCrossReference... crossReferences);
    //endregion

    //region Delete
    @Delete
    void deleteRoutes(Route... routes);

    @Delete
    void deleteLocations(Location... locations);

    @Delete
    void deleteLanguages(Language... languages);

    @Delete
    void deleteCoordinates(GpsCoordinate... coordinates);

    @Delete
    void deleteDataElement(DataElement... dataElements);

    @Delete
    void deleteUserSettings(UserSettings... userSettings);
    //endregion
}
