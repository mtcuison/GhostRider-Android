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
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.BranchPerformancePeriod;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class Import_BranchPerformance implements ImportInstance {
    public static final String TAG = Import_BranchPerformance.class.getSimpleName();

    private final Application instance;
    private final ArrayList<String> poPeriodc;
    private String sAreaCode;

    public Import_BranchPerformance(Application application) {
        this.instance = application;
        this.poPeriodc = new ArrayList<>();
        this.poPeriodc.addAll(BranchPerformancePeriod.getList());
    }

    public void setsAreaCode(String sAreaCode) {
        this.sAreaCode = sAreaCode;
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            new ImportBranchTask(instance, callback).execute(poPeriodc);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportBranchTask extends AsyncTask<ArrayList<String>, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders loHeaders;
        private final RBranchPerformance loDatabse;
        private final ConnectionUtil loConn;
        private final REmployee poUser;
        private final WebApi poApi;

        public ImportBranchTask(Application instance, ImportDataCallback callback) {
            this.callback = callback;
            this.loDatabse = new RBranchPerformance(instance);
            this.loConn = new ConnectionUtil(instance);
            this.loHeaders = HttpHeaders.getInstance(instance);
            this.poUser = new REmployee(instance);
            AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(ArrayList<String>... strings) {
            String response = "";
            try {
                if(loConn.isDeviceConnected()) {
                    if(strings[0].size() > 0) {
                        for(int x = 0; x < strings[0].size(); x++) {
                            JSONObject loJSon = new JSONObject();
                            loJSon.put("period", strings[0].get(x));
                            loJSon.put("areacd", poUser.getUserAreaCode());
                            response = WebClient.httpsPostJSon(poApi.getImportBranchPerformance(), loJSon.toString(), loHeaders.getHeaders());
                            if(response == null){
                                response = AppConstants.SERVER_NO_RESPONSE();
                            } else {
                                JSONObject loJson = new JSONObject(response);
                                String lsResult = loJson.getString("result");
                                if (lsResult.equalsIgnoreCase("success")) {
                                    JSONArray laJson = loJson.getJSONArray("detail");
                                    saveDataToLocal(laJson);
                                } else {
                                    JSONObject loError = loJson.getJSONObject("error");
                                    String message = loError.getString("message");
                                    callback.OnFailedImportData(message);
                                }
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
            List<EBranchPerformance> branchInfo = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                EBranchPerformance info = new EBranchPerformance();
                info.setPeriodxx(loJson.getString("sPeriodxx"));
                info.setBranchCd(loJson.getString("sBranchCd"));
                info.setBranchNm(loJson.getString("sBranchNm"));
                info.setMCGoalxx(Integer.parseInt(loJson.getString("nMCGoalxx")));
                info.setSPGoalxx(Float.parseFloat(loJson.getString("nSPGoalxx")));
                info.setJOGoalxx(Integer.parseInt(loJson.getString("nJOGoalxx")));
                info.setLRGoalxx(Float.parseFloat(loJson.getString("nLRGoalxx")));
                info.setMCActual(Integer.parseInt(loJson.getString("nMCActual")));
                info.setSPActual(Float.parseFloat(loJson.getString("nSPActual")));
                info.setLRActual(Float.parseFloat(loJson.getString("nLRActual")));
                branchInfo.add(info);
            }
            loDatabse.insertBulkData(branchInfo);
        }
    }
}
