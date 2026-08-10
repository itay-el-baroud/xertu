package com.callshield;
import android.app.Application;
public class CallShieldApp extends Application {
    @Override public void onCreate() {
        super.onCreate();
        try {
            com.callshield.utils.NotificationHelper.createChannel(this);
        } catch (Exception e) { e.printStackTrace(); }
    }
}
