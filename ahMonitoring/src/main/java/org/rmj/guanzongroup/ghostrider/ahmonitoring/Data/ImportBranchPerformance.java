/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Data;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.ImportInstance;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import static org.rmj.g3appdriver.GRider.Constants.AppConstants.PERFORMANCE_CURRENT_PERIOD;
import static org.rmj.g3appdriver.utils.WebApi.IMPORT_BRANCH_PERFORMANCE;

public class ImportBranchPerformance implements ImportInstance {
    public static final String TAG = ImportBranchPerformance.class.getSimpleName();

    private final Application instance;
    private final RBranchPerformance branchRepo;

    public ImportBranchPerformance(Application application) {
        this.instance = application;
        branchRepo = new RBranchPerformance(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try {
            for(int x = 1; x <= 9; x++){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("period","20210" +x);
                new ImportDataTask(instance, callback).execute(jsonObject);
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportDataTask extends AsyncTask<JSONObject, Void, String> {
        private final ConnectionUtil conn;
        private final HttpHeaders headers;
        private final RBranchPerformance branchRepo;
        private final ImportDataCallback callback;

        public ImportDataTask(Application instance,
                              ImportDataCallback callback) {
            this.conn = new ConnectionUtil(instance);
            this.headers = HttpHeaders.getInstance(instance);
            this.branchRepo = new RBranchPerformance(instance);
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            JSONObject loJSon = jsonObjects[0];
            String response = "";
            try {
//                JSONObject loJson = new JSONObject();
                String lsAreaCd = branchRepo.getUserAreaCode();
                String lsPeriod = PERFORMANCE_CURRENT_PERIOD;
//                loJSon.put("period", "202109");
//                loJson.put("period", lsPeriod);
                loJSon.put("areacd", lsAreaCd);
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(IMPORT_BRANCH_PERFORMANCE, loJSon.toString(), headers.getHeaders());
                    JSONObject loResponse = new JSONObject(response);
                    JSONArray laJson = loResponse.getJSONArray("detail");
                    Log.e(TAG,laJson.toString());
                    saveDataToLocal(laJson);
                } else {
                    response = AppConstants.NO_INTERNET();
                }
                Thread.sleep(700);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                Log.e(TAG, s);
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccessImportData();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String lsCode = loError.getString("code");
                    String message = loError.getString("message");
                    callback.OnFailedImportData(lsCode + " " +message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailedImportData(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailedImportData(e.getMessage());
            }
        }

        void saveDataToLocal(JSONArray laJson) throws Exception{
            List<EBranchPerformance> list = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EBranchPerformance info = new EBranchPerformance();
                int min = loJson.getInt("nSPGoalxx")/2;
                int max = loJson.getInt("nSPGoalxx");
                int random_mc = (int)Math.floor(Math.random()*(max-min+1000)*100);
                int random_jo = (int)Math.floor(Math.random()*(max-min+1000)*100);
                info.setPeriodxx(loJson.getString("sPeriodxx"));
                info.setBranchCd(loJson.getString("sBranchCd"));
                info.setBranchNm(loJson.getString("sBranchNm"));
                info.setMCGoalxx(loJson.getInt("nMCGoalxx"));
                info.setSPGoalxx(loJson.getInt("nSPGoalxx"));
//                info.setJOGoalxx(loJson.getInt("nJOGoalxx"));
                info.setJOGoalxx(random_jo);
                info.setLRGoalxx(loJson.getInt("nLRGoalxx"));
//                info.setMCGoalxx(loJson.getInt("nMCActual"));
                info.setMCActual(random_mc);
                info.setSPActual(loJson.getInt("nSPActual"));
                info.setLRActual(loJson.getInt("nLRActual"));
                list.add(info);
            }
            branchRepo.insertBulkData(list);
        }
    }
}
