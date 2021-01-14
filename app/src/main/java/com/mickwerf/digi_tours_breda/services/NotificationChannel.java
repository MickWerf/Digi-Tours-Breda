package com.mickwerf.digi_tours_breda.services;

import android.app.Application;
import android.app.NotificationManager;

import com.mickwerf.digi_tours_breda.R;

public class NotificationChannel extends Application {

    static final String CHANNEL_ID = "Navigation";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        android.app.NotificationChannel serviceChannel = new android.app.NotificationChannel(
                CHANNEL_ID,
                getApplicationContext().getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
        );
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(serviceChannel);
    }
}
