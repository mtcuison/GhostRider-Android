package org.rmj.g3appdriver.GRider.ImportData;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModelPrice;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Objects;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_MC_MODEL_PRICE;

public class ImportMcModelPrice implements ImportInstance{
    public static final String TAG = ImportMcModelPrice.class.getSimpleName();
    private final Application instance;
    private final AppConfigPreference poConfig;
    private final RMcModelPrice repository;
    private String lsTimeStmp = "";

    public ImportMcModelPrice(Application application) {
        this.instance = application;
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.repository = new RMcModelPrice(instance);
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
            new ImportMcModelPriceTask(callback, instance).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportMcModelPriceTask extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders headers;
        private final RMcModelPrice repository;
        private final ConnectionUtil conn;

        public ImportMcModelPriceTask(ImportDataCallback callback, Application instance) {
            this.callback = callback;
            this.headers = HttpHeaders.getInstance(instance);
            this.repository = new RMcModelPrice(instance);
            this.conn = new ConnectionUtil(instance);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_IMPORT_MC_MODEL_PRICE, jsonObjects[0].toString(), headers.getHeaders());
                    JSONObject loJson = new JSONObject(Objects.requireNonNull(response));
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if(lsResult.equalsIgnoreCase("success")){
                        JSONArray laJson = loJson.getJSONArray("detail");
                        repository.saveMcModelPrice(laJson);
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
    }
}
