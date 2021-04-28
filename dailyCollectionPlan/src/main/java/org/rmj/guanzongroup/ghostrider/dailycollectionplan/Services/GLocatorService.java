/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/27/21 4:25 PM
 * project file last modified : 4/27/21 4:25 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Services;

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
import org.rmj.g3appdriver.GRider.Database.Repositories.RLocationSysLog;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.GLocationManager;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class GLocatorService extends JobService {
    private static final String TAG = "GLocator Service";

    @Override
    public boolean onStartJob(JobParameters params) {
        try{
            new GetLocationTask(getApplication(), () -> jobFinished(params, true)).execute();
        } catch (Exception e){
            e.printStackTrace();
            jobFinished(params, true);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return true;
    }

    private static class GetLocationTask extends AsyncTask<String, Void, String>{
        private final GLocationManager loLocation;
        private final SessionManager poUser;
        private final Telephony poDevID;
        private final RLocationSysLog poSysLog;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final OnLocationSave callback;

        public interface OnLocationSave{
            void OnSave();
        }

        public GetLocationTask(Application instance, OnLocationSave callback){
            loLocation = new GLocationManager(instance);
            poUser = new SessionManager(instance);
            poDevID = new Telephony(instance);
            poSysLog = new RLocationSysLog(instance);
            poConn = new ConnectionUtil(instance);
            poHeaders = HttpHeaders.getInstance(instance);
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String lsResult = "";
            EGLocatorSysLog loSysLog = new EGLocatorSysLog();
            try {
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

                if(poConn.isDeviceConnected()) {
                    lsResult = WebClient.httpsPostJSon(WebApi.URL_DCP_LOCATION_REPORT, "", poHeaders.getHeaders());
                    if (lsResult == null) {
                        lsResult = AppConstants.SERVER_NO_RESPONSE();
                    } else {
                        JSONObject loJson = new JSONObject(lsResult);
                        if (loJson.getString("result").equalsIgnoreCase("success")){
                            poSysLog.updateSysLogStatus(loSysLog.getTransact());
                        }
                    }
                } else {
                    lsResult = AppConstants.NO_INTERNET();
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            callback.OnSave();
        }
    }
}
