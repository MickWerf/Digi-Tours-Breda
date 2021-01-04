package com.mickwerf.digi_tours_breda.gui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.services.Notify;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialise notification settings.
        Notify.initialise(getApplicationContext());
    }
}