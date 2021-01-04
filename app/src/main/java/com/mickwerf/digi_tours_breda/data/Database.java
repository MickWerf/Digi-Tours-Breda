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
        version = 2, //sets the version to 2.
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
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(), Database.class, "db1.db")
                                .fallbackToDestructiveMigration()
                                .createFromAsset("TemplateDatabase.db")
                                .build();



//                        userPref = context.getSharedPreferences(USER_DATA, MODE_PRIVATE);
//                        if (userPref.getBoolean("firstTime", true)) {
//                            insertAllData(context);
//
//                            SharedPreferences.Editor editor = userPref.edit();
//                            editor.putBoolean("firstTime", false);
//                            editor.apply();
//                        }

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
        adminAccess.insertLocations(new Location("Spanjaardsgat", false, true));
        adminAccess.insertLocations(new Location("Het Kasteel van Breda", false, true));
        adminAccess.insertLocations(new Location("Valkenberg Stadspark", false, true));
        adminAccess.insertLocations(new Location("Begijnhof van Breda", false, true));
        adminAccess.insertLocations(new Location("Eglise Wallonne", false, true));
        adminAccess.insertLocations(new Location("Stedelijk Museum Breda", false, true));
        adminAccess.insertLocations(new Location("Brouwerij De Beyerd", false, true));
        adminAccess.insertLocations(new Location("Sint-Antoniuskathedraal", false, true));
        adminAccess.insertLocations(new Location("Kasteel Bouvigne", false, true));
        adminAccess.insertLocations(new Location("Het Poolse oorlogskerkhof", false, true));
        adminAccess.insertLocations(new Location("Grote Markt", false, true));
        adminAccess.insertLocations(new Location("Wolfslaar", false, true));
        adminAccess.insertLocations(new Location("Futurodome", false, true));
        adminAccess.insertLocations(new Location("NAC", false, true));
        adminAccess.insertLocations(new Location("Onderwijshotel De Rooi Pannen", false, false));
        adminAccess.insertLocations(new Location("Apollo Hotel", false, false));
        adminAccess.insertLocations(new Location("Hotel Nassau Breda", false, false));
        adminAccess.insertLocations(new Location("Haven", false, true));
        adminAccess.insertLocations(new Location("De Barones", false, false));
        adminAccess.insertLocations(new Location("'t Sas", false, false));
        adminAccess.insertLocations(new Location("Sint Joostkapel", false, true));
        adminAccess.insertLocations(new Location("Nassau-Baroniemonument", false, true));
        adminAccess.insertLocations(new Location("Waalse Kerk Breda", false, true));
        adminAccess.insertLocations(new Location("Station Breda", false, false));
        adminAccess.insertLocations(new Location("La Source", false, true));
        adminAccess.insertLocations(new Location("Sinte Juttemis", false, true));
        adminAccess.insertLocations(new Location("Amphia ziekenhuis", false, false));
        adminAccess.insertLocations(new Location("Avans Hogeschool", false, true));


        //TODO routes maken en invullen
        adminAccess.insertRoutes(new Route("Stapje uit", false, "/res/stapje_uit"));
        adminAccess.insertRouteLocationCross(new RouteLocationCrossReference("Stapje uit", "Grote Kerk"),
                new RouteLocationCrossReference("Stapje uit", "Foodhall"));


        //TODO alle data elementen aanmaken
        adminAccess.insertDataElement(new DataElement("/res/grote_kerk_nl","VISUAL","Grote Kerk","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/grote_kerk_en","VISUAL","Grote Kerk","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/foodhall_nl","VISUAL","Foodhall","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/foodhall_en","VISUAL","Foodhall","Engels"));


        adminAccess.insertCoordinates(new GpsCoordinate(51.589031658516780964,4.7756363470046441221, "Grote Kerk"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.589479884555643706,4.775059648793251732, "FoodHall"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.5909488315029, 4.773364485278858, "Spanjaardsgat"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.59152666495261, 4.775346298772034, "Het Kasteel van Breda"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.59131108865828, 4.779007999889877, "Valkenberg Stadspark"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.59044712911638, 4.778756210934395, "Begijnhof van Breda"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58973458924113, 4.778527492707943, "Eglise Wallonne"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58966310820953, 4.780960041100787, "Stedelijk Museum Breda"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58983313946556, 4.781480027607632, "Brouwerij De Beyerd"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.587629505794766, 4.7772795852787855, "Sint-Antoniuskathedraal"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.56274117301899, 4.783498141100276, "Kasteel Bouvigne"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58282931199924, 4.747640393496762, "Het Poolse oorlogskerkhof"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.588437934001945, 4.776362512265151, "Grote Markt"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.56123200121738, 4.801673143131117, "Wolfslaar"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.590629160026566, 4.786916498772061, "Futurodome"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.59543512832473, 4.751072041100918, "NAC"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58506737632448, 4.773210241100704, "Onderwijshotel De Rooi Pannen"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.59488405904147, 4.77769368342966, "Apollo Hotel"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.587878941472354, 4.776721798772011, "Hotel Nassau Breda"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58893705691505, 4.772257835219948, "Haven"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58700679321434, 4.77471309963775, "De Barones"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58860658892324, 4.774133264055567, "'t Sas"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58533174221633, 4.776833885278809, "Sint Joostkapel"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.59258172838852, 4.7797398699364715, "Nassau-Baroniemonument"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58980726798, 4.778572512265214, "Waalse Kerk Breda"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.59582395184228, 4.779530456443361, "Station Breda"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.59602872181339, 4.771226383429701, "La Source"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.58736478713035, 4.776258465613448, "Sinte Juttemis"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.582143233467725, 4.797547438726636, "Amphia ziekenhuis"));
        adminAccess.insertCoordinates(new GpsCoordinate(51.585944501665516, 4.792382004143849, "Avans Hogeschool"));


        adminAccess.insertUserSettings(new UserSettings(1, "Nederlands", "Stapje uit"));
    }
}
