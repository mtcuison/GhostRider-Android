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
            new ImportBranchTask(callback, headers, conn, instance).execute(loJson);
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

        public ImportBranchTask(ImportDataCallback callback, HttpHeaders headers, ConnectionUtil conn, Application instance) {
            this.instance = instance;
            this.callback = callback;
            this.headers = headers;
            this.conn = conn;
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

            GConnection loConn = doConnect();

            if (loConn == null){
                Log.e(TAG, "Connection was not initialized.");
                return;
            }

            JSONObject loJson;
            String lsSQL;
            ResultSet loRS;

            for(int x = 0; x < laJson.length(); x++){
                loJson = new JSONObject(laJson.getString(x));

                //check if record already exists on database
                lsSQL = "SELECT dTimeStmp FROM Branch_Info" +
                        " WHERE sBranchCd = " + SQLUtil.toSQL((String) loJson.get("sBranchCd"));
                loRS = loConn.executeQuery(lsSQL);

                lsSQL = "";
                //record does not exists
                if (!loRS.next()){
                    //check if the record is active
                    if ("1".equals((String) loJson.get("cRecdStat"))){
                        //create insert statement
                        lsSQL = "INSERT INTO Branch_Info" +
                                    "(sBranchCd" +
                                    ",sBranchNm" +
                                    ",sDescript" +
                                    ",sAddressx" +
                                    ",sTownIDxx" +
                                    ",sAreaCode" +
                                    ",cDivision" +
                                    ",cPromoDiv" +
                                    ",cRecdStat" +
                                    ",dTimeStmp)" +
                                " VALUES" +
                                    "(" + SQLUtil.toSQL(loJson.getString("sBranchCd")) +
                                    "," + SQLUtil.toSQL(loJson.getString("sBranchNm")) +
                                    "," + SQLUtil.toSQL(loJson.getString("sDescript")) +
                                    "," + SQLUtil.toSQL(loJson.getString("sAddressx")) +
                                    "," + SQLUtil.toSQL(loJson.getString("sTownIDxx")) +
                                    "," + SQLUtil.toSQL(loJson.getString("sAreaCode")) +
                                    "," + SQLUtil.toSQL(loJson.getString("cDivision")) +
                                    "," + SQLUtil.toSQL(loJson.getString("cPromoDiv")) +
                                    "," + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                                    "," + SQLUtil.toSQL(loJson.getString("dTimeStmp")) + ")";
                    }
                } else { //record already exists
                    Date ldDate1 = SQLUtil.toDate(loRS.getString("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);
                    Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                    //compare date if the record from API is newer than the database record
                    if (!ldDate1.equals(ldDate2)){
                        //create update statement
                        lsSQL = "UPDATE Branch_Info SET" +
                                    "   sBranchNm = " + SQLUtil.toSQL(loJson.getString("sBranchNm")) +
                                    ",  sDescript = " + SQLUtil.toSQL(loJson.getString("sDescript")) +
                                    ",  sAddressx = " + SQLUtil.toSQL(loJson.getString("sAddressx")) +
                                    ",  sTownIDxx = " + SQLUtil.toSQL(loJson.getString("sTownIDxx")) +
                                    ",  sAreaCode = " + SQLUtil.toSQL(loJson.getString("sAreaCode")) +
                                    ",  cDivision = " + SQLUtil.toSQL(loJson.getString("cDivision")) +
                                    ",  cPromoDiv = " + SQLUtil.toSQL(loJson.getString("cPromoDiv")) +
                                    ",  cRecdStat = " + SQLUtil.toSQL(loJson.getString("cRecdStat")) +
                                    ",  dTimeStmp = " + SQLUtil.toSQL(loJson.getString("dTimeStmp")) +
                                " WHERE sBranchCd = " + SQLUtil.toSQL(loJson.getString("sBranchCd"));
                    }
                }

                if (!lsSQL.isEmpty()){
                    Log.d(TAG, lsSQL);
                    if (loConn.executeUpdate(lsSQL,  "", "" ,"") <= 0) {
                        Log.e(TAG, loConn.getMessage());
                    } else
                        Log.d(TAG, "Branch saved successfully.");
                } else
                    Log.d(TAG, "No record to update. Branch maybe on its latest on local database.");
            }
            Log.e(TAG, "Branch info has been save to local.");

            //terminate object connection
            loConn = null;
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
