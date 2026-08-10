package com.callshield.service;

import android.telecom.Call;
import android.telecom.CallScreeningService;
import com.callshield.data.AppDatabase;
import com.callshield.data.BlockedNumber;
import com.callshield.data.CallLogEntry;
import com.callshield.util.NotificationHelper;

public class CallBlockerService extends CallScreeningService {
    @Override
    public void onScreenCall(Call.Details details) {
        String phone = null;
        if (details.getHandle()!= null) {
            phone = details.getHandle().getSchemeSpecificPart();
        }
        if (phone == null) {
            respondToCall(details, new CallResponse.Builder().build());
            return;
        }

        AppDatabase db = AppDatabase.getInstance(this);
        BlockedNumber blocked = db.blockedDao().findByNumber(phone);

        boolean shouldBlock = false;
        if (blocked!= null) {
            if (blocked.expiresAt!= null && blocked.expiresAt < System.currentTimeMillis()) {
                db.blockedDao().delete(blocked);
            } else {
                shouldBlock = true;
                blocked.attemptsCount++;
                blocked.lastAttemptTime = System.currentTimeMillis();
                db.blockedDao().update(blocked);
            }
        }

        db.logDao().insert(new CallLogEntry(phone, "call"));

        if (shouldBlock) {
            CallResponse response = new CallResponse.Builder()
                   .setDisallowCall(true)
                   .setRejectCall(true)
                   .setSkipCallLog(false)
                   .setSkipNotification(false)
                   .build();
            respondToCall(details, response);
            int count = blocked!= null? blocked.attemptsCount : 1;
            NotificationHelper.showBlockedNotification(this, phone, count);
        } else {
            respondToCall(details, new CallResponse.Builder().build());
        }
    }
}
