/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */
package org.rmj.guanzongroup.authlibrary.UserInterface.Login;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;

public class VMLogin extends AndroidViewModel {
    public static final String TAG =  VMLogin.class.getSimpleName();
    private final Application application;
    private final AppConfigPreference poConfig;
    private final Telephony poTlphony;

    public VMLogin(@NonNull Application application) {
        super(application);
        this.application =application;
        poConfig = AppConfigPreference.getInstance(application);
        poTlphony = new Telephony(application);
    }

    @SuppressLint("NewApi")
    public String getMobileNo(){
        if(poConfig.getMobileNo().isEmpty()) {
            return poTlphony.getMobilNumbers();
        } else {
            return poConfig.getMobileNo();
        }
    }

    public int hasMobileNo(){
        if(poConfig.getMobileNo().equalsIgnoreCase("")) {
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public boolean isAgreed(){
        return poConfig.isAgreedOnTermsAndConditions();
    }

    public void setAgreedOnTerms(boolean isAgreed){
        poConfig.setAgreement(isAgreed);
    }

    public void Login(REmployee.UserAuthInfo authInfo, LoginCallback callback){
        new LoginTask(application, callback).execute(authInfo);
    }

    private static class LoginTask extends AsyncTask<org.rmj.g3appdriver.GRider.Database.Repositories.REmployee.UserAuthInfo, Void, Boolean>{
        private final LoginCallback callback;
        private final REmployee poUser;
        private final ConnectionUtil poConn;
        private final AppConfigPreference poConfig;

        private String message;

        public LoginTask(Application instance, LoginCallback callback){
            this.poUser = new REmployee(instance);
            this.callback = callback;
            this.poConfig = AppConfigPreference.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnAuthenticationLoad("GhostRider Android", "Authenticating to ghostrider app. Please wait...");
        }

        @Override
        protected Boolean doInBackground(REmployee.UserAuthInfo... authInfo) {
            try{
                Log.d(TAG, "Validating entries...");
                if(!authInfo[0].isAuthInfoValid()){
                    Log.d(TAG, "Mobile No: " + authInfo[0].getMobileNo());
                    poConfig.setMobileNo(authInfo[0].getMobileNo());
                    message = authInfo[0].getMessage();
                    return false;
                } else {
                    Log.d(TAG, "Checking internet...");
                    if(!poConn.isDeviceConnected()){
                        message = poConn.getMessage();
                        return false;
                    } else {
                        Log.d(TAG, "Authenticating account...");
                        if(!poUser.AuthenticateUser(authInfo[0])){
                            message = poUser.getMessage();
                            return false;
                        } else {
                            Thread.sleep(1000);
                            Log.d(TAG, "Downloading authorized features...");
                            if(!poUser.GetUserAuthorizeAccess()){
                                message = poUser.getMessage();
                                return false;
                            } else {
                                return true;
                            }
                        }
                    }
                }
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
                    callback.OnSuccessLoginResult();
                } else {
                    callback.OnFailedLoginResult(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.cancel(false);
        }
    }
}