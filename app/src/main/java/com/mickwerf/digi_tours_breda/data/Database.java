package com.mickwerf.digi_tours_breda.data;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;

import com.mickwerf.digi_tours_breda.data.entities.DataElement;
import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.RouteLocationCrossReference;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * Database Singleton which represents the database instance.
 *
 * Database extends the Room database and uses those functions
 */
@androidx.room.Database(
        entities = { //adds all tables to the database.
                DataElement.class,
                GpsCoordinate.class,
                Language.class,
                Location.class,
                Route.class,
                RouteLocationCrossReference.class,
                UserSettings.class
        },
        version = 1, //sets the version to 1.
        exportSchema = false //disable exporting schema, this is unneeded for implementation.
)
public abstract class Database extends RoomDatabase {
    private static volatile Database INSTANCE;
    public abstract UserDataAccess userDataAccess();
    public abstract AdminDataAccess adminDataAccess();

    private static final String USER_DATA = "userData";
    private static SharedPreferences userPref;

    public static Database getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "db.db").build();

                        userPref = context.getSharedPreferences(USER_DATA, MODE_PRIVATE);
                        if (userPref.getBoolean("firstTime", true)) {
                            insertAllData(context);

                            SharedPreferences.Editor editor = userPref.edit();
                            editor.putBoolean("firstTime", false);
                            editor.apply();
                        }

                    } else {
                        System.out.println("NO PERMISSION TO ACCESS DATABASE");
                    }
                }
            }
        }
        return INSTANCE;
    }

    private static void insertAllData(Context context) {
        AdminDataAccess adminAccess = Database.getInstance(context).adminDataAccess();
        adminAccess.insertLanguages(new Language("Nederlands"));
        adminAccess.insertLanguages(new Language("Engels"));
        adminAccess.insertLocations(new Location("Grote Kerk", false, true));
        adminAccess.insertLocations(new Location("Foodhall", false, false));
        adminAccess.insertRoutes(new Route("Stapje uit", false, "/res/stapje_uit"));
        adminAccess.insertDataElement(new DataElement("/res/grote_kerk_nl","VISUAL","Grote Kerk","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/grote_kerk_en","VISUAL","Grote Kerk","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/foodhall_nl","VISUAL","Foodhall","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/foodhall_en","VISUAL","Foodhall","Engels"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.589031658516780964,4.7756363470046441221, "Grote Kerk"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.589479884555643706,4.775059648793251732, "FoodHall"));
        adminAccess.insertUserSettings(new UserSettings(1, "Nederlands", "Stapje uit"));
    }
}
