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
    public static void requestAllPermissions(Activity a){
        try{
            List<String> l = new ArrayList<>();
            if(ContextCompat.checkSelfPermission(a, Manifest.permission.READ_PHONE_STATE)!=PackageManager.PERMISSION_GRANTED) l.add(Manifest.permission.READ_PHONE_STATE);
            if(ContextCompat.checkSelfPermission(a, Manifest.permission.READ_CALL_LOG)!=PackageManager.PERMISSION_GRANTED) l.add(Manifest.permission.READ_CALL_LOG);
            if(ContextCompat.checkSelfPermission(a, Manifest.permission.ANSWER_PHONE_CALLS)!=PackageManager.PERMISSION_GRANTED) l.add(Manifest.permission.ANSWER_PHONE_CALLS);
            if(Build.VERSION.SDK_INT >= 33){
                if(ContextCompat.checkSelfPermission(a, Manifest.permission.POST_NOTIFICATIONS)!=PackageManager.PERMISSION_GRANTED) l.add(Manifest.permission.POST_NOTIFICATIONS);
            }
            if(!l.isEmpty()) ActivityCompat.requestPermissions(a, l.toArray(new String[0]), REQ_CODE);
        }catch(Exception e){}
    }
}
