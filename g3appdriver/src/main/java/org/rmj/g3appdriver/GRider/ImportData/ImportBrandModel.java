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
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModel;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModel;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_MC_MODEL;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ImportBrandModel implements ImportInstance {
    public static final String TAG = ImportBrandModel.class.getSimpleName();
    private final RMcModel repository;
    private final ConnectionUtil conn;
    private final WebApi webApi;
    private final HttpHeaders headers;

    public ImportBrandModel(Application application){
        repository = new RMcModel(application);
        conn = new ConnectionUtil(application);
        webApi = new WebApi(application);
        headers = HttpHeaders.getInstance(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            JSONObject loJson = new JSONObject();
            loJson.put("bsearch", true);
            loJson.put("descript", "All");
            new ImportDataTask(repository, conn, webApi, headers, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportDataTask extends AsyncTask<JSONObject, Void, String>{
        private final RMcModel repository;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final HttpHeaders headers;
        private final ImportDataCallback callback;

        public ImportDataTask(RMcModel repository, ConnectionUtil conn, WebApi webApi, HttpHeaders headers, ImportDataCallback callback) {
            this.repository = repository;
            this.conn = conn;
            this.webApi = webApi;
            this.headers = headers;
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try{
                if(conn.isDeviceConnected()){
                    response = WebClient.httpsPostJSon(URL_IMPORT_MC_MODEL, jsonObjects[0].toString(), headers.getHeaders());
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
            } catch (Exception e){
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

        void saveDataToLocal(JSONArray laJson) throws Exception {
            List<EMcModel> modelList = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EMcModel model = new EMcModel();
                model.setModelIDx(loJson.getString("sModelIDx"));
                model.setModelCde(loJson.getString("sModelCde"));
                model.setModelNme(loJson.getString("sModelNme"));
                model.setBrandIDx(loJson.getString("sBrandIDx"));
                model.setMotorTyp(loJson.getString("cMotorTyp"));
                model.setRegisTyp(loJson.getString("cRegisTyp"));
                model.setEndOfLfe(loJson.getString("cEndOfLfe"));
                model.setEngineTp(loJson.getString("cEngineTp"));
                model.setHotItemx(loJson.getString("cHotItemx"));
                model.setRecdStat(loJson.getString("cRecdStat"));
                model.setTimeStmp(loJson.getString("dTimeStmp"));
                modelList.add(model);
            }
            repository.insertBulkData(modelList);
        }
    }
}
