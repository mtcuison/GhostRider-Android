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
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_TOWN;

public class ImportTown implements ImportInstance{
    public static final String TAG = ImportTown.class.getSimpleName();
    private final RTown RTown;
    private final WebApi webApi;
    private final HttpHeaders headers;
    private final ConnectionUtil conn;

    public ImportTown(Application application){
        RTown = new RTown(application);
        webApi = new WebApi(application);
        headers = HttpHeaders.getInstance(application);
        conn = new ConnectionUtil(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try {
            JSONObject loJson = new JSONObject();
            loJson.put("bsearch", true);
            loJson.put("descript", "All");
            new ImportTownTask(callback, headers, RTown, conn).execute(loJson);
            //loJson.put("dTimeStmp", lsTimeStmp);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportTownTask extends AsyncTask<JSONObject, Void, String> {
        private final ImportDataCallback callback;
        private final HttpHeaders headers;
        private final RTown repository;
        private final ConnectionUtil conn;

        public ImportTownTask(ImportDataCallback callback, HttpHeaders headers, RTown repository, ConnectionUtil conn) {
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
                    response = WebClient.httpsPostJSon(URL_IMPORT_TOWN, jsonObjects[0].toString(), headers.getHeaders());
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
            List<ETownInfo> townInfoList = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                ETownInfo townInfo = new ETownInfo();
                townInfo.setTownIDxx(loJson.getString("sTownIDxx"));
                townInfo.setTownName(loJson.getString("sTownName"));
                townInfo.setZippCode(loJson.getString("sZippCode"));
                townInfo.setProvIDxx(loJson.getString("sProvIDxx"));
                townInfo.setMuncplCd(loJson.getString("sMuncplCd"));
                townInfo.setHasRoute(loJson.getString("cHasRoute"));
                townInfo.setBlackLst(loJson.getString("cBlackLst"));
                townInfo.setRecdStat(loJson.getString("cRecdStat"));
                townInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                townInfoList.add(townInfo);
            }
            repository.insertBulkData(townInfoList);
        }
    }
}
