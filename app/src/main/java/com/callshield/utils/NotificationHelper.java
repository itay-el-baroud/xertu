package com.callshield.utils;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
public class NotificationHelper {
    public static void createChannel(Context c){
        try{
            if(Build.VERSION.SDK_INT>=26){
                NotificationChannel ch=new NotificationChannel("callshield","CallShield",4);
                NotificationManager nm=(NotificationManager)c.getSystemService(Context.NOTIFICATION_SERVICE);
                if(nm!=null) nm.createNotificationChannel(ch);
            }
        }catch(Exception e){}
    }
}
