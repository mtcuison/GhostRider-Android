/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/30/21 11:47 AM
 * project file last modified : 4/24/21 3:19 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeBusinessTrip;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RBranch;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.PetManager.OnCheckEmployeeApplicationListener;
import org.rmj.g3appdriver.lib.PetManager.PetManager;
import org.rmj.g3appdriver.lib.PetManager.model.iPM;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMAssociateDashboard extends AndroidViewModel {
    private static final String TAG =  VMAssociateDashboard.class.getSimpleName();

    private final Application instance;

    private final EmployeeMaster poEmployee;
    private iPM poApp;
    private final RBranch pobranch;
    private final ConnectionUtil poConn;

    private final AppConfigPreference poConfigx;

    private final MutableLiveData<String> psVersion = new MutableLiveData<>();

    public VMAssociateDashboard(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poEmployee = new EmployeeMaster(application);
        this.pobranch = new RBranch(application);
        this.poConfigx = AppConfigPreference.getInstance(application);
        this.psVersion.setValue(poConfigx.getVersionInfo());
        this.poConn = new ConnectionUtil(application);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return poEmployee.GetEmployeeInfo();
    }

    public LiveData<String> getVersionInfo(){
        return psVersion;
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public LiveData<List<EEmployeeLeave>> GetLeaveForApproval(){
        this.poApp = new PetManager(instance).GetInstance(PetManager.ePetManager.LEAVE_APPLICATION);
        return poApp.GetLeaveApplicationsForApproval();
    }

    public LiveData<List<EEmployeeBusinessTrip>> GetOBForApproval(){
        this.poApp = new PetManager(instance).GetInstance(PetManager.ePetManager.BUSINESS_TRIP_APPLICATION);
        return poApp.GetOBApplicationsForApproval();
    }

    public void CheckApplicationsForApproval(OnCheckEmployeeApplicationListener listener){
        new CheckApplicationForApprovalTask(listener).execute();
    }

    private class CheckApplicationForApprovalTask extends AsyncTask<Void, Void, Boolean>{

        private final OnCheckEmployeeApplicationListener mListener;

        private String message;

        public CheckApplicationForApprovalTask(OnCheckEmployeeApplicationListener mListener) {
            this.mListener = mListener;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage();
                    return false;
                }

                poApp = new PetManager(instance).GetInstance(PetManager.ePetManager.LEAVE_APPLICATION);
                if(!poApp.ImportApplications()){
                    Log.e(TAG, poApp.getMessage());
                }

                Thread.sleep(1000);

                poApp = new PetManager(instance).GetInstance(PetManager.ePetManager.BUSINESS_TRIP_APPLICATION);
                if(!poApp.ImportApplications()){
                    Log.e(TAG, poApp.getMessage());
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
            if(!isSuccess){
                mListener.OnFailed(message);
            } else {
                mListener.OnSuccess();
            }
        }
    }
}