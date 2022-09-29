/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Etc.LocationRetriever;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.dev.DeptCode;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.PetManager.SelfieLog;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.ArrayList;
import java.util.List;

public class VMSelfieLog extends AndroidViewModel {
    private static final String TAG = VMSelfieLog.class.getSimpleName();

    private final Application instance;
    private final SelfieLog poLog;
    private final RBranch pobranch;
    private final SessionManager poSession;
    private final EmployeeMaster poUser;

    public interface OnInitializeCameraCallback {
        void OnInit();
        void OnSuccess(Intent intent, String[] args);
        void OnFailed(String message, Intent intent, String[] args);
    }

    public interface OnLoginTimekeeperListener{
        void OnLogin();
        void OnSuccess(String args);
        void OnProceedCashCount(String args);
        void OnFailed(String message);
    }

    public interface OnBranchSelectedCallback {
        void OnLoad();
        void OnSuccess();
        void OnFailed(String message);
    }

    public VMSelfieLog(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poUser = new EmployeeMaster(instance);
        this.poLog = new SelfieLog(instance);
        this.pobranch = new RBranch(instance);
        this.poSession = new SessionManager(instance);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getUserInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public LiveData<EBranchInfo> getBranchInfo(String BranchCD){
        return pobranch.getBranchInfo(BranchCD);
    }

    public LiveData<List<ESelfieLog>> getAllEmployeeTimeLog(){
        return poLog.getAllEmployeeTimeLog();
    }

    public String getEmployeeLevel(){
        return poSession.getEmployeeLevel();
    }

    public interface OnBranchCheckListener{
        void OnCheck(List<EBranchInfo> area, List<EBranchInfo> all);
        void OnFailed(String message);
    }

    public void CheckBranchList(OnBranchCheckListener listener){
        new CheckBranchListTask(pobranch, listener).execute();
    }

    public void InitCameraLaunch(Activity activity, OnInitializeCameraCallback callback){
        new InitializeCameraTask(activity, instance, callback).execute();
    }

    private static class InitializeCameraTask extends AsyncTask<String, Void, Boolean>{

        private final Activity activity;
        private final Application instance;
        private final OnInitializeCameraCallback callback;
        private final SessionManager poSession;
        private final ImageFileCreator loImage;

        private Intent loIntent;
        private String[] args = new String[4];
        private String message;

        public InitializeCameraTask(Activity activity, Application instance, OnInitializeCameraCallback callback){
            this.activity = activity;
            this.instance = instance;
            this.callback = callback;
            this.poSession = new SessionManager(instance);
            this.loImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_SELFIE_LOG, poSession.getUserID());
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
                LocationRetriever loLrt = new LocationRetriever(instance, activity);
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

    private static class CheckBranchListTask extends AsyncTask<String, Void, Boolean>{

        private final RBranch poBranch;
        private final OnBranchCheckListener mListener;

        private List<EBranchInfo> area, all;
        private String message;

        public CheckBranchListTask(RBranch poBranch, OnBranchCheckListener mListener) {
            this.poBranch = poBranch;
            this.mListener = mListener;
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                area = poBranch.getAreaBranchesList();
                all = poBranch.getBranchList();
                if(all.size() > 0){
                    return true;
                } else {
                    message = "No branch list retrieve from local. Please sync device records";
                    return false;
                }
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean){
                mListener.OnCheck(area, all);
            } else {
                mListener.OnFailed(message);
            }
        }
    }

    public void checkIfAlreadyLog(String BranchCde, OnBranchSelectedCallback callback){
        new OnBranchCheckTask(instance, callback).execute(BranchCde);
    }

    private static class OnBranchCheckTask extends AsyncTask<String, Void, String>{

        private final Application instance;
        private final OnBranchSelectedCallback callback;
        private final SelfieLog poLog;

        public OnBranchCheckTask(Application application, OnBranchSelectedCallback callback) {
            this.instance = application;
            this.callback = callback;
            this.poLog = new SelfieLog(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad();
        }

        @Override
        protected String doInBackground(String... strings) {
            String BranchCD = strings[0];
            if(poLog.checkBranchCodeIfExist(BranchCD, AppConstants.CURRENT_DATE) == 2){
                return AppConstants.LOCAL_EXCEPTION_ERROR("Only 2 Selfie log per branch is allowed.");
            } else {
                try {
                    return AppConstants.APPROVAL_CODE_GENERATED("Branch selected.");
                } catch (Exception e) {
                    e.printStackTrace();
                    return AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
                }
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailed(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailed(e.getMessage());
            }
        }
    }

    public void TimeIn(SelfieLog.SelfieLogDetail foVal, OnLoginTimekeeperListener callback){
        new TimeInTask(instance, callback).execute(foVal);
    }

    public static class TimeInTask extends AsyncTask<SelfieLog.SelfieLogDetail, Void, Integer>{

        private final SessionManager poSession;
        private final SelfieLog poSys;
        private final OnLoginTimekeeperListener callback;

        private final ConnectionUtil poConn;

        private String message;

        public TimeInTask(Application instance, OnLoginTimekeeperListener callback) {
            this.poSession = new SessionManager(instance);
            this.poSys = new SelfieLog(instance);
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(SelfieLog.SelfieLogDetail... selfieLogs) {
            try{
                String lsTransNo = poSys.SaveSelfieLog(selfieLogs[0]);
                if(lsTransNo == null){
                    message = poSys.getMessage();
                    return 0;
                }

                if(!poConn.isDeviceConnected()){
                    message = "Your selfie log has been save to local.";
                    return 1;
                }

                if(!poSys.UploadSelfieLog(lsTransNo)){
                    message = poSys.getMessage();
                    return 0;
                }

                //TODO: Revise the return value from boolean to int
                //check if user must be required to proceed for cash count and random stock inventory
                if(poSession.getEmployeeLevel().equalsIgnoreCase(String.valueOf(DeptCode.LEVEL_AREA_MANAGER))){

                    //Usually message is only use for storing and error message.
                    // this time message value will be branch code which will be pass for cash count entry.
                    message = selfieLogs[0].getBranchCode();
                    return 2;
                }
                return 1;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return 0;
            }
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result){
                case 0:
                    callback.OnFailed(message);
                    break;
                case 1:
                    callback.OnSuccess(message);
                    break;
                case 2:
                    // message value will be branch code.
                    callback.OnProceedCashCount(message);
                    break;
                default:
                    break;
            }
        }
    }
}