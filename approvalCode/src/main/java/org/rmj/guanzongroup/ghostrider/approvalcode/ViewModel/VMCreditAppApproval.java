/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.approvalcode.Etc.ViewModelCallback;
import org.rmj.guanzongroup.ghostrider.approvalcode.Model.CreditApp;

import java.io.IOException;

public class VMCreditAppApproval extends AndroidViewModel {
    private final ConnectionUtil poConn;
    private final HttpHeaders poHeaders;

    public VMCreditAppApproval(@NonNull Application application) {
        super(application);
        poConn = new ConnectionUtil(application);
        poHeaders = HttpHeaders.getInstance(application);
    }

    public void LoadApplication(CreditApp params, OnLoadApplicationListener listener){
        new LoadApplicationTask(poConn, poHeaders, listener).execute(params);
    }

    public void ApproveApplication(CreditApp.Application foParams, ViewModelCallback listener){
        new ApproveRequestTask(poConn, poHeaders, listener).execute(foParams);
    }

    private static class LoadApplicationTask extends AsyncTask<CreditApp, Void, String>{
        private final ConnectionUtil loConn;
        private final HttpHeaders loHeaders;
        private final OnLoadApplicationListener listener;

        public LoadApplicationTask(ConnectionUtil loConn, HttpHeaders loHeaders, OnLoadApplicationListener listener) {
            this.loConn = loConn;
            this.loHeaders = loHeaders;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnLoad("Approval Code", "Loading Credit Application. Please wait...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(CreditApp... CreditApp) {
            String lsResponse = "";
            try {
                if (CreditApp[0].isDataValid()) {
                    if (loConn.isDeviceConnected()) {
                        JSONObject loJson = new JSONObject();
                        loJson.put("systemcd", CreditApp[0].getSystemCD());
                        loJson.put("branchcd", CreditApp[0].getBranchCD());
                        loJson.put("transnox", CreditApp[0].getTransNox());
                        lsResponse = WebClient.httpsPostJSon(WebApi.URL_LOAD_APPLICATION_APPROVAL, loJson.toString(), loHeaders.getHeaders());
                        if(lsResponse == null){
                            lsResponse = AppConstants.SERVER_NO_RESPONSE();
                        }
                    } else {
                        lsResponse = AppConstants.NO_INTERNET();
                    }
                } else {
                    lsResponse = AppConstants.APPROVAL_CODE_EMPTY(CreditApp[0].getMessage());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    listener.OnLoadSuccess(loJson.getString("detail"));
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String lsError = loError.getString("message");
                    listener.OnLoadFailed(lsError);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public interface OnLoadApplicationListener{
        void OnLoad(String Title, String Message);
        void OnLoadSuccess(String args);
        void OnLoadFailed(String message);
    }

    private static class ApproveRequestTask extends AsyncTask<CreditApp.Application, Void, String>{
        private final ConnectionUtil loConn;
        private final HttpHeaders loHeaders;
        private final ViewModelCallback listener;

        public ApproveRequestTask(ConnectionUtil loConn, HttpHeaders loHeaders, ViewModelCallback listener) {
            this.loConn = loConn;
            this.loHeaders = loHeaders;
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnLoadData("Approval Code", "Sending approval request. Please wait...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(CreditApp.Application... params) {
            String lsResponse = "";
            try {
                if(params[0].isDataValid()) {
                        if (loConn.isDeviceConnected()) {
                            JSONObject loJson = new JSONObject();
                            loJson.put("transnox", params[0].getTransNox());
                            loJson.put("reasonxx", params[0].getReasonxx());
                            loJson.put("approved", params[0].getApproved());
                            lsResponse = WebClient.httpsPostJSon(WebApi.URL_APPLICATION_APPROVE, loJson.toString(), loHeaders.getHeaders());
                            if(lsResponse == null){
                                lsResponse = AppConstants.SERVER_NO_RESPONSE();
                            }
                        } else {
                            lsResponse = AppConstants.NO_INTERNET();
                        }
                } else{
                    lsResponse = AppConstants.APPROVAL_CODE_EMPTY(params[0].getMessage());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return lsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    listener.OnSuccessResult(loJson.getString("apprcode"));
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    listener.OnFailedResult(loError.getString("message"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}