package com.mickwerf.digi_tours_breda.data;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mickwerf.digi_tours_breda.data.entities.DataElement;
import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.RouteStep;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;

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
                RouteStep.class,
                UserSettings.class
        },
        version = 4, //sets the version to 4.
        exportSchema = false //disable exporting schema, this is unneeded for implementation.
)
public abstract class Database extends RoomDatabase {
    private static final String TAG = Database.class.getSimpleName();
    private static volatile Database INSTANCE;
    public abstract UserDataAccess userDataAccess();
    public abstract AdminDataAccess adminDataAccess();

    public static Database getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {

                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "db1.db")
                                .createFromAsset("TemplateDatabaseV4.4.db")
                                .build();
                    } else {
                        Log.e(TAG, "NO PERMISSION TO ACCESS DATABASE");
                    }
                }
            }
        }
        return INSTANCE;
    }
}
