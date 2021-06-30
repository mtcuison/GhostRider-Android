/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 6/24/21 3:04 PM
 * project file last modified : 6/24/21 3:04 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Service;

import android.app.Application;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R; 

public class GLocatorService extends Service {

    private final Application instance;

    public GLocatorService(Application application){
        this.instance = application;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public Notification getForeGroundNotificationService(){
        Intent notificationIntent = new Intent(instance, Activity_CollectionList.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(instance, 0, notificationIntent, 0);

        return new Notification.Builder(instance, "GeoLocatorService")
                .setContentTitle("Daily Collection Plan")
                .setContentText("Collection Service is running...")
                .setSmallIcon(R.drawable.guanzo_small_logo)
                .setContentIntent(pendingIntent)
                .setTicker("Location tracking...")
                .build();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
