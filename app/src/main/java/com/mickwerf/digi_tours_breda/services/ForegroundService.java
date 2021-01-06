package com.mickwerf.digi_tours_breda.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.mickwerf.digi_tours_breda.R;
import com.mickwerf.digi_tours_breda.gui.activities.MainActivity;

public class ForegroundService extends Service {
    private static final int NOTIFY_ID = 1;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startForeground(NOTIFY_ID, getMyActivityNotification());
        return super.onStartCommand(intent, flags, startId);
    }

    private Notification getMyActivityNotification(){
        PendingIntent contentIntent = PendingIntent.getActivity(this,
                0, new Intent(this, MainActivity.class), 0);

        return new Notification.Builder(this)
                .setContentTitle("App")
                .setContentText("test")
                .setSmallIcon(R.drawable.logo)
                .setContentIntent(contentIntent)
                .build();
    }

    private void updateNotification(){

        Notification notification = getMyActivityNotification();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
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
