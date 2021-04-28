/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/28/21 3:52 PM
 * project file last modified : 4/28/21 3:52 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Services.GLocatorService;

import java.util.Objects;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class LocatorScheduler {
    public static final String TAG = LocatorScheduler.class.getSimpleName();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void scheduleJob(Context context){
        @SuppressLint("JobSchedulerService") ComponentName loComp = new ComponentName(context, GLocatorService.class);
        @SuppressLint("MissingPermission")
        JobInfo loJob = new JobInfo.Builder(AppConstants.GLocatorServiceID, loComp)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .setPeriodic(900000)
                .build();
        JobScheduler loScheduler = (JobScheduler)context.getSystemService(JOB_SCHEDULER_SERVICE);
        int liResult = Objects.requireNonNull(loScheduler).schedule(loJob);
        if(liResult == JobScheduler.RESULT_SUCCESS){
            Log.e(TAG, "Job Scheduled");
        } else {
            Log.e(TAG, "Job Schedule Failed.");
        }
    }
}
