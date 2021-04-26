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
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcBrand;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Objects;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_BRAND;

public class ImportBrand implements ImportInstance{
    public static final String TAG = ImportBrand.class.getSimpleName();
    private final Application instance;
    private final AppConfigPreference poConfig;
    private final RMcBrand repository;
    private String lsTimeStmp = "";

    public ImportBrand(Application application){
        this.instance = application;
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.repository = new RMcBrand(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            JSONObject loJson = new JSONObject();
            if(poConfig.isAppFirstLaunch()) {
                loJson.put("bsearch", true);
                loJson.put("descript", "All");
            } else {
                loJson.put("bsearch", true);
                loJson.put("descript", "All");
                lsTimeStmp = repository.getLatestDataTime();
                loJson.put("dTimeStmp", lsTimeStmp);
            }
            new ImportBrandTask(instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportBrandTask extends AsyncTask<JSONObject, Void, String>{
        private final RMcBrand repository;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final HttpHeaders headers;
        private final ImportDataCallback callback;

        public ImportBrandTask(Application instance, ImportDataCallback callback) {
            this.repository = new RMcBrand(instance);
            this.conn = new ConnectionUtil(instance);
            this.webApi = new WebApi(instance);
            this.headers = HttpHeaders.getInstance(instance);
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_IMPORT_BRAND, jsonObjects[0].toString(), headers.getHeaders());
                    JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if(lsResult.equalsIgnoreCase("success")){
                        JSONArray laJson = loJson.getJSONArray("detail");
                        repository.insertBrandInfo(laJson);
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
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            try {
                JSONObject loJson = new JSONObject(string);
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
    }
}
