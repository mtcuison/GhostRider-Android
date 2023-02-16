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
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.dev.Database.Entities.ETokenInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.AppTokenManager;
import org.rmj.g3appdriver.dev.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.Repositories.RProvince;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.SessionManager;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.epacss.BuildConfig;

public class VMSplashScreen extends AndroidViewModel {
    private static final String TAG = VMSplashScreen.class.getSimpleName();

    private final Application instance;

    private final AppConfigPreference poConfigx;

    public VMSplashScreen(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poConfigx = AppConfigPreference.getInstance(application);
        this.poConfigx.setPackageName(BuildConfig.APPLICATION_ID);
        this.poConfigx.setTemp_ProductID("gRider");
        this.poConfigx.setUpdateLocally(false);
        this.poConfigx.setupAppVersionInfo(BuildConfig.VERSION_CODE, BuildConfig.VERSION_NAME, "");
        ETokenInfo loToken = new ETokenInfo();
        loToken.setTokenInf("temp_token");
        new CheckConnectionTask(application).execute();
    }

    public void SaveFirebaseToken(String fsVal){
        new SaveTokenTask().execute(fsVal);
    }

    private class SaveTokenTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected Boolean doInBackground(String... strings) {
            new AppTokenManager(instance).SaveFirebaseToken(strings[0]);
            return null;
        }
    }

    private static class CheckConnectionTask extends AsyncTask<String, Void, Boolean>{

        private final Application instance;
        private final ConnectionUtil poConn;

        public CheckConnectionTask(Application instance) {
            this.instance = instance;
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!poConn.isDeviceConnected()){
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
        void OnHasDCP();
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

                    LRDcp loDcp = new LRDcp(instance);
                    if(loDcp.HasCollection()){
                        publishProgress(5);
                    }

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
            if(values[0] < 5) {
                callback.OnProgress(lsArgs, values[0]);
            } else {
                callback.OnHasDCP();
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
                    callback.OnSuccess();
                    break;
                default:
                    callback.OnNoSession();
                    break;
            }
        }
    }

}
