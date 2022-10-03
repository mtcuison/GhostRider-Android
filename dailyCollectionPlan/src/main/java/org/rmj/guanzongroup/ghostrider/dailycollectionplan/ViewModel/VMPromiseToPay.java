/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.LocationRetriever;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.lib.integsys.Dcp.PromiseToPay;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;
import org.rmj.guanzongroup.ghostrider.notifications.Function.GRiderErrorReport;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMPromiseToPay extends AndroidViewModel {
    private static final String TAG = VMPromiseToPay.class.getSimpleName();

    private final LRDcp poSys;
    private final EmployeeMaster poUser;

    private final RBranch poBranch;
    private final Application instance;

    public VMPromiseToPay(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new LRDcp(application);
        this.poBranch = new RBranch(application);
        this.poUser = new EmployeeMaster(application);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<EDCPCollectionDetail> GetCollectionDetail(String TransNo, int EntryNo, String Accountno){
        return poSys.GetAccountDetailForTransaction(TransNo, Accountno, String.valueOf(EntryNo));
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return poBranch.getAllMcBranchInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<String[]> getAllBranchNames(){
        return poBranch.getAllMcBranchNames();
    }

    public LiveData<EBranchInfo> getUserBranchEmployee(){
        return poBranch.getUserBranchInfo();
    }

    public void InitCameraLaunch(Activity activity, OnInitializeCameraCallback callback){
        new InitializeCameraTask(activity, instance, callback).execute();
    }

    private static class InitializeCameraTask extends AsyncTask<String, Void, Boolean>{

        private final OnInitializeCameraCallback callback;
        private final ImageFileCreator loImage;
        private final LocationRetriever loLrt;

        private Intent loIntent;
        private String[] args = new String[4];
        private String message;

        public InitializeCameraTask(Activity activity, Application instance, OnInitializeCameraCallback callback){
            this.callback = callback;
            SessionManager poSession = new SessionManager(instance);
            this.loImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_SELFIE_LOG, poSession.getUserID());
            this.loLrt = new LocationRetriever(instance, activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnInit();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!loImage.IsFileCreated()){
                message = loImage.getMessage();
                return false;
            } else {
                if(loLrt.HasLocation()){
                    args[0] = loImage.getFilePath();
                    args[1] = loImage.getFileName();
                    args[2] = loLrt.getLatitude();
                    args[3] = loLrt.getLongitude();
                    loIntent = loImage.getCameraIntent();
                    return true;
                } else {
                    args[0] = loImage.getFilePath();
                    args[1] = loImage.getFileName();
                    args[2] = loLrt.getLatitude();
                    args[3] = loLrt.getLongitude();
                    loIntent = loImage.getCameraIntent();
                    message = loLrt.getMessage();
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccess(loIntent, args);
            } else {
                callback.OnFailed(message, loIntent, args);
            }
        }
    }

    public void SaveTransaction(PromiseToPay foVal, ViewModelCallback callback){
        new SaveTransactionTask(callback).execute(foVal);
    }

    private class SaveTransactionTask extends AsyncTask<PromiseToPay, Void, Boolean>{

        private final ViewModelCallback callback;

        private String message;

        public SaveTransactionTask(ViewModelCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(PromiseToPay... obj) {
            if(!poSys.SavePTP(obj[0])){
                message = poSys.getMessage();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnFailedResult(message);
            } else {
                callback.OnSuccessResult();
            }
        }
    }
}