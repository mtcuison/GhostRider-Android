/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/26/21 4:25 PM
 * project file last modified : 4/26/21 4:25 PM
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
import org.rmj.g3appdriver.GRider.Database.Entities.ESysConfig;
import org.rmj.g3appdriver.GRider.Database.Repositories.RSysConfig;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_SYS_CONFIG;

public class Import_SysConfig implements ImportInstance{
    private static final String TAG = Import_SysConfig.class.getSimpleName();

    private final Application instance;

    public Import_SysConfig(Application instance) {
        this.instance = instance;
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        JSONObject loJson = new JSONObject();
        try{
            loJson.put("period", "201911");
            new ImportConfigTask(instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportConfigTask extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final RSysConfig poDataBse;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;

        public ImportConfigTask(Application instance, ImportDataCallback callback){
            this.callback = callback;
            this.poDataBse = new RSysConfig(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(poConn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_IMPORT_SYS_CONFIG, jsonObjects[0].toString(), poHeaders.getHeaders());
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
            List<ESysConfig> sysConfigs = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                ESysConfig info = new ESysConfig();
                info.setConfigCd(loJson.getString("sConfigCd"));
                info.setConfigDs(loJson.getString("sConfigDs"));
                info.setConfigVl(loJson.getString("sConfigVl"));
                info.setTimeStmp(loJson.getString("dTimeStmp"));
                sysConfigs.add(info);
            }
            poDataBse.insertSysConfig(sysConfigs);
            Log.e(TAG, "Branch info has been save to local.");
        }
    }
}
