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
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_OCCUPATIONS;

public class Import_Occupations implements ImportInstance {
    public static final String TAG = Import_Occupations.class.getSimpleName();
    private final ROccupation db;
    private final ConnectionUtil loConnectx;
    private final HttpHeaders loHeaders;
    private final AppConfigPreference poConfig;
    private String lsTimeStmp;

    public Import_Occupations(Application application) {
        this.db = new ROccupation(application);
        this.loConnectx = new ConnectionUtil(application);
        this.loHeaders = HttpHeaders.getInstance(application);
        this.poConfig = AppConfigPreference.getInstance(application);
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
            new ImportDataTask(db, loConnectx, loHeaders, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportDataTask extends AsyncTask<JSONObject, Void, String> {
        private final ROccupation db;
        private final ConnectionUtil loConnectx;
        private final HttpHeaders loHeaders;
        private final ImportDataCallback callback;

        public ImportDataTask(ROccupation db, ConnectionUtil loConnectx, HttpHeaders loHeaders, ImportDataCallback callback) {
            this.db = db;
            this.loConnectx = loConnectx;
            this.loHeaders = loHeaders;
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if (loConnectx.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_IMPORT_OCCUPATIONS, jsonObjects[0].toString(), loHeaders.getHeaders());
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
