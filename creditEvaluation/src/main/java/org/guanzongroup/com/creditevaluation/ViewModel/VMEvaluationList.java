/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditEvaluation
 * Electronic Personnel Access Control Security System
 * project file created : 3/11/22, 11:19 AM
 * project file last modified : 3/11/22, 11:19 AM
 */

package org.guanzongroup.com.creditevaluation.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.guanzongroup.com.creditevaluation.Core.EvaluatorManager;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.ImportData.Import_LoanApplications;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

public class VMEvaluationList extends AndroidViewModel {
    private static final String TAG = VMEvaluationList.class.getSimpleName();
    private final Application instance;
    private final EvaluatorManager poManager;
    private final SessionManager poSession;
    private final Import_LoanApplications poImport;
    private final MutableLiveData<String> sCredInvxx = new MutableLiveData<>();

    private final RBranch RBranch;
    private final EmployeeMaster poEmploye;
    public VMEvaluationList(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poManager = new EvaluatorManager(application);
        this.poSession = new SessionManager(application);
        this.poEmploye = new EmployeeMaster(application);
        this.poImport = new Import_LoanApplications(application);
        this.RBranch = new RBranch(application);
    }

    public void importApplicationInfo(String fsTransno, ViewModelCallback callback) {
        try{
            new AddApplicationInfoTask(instance,fsTransno.trim(),callback).execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public LiveData<EBranchInfo> getUserBranch(){
        return RBranch.getUserBranchInfo();
    }
    public static class AddApplicationInfoTask extends AsyncTask<JSONObject, Void, String> {
        private final RCreditApplication db;
        private final RImageInfo loImage;
        private final ConnectionUtil loConnectx;
        private final HttpHeaders loHeaders;
        private final SessionManager poUser;
        private final AppConfigPreference poConfig;
        private final ViewModelCallback callback;
        private final EvaluatorManager foManager;
        private final String foTransNox;

        public AddApplicationInfoTask(Application application, String transNo,ViewModelCallback callback) {
            this.db = new RCreditApplication(application);
            this.loImage = new RImageInfo(application);
            this.loConnectx = new ConnectionUtil(application);
            this.loHeaders = HttpHeaders.getInstance(application);
            this.poUser = new SessionManager(application);
            this.poConfig = AppConfigPreference.getInstance(application);
            this.callback = callback;
            this.foTransNox = transNo;
            this.foManager = new EvaluatorManager(application);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if (loConnectx.isDeviceConnected()) {
                    foManager.AddApplication(foTransNox, new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            Log.e("Add Success", args);
                        }

                        @Override
                        public void OnFailed(String message) {
                            Log.e("Add Failed", message);
                        }
                    });
                }else {
                    response = AppConstants.NO_INTERNET();
                }
            } catch (Exception e) {
                e.printStackTrace();
                e.getCause();
            }
            return response;
        }
    }
    public interface OnImportCallBack{
        void onStartImport();
        void onSuccessImport();
        void onImportFailed(String message);
    }
    public LiveData<DCreditOnlineApplicationCI.oDataEvaluationInfo> getForEvaluationInfo(String transNox) {
        return poManager.getForEvaluationInfo(transNox);
    }

    public LiveData<List<DCreditOnlineApplicationCI.oDataEvaluationInfo>> getForEvaluationListData() {
        return poManager.getForEvaluationListData();
    }

    public void importApplicationInfo(OnImportCallBack callback) {
        try{
            JSONObject loJson = new JSONObject();
            loJson.put("sUserIDxx", poSession.getUserID());
            new ImportDataTask(instance, callback).execute(loJson);} catch (Exception e){
            e.printStackTrace();
        }
    }

    public static class ImportDataTask extends AsyncTask<JSONObject, Void, String>{
        private final RCreditApplication db;
        private final RImageInfo loImage;
        private final ConnectionUtil loConnectx;
        private final HttpHeaders loHeaders;
        private final SessionManager poUser;
        private final AppConfigPreference poConfig;
        private final OnImportCallBack callback;
        private final WebApi poWebApi;

        public ImportDataTask(Application application, OnImportCallBack callback) {
            this.db = new RCreditApplication(application);
            this.loImage = new RImageInfo(application);
            this.loConnectx = new ConnectionUtil(application);
            this.loHeaders = HttpHeaders.getInstance(application);
            this.poUser = new SessionManager(application);
            this.poConfig = AppConfigPreference.getInstance(application);
            this.callback = callback;
            this.poWebApi = new WebApi(true);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onStartImport();
        }
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... jsonObjects) {
            String response = "";
            try {
                if (loConnectx.isDeviceConnected()) {
                    response = WebClient.httpsPostJSon(poWebApi.getUrlImportOnlineApplications(poConfig.isBackUpServer()), jsonObjects[0].toString(), loHeaders.getHeaders());
                    JSONObject loJson = new JSONObject(response);
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
                    callback.onSuccessImport();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.onImportFailed(message);
                }
            } catch (JSONException e) {
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
    public void DownloadCreditApplications(OnImportCallBack callBack){
        try{
            new DownloadCreditApplications(instance,poManager, callBack).execute();} catch (Exception e){
            e.printStackTrace();
        }
    }
    private  class DownloadCreditApplications extends AsyncTask<Void, Void, String> {
        private final EvaluatorManager poCIEvaluation;
        private final OnImportCallBack callback;
        private final ConnectionUtil poConn;
        public DownloadCreditApplications(Application app, EvaluatorManager poCIEvaluation, OnImportCallBack callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.callback = callback;
            this.poConn = new ConnectionUtil(app);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onStartImport();
        }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                final String[] response = {""};
                if (!poConn.isDeviceConnected()) {
                    response[0] = "Unable to connect.";
                } else {
                    poCIEvaluation.DownloadForCIApplications(new EvaluatorManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            response[0] = args;
                        }

                        @Override
                        public void OnFailed(String message) {
                            response[0] = message;
                        }
                    });

                }
                Thread.sleep(1000);
                return response[0];

            } catch (NullPointerException e){
                e.printStackTrace();
                return e.getMessage();
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.onSuccessImport();
            } else {
                callback.onImportFailed(s);
            }
        }
    }
}
