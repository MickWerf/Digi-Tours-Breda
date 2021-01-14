package com.mickwerf.digi_tours_breda.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.gui.activities.MainActivity;
import com.mickwerf.digi_tours_breda.gui.fragments.MapScreenFragment;

import static com.mickwerf.digi_tours_breda.services.ServiceChannel.CHANNEL_ID;

public class ForegroundService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,
                0, notificationIntent, 0);

        MapScreenFragment.gpsLogic.start();

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Digitours Breda Edition")
                .setContentText("App is running")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(pendingIntent)
                .build();

        startForeground(1, notification);

        return START_NOT_STICKY;
    }

    public static void BuildNotification(String locationName, Context context) {

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    context.getApplicationContext().getString(R.string.channel_name),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Digitours Breda Edition")
                .setContentText(context.getString(R.string.ArrivedAt) + " " + locationName)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.drawable.logo);

        NotificationManagerCompat.from(context).notify(1, builder.build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}