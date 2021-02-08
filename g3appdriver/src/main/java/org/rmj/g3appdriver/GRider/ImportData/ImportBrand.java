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
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_BRAND;

public class ImportBrand implements ImportInstance{
    public static final String TAG = ImportBrand.class.getSimpleName();
    private final RMcBrand repository;
    private final ConnectionUtil conn;
    private final WebApi webApi;
    private final HttpHeaders headers;

    public ImportBrand(Application application){
        repository = new RMcBrand(application);
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
            new ImportBrandTask(repository, conn, webApi, headers, callback).execute(loJson);
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

        public ImportBrandTask(RMcBrand repository, ConnectionUtil conn, WebApi webApi, HttpHeaders headers, ImportDataCallback callback) {
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
                    response = WebClient.httpsPostJSon(URL_IMPORT_BRAND, jsonObjects[0].toString(), (HashMap) headers.getHeaders());
                    JSONObject loJson = new JSONObject(response);
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

//        void saveDataToLocal(JSONArray laJson) throws Exception{
//            List<EMcBrand> brandList = new ArrayList<>();
//            for(int x = 0; x < laJson.length(); x++){
//                JSONObject loJson = laJson.getJSONObject(x);
//                EMcBrand brand = new EMcBrand();
//                brand.setBrandIDx(loJson.getString("sBrandIDx"));
//                brand.setBrandNme(loJson.getString("sBrandNme"));
//                brand.setRecdStat(loJson.getString("cRecdStat"));
//                brand.setTimeStmp(loJson.getString("dTimeStmp"));
//                brandList.add(brand);
//            }
//            repository.insertBulkData(brandList);
//        }
    }
}
