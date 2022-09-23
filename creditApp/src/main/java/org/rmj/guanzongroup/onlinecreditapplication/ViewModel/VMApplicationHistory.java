/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.rmj.apprdiver.util.WebFile;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DownloadImageCallBack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMApplicationHistory extends AndroidViewModel {
    private static final String TAG = VMApplicationHistory.class.getSimpleName();
    private final Application instance;
    private final RCreditApplication poCreditApp;
    private final Import_LoanApplications poImport;
    private final RImageInfo poImage;
    private final SessionManager poUser;
    public VMApplicationHistory(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCreditApp = new RCreditApplication(application);
        this.poImport = new Import_LoanApplications(application);
        this.poImage = new RImageInfo(application);
        this.poUser = new SessionManager(application);
    }
    public interface OnImportCallBack{
        void onStartImport();
        void onSuccessImport();
        void onImportFailed(String message);
    }
//    public void LoadApplications(OnImportCallBack callBack){
//        JSONObject loJson = new JSONObject();
//        try{
//            String userIDs = poUser.getUserID();
////            org.json.JSONObject loJson = new org.json.JSONObject();
//            loJson.put("sUserIDxx", userIDs);
//            new ImportDataTask(instance, callBack).execute(loJson);
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//    }

    public LiveData<List<DCreditApplication.ApplicationLog>> getApplicationHistory(){
        return poCreditApp.getApplicationHistory();
    }
    public LiveData<EImageInfo> getImageLogPreview(String TransNox){
        return poImage.getImageLogPreview(TransNox);
    }

    public void ExportGOCasInfo(String TransNox){

    }

    public void DeleteGOCasInfo(String TransNox){

    }

    public void UpdateGOCasInfo(String TransNox){

    }

    public void saveImageFile(EImageInfo foImage) {
//        foImage.setTransNox(poImage.getImageNextCode());
//        poImage.insertImageInfo(foImage);
    }
    public void saveApplicantImageFromCamera(String TransNox) {
        try {
            poCreditApp.updateApplicantImageStat(TransNox);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void uploadImage(EImageInfo foImage){
        new UploadImageFileTask(instance, foImage).execute(foImage.getSourceNo());
    }

    public String getApplicantPhoto() {
        // TODO: Create AsyncTask to return image path
        return null;
    }

    @SuppressLint("StaticFieldLeak")
    private class UploadImageFileTask extends AsyncTask<String, Void, String>{
        private final Application instance;
        private String TransNox;
        private ConnectionUtil poConn;
        private SessionManager poUser;
        private final EImageInfo poPhoto;
        private final AppConfigPreference poConfig;

        private UploadImageFileTask(Application instance, EImageInfo foImage){
            this.instance = instance;
            this.poPhoto = foImage;
            this.poConfig = AppConfigPreference.getInstance(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poConn = new ConnectionUtil(instance);
            poUser = new SessionManager(instance);
        }

        @Override
        protected String doInBackground(String... strings) {
            TransNox = strings[0];
            String lsResponse = "";
            if(poConn.isDeviceConnected()){
                String lsClient = WebFileServer.RequestClientToken(poConfig.ProducID(),
                        poUser.getClientId(),
                        poUser.getUserID());
                String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                if(!lsAccess.isEmpty()){

                    org.json.simple.JSONObject loUpload = WebFileServer.UploadFile(
                            poPhoto.getFileLoct(),
                            lsAccess,
                            "0029",
                            poUser.getUserID(),
                            poPhoto.getImageNme(),
                            poUser.getBranchCode(),
                            poPhoto.getSourceCD(),
                            TransNox,
                            "");

                    lsResponse = (String) loUpload.get("result");
                    Log.e(TAG, "Uploading image result : " + lsResponse);

                    if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                        String lsTransNo = (String) loUpload.get("sTransNox");
                        poImage.updateImageInfo(lsTransNo, poPhoto.getTransNox());
                        poCreditApp.updateCustomerImageStat(TransNox);
                        lsResponse = loUpload.toJSONString();
                    } else {
                        lsResponse = loUpload.toJSONString();
                    }
                }
            }
            return lsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                org.json.JSONObject loJson = new org.json.JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                } else {
                    org.json.JSONObject loError = loJson.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void DownloadDocumentFile(String fsSourceNo, DownloadImageCallBack callback) {
        try {
            new DownloadDocumentFile(instance, fsSourceNo, callback).execute();
        } catch (Exception e) {
        }
    }

    public class DownloadDocumentFile extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConn;
        private final DownloadImageCallBack callback;
        private final SessionManager poUser;
        private final RImageInfo poImage;
        private final String psSourceNo;
        private final AppConfigPreference poConfig;

        public DownloadDocumentFile(Application instance, String fsSourceNo, DownloadImageCallBack callback) {
            this.poConn = new ConnectionUtil(instance);
            this.poUser = new SessionManager(instance);
            this.psSourceNo = fsSourceNo;
            this.callback = callback;
            this.poImage = new RImageInfo(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            String lsResult;
            try {
                if (!poConn.isDeviceConnected()) {
                    lsResult = AppConstants.NO_INTERNET();
                } else {

                    String lsClient = WebFileServer.RequestClientToken(poConfig.ProducID(), poUser.getClientId(), poUser.getUserID());
                    String lsAccess = WebFileServer.RequestAccessToken(lsClient);

                    if (lsClient.isEmpty() || lsAccess.isEmpty()) {
                        lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Failed to request generated Client or Access token.");
                    } else {
                        String imageName = psSourceNo + "_0_" + "0029.png";
                        String root = String.valueOf(instance.getExternalFilesDir(null));
                        File fileLoc = new File(root + "/" + "/CreditApp/" + psSourceNo + "/");
                        if (!fileLoc.exists()) {
                            fileLoc.mkdirs();
                        }
                        org.json.simple.JSONObject loDownload = WebFileServer.DownloadFile(lsAccess,
                                "0029",
                                "",
                                imageName,
                                "COAD",
                                psSourceNo,
                                "");

                        String lsResponse = (String) loDownload.get("result");
                        if (Objects.requireNonNull(lsResponse).equalsIgnoreCase("success")) {
                            //convert to image and save to proper file location
                            JSONParser loParser = new JSONParser();
                            loDownload = (org.json.simple.JSONObject) loParser.parse(loDownload.get("payload").toString());
                            String location = fileLoc.getAbsolutePath() + "/";
                            if (WebFile.Base64ToFile((String) loDownload.get("data"),
                                    (String) loDownload.get("hash"),
                                    location,
                                    (String) loDownload.get("filename"))){
                                Log.d(TAG, "File hash was converted to file successfully.");
                                //insert entry to image info
                                EImageInfo loImage = new EImageInfo();
                                loImage.setTransNox((String) loDownload.get("transnox"));
                                loImage.setSourceCD("COAD");
                                loImage.setSourceNo(psSourceNo);
                                loImage.setDtlSrcNo(psSourceNo);
                                loImage.setFileCode("0029");
                                loImage.setMD5Hashx((String) loDownload.get("hash"));
                                loImage.setFileLoct(fileLoc.getAbsolutePath() +"/" + imageName);
                                loImage.setImageNme((String) loDownload.get("filename"));
                                loImage.setLatitude("0.0");
                                loImage.setLongitud("0.0");
                                loImage.setSendDate(new AppConstants().DATE_MODIFIED);
                                loImage.setSendStat("1");
                                //loImage....
                                ScannerConstants.PhotoPath = loImage.getFileLoct();
//                                poImage.insertDownloadedImageInfo(loImage);

                                poCreditApp.updateApplicantImageStat(psSourceNo);
                                //end - insert entry to image info
                                Log.e(TAG,loDownload.get("transnox").toString());
                                //todo:
                                //insert/update entry to credit_online_application_documents
                                //end - convert to image and save to proper file location

//                               new string response for success to convert file notification
                                loDownload = (org.json.simple.JSONObject) loParser.parse("{\"result\":\"success\",\"convert\":\"true\",\"message\":\""+ fileLoc.getAbsolutePath() +"/" + imageName + "\"}");
                                lsResult = String.valueOf(loDownload);
                            } else{
                                Log.e(TAG, "Unable to convert file.");
                                loDownload = (org.json.simple.JSONObject) loParser.parse("{\"result\":\"success\",\"convert\":\"false\",\"message\":\"Unable to convert file.\"}");
                                lsResult = String.valueOf(loDownload);

                            }
                        }else{
//                            default string response for error message
                            lsResult = String.valueOf(loDownload);
                        }

                        Thread.sleep(1000);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                org.json.JSONObject loJson = new org.json.JSONObject(s);
                if (loJson.getString("result").equalsIgnoreCase("success")) {
                    if (loJson.getString("convert").equalsIgnoreCase(String.valueOf(true))){
                        callback.onSaveSuccessResult(loJson.getString("message"));
                    }else{
                        callback.onFailedResult(loJson.getString("message"));
                    }
                } else {
                    org.json.JSONObject loError = loJson.getJSONObject("error");
                    callback.onFailedResult(loError.getString("message"));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void ImportLoanApplication(OnImportCallBack callBack){

        JSONObject loJson = new JSONObject();
        try {
            String userID = poUser.getUserID();
            loJson.put("bycode", true);
            loJson.put("sUserIDxx",userID);
            new ImportLoanApplication(instance, callBack).execute(loJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }catch (NullPointerException e) {
            e.printStackTrace();
        }

    }


    private class ImportLoanApplication extends AsyncTask<JSONObject, Void, String>{
        private final RCreditApplication db;
        private final ConnectionUtil loConnectx;
        private final HttpHeaders loHeaders;
        private final SessionManager poUser;
        private final WebApi poApi;
        private final AppConfigPreference poConfig;
        private final OnImportCallBack callback;

        public ImportLoanApplication(Application application, OnImportCallBack callback) {
            this.db = new RCreditApplication(application);
            this.loConnectx = new ConnectionUtil(application);
            this.loHeaders = HttpHeaders.getInstance(application);
            this.poUser = new SessionManager(application);
            this.poConfig = AppConfigPreference.getInstance(application);
            this.poApi = new WebApi(poConfig.getTestStatus());
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onStartImport();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(org.json.JSONObject... jsonObjects) {
            String response = "";
            try {
                if (loConnectx.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(poApi.getUrlImportOnlineApplications(poConfig.isBackUpServer()), jsonObjects[0].toString(), loHeaders.getHeaders());
                    org.json.JSONObject loJson = new org.json.JSONObject(response);
                    Log.e(TAG, loJson.getString("result"));
                    String lsResult = loJson.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        JSONArray laJson = loJson.getJSONArray("detail");
                        saveDataToLocal(laJson);

                        Thread.sleep(500);

                        String lsClient = WebFileServer.RequestClientToken(poConfig.ProducID(), poUser.getClientId(), poUser.getUserID());
                        String lsAccess = WebFileServer.RequestAccessToken(lsClient);
                        org.json.simple.JSONObject loResult = WebFileServer.CheckFile(lsAccess,
                                "0029",
                                poUser.getBranchCode(),
                                "",
                                "");
                        lsResult = (String) loResult.get("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            org.json.JSONObject loResponse = new org.json.JSONObject(loResult.toJSONString());
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
                if(lsResult.equalsIgnoreCase("success")){
                    callback.onSuccessImport();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.onImportFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onImportFailed(e.getMessage());
            }catch (NullPointerException e) {
                e.printStackTrace();
                callback.onImportFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.onImportFailed(e.getMessage());
            }
        }
        void saveDataToLocal(JSONArray laJson) throws Exception{
            List<ECreditApplication> creditApplications = new ArrayList<>();
            for(int x = 0; x < laJson.length(); x++){
                org.json.JSONObject loJson = laJson.getJSONObject(x);
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
            for(int x = 0; x < faJson.length(); x++){
                org.json.JSONObject loJSon = faJson.getJSONObject(x);
                String Transnox = loJSon.getString("sReferNox");
                db.updateCustomerImageStat(Transnox);
            }
        }
    }

}
