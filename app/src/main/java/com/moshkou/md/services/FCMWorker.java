package com.moshkou.md.services;


import android.content.Context;
import androidx.annotation.NonNull;
import android.util.Log;

import androidx.work.Worker;
import androidx.work.WorkerParameters;


public class FCMWorker extends Worker {

    private static final String TAG = "Volley";

    public FCMWorker(@NonNull Context appContext, @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "Performing long running task in scheduled job");
        // TODO(developer): add long running task here.
        return Result.success();
    }
}