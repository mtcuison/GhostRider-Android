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
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_PROVINCE;

public class ImportProvinces implements ImportInstance{
    public static final String TAG = ImportProvinces.class.getSimpleName();
    private final RProvince poProv;
    private final WebApi webApi;
    private final HttpHeaders headers;
    private final ConnectionUtil conn;
    private final AppConfigPreference poConfig;
    private String lsTimeStmp = "";

    public ImportProvinces(Application application) {
        poProv = new RProvince(application);
        webApi = new WebApi(application);
        headers = HttpHeaders.getInstance(application);
        conn = new ConnectionUtil(application);
        poConfig = AppConfigPreference.getInstance(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try {
            JSONObject loJson = new JSONObject();
            if(poConfig.isAppFirstLaunch()) {
                loJson.put("bsearch", true);
                loJson.put("descript", "All");
            } else {
                loJson.put("bsearch", true);
                loJson.put("descript", "All");
                lsTimeStmp = poProv.getLatestDataTime();
                loJson.put("dTimeStmp", lsTimeStmp);
            }
            new ImportProvinceTask(callback, headers, poProv, conn).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportProvinceTask extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders headers;
        private final RProvince repository;
        private final ConnectionUtil conn;

        public ImportProvinceTask(ImportDataCallback callback, HttpHeaders headers, RProvince repository, ConnectionUtil conn) {
            this.callback = callback;
            this.headers = headers;
            this.repository = repository;
            this.conn = conn;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_IMPORT_PROVINCE, jsonObjects[0].toString(), headers.getHeaders());
                    JSONObject loJson = new JSONObject(response);
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if(lsResult.equalsIgnoreCase("success")){
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
                if(lsResult.equalsIgnoreCase("success")){
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

        void saveDataToLocal(JSONArray laJson) throws Exception{
            List<EProvinceInfo> provinceInfoList = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                EProvinceInfo provinceInfo = new EProvinceInfo();
                provinceInfo.setProvIDxx(loJson.getString("sProvIDxx"));
                provinceInfo.setProvName(loJson.getString("sProvName"));
                provinceInfo.setRecdStat(loJson.getString("cRecdStat").charAt(0));
                provinceInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                provinceInfoList.add(provinceInfo);
            }
            repository.insertBulkInfo(provinceInfoList);
        }
    }
}
