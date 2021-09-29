/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 8/16/21 8:50 AM
 * project file last modified : 8/16/21 8:21 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.LeaveApprovalInfo;

import static org.rmj.g3appdriver.utils.WebApi.URL_GET_LEAVE_APPLICATION;

public class VMLeaveApproval extends AndroidViewModel {

    public static final String TAG = VMLeaveApproval.class.getSimpleName();
    private final Application instance;
    private final RBranch pobranch;
    private final REmployee poUser;
    private final REmployeeLeave poLeave;

    private final MutableLiveData<String> TransNox = new MutableLiveData<>();

    public VMLeaveApproval(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new RBranch(instance);
        this.poUser = new REmployee(instance);
        this.poLeave = new REmployeeLeave(instance);
        this.TransNox.setValue("");
    }

    public interface OnDownloadLeaveAppInfo {
        void OnDownload();
        void OnSuccessDownload(String TransNox);
        void OnFailedDownload(String message);
    }

    public interface OnConfirmLeaveAppCallback {
        void onConfirm();
        void onSuccess();
        void onFailed(String message);
    }

    public void setTransNox(String transNox){
        this.TransNox.setValue(transNox);
    }

    public LiveData<String> getTransNox(){
        return TransNox;
    }

    public LiveData<EEmployeeLeave> getEmployeeLeaveInfo(String TransNox){
        return poLeave.getEmployeeLeaveInfo(TransNox);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getEmployeeInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public void downloadLeaveApplication(String transNox, OnDownloadLeaveAppInfo callBack){
        JSONObject loJson = new JSONObject();
        try {
            if(transNox.isEmpty()){
                callBack.OnFailedDownload("Please enter leave transaction no.");
            } else {
                loJson.put("sTransNox", transNox);
                new DownloadLeaveAppTask(instance, callBack).execute(loJson);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            callBack.OnFailedDownload(e.getMessage());
        }
    }

    private static class DownloadLeaveAppTask extends AsyncTask<JSONObject, Void, String> {
        private final HttpHeaders headers;
        private final ConnectionUtil conn;
        private final REmployeeLeave poLeave;
        private final OnDownloadLeaveAppInfo callback;
        private String TransNox;

        public DownloadLeaveAppTask(Application instance, OnDownloadLeaveAppInfo callback) {
            this.headers = HttpHeaders.getInstance(instance);
            this.conn = new ConnectionUtil(instance);
            this.poLeave = new REmployeeLeave(instance);
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(JSONObject... strings) {
            String response = "";
            try{
                if(conn.isDeviceConnected()) {
                    response = WebClient.sendRequest(URL_GET_LEAVE_APPLICATION, strings[0].toString(), headers.getHeaders());
                    JSONObject loResponse = new JSONObject(response);
                    String lsResult = loResponse.getString("result");
                    if (lsResult.equalsIgnoreCase("success")) {
                        JSONArray jsonA = loResponse.getJSONArray("payload");
                        JSONObject loJson = jsonA.getJSONObject(0);
                        if(poLeave.getTransnoxIfExist(loJson.getString("sTransNox")).size() > 0){
                            Log.d(TAG, "Leave application already exist.");
                        } else {
                            EEmployeeLeave loLeave = new EEmployeeLeave();
                            TransNox = loJson.getString("sTransNox");
                            loLeave.setTransNox(loJson.getString("sTransNox"));
                            loLeave.setTransact(loJson.getString("dTransact"));
                            loLeave.setEmployID(loJson.getString("xEmployee"));
                            loLeave.setBranchNm(loJson.getString("sBranchNm"));
                            loLeave.setDeptName(loJson.getString("sDeptName"));
                            loLeave.setPositnNm(loJson.getString("sPositnNm"));
                            loLeave.setAppldFrx(loJson.getString("dAppldFrx"));
                            loLeave.setAppldTox(loJson.getString("dAppldTox"));
                            loLeave.setNoDaysxx(loJson.getString("nNoDaysxx"));
                            loLeave.setPurposex(loJson.getString("sPurposex"));
                            loLeave.setLeaveTyp(loJson.getString("cLeaveTyp"));
                            loLeave.setLveCredt(loJson.getString("nLveCredt"));
                            loLeave.setTranStat(loJson.getString("cTranStat"));
                            poLeave.insertApplication(loLeave);
                        }
                    }
                } else {
                    response = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (NullPointerException e){
                e.printStackTrace();
            }catch (Exception e){
                e.printStackTrace();
            }
            return response;
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

    public void confirmLeaveApplication(LeaveApprovalInfo infoModel, OnConfirmLeaveAppCallback callBack){
        new ConfirmLeaveTask(infoModel, instance, callBack).execute();
    }

    private static class ConfirmLeaveTask extends AsyncTask<Void, Void, String> {

        private final LeaveApprovalInfo infoModel;
        private final OnConfirmLeaveAppCallback callback;

        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final REmployeeLeave poLeave;

        public ConfirmLeaveTask(LeaveApprovalInfo infoModel, Application instance, OnConfirmLeaveAppCallback callback) {
            this.infoModel = infoModel;
            this.callback = callback;
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poLeave = new REmployeeLeave(instance);
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            String lsResponse;
            try{

                JSONObject loJson = new JSONObject();
                loJson.put("sTransNox", infoModel.getTransNox());
                loJson.put("dTransact", AppConstants.CURRENT_DATE);
                loJson.put("dAppldFrx", infoModel.getAppldFrx());
                loJson.put("dAppldTox", infoModel.getAppldTox());
                loJson.put("cTranStat", infoModel.getTranStat());
                loJson.put("nWithPayx", infoModel.getWithPayx());
                loJson.put("nWithOPay", infoModel.getWithOPay());
                loJson.put("sApproved", infoModel.getApproved());
                loJson.put("dApproved", AppConstants.CURRENT_DATE);

                if(poConn.isDeviceConnected()) {
                    lsResponse = WebClient.sendRequest(WebApi.URL_CONFIRM_LEAVE_APPLICATION, loJson.toString(), poHeaders.getHeaders());
                    if(lsResponse == null) {
                        lsResponse = AppConstants.SERVER_NO_RESPONSE();
                    } else {
                        JSONObject jsonResponse = new JSONObject(lsResponse);
                        String lsResult = jsonResponse.getString("result");
                        if(lsResult.equalsIgnoreCase("success")){
                            poLeave.updateLeaveApproval(infoModel.getTranStat(), infoModel.getTransNox(), new AppConstants().DATE_MODIFIED);
                        }
                        Log.e(TAG, lsResponse);
                    }
                } else {
                    lsResponse = AppConstants.SERVER_NO_RESPONSE();
                    Log.e(TAG, "else " + lsResponse);
                }

            } catch (NullPointerException e){
                e.printStackTrace();
                lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }catch (Exception e){
                e.printStackTrace();
                lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResponse;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onConfirm();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.onSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.onFailed(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.onFailed(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.onFailed(e.getMessage());
            }
        }
    }
}