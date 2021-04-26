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

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VMLogin extends AndroidViewModel {
    public static final String TAG =  VMLogin.class.getSimpleName();
    private final REmployee REmployee;
    private final WebApi webApi;
    private final HttpHeaders headers;
    private final ConnectionUtil conn;
    private final SessionManager session;

    public VMLogin(@NonNull Application application) {
        super(application);
        REmployee = new REmployee(application);
        webApi = new WebApi(application);
        headers = HttpHeaders.getInstance(application);
        conn = new ConnectionUtil(application);
        session = new SessionManager(application);
    }

    public void Login(final UserAuthInfo authInfo, LoginCallback callback){
        if(authInfo.isAuthInfoValid()) {
            JSONObject params = new JSONObject();
            try {
                params.put("user", authInfo.getEmail());
                params.put("pswd", authInfo.getPassword());
            }catch (Exception e){
                e.printStackTrace();
            }

            if(conn.isDeviceConnected()) {
                new LoginTask(webApi, headers, session, REmployee, callback).execute(params);
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

        public LoginTask(WebApi webApi, HttpHeaders headers, SessionManager sessionManager, REmployee REmployee, LoginCallback callback){
            this.webApi = webApi;
            this.headers = headers;
            this.REmployee = REmployee;
            this.callback = callback;
            this.sessionManager = sessionManager;
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
                Log.e(TAG, response);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loResponse = new JSONObject(s);
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