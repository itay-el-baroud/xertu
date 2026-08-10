package com.callshield.util;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
public class NotificationHelper {
    public static void createChannel(Context ctx){
        try{
            if(Build.VERSION.SDK_INT>=26){
                NotificationChannel ch=new NotificationChannel("callshield","CallShield",NotificationManager.IMPORTANCE_DEFAULT);
                NotificationManager nm=(NotificationManager)ctx.getSystemService(Context.NOTIFICATION_SERVICE);
                if(nm!=null) nm.createNotificationChannel(ch);
            }
        }catch(Exception e){}
    }
}
