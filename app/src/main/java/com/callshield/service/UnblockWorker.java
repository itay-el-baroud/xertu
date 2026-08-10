package com.callshield.service;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.callshield.data.AppDatabase;

public class UnblockWorker extends Worker {
    public UnblockWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppDatabase db = AppDatabase.getInstance(getApplicationContext());
        long now = System.currentTimeMillis();
        db.blockedDao().deleteExpired(now);
        return Result.success();
    }
}
