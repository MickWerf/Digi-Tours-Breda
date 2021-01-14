package com.mickwerf.digi_tours_breda.services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.gui.activities.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

import static com.mickwerf.digi_tours_breda.services.NotificationChannel.CHANNEL_ID;

public class ForegroundNotification extends Service {

    private static final String TAG = ForegroundNotification.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "Foreground service called.");

        Notify.initialise(getApplicationContext());

        NotificationCompat.Builder notificationBuilder = Notify.createNotification("Museum", "Sla links af", new Intent(this, MainActivity.class), getApplicationContext());

        startForeground(1, notificationBuilder.build());

        // TODO replace timer with callback or Current User GPS Location change listener.
        Timer timer = new Timer("counter timer");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.d(TAG, "Timer 1s called.");

                // TODO write here logic to get current notification information for user
                // Show to which waypoint the user is walking,   e.g. Musem.
                // Show how much further the user has to walk,   e.g. 150 m.
                // Show in which direction the user has to walk, e.g. Go north on the Kerkplein.
                Notify.createNotification("Museum", "Sla links af", getApplicationContext());
            }
        }, 0, 1000);

        return START_REDELIVER_INTENT;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
