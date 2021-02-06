package org.rmj.g3appdriver.GRider.ImportData;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModelPrice;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModelPrice;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_MC_MODEL_PRICE;

public class ImportMcModelPrice implements ImportInstance{
    public static final String TAG = ImportMcModelPrice.class.getSimpleName();
    private final RMcModelPrice repository;
    private final ConnectionUtil conn;
    private final HttpHeaders headers;

    public ImportMcModelPrice(Application application) {
        repository = new RMcModelPrice(application);
        conn = new ConnectionUtil(application);
        WebApi webApi = new WebApi(application);
        headers = HttpHeaders.getInstance(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            JSONObject loJson = new JSONObject();
            loJson.put("bsearch", true);
            loJson.put("descript", "All");
            new ImportMcModelPriceTask(callback, headers, repository, conn).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportMcModelPriceTask extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders headers;
        private final RMcModelPrice repository;
        private final ConnectionUtil conn;

        public ImportMcModelPriceTask(ImportDataCallback callback, HttpHeaders headers, RMcModelPrice repository, ConnectionUtil conn) {
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
                    response = WebClient.httpsPostJSon(URL_IMPORT_MC_MODEL_PRICE, jsonObjects[0].toString(), headers.getHeaders());
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
            try{
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
            } catch (Exception e){
                e.printStackTrace();
                callback.OnFailedImportData(e.getMessage());
            }
        }

        void saveDataToLocal(JSONArray laJson) throws Exception {
            List<EMcModelPrice> modelPrices = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                EMcModelPrice modelPrice = new EMcModelPrice();
                modelPrice.setModelIDx(loJson.getString("sModelIDx"));
                modelPrice.setSelPrice(loJson.getString("nSelPrice"));
                modelPrice.setLastPrce(loJson.getString("nLastPrce"));
                modelPrice.setDealrPrc(loJson.getString("nDealrPrc"));
                modelPrice.setMinDownx(loJson.getString("nMinDownx"));
                modelPrice.setMCCatIDx(loJson.getString("sMCCatIDx"));
                modelPrice.setPricexxx(loJson.getString("dPricexxx"));
                modelPrice.setInsPrice(loJson.getString("dInsPrice"));
                modelPrice.setRecdStat(loJson.getString("cRecdStat"));
                modelPrice.setTimeStmp(loJson.getString("dTimeStmp"));
                modelPrices.add(modelPrice);
            }
            repository.insertBulkData(modelPrices);
        }
    }
}
