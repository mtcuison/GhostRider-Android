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

import static org.rmj.g3appdriver.utils.WebApi.IMPORT_AREA_PERFORMANCE;
import static org.rmj.g3appdriver.utils.WebApi.IMPORT_BRANCH_PERFORMANCE;

import android.app.Application;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.BranchPerformancePeriod;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
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
import org.rmj.g3appdriver.GRider.ImportData.Import_AreaPerformance;
import org.rmj.g3appdriver.GRider.ImportData.Import_BankList;
import org.rmj.g3appdriver.GRider.ImportData.Import_BranchPerformance;
import org.rmj.g3appdriver.GRider.ImportData.Import_Occupations;
import org.rmj.g3appdriver.GRider.ImportData.Import_Relation;
import org.rmj.g3appdriver.GRider.ImportData.Import_SysConfig;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.Arrays;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class PerformanceImportService extends JobService {
    public static final String TAG = PerformanceImportService.class.getSimpleName();

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
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
                new Import_AreaPerformance(getApplication()),
                new Import_BranchPerformance(getApplication())};
        new Thread(() -> {
            for (ImportInstance importInstance : importInstances) {
                importInstance.ImportData(new ImportDataCallback() {
                    @Override
                    public void OnSuccessImportData() {
                        Log.e(TAG, importInstance.getClass().getSimpleName() + " import success.");
                    }

                    @Override
                    public void OnFailedImportData(String message) {
                        Log.e(TAG, importInstance.getClass().getSimpleName() + " import failed. " + message);
                    }
                });
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            AppConfigPreference.getInstance(PerformanceImportService.this).setLastSyncDate(new AppConstants().CURRENT_DATE);
            jobFinished(params, false);
        }).start();
    }

    private static class DownloadAreaBranchesPerformanceTask extends AsyncTask<String, Void, String> {

        private final RAreaPerformance poArea;
        private final RBranchPerformance poBranch;
        private final REmployee poUser;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;

        public DownloadAreaBranchesPerformanceTask(Application instance) {
            this.poArea = new RAreaPerformance(instance);
            this.poBranch = new RBranchPerformance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new REmployee(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... strings) {
            String response = "";
            try {
                ArrayList<String> lsPeriod = BranchPerformancePeriod.getList();
                if(poConn.isDeviceConnected()) {
                    if(lsPeriod.size() > 0) {
                        for(int x = 0 ; x < lsPeriod.size(); x++) {
                            JSONObject loJSon = new JSONObject();
                            loJSon.put("period", lsPeriod.get(x));
                            loJSon.put("areacd", poUser.getUserAreaCode());
                            response = WebClient.httpsPostJSon(IMPORT_AREA_PERFORMANCE, loJSon.toString(), poHeaders.getHeaders());
                            JSONObject loJson = new JSONObject(response);
                            Log.e(TAG, loJson.getString("result"));
                            String lsResult = loJson.getString("result");
                            if (lsResult.equalsIgnoreCase("success")) {
                                JSONArray laJson = loJson.getJSONArray("detail");
//                                saveDataToLocal(laJson);
//                                for(int x = 0; x < strings[0].size(); x++) {
//                                    JSONObject loJSon = new JSONObject();
//                                    loJSon.put("period", strings[0].get(x));
//                                    loJSon.put("areacd", poUser.getUserAreaCode());
//                                    response = WebClient.httpsPostJSon(IMPORT_BRANCH_PERFORMANCE, loJSon.toString(), loHeaders.getHeaders());
//                                    if(response == null){
//                                        response = AppConstants.SERVER_NO_RESPONSE();
//                                    } else {
//                                        JSONObject loJson = new JSONObject(response);
//                                        Log.e(TAG, loJson.getString("result"));
//                                        String lsResult = loJson.getString("result");
//                                        if (lsResult.equalsIgnoreCase("success")) {
//                                            JSONArray laJson = loJson.getJSONArray("detail");
//                                            Log.e(TAG, laJson.toString());
//                                            saveDataToLocal(laJson);
//                                        } else {
//                                            JSONObject loError = loJson.getJSONObject("error");
//                                            String message = loError.getString("message");
//                                            callback.OnFailedImportData(message);
//                                        }
//                                    }
//                                    Thread.sleep(1000);
//                                }
                            } else {
                                JSONObject loError = loJson.getJSONObject("error");
                                String message = loError.getString("message");
//                                callback.OnFailedImportData(message);
                            }
                            Thread.sleep(1000);
                        }
                    }
                } else {
                    response = AppConstants.NO_INTERNET();
                }
            } catch (Exception e) {
                Log.e(TAG, Arrays.toString(e.getStackTrace()));
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
