package com.callshield.service;

import android.telecom.Call;
import android.telecom.CallScreeningService;
import androidx.annotation.NonNull;
import com.callshield.utils.NotificationHelper;

public class CallBlockerService extends CallScreeningService {

    @Override
    public void onScreenCall(@NonNull Call.Details details) {
        try {
            NotificationHelper.createChannel(this);
        } catch (Exception e) {}

        try {
            CallResponse.Builder builder = new CallResponse.Builder();
            respondToCall(details, builder.build());
        } catch (Exception e) {
            try {
                respondToCall(details, new CallResponse.Builder().build());
            } catch (Exception ex) {}
        }
    }
}
