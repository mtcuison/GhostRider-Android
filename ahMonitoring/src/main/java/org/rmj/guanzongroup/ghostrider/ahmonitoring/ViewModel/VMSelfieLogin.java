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
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RLogSelfie;
import org.rmj.g3appdriver.GRider.Etc.LocationRetriever;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Core.SelfieLog;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.List;

public class VMSelfieLogin extends AndroidViewModel {
    private static final String TAG = VMSelfieLogin.class.getSimpleName();
    private final Application instance;
    private final RLogSelfie poLog;
    private final RBranch pobranch;
    private final SessionManager poSession;
    private final REmployee poUser;

    private final MutableLiveData<Boolean> pbGranted = new MutableLiveData<>();
    private final MutableLiveData<String[]> paPermisions = new MutableLiveData<>();

    public interface OnInitializeCameraCallback {
        void OnInit();
        void OnSuccess(Intent intent, String[] args);
        void OnFailed(String message, Intent intent, String[] args);
    }

    public interface OnLoginTimekeeperListener{
        void OnLogin();
        void OnSuccess(String args);
        void OnFailed(String message);
    }

    public interface OnBranchSelectedCallback {
        void OnLoad();
        void OnSuccess();
        void OnFailed(String message);
    }

    public VMSelfieLogin(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poUser = new REmployee(instance);
        this.poLog = new RLogSelfie(instance);
        this.pobranch = new RBranch(instance);
        this.poSession = new SessionManager(instance);
        paPermisions.setValue(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION});
        pbGranted.setValue(hasPermissions(application.getApplicationContext(), paPermisions.getValue()));
    }
    public LiveData<Boolean> isPermissionsGranted(){
        return pbGranted;
    }

    public LiveData<String[]> getPermisions(){
        return paPermisions;
    }

    public void setPermissionsGranted(boolean isGranted){
        this.pbGranted.setValue(isGranted);
    }

    private static boolean hasPermissions(Context context, String... permissions){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M && permissions!=null ){
            for (String permission: permissions){
                if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                    return false;
                }
            }
        }
        return true;
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

    public LiveData<List<ELog_Selfie>> getAllEmployeeTimeLog(){
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

//    private static class InitCameraTask extends AsyncTask<String, Void, Boolean>{
//
//        private final Activity activity;
//        private final Application instance;
//        private final OnInitializeCameraCallback callback;
//        private final SessionManager poSession;
//        private final ImageFileCreator poImage;
//
//        private Intent loIntent;
//        private String message;
//
//        public InitCameraTask(Activity activity, Application instance, OnInitializeCameraCallback callback) {
//            this.activity = activity;
//            this.instance = instance;
//            this.callback = callback;
//            this.poSession = new SessionManager(instance);
//            this.poImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_SELFIE_LOG, poSession.getUserID());
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            callback.OnInit();
//        }
//
//        @Override
//        protected Boolean doInBackground(String... strings) {
//            try{
//                if(!poImage.IsFileCreated()){
//                    message = poImage.getMessage();
//                    return false;
//                } else {
//
//                }
//            } catch (Exception e){
//                e.printStackTrace();
//                message = e.getMessage();
//                return false;
//            }
//        }
//
//        @Override
//        protected void onPostExecute(Boolean success) {
//            super.onPostExecute(success);
//            if(success){
//                callback.OnSuccess(loIntent, args);
//            } else {
//                callback.OnFailed(message);
//            }
//        }
//    }

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
        private final RLogSelfie poLog;
        private String lsResult = "";

        public OnBranchCheckTask(Application application, OnBranchSelectedCallback callback) {
            this.instance = application;
            this.callback = callback;
            this.poLog = new RLogSelfie(instance);
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

    public void importInventoryTask(String sBranchCode){
        new ImportInventoryTask(instance).execute(sBranchCode);
    }

    private static class ImportInventoryTask extends AsyncTask<String, String, String>{

        private final SelfieLog poSlMaster;
        private final AppConfigPreference poConfig;

        public ImportInventoryTask(Application application) {
            this.poSlMaster = new SelfieLog(application);
            this.poConfig = AppConfigPreference.getInstance(application);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e(TAG, "Started importing branch inventory");
        }

        @Override
        protected String doInBackground(String... strings) {
            poSlMaster.RequestInventoryStocks(strings[0], new SelfieLog.OnActionCallback() {
                @Override
                public void OnSuccess(String args) {
                    poConfig.setInventoryCount(true);
                    Log.e(TAG, "Started importing branch inventory");
                }

                @Override
                public void OnFailed(String message) {
                    poConfig.setInventoryCount(true);
                    Log.e(TAG, "unable to download inventory stocks. " + message);
                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e(TAG, "Finished importing branch inventory");
        }
    }

    public void loginTimeKeeper(ELog_Selfie selfieLog, EImageInfo loImage, OnLoginTimekeeperListener callback){
        try {
            new LoginTimekeeperTask(loImage, selfieLog, instance, callback).execute();
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    private static class LoginTimekeeperTask extends AsyncTask<JSONObject, Void, String>{
        private final ConnectionUtil poConn;
        private final EImageInfo poImageInfo;
        private final ELog_Selfie selfieLog;
        private final OnLoginTimekeeperListener callback;

        private final SelfieLog poSlMaster;
        private String lsResult = "";

        public LoginTimekeeperTask(EImageInfo foImage, ELog_Selfie logInfo, Application instance, OnLoginTimekeeperListener callback){
            this.poImageInfo = foImage;
            this.selfieLog = logInfo;
            this.poConn = new ConnectionUtil(instance);
            this.callback = callback;
            this.poSlMaster = new SelfieLog(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLogin();
        }

        @Override
        protected String doInBackground(JSONObject... loJson) {
            poSlMaster.setImageInfo(poImageInfo);
            poSlMaster.setSelfieLogInfo(selfieLog);
            if (poConn.isDeviceConnected()) {

                poSlMaster.PostSelfieInfo(new SelfieLog.OnActionCallback() {
                    @Override
                    public void OnSuccess(String args) {
                        try {
                            lsResult = AppConstants.APPROVAL_CODE_GENERATED(args);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void OnFailed(String message) {
                        try {
                            lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(message);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess("Login successfully");
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
}