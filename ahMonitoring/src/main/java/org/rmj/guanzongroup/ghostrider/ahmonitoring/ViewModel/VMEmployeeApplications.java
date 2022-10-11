/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 9/9/21, 11:53 AM
 * project file last modified : 9/9/21, 11:51 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.lib.PetManager.EmployeeLeave;
import org.rmj.g3appdriver.lib.PetManager.EmployeeOB;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMEmployeeApplications extends AndroidViewModel {
    private static final String TAG = VMEmployeeApplications.class.getSimpleName();

    private final Application instance;
    private final EmployeeLeave poLeave;
    private final EmployeeOB poBuss;
    private final RBranch poBranch;

    private final MutableLiveData<Integer> pnLeave = new MutableLiveData<>();

    public interface OnDownloadApplicationListener {
        void OnDownload(String Title, String message);
        void OnDownloadSuccess();
        void OnDownloadFailed(String message);
    }

    public VMEmployeeApplications(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poLeave = new EmployeeLeave(instance);
        this.poBranch = new RBranch(instance);
        this.poBuss = new EmployeeOB(instance);
        this.pnLeave.setValue(0);
    }

    public void setApplicationType(int fnVal){
        pnLeave.setValue(fnVal);
    }

    public LiveData<Integer> GetApplicationType(){
        return pnLeave;
    }

    public LiveData<List<EEmployeeLeave>> getApproveLeaveList(){
        return poLeave.getApproveLeaveList();
    }

    public LiveData<List<EEmployeeBusinessTrip>> GetApproveBusTrip(){
        return poBuss.GetApproveBusTrip();
    }

    public LiveData<List<EEmployeeLeave>> getEmployeeLeaveForApprovalList(){
        return poLeave.getEmployeeLeaveForApprovalList();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public void  DownloadLeaveForApproval(OnDownloadApplicationListener listener){
        new DownloadLeaveTask(instance, listener).execute();
    }

    private static class DownloadLeaveTask extends AsyncTask<Void, Void, Boolean>{

        private final EmployeeLeave loLeave;
        private final ConnectionUtil loConn;
        private final EmployeeOB poBusTrip;
        private final OnDownloadApplicationListener mListener;

        private String message;

        public DownloadLeaveTask(Application instance, OnDownloadApplicationListener listener){
            this.loLeave = new EmployeeLeave(instance);
            this.loConn = new ConnectionUtil(instance);
            this.poBusTrip = new EmployeeOB(instance);
            this.mListener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mListener.OnDownload("PET Manager","Downloading leave applications. Please wait...");
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                if (!loConn.isDeviceConnected()) {
                    message = loConn.getMessage();
                    return false;
                }

                if(!loLeave.DownloadApplications()){
                    message = loLeave.getMessage();
                    Log.e(TAG, message);
                }

                if(!poBusTrip.DownloadApplications()){
                    message = poBusTrip.getMessage();
                    Log.e(TAG, message);
                }

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
            if(isSuccess){
                mListener.OnDownloadSuccess();
            } else {
                mListener.OnDownloadFailed(message);
            }
        }
    }
}


