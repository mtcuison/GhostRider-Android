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
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

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
            new ImportDataTask(instance, callback).execute();
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
            String response = "";
            try {
                JSONObject loJson = new JSONObject();
                String lsAreaCd = branchRepo.getUserAreaCode();
                String lsPeriod = PERFORMANCE_CURRENT_PERIOD;
                loJson.put("period", "202109");
//                loJson.put("period", lsPeriod);
                loJson.put("areacd", lsAreaCd);
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(IMPORT_BRANCH_PERFORMANCE, loJson.toString(), headers.getHeaders());
                    JSONObject loResponse = new JSONObject(response);
                    JSONArray laJson = loResponse.getJSONArray("detail");
                    saveDataToLocal(laJson);
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
                info.setPeriodxx(loJson.getString("sPeriodxx"));
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
