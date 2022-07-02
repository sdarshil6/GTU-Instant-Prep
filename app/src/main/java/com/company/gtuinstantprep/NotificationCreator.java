package com.company.gtuinstantprep;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

public class NotificationCreator extends Application {

    public static final String CHANNEL1_ID = "channel_1";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name_channel1 = getString(R.string.channel1_name);
            String description_channel1 = getString(R.string.channel1_description);
            int importance_channel1 = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel1 = new NotificationChannel(CHANNEL1_ID, name_channel1, importance_channel1);
            channel1.setDescription(description_channel1);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel1);
            Log.d("TAG", "NotificationCreator is active");
        }

    }
}
