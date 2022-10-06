/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.AppTokenManager;
import org.rmj.g3appdriver.dev.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.dev.Database.Repositories.RProvince;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.PetManager.SelfieLog;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.epacss.BuildConfig;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class VMSplashScreen extends AndroidViewModel {
    private static final String TAG = VMSplashScreen.class.getSimpleName();

    private final Application instance;

    private final MutableLiveData<String> psVersion = new MutableLiveData<>();
    private final MutableLiveData<String> sEmployLevel = new MutableLiveData<>();
    private final MutableLiveData<List<String>> psPermissions = new MutableLiveData<>();
    private final EmployeeMaster poUserDbx;
    private final AppConfigPreference poConfigx;
    private final AppTokenManager poToken;
    private final RDailyCollectionPlan poDcp;
    private final SelfieLog poLogx;

    private final MutableLiveData<Boolean> pbIsGrantd = new MutableLiveData<>();

    public VMSplashScreen(@NonNull Application application) throws IOException {
        super(application);
        this.instance = application;
        this.poUserDbx = new EmployeeMaster(application);
        this.poConfigx = AppConfigPreference.getInstance(application);
        this.poConfigx.setTemp_ProductID("gRider");
        this.poConfigx.setUpdateLocally(false);
        this.poLogx = new SelfieLog(application);
        Date buildDate = new Date(BuildConfig.TIMESTAMP);
        this.poConfigx.setupAppVersionInfo(BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME, String.valueOf(buildDate.getTime()));
        this.poToken = new AppTokenManager(application);
        ETokenInfo loToken = new ETokenInfo();
        loToken.setTokenInf("temp_token");
        this.poDcp = new RDailyCollectionPlan(application);
        this.poToken.setTokenInfo(loToken);
        this.psVersion.setValue(poConfigx.getVersionInfo());
        new CheckConnectionTask(application).execute();
    }

    public void initializeApp(){
    }

    public LiveData<List<String>> GetPermissions(){
        return psPermissions;
    }

    public LiveData<String> getVersionInfo(){
        return psVersion;
    }

    public LiveData<String> getEmployeeLevel(){
        return this.sEmployLevel;
    }

    private static boolean hasPermissions(Context context, String... permissions){
        for (String permission: permissions){
            if(ActivityCompat.checkSelfPermission(context, permission)!= PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

    private static class CheckConnectionTask extends AsyncTask<String, Void, Boolean>{

        private final Application instance;
        private final ConnectionUtil poConn;

        private String message;

        public CheckConnectionTask(Application instance) {
            this.instance = instance;
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!poConn.isDeviceConnected()){
                message = poConn.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                Toast.makeText(instance, "Device connected", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(instance, "Offline mode", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void InitializeData(OnInitializeCallback onInitializecallback){
        new InitializeDataTask(instance, onInitializecallback).execute();
    }

    public interface OnInitializeCallback {
        void OnProgress(String args, int progress);
        void OnSuccess();
        void OnNoSession();
        void OnFailed(String message);
    }

    private static class InitializeDataTask extends AsyncTask<String, Integer, Integer>{

        private final Application instance;
        private final OnInitializeCallback callback;
        private final ConnectionUtil poConn;
        private final AppConfigPreference poConfig;
        private final EmployeeMaster poUser;
        private final SessionManager poSession;

        private String message;

        public InitializeDataTask(Application instance, OnInitializeCallback callback) {
            this.instance = instance;
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
            this.poUser = new EmployeeMaster(instance);
            this.poSession = new SessionManager(instance);
        }

        @Override
        protected Integer doInBackground(String... strings) {
            try{
                if(poConn.isDeviceConnected()){
                    Log.d(TAG, "Initializing barangay data.");
                    if(!new RBarangay(instance).ImportBarangay()){
                        Log.e(TAG, "Unable to import barangay");
                    }
                    publishProgress(1);

                    Log.d(TAG, "Initializing town data.");
                    if(!new RTown(instance).ImportTown()){
                        Log.e(TAG, "Unable to import town");
                    }
                    publishProgress(2);

                    Log.d(TAG, "Initializing province data.");
                    if(!new RProvince(instance).ImportProvince()){
                        Log.e(TAG, "Unable to import province");
                    }
                    publishProgress(3);

                    if(!new RBranch(instance).ImportBranches()){
                        Log.e(TAG, "Unable to import branches");
                    }
                    publishProgress(4);

                    if(!poSession.isLoggedIn()){
                        return 2;
                    }

                    if(!poUser.IsSessionValid()){
                        return 2;
                    }

                    return 1;
                } else {
                    if(!poConfig.isAppFirstLaunch()){
                        message = "Offline Mode.";
                        return 1;
                    }

                    message = "Please connect you device to internet to download data.";
                    return 0;
                }
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return 0;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            String lsArgs;
            if(poConfig.isAppFirstLaunch()){

                lsArgs = "Importing Data...";
            } else {
                lsArgs = "Updating Data...";
            }
            callback.OnProgress(lsArgs, values[0]);
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result){
                case 0:
                    callback.OnFailed(message);
                    break;
                case 1:
                    callback.OnSuccess();
                    break;
                default:
                    callback.OnNoSession();
                    break;
            }
        }
    }

}
