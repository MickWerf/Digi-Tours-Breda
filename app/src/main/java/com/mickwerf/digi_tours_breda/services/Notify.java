package com.mickwerf.digi_tours_breda.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.app.NotificationCompat;

import com.mickwerf.digi_tours_breda.R;

public class Notify {

    /**
     * Unique channel ID to categorise the notifications.
     */
    private final static String CHANNEL_ID = "navigation";

    /**
     * Initialise the notification types in the Android settings.
     * @param context Necessary to access the system service.
     */
    public static void initialise(Context context) {
        NotificationManager nm = context.getSystemService(NotificationManager.class);
        nm.createNotificationChannel(new NotificationChannel(
                CHANNEL_ID,
                context.getString(R.string.channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
        ));
    }

    /**
     * Create a clean notification
     * @param waypoint Name of the waypoint.
     * @param context  Necessary to access the system service.
     */
    public static void createNotification(String waypoint, String directions, Context context) {
        createNotification(waypoint, directions, null, context);
    }

    /**
     * Create a clean notification with intend.
     * @param waypoint Name of the waypoint.
     * @param intent   Necessary to open the intent when notification is pressed.
     * @param context  Necessary to access the system service.
     */
    public static void createNotification(String waypoint, String directions, Intent intent, Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_small)
                .setContentTitle("Arrived at " + waypoint)
                .setContentText(directions)
                .setStyle(new NotificationCompat.BigTextStyle())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (intent != null)
            builder.setContentIntent(PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_NO_CREATE));
        context.getSystemService(NotificationManager.class).notify((int) System.currentTimeMillis(), builder.build());
    }

    /**
     * Vibrate the phone.
     * @param context Necessary to access the vibrator service.
     */
    public static void vibrate(Context context) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    /**
     * Play the Android notification sound.
     * @param context Necessary to create a ringtone.
     */
    public static void playNotificationSound(Context context) {
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone r = RingtoneManager.getRingtone(context, notification);
        r.play();
    }
}
