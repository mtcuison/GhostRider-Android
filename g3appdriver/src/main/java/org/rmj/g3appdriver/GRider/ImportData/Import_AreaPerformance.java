/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GRider.ImportData;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.BranchPerformancePeriod;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Import_AreaPerformance implements ImportInstance {
    private static final String TAG = Import_AreaPerformance.class.getSimpleName();
    private final Application poApp;

    public Import_AreaPerformance(Application application) {
        this.poApp = application;
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            new ImportAreaTask(poApp, callback).execute(BranchPerformancePeriod.getList());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportAreaTask extends AsyncTask<ArrayList<String>, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders loHeaders;
        private final RAreaPerformance loDatabse;
        private final ConnectionUtil loConn;
        private final REmployee poUser;
        private final WebApi poApi;
        private final AppConfigPreference loConfig;

        public ImportAreaTask(Application instance, ImportDataCallback callback) {
            this.callback = callback;
            this.loHeaders = HttpHeaders.getInstance(instance);
            this.loDatabse = new RAreaPerformance(instance);
            this.loConn = new ConnectionUtil(instance);
            this.poUser = new REmployee(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(ArrayList<String>... arrayLists) {
            String response = "";
            try {
                if(loConn.isDeviceConnected()) {
                    if(arrayLists[0].size() > 0) {
                        for(int x = 0 ; x < arrayLists[0].size(); x++) {
                            JSONObject loJSon = new JSONObject();
                            loJSon.put("period", arrayLists[0].get(x));
                            loJSon.put("areacd", poUser.getUserAreaCode());
                            response = WebClient.httpsPostJSon(poApi.getImportAreaPerformance(loConfig.isBackUpServer()), loJSon.toString(), loHeaders.getHeaders());
                            JSONObject loJson = new JSONObject(response);
                            Log.e(TAG, loJson.getString("result"));
                            String lsResult = loJson.getString("result");
                            if (lsResult.equalsIgnoreCase("success")) {
                                JSONArray laJson = loJson.getJSONArray("detail");
                                saveDataToLocal(laJson);
                            } else {
                                JSONObject loError = loJson.getJSONObject("error");
                                String message = loError.getString("message");
                                callback.OnFailedImportData(message);
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

        void saveDataToLocal(JSONArray laJson) throws Exception{
            List<EAreaPerformance> areaInfos = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                EAreaPerformance info = new EAreaPerformance();
                info.setPeriodxx(loJson.getString("sPeriodxx"));
                info.setAreaCode(loJson.getString("sAreaCode"));
                info.setAreaDesc(loJson.getString("sAreaDesc"));
                info.setMCGoalxx(Integer.parseInt(loJson.getString("nMCGoalxx")));
                info.setSPGoalxx(Float.parseFloat(loJson.getString("nSPGoalxx")));
                info.setJOGoalxx(Integer.parseInt(loJson.getString("nJOGoalxx")));
                info.setLRGoalxx(Float.parseFloat(loJson.getString("nLRGoalxx")));
                info.setMCActual(Integer.parseInt(loJson.getString("nMCActual")));
                info.setSPActual(Float.parseFloat(loJson.getString("nSPActual")));
                info.setLRActual(Float.parseFloat(loJson.getString("nLRActual")));
                areaInfos.add(info);
            }
            loDatabse.insertBulkData(areaInfos);
            Log.e(TAG, "Branch info has been save to local.");
        }
    }
}
