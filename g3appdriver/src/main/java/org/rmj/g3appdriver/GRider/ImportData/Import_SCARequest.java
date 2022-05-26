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
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ESCA_Request;
import org.rmj.g3appdriver.GRider.Database.Repositories.RApprovalCode;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

public class Import_SCARequest implements ImportInstance{
    public static final String TAG = Import_SCARequest.class.getSimpleName();
    private final Application instance;

    public Import_SCARequest(Application application) {
        this.instance = application;
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            JSONObject loJson = new JSONObject();
            loJson.put("bsearch", true);
            loJson.put("id", "All");
            new ImportDataTask(instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportDataTask extends AsyncTask<JSONObject, Void, String> {
        private final RApprovalCode db;
        private final ConnectionUtil loConnectx;
        private final HttpHeaders loHeaders;
        private final WebApi poApi;
        private final AppConfigPreference loConfig;
        private final ImportDataCallback callback;

        public ImportDataTask(Application instance, ImportDataCallback callback) {
            this.db = new RApprovalCode(instance);
            this.loConnectx = new ConnectionUtil(instance);
            this.loHeaders = HttpHeaders.getInstance(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if (loConnectx.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(poApi.getUrlScaRequest(loConfig.isBackUpServer()), jsonObjects[0].toString(), loHeaders.getHeaders());
                    JSONObject loJson = new JSONObject(response);
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        JSONArray laJson = loJson.getJSONArray("detail");
                        saveDataToLocal(laJson);
                    }
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
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if (lsResult.equalsIgnoreCase("success")) {
                    callback.OnSuccessImportData();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailedImportData(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailedImportData(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailedImportData(e.getMessage());
            }
        }

        void saveDataToLocal(JSONArray laJson) throws Exception {
            List<ESCA_Request> requestList = new ArrayList<>();
            for (int x = 0; x < laJson.length(); x++) {
                JSONObject loJson = laJson.getJSONObject(x);
                ESCA_Request info = new ESCA_Request();
                info.setSCACodex(loJson.getString("sSCACodex"));
                info.setSCATitle(loJson.getString("sSCATitle"));
                info.setSCADescx(loJson.getString("sSCADescx"));
                info.setSCATypex(loJson.getString("cSCATypex"));
                info.setAreaHead(loJson.getString("cAreaHead"));
                info.setHCMDeptx(loJson.getString("cHCMDeptx"));
                info.setCSSDeptx(loJson.getString("cCSSDeptx"));
                info.setComplnce(loJson.getString("cComplnce"));
                info.setMktgDept(loJson.getString("cMktgDept"));
                info.setASMDeptx(loJson.getString("cASMDeptx"));
                info.setTLMDeptx(loJson.getString("cTLMDeptx"));
                info.setSCMDeptx(loJson.getString("cSCMDeptx"));
                info.setRecdStat(loJson.getString("cRecdStat"));
                info.setTimeStmp(loJson.getString("dTimeStmp"));
                requestList.add(info);
            }
            db.insertBulkData(requestList);
        }
    }
}
