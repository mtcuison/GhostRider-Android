/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/30/21 10:00 AM
 * project file last modified : 4/30/21 10:00 AM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Service;

import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLocationSysLog;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.GLocationManager;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder;

import static org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder.DCP_DATA;
import static org.rmj.guanzongroup.ghostrider.notifications.Object.GNotifBuilder.createNotification;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DCPLocatorService extends JobService {
    private static final String TAG = DCPLocatorService.class.getSimpleName();
    private GNotifBuilder loBuilder;
    private final GLocationManager loLocation = new GLocationManager(DCPLocatorService.this);

    @Override
    public boolean onStartJob(JobParameters params) {
        loBuilder = createNotification(DCPLocatorService.this, "Daily Collection Plan", "While Dcp is on going. Location must be enabled.", DCP_DATA);
        loBuilder.setPriority(true);
        new Thread(() -> new GetLocationTask(getApplication(), loLocation, new GetLocationTask.OnLocationSave() {
            @Override
            public void OnStartTracking() {
                loBuilder.show();
            }

            @Override
            public void OnSave() {
                jobFinished(params, false);
            }

            @Override
            public void OnStopTracking() {
                loBuilder.dismiss();
            }
        }).execute()).start();
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    private static class GetLocationTask extends AsyncTask<String, Void, String> {
        private final GLocationManager loLocation;
        private final SessionManager poUser;
        private final Telephony poDevID;
        private final RLocationSysLog poSysLog;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final RDailyCollectionPlan poDcp;
        private final GetLocationTask.OnLocationSave callback;

        private boolean hasDcp = false;

        public interface OnLocationSave{
            void OnStartTracking();
            void OnSave();
            void OnStopTracking();
        }

        public GetLocationTask(Application instance, GLocationManager foLocation, GetLocationTask.OnLocationSave callback){
            loLocation = foLocation;
            poUser = new SessionManager(instance);
            poDevID = new Telephony(instance);
            poSysLog = new RLocationSysLog(instance);
            poConn = new ConnectionUtil(instance);
            poHeaders = HttpHeaders.getInstance(instance);
            poDcp = new RDailyCollectionPlan(instance);
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            String lsResult = "";
            EGLocatorSysLog loSysLog = new EGLocatorSysLog();
            try {
                if(poDcp.getDCPStatus() > 0) {
                    hasDcp = true;
                    loLocation.getLocation((latitude, longitude) -> {
                        Log.d(TAG, "Current Device Location Retrieve : Latitude " + latitude + ", Longitude" + longitude);
                        loSysLog.setDeviceID(poDevID.getDeviceID());
                        loSysLog.setLatitude(latitude);
                        loSysLog.setLongitud(longitude);
                        loSysLog.setTimeStmp(AppConstants.DATE_MODIFIED);
                        loSysLog.setTransact(AppConstants.DATE_MODIFIED);
                        loSysLog.setUserIDxx(poUser.getUserID());
                        poSysLog.saveCurrentLocation(loSysLog);
                    });

                    if (poConn.isDeviceConnected()) {
                        lsResult = WebClient.httpsPostJSon(WebApi.URL_DCP_LOCATION_REPORT, "", poHeaders.getHeaders());
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(hasDcp){
                callback.OnStartTracking();
            } else {
                callback.OnStopTracking();
            }
            callback.OnSave();
        }
    }
}
