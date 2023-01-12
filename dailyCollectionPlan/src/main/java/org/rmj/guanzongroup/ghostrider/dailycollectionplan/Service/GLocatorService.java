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
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ServiceInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.dev.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.dev.Database.Repositories.RLocationSysLog;
import org.rmj.g3appdriver.dev.Database.Repositories.RSysConfig;
import org.rmj.g3appdriver.dev.GLocationManager;
import org.rmj.g3appdriver.dev.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.dev.WebClient;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.LocationRetriever;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
