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

import com.mickwerf.digi_tours_breda.R;
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
        version = 3, //sets the version to 3.
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
//                                .fallbackToDestructiveMigration()
//                                .createFromAsset("newDatabase.db")
                                .build();



                        userPref = context.getSharedPreferences(USER_DATA, MODE_PRIVATE);
                        if (userPref.getBoolean("first_time", true)) {
                            insertAllData(context);

                            SharedPreferences.Editor editor = userPref.edit();
                            editor.putBoolean("first_time", false);
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
        adminAccess.insertLanguages(new Language("Duits"));
        adminAccess.insertLanguages(new Language("Generic"));


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


        adminAccess.insertRoutes(new Route("Stapje uit", false, "/res/route/stapje_uit.png", "/res/route/stapje_uit_nl.txt", "/res/route/stapje_uit_en.txt", "/res/route/stapje_uit_de.txt"));
        adminAccess.insertRouteLocationCross(
                new RouteLocationCrossReference("Stapje uit", "Grote Kerk"),
                new RouteLocationCrossReference("Stapje uit", "Foodhall"),
                new RouteLocationCrossReference("Stapje uit", "Spanjaardsgat"),
                new RouteLocationCrossReference("Stapje uit", "Het Kasteel van Breda"),
                new RouteLocationCrossReference("Stapje uit", "Valkenberg Stadspark")
        );

        adminAccess.insertRoutes(new Route("Lekker op stap", false, "/res/route/lekker_op_stap.png", "/res/route/lekker_op_stap_nl.txt", "/res/route/lekker_op_stap_en.txt", "/res/route/lekker_op_stap_de.txt"));
        adminAccess.insertRouteLocationCross(
                new RouteLocationCrossReference("Lekker op stap", "Begijnhof van Breda"),
                new RouteLocationCrossReference("Lekker op stap", "Eglise Wallonne"),
                new RouteLocationCrossReference("Lekker op stap", "Stedelijk Museum Breda"),
                new RouteLocationCrossReference("Lekker op stap", "Brouwerij De Beyerd"),
                new RouteLocationCrossReference("Lekker op stap", "Sint-Antoniuskathedraal"),
                new RouteLocationCrossReference("Lekker op stap", "Kasteel Bouvigne"),
                new RouteLocationCrossReference("Lekker op stap", "Het Poolse oorlogskerkhof"),
                new RouteLocationCrossReference("Lekker op stap", "Grote Markt"),
                new RouteLocationCrossReference("Lekker op stap", "Wolfslaar"),
                new RouteLocationCrossReference("Lekker op stap", "Futurodome")
        );

        adminAccess.insertRoutes(new Route("Girls night out!", false, "/res/route/girls_night_out.png", "/res/route/girls_night_out_nl.txt", "/res/route/girls_night_out_en.txt", "/res/route/girls_night_out_de.txt"));
        adminAccess.insertRouteLocationCross(
                new RouteLocationCrossReference("Girls night out!", "NAC"),
                new RouteLocationCrossReference("Girls night out!", "Onderwijshotel De Rooi Pannen"),
                new RouteLocationCrossReference("Girls night out!", "Apollo Hotel"),
                new RouteLocationCrossReference("Girls night out!", "Hotel Nassau Breda")
        );

        adminAccess.insertRoutes(new Route("De gamer route", false, "/res/route/gamer_route.png", "/res/route/gamer_route_nl.txt", "/res/route/gamer_route_en.txt", "/res/route/gamer_route_de.txt"));
        adminAccess.insertRouteLocationCross(
                new RouteLocationCrossReference("De gamer route", "Haven"),
                new RouteLocationCrossReference("De gamer route", "De Barones")
        );

        adminAccess.insertRoutes(new Route("De benenwagen", false, "/res/route/benenwagen.png", "/res/route/benenwagen_nl.txt", "/res/route/benenwagen_en.txt", "/res/route/benenwagen_de.txt"));
        adminAccess.insertRouteLocationCross(
                new RouteLocationCrossReference("De benenwagen", "'t Sas"),
                new RouteLocationCrossReference("De benenwagen", "Sint Joostkapel"),
                new RouteLocationCrossReference("De benenwagen", "Nassau-Baroniemonument"),
                new RouteLocationCrossReference("De benenwagen", "Waalse Kerk Breda"),
                new RouteLocationCrossReference("De benenwagen", "Station Breda"),
                new RouteLocationCrossReference("De benenwagen", "La Source"),
                new RouteLocationCrossReference("De benenwagen", "Sinte Juttemis"),
                new RouteLocationCrossReference("De benenwagen", "Amphia ziekenhuis"),
                new RouteLocationCrossReference("De benenwagen", "Avans Hogeschool")
        );

        adminAccess.insertRoutes(new Route("Avans test route", false, "/res/route/test_route.png", "/res/route/test_route_nl.txt", "/res/route/test_route_en.txt", "/res/route/test_route_de.txt"));
        adminAccess.insertRouteLocationCross(
                new RouteLocationCrossReference("Avans test route", "Valkenberg Stadspark"),
                new RouteLocationCrossReference("Avans test route", "Avans Hogeschool")
        );


        //TODO alle data elementen aanmaken
        adminAccess.insertDataElement(new DataElement("/res/location/grote_kerk_nl.txt","TEXT","Grote Kerk","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/grote_kerk_en.txt","TEXT","Grote Kerk","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/grote_kerk.png","VISUAL","Grote Kerk","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/foodhall_nl.txt","TEXT","Foodhall","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/foodhall_en.txt","TEXT","Foodhall","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/foodhall.png","VISUAL","Foodhall","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/spanjaardsgat_nl.txt","TEXT","Spanjaardsgat","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/spanjaardsgat_en.txt","TEXT","Spanjaardsgat","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/spanjaardsgat.png","VISUAL","Spanjaardsgat","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/het_kasteel_van_breda_nl.txt","TEXT","Het Kasteel van Breda","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/het_kasteel_van_breda_en.txt","TEXT","Het Kasteel van Breda","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/het_kasteel_van_breda.png","VISUAL","Het Kasteel van Breda","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/valkenberg_stadspark_nl.txt","TEXT","Valkenberg Stadspark","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/valkenberg_stadspark_en.txt","TEXT","Valkenberg Stadspark","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/valkenberg_stadspark.png","VISUAL","Valkenberg Stadspark","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/begijnhof_van_breda_nl.txt","TEXT","Begijnhof van Breda","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/begijnhof_van_breda_en.txt","TEXT","Begijnhof van Breda","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/begijnhof_van_breda.png","VISUAL","Begijnhof van Breda","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/eglise_wallonne_nl.txt","TEXT","Eglise Wallonne","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/eglise_wallonne_en.txt","TEXT","Eglise Wallonne","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/eglise_wallonne.png","VISUAL","Eglise Wallonne","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/stedelijk_museum_breda_nl.txt","TEXT","Stedelijk Museum Breda","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/stedelijk_museum_breda_en.txt","TEXT","Stedelijk Museum Breda","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/stedelijk_museum_breda.png","VISUAL","Stedelijk Museum Breda","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/brouwerij_de_beyerd_nl.txt","TEXT","Brouwerij De Beyerd","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/brouwerij_de_beyerd_en.txt","TEXT","Brouwerij De Beyerd","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/brouwerij_de_beyerd.png","VISUAL","Brouwerij De Beyerd","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/sint_antoniuskathedraal_nl.txt","TEXT","Sint-Antoniuskathedraal","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/sint_antoniuskathedraal_en.txt","TEXT","Sint-Antoniuskathedraal","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/sint_antoniuskathedraal.png","VISUAL","Sint-Antoniuskathedraal","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/kasteel_bouvigne_nl.txt","TEXT","Kasteel Bouvigne","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/kasteel_bouvigne_en.txt","TEXT","Kasteel Bouvigne","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/kasteel_bouvigne.png","VISUAL","Kasteel Bouvigne","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/het_poolse_oorlogskerkhof_nl.txt","TEXT","Het Poolse oorlogskerkhof","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/het_poolse_oorlogskerkhof_en.txt","TEXT","Het Poolse oorlogskerkhof","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/het_poolse_oorlogskerkhof.png","VISUAL","Het Poolse oorlogskerkhof","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/grote_markt_nl.txt","TEXT","Grote Markt","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/grote_markt_en.txt","TEXT","Grote Markt","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/grote_markt.png","VISUAL","Grote Markt","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/wolfslaar_nl.txt","TEXT","Wolfslaar","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/wolfslaar_en.txt","TEXT","Wolfslaar","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/wolfslaar.png","VISUAL","Wolfslaar","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/futurodome_nl.txt","TEXT","Futurodome","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/futurodome_en.txt","TEXT","Futurodome","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/futurodome.png","VISUAL","Futurodome","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/nac_nl.txt","TEXT","NAC","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/nac_en.txt","TEXT","NAC","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/nac.png","VISUAL","NAC","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/onderwijshotel_de_rooi_pannen_nl.txt","TEXT","Onderwijshotel De Rooi Pannen","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/onderwijshotel_de_rooi_pannen_en.txt","TEXT","Onderwijshotel De Rooi Pannen","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/onderwijshotel_de_rooi_pannen.png","VISUAL","Onderwijshotel De Rooi Pannen","Generic"));

        adminAccess.insertDataElement(new DataElement("/res/location/apollo_hotel_nl.txt","TEXT","Apollo Hotel","Nederlands"));
        adminAccess.insertDataElement(new DataElement("/res/location/apollo_hotel_en.txt","TEXT","Apollo Hotel","Engels"));
        adminAccess.insertDataElement(new DataElement("/res/location/apollo_hotel.png","VISUAL","Apollo Hotel","Generic"));


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
