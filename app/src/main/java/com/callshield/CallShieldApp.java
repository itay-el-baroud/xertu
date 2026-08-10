package com.callshield;

import android.app.Application;
import androidx.work.Configuration;
import com.callshield.util.NotificationHelper;

public class CallShieldApp extends Application implements Configuration.Provider {
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createChannels(this);
    }

    @Override
    public Configuration getWorkManagerConfiguration() {
        return new Configuration.Builder().build();
    }
}
