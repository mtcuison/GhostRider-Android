/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

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
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

public class Import_LoanApplications implements ImportInstance{
    public static final String TAG = Import_LoanApplications.class.getSimpleName();
    private final Application instance;
    private final SessionManager poUser;

    public Import_LoanApplications(Application application) {
        this.instance = application;
        this.poUser = new SessionManager(application);
    }

    @Override
    public void ImportData(ImportDataCallback callback) {
        try{
            JSONObject loJson = new JSONObject();
            loJson.put("sUserIDxx", poUser.getUserID());
            new ImportDataTask(instance, callback).execute(loJson);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class ImportDataTask extends AsyncTask<JSONObject, Void, String>{
        private final RCreditApplication db;
        private final RImageInfo loImage;
        private final ConnectionUtil loConnectx;
        private final HttpHeaders loHeaders;
        private final SessionManager poUser;
        private final ImportDataCallback callback;

        public ImportDataTask(Application application, ImportDataCallback callback) {
            this.db = new RCreditApplication(application);
            this.loImage = new RImageInfo(application);
            this.loConnectx = new ConnectionUtil(application);
            this.loHeaders = HttpHeaders.getInstance(application);
            this.poUser = new SessionManager(application);
            this.callback = callback;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if (loConnectx.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(WebApi.URL_IMPORT_ONLINE_APPLICATIONS, jsonObjects[0].toString(), loHeaders.getHeaders());
                    JSONObject loJson = new JSONObject(response);
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        JSONArray laJson = loJson.getJSONArray("detail");
                        saveDataToLocal(laJson);

                        Thread.sleep(500);

                        String lsClient = WebFileServer.RequestClientToken("IntegSys", poUser.getClientId(), poUser.getUserID());
                        String lsAccess = WebFileServer.RequestAccessToken(lsClient);
                        org.json.simple.JSONObject loResult = WebFileServer.CheckFile(lsAccess,
                                "0029",
                                poUser.getBranchCode(),
                                "",
                                "");
                        lsResult = (String) loResult.get("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            JSONObject loResponse = new JSONObject(loResult.toJSONString());
                            String lsArray = loResponse.getString("detail");
                            JSONArray loArr = new JSONArray(lsArray);
                            updateCustomerImageStat(loArr);
                        }
                    }
                } else {
                    response = AppConstants.NO_INTERNET();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
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

        void saveDataToLocal(JSONArray laJson) throws Exception{
            List<ECreditApplication> creditApplications = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                JSONObject loJson = laJson.getJSONObject(x);
                ECreditApplication info = new ECreditApplication();
                info.setTransNox(loJson.getString("sTransNox"));
                info.setBranchCd(loJson.getString("sBranchCd"));
                info.setTransact(loJson.getString("dTransact"));
                info.setClientNm(loJson.getString("sClientNm"));
                info.setGOCASNox(loJson.getString("sGOCASNox"));
                //TODO : Unit Applied is default to 0 cuz empty String is return from Server upon request
                // this must be update when credit is available in Telecom
                info.setUnitAppl("0");
                info.setSourceCD(loJson.getString("sSourceCD"));
                info.setDetlInfo(loJson.getString("sDetlInfo"));
                info.setQMatchNo(loJson.getString("sQMatchNo"));
                info.setWithCIxx(loJson.getString("cWithCIxx"));
                info.setDownPaym(Double.parseDouble(loJson.getString("nDownPaym")));
                info.setRemarksx(loJson.getString("sRemarksx"));
                info.setCreatedx(loJson.getString("sCreatedx"));
                info.setDateCreatedx(loJson.getString("dCreatedx"));
                info.setSendStat("1");
                info.setVerified(loJson.getString("sVerified"));
                info.setDateVerified(loJson.getString("dVerified"));
                info.setTranStat(loJson.getString("cTranStat"));
                info.setDivision(loJson.getString("cDivision"));
                info.setReceived(loJson.getString("dReceived"));
                info.setCaptured("0");
                creditApplications.add(info);
            }
            db.insertBulkData(creditApplications);
        }

        private void updateCustomerImageStat(JSONArray faJson) throws Exception{
            for(int x = 0; x < faJson.length(); x++) {
                JSONObject loJSon = faJson.getJSONObject(x);
                String Transnox = loJSon.getString("sReferNox");
                db.updateCustomerImageStat(Transnox);
            }
        }
    }
}
