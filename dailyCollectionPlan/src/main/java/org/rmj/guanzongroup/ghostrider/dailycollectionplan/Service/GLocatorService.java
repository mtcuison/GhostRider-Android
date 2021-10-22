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
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLocationSysLog;
import org.rmj.g3appdriver.GRider.Database.Repositories.RSysConfig;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
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
        PendingIntent loPending  = PendingIntent.getActivities(GLocatorService.this, 34, new Intent[]{loIntent}, 0);
        poConfig = new RSysConfig(getApplication());
        loNotif = new NotificationCompat.Builder(GLocatorService.this, "gRiderLocator");
        loNotif.setContentTitle("Daily Collection Plan");
        loNotif.setDefaults(NotificationCompat.DEFAULT_ALL);
        loNotif.setSmallIcon(R.drawable.ic_location_tracker);
        loNotif.setContentText("DCP location service is running...");
        loNotif.setContentIntent(loPending);
        loNotif.setAutoCancel(false);
        loNotif.setPriority(NotificationCompat.PRIORITY_MAX);

        startLocationTracking();
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

    private final LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(@NonNull LocationResult locationResult) {
            locationResult.getLastLocation();
            double latitude = locationResult.getLastLocation().getLatitude();
            double longitude = locationResult.getLastLocation().getLongitude();
            try {
                JSONObject loJson = new JSONObject();
                loJson.put("latitude", latitude);
                loJson.put("longitude", longitude);
                new SaveLocationTask(getApplication()).execute(loJson);
                Log.e(TAG, loJson.toString());
            } catch (Exception e){
                e.printStackTrace();
            }
            super.onLocationResult(locationResult);
        }
    };

    @SuppressLint("MissingPermission")
    private void startLocationTracking(){
        LocationRequest loRequest = new LocationRequest();
        poConfig.getLocationInterval(result -> {
            if(result == null){
                result = "10";
            }
            long interval = Long.parseLong(result);
            interval = interval * 60000;
            loRequest.setInterval(interval);
            loRequest.setFastestInterval(120000);
            loRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.getFusedLocationProviderClient(GLocatorService.this).requestLocationUpdates(loRequest, locationCallback, Looper.getMainLooper());
        });
    }

    private static class SaveLocationTask extends AsyncTask<JSONObject, Void, String> {
        private final Application instance;
        private final SessionManager poUser;
        private final Telephony poDevID;
        private final RLocationSysLog poSysLog;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final RDailyCollectionPlan poDcp;
        private final String DateTime;

        private boolean hasDcp = false;

        public SaveLocationTask(Application instance){
            this.instance = instance;
            poUser = new SessionManager(instance);
            poDevID = new Telephony(instance);
            poSysLog = new RLocationSysLog(instance);
            poConn = new ConnectionUtil(instance);
            poHeaders = HttpHeaders.getInstance(instance);
            poDcp = new RDailyCollectionPlan(instance);
            this.DateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());
        }

        @SuppressLint("NewApi")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String lsResult = "";
            EGLocatorSysLog loSysLog = new EGLocatorSysLog();
            try {
                JSONObject loDetail = jsonObjects[0];
                if(poDcp.getDCPStatus() > 0) {
                    hasDcp = true;
                    loSysLog.setDeviceID(poDevID.getDeviceID());
                    loSysLog.setLatitude(loDetail.getString("latitude"));
                    loSysLog.setLongitud(loDetail.getString("longitude"));
                    loSysLog.setTimeStmp(DateTime);
                    loSysLog.setTransact(DateTime);
                    loSysLog.setUserIDxx(poUser.getUserID());
                    String lsLat = loDetail.getString("latitude");
                    String lsLon = loDetail.getString("longitude");
                    if(isLocationEnabled(instance)) {
                        loSysLog.setRemarksx("Location service is not enabled.");
                    } else if(lsLat.equalsIgnoreCase("0.00000000000") ||
                    lsLon.equalsIgnoreCase("0.00000000000")){
                        loSysLog.setRemarksx("Unable to trace location while gps is active.");
                    } else {

                    }
                    poSysLog.saveCurrentLocation(loSysLog);

                    if (poConn.isDeviceConnected()) {
                        JSONObject params = new JSONObject();
                        params.put("dTransact", loSysLog.getTransact());
                        params.put("nLatitude", loSysLog.getLatitude());
                        params.put("nLongitud", loSysLog.getLongitud());
                        lsResult = WebClient.sendRequest(WebApi.URL_DCP_LOCATION_REPORT, params.toString(), poHeaders.getHeaders());
                        if (lsResult == null) {
                            lsResult = AppConstants.SERVER_NO_RESPONSE();
                        } else {
                            JSONObject loJson = new JSONObject(lsResult);
                            if (loJson.getString("result").equalsIgnoreCase("success")) {
                                poSysLog.updateSysLogStatus(loSysLog.getTransact());
                            }
                        }
                    } else {
                        lsResult = AppConstants.NO_INTERNET();
                    }
                } else {
                    hasDcp = false;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResult;
        }

        public static boolean isLocationEnabled(Context context) {
            int locationMode = 0;
            String locationProviders;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                try {
                    locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

                } catch (Settings.SettingNotFoundException e) {
                    e.printStackTrace();
                    return false;
                }

                return locationMode != Settings.Secure.LOCATION_MODE_OFF;

            }else{
                locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
                return !TextUtils.isEmpty(locationProviders);
            }


        }
    }
}
