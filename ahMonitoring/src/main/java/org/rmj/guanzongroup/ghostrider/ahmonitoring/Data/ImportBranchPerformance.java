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

import static org.rmj.g3appdriver.utils.WebApi.IMPORT_BRANCH_PERFORMANCE;

public class ImportBranchPerformance implements ImportInstance {
    public static final String TAG = ImportBranchPerformance.class.getSimpleName();

    private final ConnectionUtil conn;
    private final HttpHeaders headers;
    private final WebApi webApi;
    private final RBranchPerformance branchRepo;

    public ImportBranchPerformance(Application application) {
        conn = new ConnectionUtil(application);
        headers = HttpHeaders.getInstance(application);
        webApi = new WebApi(application);
        branchRepo = new RBranchPerformance(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try {
            JSONObject loJson = new JSONObject();
            loJson.put("period", "201911");
            loJson.put("areacd", "AreaCode");
            new ImportDataTask(conn, headers, webApi, branchRepo, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportDataTask extends AsyncTask<JSONObject, Void, String> {
        private final ConnectionUtil conn;
        private final HttpHeaders headers;
        private final WebApi webApi;
        private final RBranchPerformance branchRepo;
        private final ImportDataCallback callback;

        public ImportDataTask(ConnectionUtil conn,
                              HttpHeaders headers,
                              WebApi webApi,
                              RBranchPerformance branchRepo,
                              ImportDataCallback callback) {
            this.conn = conn;
            this.headers = headers;
            this.webApi = webApi;
            this.branchRepo = branchRepo;
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(IMPORT_BRANCH_PERFORMANCE, jsonObjects[0].toString(), (HashMap) headers.getHeaders());
                } else {
                    response = AppConstants.NO_INTERNET();
                }

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
                    JSONArray laJson = loJson.getJSONArray("detail");
                    saveDataToLocal(laJson);
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
                info.setBranchCd(loJson.getString("sBranchCd"));
                info.setBranchNm(loJson.getString("sBranchNm"));
                info.setMCGoalxx(loJson.getInt("nMCGoalxx"));
                info.setSPGoalxx(loJson.getInt("nSPGoalxx"));
                info.setJOGoalxx(loJson.getInt("nJOGoalxx"));
                info.setLRGoalxx(loJson.getInt("nLRGoalxx"));
                info.setMCGoalxx(loJson.getInt("nMCActual"));
                info.setSPActual(loJson.getInt("nSPActual"));
                info.setLRActual(loJson.getInt("nLRActual"));
                list.add(info);
            }
            branchRepo.insertBulkData(list);
        }
    }
}
