package com.mickwerf.digi_tours_breda.data;

import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.mickwerf.digi_tours_breda.data.entities.DataElement;
import com.mickwerf.digi_tours_breda.data.entities.GpsCoordinate;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.data.entities.Location;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.data.entities.RouteLocationCrossReference;
import com.mickwerf.digi_tours_breda.data.entities.UserSettings;

@androidx.room.Database(
        entities = {
                DataElement.class,
                GpsCoordinate.class,
                Language.class,
                Location.class,
                Route.class,
                RouteLocationCrossReference.class,
                UserSettings.class
        },
        version = 1,
        exportSchema = false
)
public abstract class Database extends RoomDatabase {
    private static volatile Database INSTANCE;
    public abstract UserDataAccess userDataAccess();
    public abstract AdminDataAccess adminDataAccess();

    public static Database getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (Database.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "digi_tours_breda").build();
                }
            }
        }
        return INSTANCE;
    }
}
