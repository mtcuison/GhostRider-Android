package org.rmj.g3appdriver.GRider.ImportData;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.appdriver.base.GProperty;
import org.rmj.appdriver.crypt.GCryptFactory;
import org.rmj.appdriver.iface.iGCrypt;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_BRANCHES;

public class ImportBranch implements ImportInstance{
    private static final String TAG = ImportBranch.class.getSimpleName();
    private final ConnectionUtil conn;
    private final WebApi webApi;
    private final HttpHeaders headers;
    private final Application instance;
    private final RBranch repository;

    public ImportBranch(Application application){
        instance = application;
        conn = new ConnectionUtil(instance);
        webApi = new WebApi(instance.getApplicationContext());
        headers = HttpHeaders.getInstance(instance);
        repository = new RBranch(instance);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try {
            JSONObject loJson = new JSONObject();
            loJson.put("bsearch", true);
            loJson.put("descript", "All");
            new ImportBranchTask(callback, headers, conn, instance, repository).execute(loJson);
            //loJson.put("dTimeStmp", lsTimeStmp);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class ImportBranchTask extends AsyncTask<JSONObject, Void, String>{
        private final ImportDataCallback callback;
        private final HttpHeaders headers;
        private final ConnectionUtil conn;
        private final Application instance;
        private final RBranch repository;

        public ImportBranchTask(ImportDataCallback callback, HttpHeaders headers, ConnectionUtil conn, Application instance, RBranch repository) {
            this.instance = instance;
            this.callback = callback;
            this.headers = headers;
            this.conn = conn;
            this.repository = repository;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if(conn.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(URL_IMPORT_BRANCHES, jsonObjects[0].toString(), headers.getHeaders());
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
            List<EBranchInfo> branchInfos = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = new JSONObject(laJson.getString(x));
                EBranchInfo branchInfo = new EBranchInfo();
                branchInfo.setBranchCd(loJson.getString("sBranchCd"));
                branchInfo.setBranchNm(loJson.getString("sBranchNm"));
                branchInfo.setDescript(loJson.getString("sDescript"));
                branchInfo.setAddressx(loJson.getString("sAddressx"));
                branchInfo.setTownIDxx(loJson.getString("sTownIDxx"));
                branchInfo.setAreaCode(loJson.getString("sAreaCode"));
                branchInfo.setDivision(loJson.getString("cDivision"));
                branchInfo.setPromoDiv(loJson.getString("cPromoDiv"));
                branchInfo.setRecdStat(loJson.getString("cRecdStat"));
                branchInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                branchInfos.add(branchInfo);
            }
            repository.insertBulkData(branchInfos);
            Log.e(TAG, "Branch info has been save to local.");
        }

        private GConnection doConnect(){
            try{
                InputStream inputStream = instance.getAssets().open("GhostRiderXP.properties");

                //initialize GProperty
                GProperty loProperty = new GProperty(inputStream, "IntegSys");
                if (loProperty.loadConfig()){
                    Log.d(TAG, "Config File was loaded.");

                    loProperty.setDBHost(instance.getPackageName());
                } else {
                    Log.e(TAG, "Unable to load config file.");
                }
                //initialize GCrypt
                iGCrypt loCrypt = GCryptFactory.make(GCryptFactory.CrypType.AESCrypt);

                GConnection loConn = new GConnection();
                loConn.setGProperty(loProperty);
                loConn.setGAESCrypt(loCrypt);

                if (!loConn.connect()){
                    Log.e("TAG", "Unable to initialize connection");
                    return null;
                } else {
                    Log.d("TAG", "Connection was successful.");
                    return loConn;
                }
            } catch (Exception ex){
                Log.e("TAG", ex.getMessage());
                return null;
            }
        }
    }
}
