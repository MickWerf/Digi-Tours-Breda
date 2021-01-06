package com.mickwerf.digi_tours_breda.gui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.data.entities.Language;
import com.mickwerf.digi_tours_breda.gui.fragments.MapScreenFragment;
import com.mickwerf.digi_tours_breda.gui.fragments.RouteOverviewFragment;
import com.mickwerf.digi_tours_breda.gui.fragments.SettingScreenFragment;
import com.mickwerf.digi_tours_breda.gui.popups.GPSLossPopup;
import com.mickwerf.digi_tours_breda.live_data.MainViewModel;
import com.mickwerf.digi_tours_breda.services.Notify;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Locale;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ImageView homeButton;
    private ImageView settingsButton;
    private ImageView directionsButton;
    private TextView settingsTextView;
    private TextView homeTextView;
    private TextView directionsTextView;

    private FragmentManager fragmentManager;
    private LocationManager locationManager;

    private SettingScreenFragment settingScreenFragment;
    private RouteOverviewFragment routeOverviewFragment;
    private MapScreenFragment mapScreenFragment;
    private GPSLossPopup gpsLossPopup;

    private MainViewModel mainViewModel;

    private boolean isRequested = false;
    private boolean hasGpsSignal;
    private String presetFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();

        if (intent.hasExtra("loadFragment")) {
            presetFragment = getIntent().getExtras().getString("loadFragment");
        }


        requestPermissions(new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
        }, 1);

        // Initialise notification settings.
        Notify.initialise(getApplicationContext());
        this.mainViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

    }

    //Initialize all views
    public void initialize() {

        this.homeButton = findViewById(R.id.HomeImageView);
        this.settingsButton = findViewById(R.id.SettingsImageView);
        this.directionsButton = findViewById(R.id.DirectionsImageView);

        this.directionsTextView = findViewById(R.id.DirectionsTV);
        this.homeTextView = findViewById(R.id.HomeTV);
        this.settingsTextView = findViewById(R.id.SettingsTV);

        this.fragmentManager = getSupportFragmentManager();
        this.locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        this.settingScreenFragment = new SettingScreenFragment(this);

        this.routeOverviewFragment = new RouteOverviewFragment(this.mainViewModel, this);

        this.mapScreenFragment = new MapScreenFragment(this.mainViewModel, this);

        if (presetFragment == null) {
            fragmentManager.beginTransaction().replace(R.id.fragmentContainer, this.routeOverviewFragment).commit();
        } else if (presetFragment.equals("settings")) {
            toSettingsView();
            presetFragment = null;
        }
        this.gpsLossPopup = new GPSLossPopup();

        this.hasGpsSignal = checkGpsPermission();

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, this.routeOverviewFragment).commit();
    }

    public void updateUserSettings(String localeCode, String Language) {
        Runnable runnable = () -> {
            mainViewModel.setCurrentLanguage(new Language(Language), new Locale(localeCode.toLowerCase()));
        };
        Thread t = new Thread(runnable);
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void restartActivity() {
        Objects.requireNonNull(this).finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("loadFragment", "settings");
        startActivity(intent);
        toSettingsView();
        Toast toast = Toast.makeText(getApplicationContext(), R.string.changedLanguage, Toast.LENGTH_SHORT);
        toast.show();
    }


    //Set all on-click listeners
    public void setClickListeners() {

        this.settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toSettingsView();
            }
        });

        this.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toHomeView();
            }
        });

        this.directionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toDirectionsView();
            }
        });

        // Timer to check GPS availability
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkGpsBlock();
            }
        }, 0, 500);
    }

    private void checkGpsBlock() {
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            if (hasGpsSignal) {
                Log.d("No GPS popup", "GPS deactivated.");
                hasGpsSignal = false;
                try {
                    fragmentManager.beginTransaction().add(R.id.fragmentContainer, gpsLossPopup).commit();
                } catch (IllegalStateException e) {
                    e.getLocalizedMessage();
                }
            }
        } else if (!hasGpsSignal) {
            Log.d("No GPS popup", "GPS activated.");
            hasGpsSignal = true;
            try {
                fragmentManager.beginTransaction().remove(gpsLossPopup).commit();
            } catch (IllegalStateException e) {
                e.getLocalizedMessage();
            }
        }
    }

    private boolean checkGpsPermission() {
        return !locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    //Open settings view
    public void toSettingsView() {
        this.homeTextView.setVisibility(View.GONE);
        this.directionsTextView.setVisibility(View.GONE);
        this.settingsTextView.setVisibility(View.VISIBLE);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, this.settingScreenFragment).commit();
    }

    //Open home view
    public void toHomeView() {
        this.homeTextView.setVisibility(View.VISIBLE);
        this.directionsTextView.setVisibility(View.GONE);
        this.settingsTextView.setVisibility(View.GONE);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, this.routeOverviewFragment).commit();
    }

    //Open directions view
    public void toDirectionsView() {
        this.homeTextView.setVisibility(View.GONE);
        this.directionsTextView.setVisibility(View.VISIBLE);
        this.settingsTextView.setVisibility(View.GONE);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer, this.mapScreenFragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (!isRequested) {
            ArrayList<String> permissionsToRequest = new ArrayList<>();
            for (int i = 0; i < grantResults.length; i++) {
                permissionsToRequest.add(permissions[i]);
            }

            if (permissionsToRequest.size() > 0) {
                ActivityCompat.requestPermissions(
                        this,
                        permissionsToRequest.toArray(new String[0]),
                        1);
            }

            if (grantResults.length > 0 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED && !isRequested) {
                initialize();
                setClickListeners();
                this.isRequested = true;
            }
        }
    }

    public MapScreenFragment getMapScreenFragment() {
        return this.mapScreenFragment;
    }

    public MainViewModel getMainViewModel() {
        return mainViewModel;
    }
}