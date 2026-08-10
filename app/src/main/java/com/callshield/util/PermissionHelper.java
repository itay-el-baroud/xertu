package com.callshield.utils;
import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;
public class PermissionHelper {
    public static final int REQ_CODE = 1001;
    public static void requestAllPermissions(Activity activity) {
        try {
            List<String> toAdd = new ArrayList<>();
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED)
                toAdd.add(Manifest.permission.READ_PHONE_STATE);
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CALL_LOG)!= PackageManager.PERMISSION_GRANTED)
                toAdd.add(Manifest.permission.READ_CALL_LOG);
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ANSWER_PHONE_CALLS)!= PackageManager.PERMISSION_GRANTED)
                toAdd.add(Manifest.permission.ANSWER_PHONE_CALLS);
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED)
                toAdd.add(Manifest.permission.READ_CONTACTS);
            if (Build.VERSION.SDK_INT >= 33) {
                if (ContextCompat.checkSelfPermission(activity, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED)
                    toAdd.add(Manifest.permission.POST_NOTIFICATIONS);
            }
            if (!toAdd.isEmpty()) {
                ActivityCompat.requestPermissions(activity, toAdd.toArray(new String[0]), REQ_CODE);
            }
        } catch (Exception e) { e.printStackTrace(); }
    }
}
