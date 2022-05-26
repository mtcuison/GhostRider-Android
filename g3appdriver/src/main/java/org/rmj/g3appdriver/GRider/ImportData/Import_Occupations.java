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
import org.rmj.g3appdriver.GRider.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.ROccupation;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

public class Import_Occupations implements ImportInstance {
    public static final String TAG = Import_Occupations.class.getSimpleName();
    private final Application instance;
    private final ROccupation db;
    private final AppConfigPreference poConfig;
    private String lsTimeStmp;

    public Import_Occupations(Application application) {
        this.instance = application;
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.db = new ROccupation(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            JSONObject loJson = new JSONObject();
            if(poConfig.isAppFirstLaunch()) {
                loJson.put("bsearch", true);
                loJson.put("id", "All");
            } else {
                loJson.put("bsearch", true);
                loJson.put("id", "All");
                lsTimeStmp = db.getLatestDataTime();
                loJson.put("dTimeStmp", lsTimeStmp);
            }
            new ImportDataTask(instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportDataTask extends AsyncTask<JSONObject, Void, String> {
        private final ROccupation db;
        private final ConnectionUtil loConnectx;
        private final HttpHeaders loHeaders;
        private final WebApi poApi;
        private final AppConfigPreference poConfig;
        private final ImportDataCallback callback;

        public ImportDataTask(Application instance, ImportDataCallback callback) {
            this.db = new ROccupation(instance);
            this.loConnectx = new ConnectionUtil(instance);
            this.loHeaders = HttpHeaders.getInstance(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(poConfig.getTestStatus());
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if (loConnectx.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(poApi.getUrlImportOccupations(poConfig.isBackUpServer()), jsonObjects[0].toString(), loHeaders.getHeaders());
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
            List<EOccupationInfo> occupationInfoList = new ArrayList<>();
            for (int x = 0; x < laJson.length(); x++) {
                JSONObject loJson = laJson.getJSONObject(x);
                EOccupationInfo info = new EOccupationInfo();
                info.setOccptnID(loJson.getString("sOccptnID"));
                info.setOccptnNm(loJson.getString("sOccptnNm"));
                info.setRecdStat(loJson.getString("cRecdStat"));
                info.setTimeStmp(loJson.getString("dTimeStmp"));
                occupationInfoList.add(info);
            }
            db.insertBulkData(occupationInfoList);
        }
    }
}
