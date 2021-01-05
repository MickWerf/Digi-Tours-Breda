package com.mickwerf.digi_tours_breda.gui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.gui.activities.MainActivity;


public class SettingScreenFragment extends Fragment {


    private ImageView howToUseIV;
    private ImageView noGpsIV;

    private TextView howToUseTV;
    private TextView noGpsTV;

    private TextView howToUseText;
    private TextView noGpsText;

    private boolean howToUseOpen;
    private boolean noGpsOpen;

    private ImageView dutchButton;
    private ImageView germanButton;
    private ImageView englishButton;

    private MainActivity mainActivity;

    public SettingScreenFragment(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_screen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.howToUseIV = this.getView().findViewById(R.id.howToUseIV);
        this.noGpsIV = this.getView().findViewById(R.id.noGpsIV);

        this.noGpsOpen = false;
        this.howToUseOpen = false;

        this.howToUseTV = this.getView().findViewById(R.id.HowToUseTV);
        this.noGpsTV = this.getView().findViewById(R.id.noGpsTV);

        this.englishButton = this.getView().findViewById(R.id.englishButton);
        this.dutchButton = this.getView().findViewById(R.id.dutchButton);
        this.germanButton = this.getView().findViewById(R.id.germanButton);

        this.noGpsText = this.getView().findViewById(R.id.noGpsText);
        this.howToUseText = this.getView().findViewById(R.id.howToUseText);

        setOnClickListeners();
    }

    public void setOnClickListeners(){

        this.howToUseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHowToUse();
            }
        });

        this.noGpsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoGpsCase();
            }
        });

        this.howToUseIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHowToUse();
            }
        });

        this.noGpsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoGpsCase();
            }
        });

        this.dutchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToDutch();
            }
        });

        this.englishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToEnglish();
            }
        });

        this.germanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeToGerman();
            }
        });

        this.howToUseText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showHowToUse();
            }
        });

        this.noGpsText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNoGpsCase();
            }
        });
    }

    public void changeToEnglish() {
        //TODO change language to English
        mainActivity.updateUserSettings("en", "Engels");
        mainActivity.restartActivity();
    }

    public void changeToDutch() {
        //TODO change language to Dutch
        mainActivity.updateUserSettings("nl", "Nederlands");
        mainActivity.restartActivity();
    }

    public void changeToGerman() {
        //TODO change language to German
        mainActivity.updateUserSettings("de", "Duits");
        mainActivity.restartActivity();
    }

    public void showHowToUse(){
        if(!this.howToUseOpen){
            this.howToUseIV.setRotation(90);
            this.howToUseTV.setVisibility(View.VISIBLE);
            if(noGpsOpen){
                showNoGpsCase();
            }
            this.howToUseOpen = true;

        }else {
            this.howToUseIV.setRotation(0);
            this.howToUseTV.setVisibility(View.GONE);
            this.howToUseOpen = false;
        }
    }

    public void showNoGpsCase(){
        if(!this.noGpsOpen){
            this.noGpsIV.setRotation(90);
            this.noGpsTV.setVisibility(View.VISIBLE);
            if(howToUseOpen){
                showHowToUse();
            }
            this.noGpsOpen = true;

        }else {
            this.noGpsIV.setRotation(0);
            this.noGpsTV.setVisibility(View.GONE);
            this.noGpsOpen = false;
        }
    }
}