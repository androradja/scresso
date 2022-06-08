package com.putrayelfihapp.mp3juicecc.tools;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.putrayelfihapp.mp3juicecc.notifservice.CreateNotification;


public class Config {

    public static String SUGGEST="http://suggest.fando.id/search.php?q=";
    public static String SOUNCLOUDAPI="https://fando.id/soundcloud/";
    public static String SEARCH=SOUNCLOUDAPI+"search.php?q=";
    public static String GENRE=SOUNCLOUDAPI+"genre.php?genre=";
    public static String DOWNLOAD="https://fando.id/soundcloud/get.php?id=";




    public static void createChannel(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CreateNotification.CHANNEL_ID,
                    "Music App", NotificationManager.IMPORTANCE_LOW);
            NotificationManager notificationManager = activity.getSystemService(NotificationManager.class);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }
    }
}
