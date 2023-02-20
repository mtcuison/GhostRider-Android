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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.lib.ApprovalCode.ApprovalCode;
import org.rmj.g3appdriver.lib.ApprovalCode.model.SCA;
import org.rmj.g3appdriver.lib.ApprovalCode.pojo.CreditAppInfo;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.approvalcode.Etc.ViewModelCallback;
import org.rmj.g3appdriver.lib.ApprovalCode.pojo.CreditApp;

public class VMCreditAppApproval extends AndroidViewModel {
    private final ConnectionUtil poConn;
    private final SCA poSys;

    public VMCreditAppApproval(@NonNull Application application) {
        super(application);
        this.poConn = new ConnectionUtil(application);
        this.poSys = new ApprovalCode(application).getInstance(ApprovalCode.eSCA.CREDIT_APP);
    }

    public void LoadApplication(String args, String args1, String args2, OnLoadApplicationListener listener){
        new LoadApplicationTask(listener).execute(args, args1, args2);
    }

    public void ApproveApplication(CreditApp.Application foParams, ViewModelCallback listener){
        new ApproveRequestTask(listener).execute(foParams);
    }

    private class LoadApplicationTask extends AsyncTask<String, Void, CreditAppInfo>{

        private final OnLoadApplicationListener listener;

        private String message;

        public LoadApplicationTask(OnLoadApplicationListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnLoad("Approval Code", "Loading Credit Application. Please wait...");
        }

        @Override
        protected CreditAppInfo doInBackground(String... args) {
            try {
                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return null;
                }

                String
                sSystemCD = args[0],
                lsTransNo = args[1],
                lsBrnchCd = args[2];

                CreditAppInfo loApp = poSys.LoadApplication(sSystemCD, lsBrnchCd, lsTransNo);
                if(loApp == null){
                    message = poSys.getMessage();
                    return null;
                }

                return loApp;
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(CreditAppInfo result) {
            super.onPostExecute(result);
            if(result == null){
                listener.OnLoadFailed(message);
            } else {
                listener.OnLoadSuccess(result);
            }
        }
    }

    public interface OnLoadApplicationListener{
        void OnLoad(String Title, String Message);
        void OnLoadSuccess(CreditAppInfo args);
        void OnLoadFailed(String message);
    }

    private class ApproveRequestTask extends AsyncTask<CreditApp.Application, Void, String>{

        private final ViewModelCallback listener;

        private String message;

        public ApproveRequestTask(ViewModelCallback listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnLoadData("Approval Code", "Sending approval request. Please wait...");
        }

        @Override
        protected String doInBackground(CreditApp.Application... params) {
            try{
                if(!params[0].isDataValid()){
                    message = params[0].getMessage();
                    return null;
                }

                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return null;
                }

                String lsCode = poSys.GenerateCode(params[0]);

                if(lsCode == null){
                    message = poSys.getMessage();
                    return null;
                }

                return lsCode;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if(result == null){
                listener.OnFailedResult(message);
            } else {
                listener.OnSuccessResult(result);
            }
        }
    }
}