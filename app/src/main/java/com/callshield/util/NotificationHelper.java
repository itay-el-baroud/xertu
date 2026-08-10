package com.callshield.util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import com.callshield.R;

public class NotificationHelper {
    private static final String CHANNEL_ID = "callshield_channel";

    public static void createChannels(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "CallShield Alerts",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Notifications for blocked calls");
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager!= null) manager.createNotificationChannel(channel);
        }
    }

    public static void showBlockedNotification(Context context, String number, int count) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
               .setSmallIcon(R.drawable.ic_block)
               .setContentTitle("Blocked call")
               .setContentText(number + " tried " + count + " times")
               .setPriority(NotificationCompat.PRIORITY_HIGH)
               .setAutoCancel(true);
        if (manager!= null) manager.notify(number.hashCode(), builder.build());
    }
}
