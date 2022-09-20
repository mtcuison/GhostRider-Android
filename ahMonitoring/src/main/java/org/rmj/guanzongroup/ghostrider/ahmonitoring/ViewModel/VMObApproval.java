/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/20/21, 3:50 PM
 * project file last modified : 9/20/21, 3:50 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

public class VMObApproval extends AndroidViewModel {
    private static final String TAG = VMObApproval.class.getSimpleName();

    private final Application instance;

    private final REmployeeBusinessTrip poBusTrip;
    private final RBranch poBranch;

    private final MutableLiveData<String> TransNox = new MutableLiveData<>();

    public interface OnDownloadBusinessTripCallback{
        void OnDownload(String title, String message);
        void OnSuccessDownload(String TransNox);
        void OnFailedDownload(String message);
    }

    public interface OnConfirmApplicationCallback{
        void OnConfirm(String title, String message);
        void OnSuccess(String message);
        void OnFailed(String message);
    }

    public VMObApproval(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poBusTrip = new REmployeeBusinessTrip(application);
        this.poBranch = new RBranch(application);
        this.TransNox.setValue("");
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public void setTransNox(String TransNox){
        this.TransNox.setValue(TransNox);
    }

    public LiveData<String> getTransNox(){
        return TransNox;
    }

    public LiveData<EEmployeeBusinessTrip> getBusinessTripInfo(String TransNox){
        return poBusTrip.getBusinessTripInfo(TransNox);
    }

    public void downloadBusinessTrip(String TransNox, OnDownloadBusinessTripCallback callback){
        try{
            new DownloadBusinessTripTask(instance, TransNox, callback).execute();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private static class DownloadBusinessTripTask extends AsyncTask<String, Void, String>{

        private final REmployeeBusinessTrip poBusTrip;
        private final ConnectionUtil poConn;
        private final HttpHeaders poHeaders;
        private final String TransNox;
        private final WebApi poApi;
        private final AppConfigPreference loConfig;
        private final OnDownloadBusinessTripCallback callback;

        public DownloadBusinessTripTask(Application instance, String TransNox, OnDownloadBusinessTripCallback callback) {
            this.poBusTrip = new REmployeeBusinessTrip(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
            this.poApi = new WebApi(loConfig.getTestStatus());
            this.TransNox = TransNox;
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload("PET Manager", "Downloading business trip. Please wait...");
        }

        @SuppressLint("NewApi")
        @Override
        protected String doInBackground(String... strings) {
            String lsResult;
            try{
                JSONObject params = new JSONObject();
                params.put("sTransNox", TransNox);
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.NO_INTERNET();
                } else {
                    lsResult = WebClient.httpsPostJSon(poApi.getUrlGetObApplication(loConfig.isBackUpServer()), params.toString(), poHeaders.getHeaders());
                    if (lsResult == null) {
                        lsResult = AppConstants.SERVER_NO_RESPONSE();
                    } else {
                        JSONObject loResponse = new JSONObject(lsResult);
                        String result = loResponse.getString("result");
                        if (result.equalsIgnoreCase("success")) {
                            JSONArray jsonA = loResponse.getJSONArray("payload");
                            JSONObject loJson = jsonA.getJSONObject(0);
                            if (poBusTrip.getOBIfExist(loJson.getString("sTransNox")).size() > 0) {
                                Log.d(TAG, "OB application already exist.");
                            } else {
                                EEmployeeBusinessTrip loOB = new EEmployeeBusinessTrip();
                                loOB.setTransNox(loJson.getString("sTransNox"));
                                loOB.setTransact(loJson.getString("dTransact"));
                                loOB.setEmployee(loJson.getString("sCompnyNm"));
                                loOB.setBranchNm(loJson.getString("sBranchNm"));
                                loOB.setDeptName(loJson.getString("sDeptName"));
                                loOB.setDateFrom(loJson.getString("dDateFrom"));
                                loOB.setDateThru(loJson.getString("dDateThru"));
                                loOB.setRemarksx(loJson.getString("sRemarksx"));
                                loOB.setTranStat(loJson.getString("cTranStat"));
                                poBusTrip.insert(loOB);
                            }
                        }
                    }
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccessDownload(TransNox);
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailedDownload(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailedDownload(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailedDownload(e.getMessage());
            }
        }
    }

    public void confirmOBApplication(REmployeeBusinessTrip.OBApprovalInfo infoModel, OnConfirmApplicationCallback callback){
        new ConfirmApplicationTask(instance, callback).execute(infoModel);
    }

    private static class ConfirmApplicationTask extends AsyncTask<REmployeeBusinessTrip.OBApprovalInfo, Void, Boolean>{

        private final REmployeeBusinessTrip poBusTrip;
        private final ConnectionUtil poConn;
        private final OnConfirmApplicationCallback callback;

        private String message;

        public ConfirmApplicationTask(Application instance, OnConfirmApplicationCallback callback){
            this.poConn = new ConnectionUtil(instance);
            this.callback = callback;
            this.poBusTrip = new REmployeeBusinessTrip(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnConfirm("PET Manager", "Sending approval request. Please wait...");
        }

        @SuppressLint("NewApi")
        @Override
        protected Boolean doInBackground(REmployeeBusinessTrip.OBApprovalInfo... obApprovalInfos) {
            try{
                String lsTransNox = poBusTrip.SaveBusinessTripApproval(obApprovalInfos[0]);
                if(lsTransNox == null){
                    message = poBusTrip.getMessage();
                    return false;
                }

                if(!poConn.isDeviceConnected()) {
                    message = poConn.getMessage() + " Your approval will be automatically send if device is reconnected to internet.";
                    return false;
                }

                if(!poBusTrip.PostBusinessTripApproval(lsTransNox)){
                    message = poBusTrip.getMessage();
                    return false;
                }

                message = poBusTrip.getMessage();
                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            try {
                if(isSuccess){
                    callback.OnSuccess(message);
                } else {
                    callback.OnFailed(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailed(e.getMessage());
            }
        }
    }
}
