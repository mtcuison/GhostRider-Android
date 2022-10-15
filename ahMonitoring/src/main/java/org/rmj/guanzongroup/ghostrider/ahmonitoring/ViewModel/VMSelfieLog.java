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

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.etc.LocationRetriever;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.SelfieLog.SelfieLog;
import org.rmj.g3appdriver.lib.integsys.CashCount.CashCount;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.List;

public class VMSelfieLog extends AndroidViewModel {
    private static final String TAG = VMSelfieLog.class.getSimpleName();

    private final Application instance;
    private final SelfieLog poLog;
    private final CashCount poCash;
    private final RBranch pobranch;
    private final EmployeeMaster poUser;
    private final MutableLiveData<String> pdTransact = new MutableLiveData<>();

    public interface OnLoginTimekeeperListener{
        void OnLogin();
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public interface OnValidateCashCount{
        void OnValidate();
        void OnProceed(String args);
        void OnWarning(String message);
        void OnFailed(String message);
        void OnUnauthorize(String message);
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
        this.poCash = new CashCount(instance);
        this.pdTransact.setValue(AppConstants.CURRENT_DATE);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public LiveData<EBranchInfo> getBranchInfo(String BranchCD){
        return pobranch.getBranchInfo(BranchCD);
    }

    public LiveData<String> GetSelectedDate(){
        return pdTransact;
    }

    public void SetSelectedDate(String fsVal){
        this.pdTransact.setValue(fsVal);
    }

    public LiveData<List<ESelfieLog>> getAllEmployeeTimeLog(String fsVal){
        return poLog.GetAllEmployeeTimeLog(fsVal);
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
        private final ImageFileCreator loImage;

        private Intent loIntent;
        private final String[] args = new String[4];
        private String message;

        public InitializeCameraTask(Activity activity, Application instance, OnInitializeCameraCallback callback){
            this.activity = activity;
            this.instance = instance;
            this.callback = callback;
            SessionManager poSession = new SessionManager(instance);
            this.loImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_SELFIE_LOG, poSession.getUserID());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnInit();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!loImage.IsFileCreated(true)){
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

        private final OnBranchSelectedCallback callback;
        private final SelfieLog poLog;

        public OnBranchCheckTask(Application instance, OnBranchSelectedCallback callback) {
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

    public static class TimeInTask extends AsyncTask<SelfieLog.SelfieLogDetail, Void, Boolean>{

        private final SelfieLog poSys;
        private final OnLoginTimekeeperListener callback;

        private final ConnectionUtil poConn;

        private String message;

        public TimeInTask(Application instance, OnLoginTimekeeperListener callback) {
            this.poSys = new SelfieLog(instance);
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLogin();
        }

        @Override
        protected Boolean doInBackground(SelfieLog.SelfieLogDetail... selfieLogs) {
            try{
                String lsTransNo = poSys.SaveSelfieLog(selfieLogs[0]);
                if(lsTransNo == null){
                    message = poSys.getMessage();
                    return false;
                }

                if(!poConn.isDeviceConnected()){
                    message = "Your selfie log has been save to local.";
                    return false;
                }

                if (!poSys.UploadSelfieLog(lsTransNo)) {
                    message = poSys.getMessage();
                    return false;
                }

                //Usually message is only use for storing and error message.
                // this time message value will be branch code which will be pass for cash count entry.
                message = selfieLogs[0].getBranchCode();
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
                callback.OnFailed(message);
            } else {
                callback.OnSuccess(message);
            }
        }
    }

    public void ValidateCashCount(String fsVal, OnValidateCashCount callback){
        new ValidateUserCashCount(callback).execute(fsVal);
    }

    private class ValidateUserCashCount extends AsyncTask<String, Void, Integer>{

        private final OnValidateCashCount callback;

        private String message;

        public ValidateUserCashCount(OnValidateCashCount callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnValidate();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            int lnResult = poCash.ValidateCashCount(strings[0]);
            if(lnResult == 1){
                message = strings[0];
                return lnResult;
            }
            message = poCash.getMessage();
            return lnResult;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result){
                case 0:
                    callback.OnFailed(message);
                    break;
                case 1:
                    callback.OnProceed(message);
                    break;
                case 2:
                    callback.OnWarning(message);
                    break;
                default:
                    callback.OnUnauthorize(message);
                    break;
            }
        }
    }
}