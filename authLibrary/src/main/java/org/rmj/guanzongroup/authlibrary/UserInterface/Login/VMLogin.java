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
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.io.IOException;

public class VMLogin extends AndroidViewModel {
    public static final String TAG =  VMLogin.class.getSimpleName();
    private final Application application;
    private final REmployee REmployee;
    private final WebApi webApi;
    private final HttpHeaders headers;
    private final ConnectionUtil conn;
    private final SessionManager session;
    private final AppConfigPreference poConfig;
    private final Telephony poTlphony;

    public VMLogin(@NonNull Application application) {
        super(application);
        this.application =application;
        REmployee = new REmployee(application);
        webApi = new WebApi(application);
        headers = HttpHeaders.getInstance(application);
        conn = new ConnectionUtil(application);
        session = new SessionManager(application);
        poConfig = AppConfigPreference.getInstance(application);
        poTlphony = new Telephony(application);
    }

    @SuppressLint("NewApi")
    public String getMobileNo(){
        return poTlphony.getMobilNumbers();
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

    public void Login(final UserAuthInfo authInfo, LoginCallback callback){
        authInfo.setMobileValidity(poConfig.getMobileNo().isEmpty());
        if(authInfo.isAuthInfoValid()) {
            if(poConfig.getMobileNo().isEmpty()) {
                poConfig.setMobileNo(authInfo.getMobileNo());
            }
            JSONObject params = new JSONObject();
            try {
                params.put("user", authInfo.getEmail());
                params.put("pswd", authInfo.getPassword());
            }catch (Exception e){
                e.printStackTrace();
            }

            if(conn.isDeviceConnected()) {
                new LoginTask(application, webApi, headers, session, REmployee, callback).execute(params);
            } else {
                callback.OnFailedLoginResult("Unable to connect. Please check your internet connection.");
            }
        } else {
            callback.OnFailedLoginResult(authInfo.getMessage());
        }
    }

    private static class LoginTask extends AsyncTask<JSONObject, Void, String>{
        private final WebApi webApi;
        private final HttpHeaders headers;
        private final LoginCallback callback;
        private final REmployee REmployee;
        private final SessionManager sessionManager;
        private final AppConfigPreference poConfig;

        public LoginTask(Application application, WebApi webApi, HttpHeaders headers, SessionManager sessionManager, REmployee REmployee, LoginCallback callback){
            this.webApi = webApi;
            this.headers = headers;
            this.REmployee = REmployee;
            this.callback = callback;
            this.sessionManager = sessionManager;
            this.poConfig = AppConfigPreference.getInstance(application);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnAuthenticationLoad("GhostRider Android", "Authenticating to ghostrider app. Please wait...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(final JSONObject... authInfo) {
            String response = "";
            try {
                response = WebClient.httpsPostJSon(webApi.URL_AUTH_EMPLOYEE(), authInfo[0].toString(), headers.getHeaders());
                if(!poConfig.isAgreedOnTermsAndConditions()) {
                    JSONObject loJsonx = new JSONObject();
                    JSONObject loError = new JSONObject();
                    loError.put("message", "Please agree on terms and conditions before login.");
                    loJsonx.put("result","error");
                    loJsonx.put("error", loError);

                    response = loJsonx.toString();
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                response = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage() + "\n Please check your internet.");
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loResponse = new JSONObject(s);
                Log.e("JSONFormat", loResponse.toString());
                String lsResult = loResponse.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    saveAuthInfo(loResponse);
                    String lsClientx = loResponse.getString("sClientID");
                    String lsUserIDx = loResponse.getString("sUserIDxx");
                    String lsLogNoxx = loResponse.getString("sLogNoxxx");
                    String lsBranchx = loResponse.getString("sBranchCD");
                    String lsDeptIDx = loResponse.getString("sDeptIDxx");
                    String lsEmpIDxx = loResponse.getString("sEmpLevID");
                    String lsPostIDx = loResponse.getString("sPositnID");
                    sessionManager.initUserSession(lsUserIDx, lsClientx, lsLogNoxx, lsBranchx, lsDeptIDx, lsEmpIDxx, lsPostIDx);
                    callback.OnSuccessLoginResult();
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    callback.OnFailedLoginResult(lsMessage);
                    Log.e(TAG, s);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            this.cancel(false);
        }

        void saveAuthInfo(JSONObject jsonInfo) throws JSONException{
            EEmployeeInfo employeeInfo = new EEmployeeInfo();
            employeeInfo.setClientID(jsonInfo.getString("sClientID"));
            employeeInfo.setBranchCD(jsonInfo.getString("sBranchCD"));
            employeeInfo.setBranchNm(jsonInfo.getString("sBranchNm"));
            employeeInfo.setLogNoxxx(jsonInfo.getString("sLogNoxxx"));
            employeeInfo.setUserIDxx(jsonInfo.getString("sUserIDxx"));
            employeeInfo.setEmailAdd(jsonInfo.getString("sEmailAdd"));
            employeeInfo.setUserName(jsonInfo.getString("sUserName"));
            employeeInfo.setUserLevl(jsonInfo.getString("nUserLevl"));
            employeeInfo.setDeptIDxx(jsonInfo.getString("sDeptIDxx"));
            employeeInfo.setPositnID(jsonInfo.getString("sPositnID"));
            employeeInfo.setEmpLevID(jsonInfo.getString("sEmpLevID"));
            employeeInfo.setAllowUpd(jsonInfo.getString("cAllowUpd"));
            employeeInfo.setEmployID(jsonInfo.getString("sEmployID"));
            employeeInfo.setLoginxxx(AppConstants.DATE_MODIFIED);
            employeeInfo.setSessionx(AppConstants.CURRENT_DATE);
            REmployee.insertEmployee(employeeInfo);
        }
    }
}