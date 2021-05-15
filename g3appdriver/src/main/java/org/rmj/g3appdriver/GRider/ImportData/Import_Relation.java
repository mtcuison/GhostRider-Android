/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/14/21 4:13 PM
 * project file last modified : 5/14/21 4:13 PM
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
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RRelation;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Arrays;
import java.util.Objects;

import static org.rmj.g3appdriver.utils.WebApi.URL_BRANCH_LOAN_APP;
import static org.rmj.g3appdriver.utils.WebApi.URL_DOWNLOAD_RELATION;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_FILE_CODE;

public class Import_Relation implements ImportInstance {
    private static final String TAG = Import_Relation.class.getSimpleName();

    private final Application instance;

    private final RRelation db;
    private final ConnectionUtil loConnectx;
    private final HttpHeaders loHeaders;
    private final AppConfigPreference poConfig;
    public Import_Relation(Application application){
        this.instance = application;
        this.db = new RRelation(application);
        this.loConnectx = new ConnectionUtil(application);
        this.loHeaders = HttpHeaders.getInstance(application);
        this.poConfig = AppConfigPreference.getInstance(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try {
            JSONObject loJson = new JSONObject();
            loJson.put("descript", "All");
            loJson.put("bsearch", true);
            new ImportRelation(callback, instance).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    private static class ImportRelation extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders headers;
        private final ConnectionUtil conn;
        private final RRelation repository;


        public ImportRelation(ImportDataCallback callback, Application instance) {
            this.callback = callback;
            this.headers = HttpHeaders.getInstance(instance);
            this.conn = new ConnectionUtil(instance);
            this.repository = new RRelation(instance);

        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_DOWNLOAD_RELATION, jsonObjects[0].toString(), headers.getHeaders());
                    JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if(lsResult.equalsIgnoreCase("success")){
                        JSONArray laJson = loJson.getJSONArray("detail");
                        if(!repository.insertBulkRelation(laJson)){
                            response = AppConstants.ERROR_SAVING_TO_LOCAL();
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
    }
}
