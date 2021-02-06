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
import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_BARANGAY;

public class ImportBarangay implements ImportInstance{
    public static final String TAG = ImportBarangay.class.getSimpleName();
    private final RBarangay repository;
    private final ConnectionUtil conn;
    private final WebApi webApi;
    private final HttpHeaders headers;

    public ImportBarangay(Application application){
        repository = new RBarangay(application);
        conn = new ConnectionUtil(application);
        webApi = new WebApi(application);
        headers = HttpHeaders.getInstance(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try {
            JSONObject loJson = new JSONObject();
            loJson.put("bsearch", true);
            loJson.put("descript", "All");
            new ImportDataTask(repository, conn, webApi, headers, callback).execute(loJson);
            //loJson.put("dTimeStmp", lsTimeStmp);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportDataTask extends AsyncTask<JSONObject, Void, String>{
        private final RBarangay repository;
        private final ConnectionUtil conn;
        private final WebApi webApi;
        private final HttpHeaders headers;
        private final ImportDataCallback callback;

        public ImportDataTask(RBarangay repository, ConnectionUtil conn, WebApi webApi, HttpHeaders headers, ImportDataCallback callback) {
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
            try {
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_IMPORT_BARANGAY, jsonObjects[0].toString(), headers.getHeaders());
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
            List<EBarangayInfo> barangayInfos = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EBarangayInfo info = new EBarangayInfo();
                info.setBrgyIDxx(loJson.getString("sBrgyIDxx"));
                info.setBrgyName(loJson.getString("sBrgyName"));
                info.setTownIDxx(loJson.getString("sTownIDxx"));
                info.setHasRoute(loJson.getString("cHasRoute"));
                info.setBlackLst(loJson.getString("cBlackLst"));
                info.setRecdStat(loJson.getString("cRecdStat"));
                info.setTimeStmp(loJson.getString("dTimeStmp"));
                barangayInfos.add(info);
            }
            repository.insertBulkData(barangayInfos);
        }
    }
}
