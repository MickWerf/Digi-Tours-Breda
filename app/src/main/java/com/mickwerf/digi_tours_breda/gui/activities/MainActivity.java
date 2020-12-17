package com.mickwerf.digi_tours_breda.gui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;


import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.data.entities.Route;
import com.mickwerf.digi_tours_breda.gui.fragments.MapScreenFragment;
import com.mickwerf.digi_tours_breda.gui.fragments.RouteOverviewFragment;
import com.mickwerf.digi_tours_breda.gui.fragments.SettingScreenFragment;
import com.mickwerf.digi_tours_breda.live_data.MainViewModel;

import java.util.List;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mainViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication())).get(MainViewModel.class);

        initialize();
        setClickListeners();

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
        this.routeOverviewFragment = new RouteOverviewFragment(this.mainViewModel.getRoutes());
        this.mapScreenFragment = new MapScreenFragment(this.mainViewModel.getActiveRoute());

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
}