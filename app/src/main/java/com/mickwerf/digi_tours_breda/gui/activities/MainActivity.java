package com.mickwerf.digi_tours_breda.gui.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.gui.fragments.MapScreenFragment;
import com.mickwerf.digi_tours_breda.gui.fragments.RouteOverviewFragment;
import com.mickwerf.digi_tours_breda.gui.fragments.SettingScreenFragment;
import com.mickwerf.digi_tours_breda.live_data.MainViewModel;
import com.mickwerf.digi_tours_breda.services.Notify;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ImageView homeButton;
    private ImageView settingsButton;
    private ImageView directionsButton;
    private TextView settingsTextView;
    private TextView homeTextView;
    private TextView directionsTextView;

    private FragmentManager fragmentManager;

    private SettingScreenFragment settingScreenFragment;
    private RouteOverviewFragment routeOverviewFragment;
    private MapScreenFragment mapScreenFragment;

    private MainViewModel mainViewModel;

    private boolean isRequested = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        requestPermissions(new String[] {
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE
        }, 1);

        // Initialise notification settings.
        Notify.initialise(getApplicationContext());
        this.mainViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);
    }

    //Initialize all views
    public void initialize(){

        this.homeButton = findViewById(R.id.HomeImageView);
        this.settingsButton = findViewById(R.id.SettingsImageView);
        this.directionsButton = findViewById(R.id.DirectionsImageView);

        this.directionsTextView = findViewById(R.id.DirectionsTV);
        this.homeTextView = findViewById(R.id.HomeTV);
        this.settingsTextView = findViewById(R.id.SettingsTV);

        this.fragmentManager = getSupportFragmentManager();

        this.settingScreenFragment = new SettingScreenFragment();

        this.routeOverviewFragment = new RouteOverviewFragment(this.mainViewModel,this);

        this.mapScreenFragment = new MapScreenFragment(this.mainViewModel);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,this.routeOverviewFragment).commit();


    }

    //Set all on-click listeners
    public void setClickListeners(){

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
    }

    //Open settings view
    public void toSettingsView(){
        this.homeTextView.setVisibility(View.GONE);
        this.directionsTextView.setVisibility(View.GONE);
        this.settingsTextView.setVisibility(View.VISIBLE);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,this.settingScreenFragment).commit();
    }

    //Open home view
    public void toHomeView(){
        this.homeTextView.setVisibility(View.VISIBLE);
        this.directionsTextView.setVisibility(View.GONE);
        this.settingsTextView.setVisibility(View.GONE);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,this.routeOverviewFragment).commit();
    }

    //Open directions view
    public void toDirectionsView(){
        this.homeTextView.setVisibility(View.GONE);
        this.directionsTextView.setVisibility(View.VISIBLE);
        this.settingsTextView.setVisibility(View.GONE);

        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,this.mapScreenFragment).commit();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(!isRequested) {
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
}