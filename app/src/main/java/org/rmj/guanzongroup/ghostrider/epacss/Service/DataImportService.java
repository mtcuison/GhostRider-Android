/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Service;

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
import org.rmj.g3appdriver.GRider.ImportData.ImportBarangay;
import org.rmj.g3appdriver.GRider.ImportData.ImportBranch;
import org.rmj.g3appdriver.GRider.ImportData.ImportBrand;
import org.rmj.g3appdriver.GRider.ImportData.ImportBrandModel;
import org.rmj.g3appdriver.GRider.ImportData.ImportCategory;
import org.rmj.g3appdriver.GRider.ImportData.ImportCountry;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.ImportFileCode;
import org.rmj.g3appdriver.GRider.ImportData.ImportInstance;
import org.rmj.g3appdriver.GRider.ImportData.ImportMcModelPrice;
import org.rmj.g3appdriver.GRider.ImportData.ImportMcTermCategory;
import org.rmj.g3appdriver.GRider.ImportData.ImportProvinces;
import org.rmj.g3appdriver.GRider.ImportData.ImportTown;
import org.rmj.g3appdriver.GRider.ImportData.Import_BankList;
import org.rmj.g3appdriver.GRider.ImportData.Import_Occupations;
import org.rmj.g3appdriver.GRider.ImportData.Import_SysConfig;
import org.rmj.g3appdriver.dev.GLocationManager;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DataImportService extends JobService {
    public static final String TAG = DataImportService.class.getSimpleName();
    private AppConfigPreference poConfig;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        poConfig = AppConfigPreference.getInstance(getApplication());
        Log.e(TAG, "Data import service has started.");
        try{
            doBackgroundTask(jobParameters);
        } catch (Exception e){
            e.printStackTrace();
            jobFinished(jobParameters, false);
        }
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        Log.e(TAG, "Data import service has stop.");
        return true;
    }

    private void doBackgroundTask(JobParameters params) {
        ImportInstance[]  importInstances = {
                new Import_BankList(getApplication()),
                new ImportFileCode(getApplication()),
                new ImportBranch(getApplication()),
                new ImportBrand(getApplication()),
                new ImportBrandModel(getApplication()),
                new ImportCategory(getApplication()),
                new ImportProvinces(getApplication()),
                new ImportMcModelPrice(getApplication()),
                new ImportTown(getApplication()),
                new ImportBarangay(getApplication()),
                new ImportMcTermCategory(getApplication()),
                new ImportCountry(getApplication()),
                new Import_Occupations(getApplication()),
                new Import_SysConfig(getApplication())};
        new Thread(() -> {
            new GetLocationTask(getApplication(), () -> Log.d(TAG, "Location retrieved.")).execute();
            for (ImportInstance importInstance : importInstances) {
                importInstance.ImportData(new ImportDataCallback() {
                    @Override
                    public void OnSuccessImportData() {

                    }

                    @Override
                    public void OnFailedImportData(String message) {

                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            jobFinished(params, false);
        }).start();
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

        public interface OnLocationSave{
            void OnSave();
        }

        public GetLocationTask(Application instance, GetLocationTask.OnLocationSave callback){
            loLocation = new GLocationManager(instance);
            poUser = new SessionManager(instance);
            poDevID = new Telephony(instance);
            poSysLog = new RLocationSysLog(instance);
            poConn = new ConnectionUtil(instance);
            poHeaders = HttpHeaders.getInstance(instance);
            poDcp = new RDailyCollectionPlan(instance);
            this.callback = callback;
        }

        @Override
        protected String doInBackground(String... strings) {
            String lsResult = "";
            EGLocatorSysLog loSysLog = new EGLocatorSysLog();
            try {
                if(poDcp.getDCPStatus() > 0) {
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
