package com.mickwerf.digi_tours_breda.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.gui.activities.MainActivity;
import com.mickwerf.digi_tours_breda.gui.fragments.MapScreenFragment;
import com.mickwerf.digi_tours_breda.live_data.route_logic.GpsLogic;

import static com.mickwerf.digi_tours_breda.services.ServiceChannel.CHANNEL_ID;

public class ForegroundService extends Service {
    //private static final int NOTIFY_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        //startForeground(NOTIFY_ID, getMyActivityNotification());
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

    public static void BuildNotification(String locationname, Context context){

        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, notificationIntent, 0);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            android.app.NotificationChannel channel = new NotificationChannel(
                    "NotificationChannel",
                    "Test Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,"NotificationChannel");
        builder.setContentTitle("Digitours Breda Edition");
        builder.setContentText(context.getString(R.string.ArrivedAt) +" "+ locationname);
        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.logo);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);


        managerCompat.notify(2,builder.build());



//        Intent notificationIntent = new Intent(context, MainActivity.class);
//        PendingIntent pendingIntent = PendingIntent.getActivity(context,
//                0, notificationIntent, 0);
//
//        Notification notification = new NotificationCompat.Builder(context, "NotificationChannel")
//                .setContentTitle("Digitours-Breda")
//                .setContentText(locationname+" arrived")
//                .setSmallIcon(R.drawable.logo)
//                .setContentIntent(pendingIntent)
//                .build();
    }

//    private Notification getMyActivityNotification(){
//        PendingIntent contentIntent = PendingIntent.getActivity(this,
//                0, new Intent(this, MainActivity.class), 0);
//
//        return new Notification.Builder(this)
//                .setContentTitle("App")
//                .setContentText("test")
//                .setSmallIcon(R.drawable.logo)
//                .setContentIntent(contentIntent)
//                .build();
//    }
//
//    private void updateNotification(){
//
//        Notification notification = getMyActivityNotification();
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(NOTIFY_ID, notification);
//    }

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