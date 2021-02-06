package org.rmj.g3appdriver.GRider.ImportData;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.IMPORT_AREA_PERFORMANCE;

public class Import_AreaPerformance implements ImportInstance {
    private static final String TAG = Import_AreaPerformance.class.getSimpleName();

    private final RAreaPerformance poDataBse;
    private final ConnectionUtil poConn;
    private final HttpHeaders poHeaders;

    public Import_AreaPerformance(Application application) {
        this.poDataBse = new RAreaPerformance(application);
        this.poConn = new ConnectionUtil(application);
        this.poHeaders = HttpHeaders.getInstance(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        JSONObject loJson = new JSONObject();
        try{
            loJson.put("period", "201911");
            new ImportAreaTask(callback, poHeaders, poDataBse, poConn).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportAreaTask extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders loHeaders;
        private final RAreaPerformance loDatabse;
        private final ConnectionUtil loConn;

        public ImportAreaTask(ImportDataCallback callback, HttpHeaders loHeaders, RAreaPerformance loDatabse, ConnectionUtil loConn) {
            this.callback = callback;
            this.loHeaders = loHeaders;
            this.loDatabse = loDatabse;
            this.loConn = loConn;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(loConn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(IMPORT_AREA_PERFORMANCE, jsonObjects[0].toString(), loHeaders.getHeaders());
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
            List<EAreaPerformance> areaInfos = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                EAreaPerformance info = new EAreaPerformance();
                info.setAreaCode(loJson.getString("sAreaCode"));
                info.setAreaDesc(loJson.getString("sAreaDesc"));
                info.setMCGoalxx(Integer.parseInt(loJson.getString("nMCGoalxx")));
                info.setSPGoalxx(Float.parseFloat(loJson.getString("nSPGoalxx")));
                info.setJOGoalxx(Integer.parseInt(loJson.getString("nJOGoalxx")));
                info.setLRGoalxx(Float.parseFloat(loJson.getString("nLRGoalxx")));
                info.setMCActual(Integer.parseInt(loJson.getString("nMCActual")));
                info.setSPActual(Float.parseFloat(loJson.getString("nSPActual")));
                info.setLRActual(Float.parseFloat(loJson.getString("nLRActual")));
                areaInfos.add(info);
            }
            loDatabse.insertBulkData(areaInfos);
            Log.e(TAG, "Branch info has been save to local.");
        }
    }
}
