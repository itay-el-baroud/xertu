package com.callshield.utils;
import android.Manifest;
import android.app.Activity;
import androidx.core.app.ActivityCompat;
import java.util.ArrayList;
import java.util.List;
public class PermissionHelper {
    public static final int REQ_CODE=1001;
    public static void requestAllPermissions(Activity a){
        try{
            List<String> l=new ArrayList<>();
            l.add(Manifest.permission.READ_PHONE_STATE);
            l.add(Manifest.permission.READ_CALL_LOG);
            l.add(Manifest.permission.ANSWER_PHONE_CALLS);
            ActivityCompat.requestPermissions(a,l.toArray(new String[0]),REQ_CODE);
        }catch(Exception e){}
    }
}
