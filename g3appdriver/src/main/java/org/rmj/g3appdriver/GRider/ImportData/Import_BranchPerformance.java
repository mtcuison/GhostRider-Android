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
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.IMPORT_BRANCH_PERFORMANCE;

public class Import_BranchPerformance implements ImportInstance {
    public static final String TAG = Import_BranchPerformance.class.getSimpleName();

    private final Application instance;

    private String sAreaCode;

    public Import_BranchPerformance(Application application) {
        this.instance = application;
    }

    public void setsAreaCode(String sAreaCode) {
        this.sAreaCode = sAreaCode;
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        JSONObject loJson = new JSONObject();
        try{
            loJson.put("period", "201911");
            new ImportBranchTask(instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportBranchTask extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders loHeaders;
        private final RBranchPerformance loDatabse;
        private final ConnectionUtil loConn;
        private final REmployee poUser;

        public ImportBranchTask(Application application, ImportDataCallback callback) {
            this.callback = callback;
            this.loDatabse = new RBranchPerformance(application);
            this.loConn = new ConnectionUtil(application);
            this.loHeaders = HttpHeaders.getInstance(application);
            this.poUser = new REmployee(application);
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            JSONObject loJSon = jsonObjects[0];
            String response = "";
            try {
                if(loConn.isDeviceConnected()) {
                    loJSon.put("areacd", poUser.getUserAreaCode());
                    response = WebClient.httpsPostJSon(IMPORT_BRANCH_PERFORMANCE, loJSon.toString(), loHeaders.getHeaders());
                    JSONObject loJson = new JSONObject(response);
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if(lsResult.equalsIgnoreCase("success")){
                        JSONArray laJson = loJson.getJSONArray("detail");
                        saveDataToLocal(laJson);
                    } else {
                        JSONObject loError = loJson.getJSONObject("error");
                        String message = loError.getString("message");
                        callback.OnFailedImportData(message);
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
            Log.e(TAG, "Branch info has been save to local.");
        }
    }
}
