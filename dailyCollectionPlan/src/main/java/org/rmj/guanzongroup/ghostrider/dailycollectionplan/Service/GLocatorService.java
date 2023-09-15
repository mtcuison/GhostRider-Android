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

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import org.rmj.g3appdriver.GCircle.room.Repositories.RSysConfig;
import org.rmj.g3appdriver.lib.Location.LocationRetriever;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class GLocatorService extends Service {
    private static final String TAG = GLocatorService.class.getSimpleName();
    private NotificationCompat.Builder loNotif;

    private RSysConfig poConfig;

    private long pnInterval = 0;

    public GLocatorService() {}

    public GLocatorService(long fnInterval) {
        this.pnInterval = fnInterval;
    }

    @SuppressLint("NewApi")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        createServiceNotification();
        Intent loIntent = new Intent(GLocatorService.this, Activity_CollectionList.class);
        PendingIntent loPending  = PendingIntent.getActivities(GLocatorService.this, 34, new Intent[]{loIntent}, PendingIntent.FLAG_IMMUTABLE);
        poConfig = new RSysConfig(getApplication());
        loNotif = new NotificationCompat.Builder(GLocatorService.this, "gRiderLocator");
        loNotif.setContentTitle("Daily Collection Plan");
        loNotif.setDefaults(NotificationCompat.DEFAULT_ALL);
        loNotif.setSmallIcon(R.drawable.ic_location_tracker);
        loNotif.setContentText("DCP location service is running...");
        loNotif.setContentIntent(loPending);
        loNotif.setAutoCancel(false);
        loNotif.setPriority(NotificationCompat.PRIORITY_MAX);

        new LocationRetriever(getApplication()).GetLocationOnBackgroud();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(1, loNotif.build(), ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION);
        } else {
            startForeground(1, loNotif.build());
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createServiceNotification(){
        NotificationChannel loChannel = new NotificationChannel("gRiderLocator", "DCP Location Service", NotificationManager.IMPORTANCE_HIGH);
        NotificationManager loManager = getSystemService(NotificationManager.class);
        loManager.createNotificationChannel(loChannel);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        stopForeground(STOP_FOREGROUND_REMOVE);
        super.onDestroy();
    }
}
