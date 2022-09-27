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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;

import org.rmj.g3appdriver.lib.PetManager.EmployeeOB;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class VMObApproval extends AndroidViewModel {
    private static final String TAG = VMObApproval.class.getSimpleName();

    private final Application instance;

    private final EmployeeOB poBusTrip;
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
        this.poBusTrip = new EmployeeOB(application);
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
        new DownloadBusinessTripTask(instance, callback).execute(TransNox);
    }

    private static class DownloadBusinessTripTask extends AsyncTask<String, Void, Boolean>{

        private final EmployeeOB poSys;
        private final ConnectionUtil poConn;
        private final OnDownloadBusinessTripCallback callback;

        private String transno, message;

        public DownloadBusinessTripTask(Application instance, OnDownloadBusinessTripCallback callback) {
            this.poSys = new EmployeeOB(instance);
            this.poConn = new ConnectionUtil(instance);
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return false;
                }

                if(!poSys.DownloadApplication(strings[0])){
                    message = poSys.getMessage();
                    return false;
                }

                transno = strings[0];
                return true;

            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload("PET Manager", "Downloading business trip. Please wait...");
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccessDownload(transno);
            } else {
                callback.OnFailedDownload(message);
            }
        }
    }

    public void confirmOBApplication(EmployeeOB.OBApprovalInfo infoModel, OnConfirmApplicationCallback callback){
        new ConfirmApplicationTask(instance, callback).execute(infoModel);
    }

    private static class ConfirmApplicationTask extends AsyncTask<EmployeeOB.OBApprovalInfo, Void, Boolean>{

        private final EmployeeOB poBusTrip;
        private final ConnectionUtil poConn;
        private final OnConfirmApplicationCallback callback;

        private String message;

        public ConfirmApplicationTask(Application instance, OnConfirmApplicationCallback callback){
            this.poConn = new ConnectionUtil(instance);
            this.callback = callback;
            this.poBusTrip = new EmployeeOB(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnConfirm("PET Manager", "Sending approval request. Please wait...");
        }

        @SuppressLint("NewApi")
        @Override
        protected Boolean doInBackground(EmployeeOB.OBApprovalInfo... obApprovalInfos) {
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
