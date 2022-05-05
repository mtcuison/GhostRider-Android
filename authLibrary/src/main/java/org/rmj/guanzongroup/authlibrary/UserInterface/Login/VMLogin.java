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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeRole;
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
    private final SessionManager session;
    private final AppConfigPreference poConfig;
    private final Telephony poTlphony;

    public VMLogin(@NonNull Application application) {
        super(application);
        this.application =application;
        REmployee = new REmployee(application);
        headers = HttpHeaders.getInstance(application);
        session = new SessionManager(application);
        poConfig = AppConfigPreference.getInstance(application);
        webApi = new WebApi(poConfig.getTestStatus());
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

    public void Login(final UserAuthInfo authInfo, LoginCallback callback){
        authInfo.setMobileValidity(poConfig.getMobileNo().isEmpty());
        if(authInfo.isAuthInfoValid()) {
            JSONObject params = new JSONObject();
            try {
                params.put("user", authInfo.getEmail());
                params.put("pswd", authInfo.getPassword());
                poConfig.setMobileNo(authInfo.getMobileNo());
                new LoginTask(application, webApi, headers, session, REmployee, callback).execute(params);
            }catch (Exception e){
                e.printStackTrace();
                callback.OnFailedLoginResult("An error occur while authentication. " + e.getMessage() + " Please take a screenshot and report to MIS");
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
        private final ConnectionUtil poConn;
        private final AppConfigPreference poConfig;
        private final REmployeeRole poRole;
        private final Telephony poDevID;

        public LoginTask(Application application, WebApi webApi, HttpHeaders headers, SessionManager sessionManager, REmployee REmployee, LoginCallback callback){
            this.webApi = webApi;
            this.headers = headers;
            this.REmployee = REmployee;
            this.callback = callback;
            this.sessionManager = sessionManager;
            this.poDevID = new Telephony(application);
            this.poConn = new ConnectionUtil(application);
            this.poConfig = AppConfigPreference.getInstance(application);
            this.poRole = new REmployeeRole(application);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnAuthenticationLoad("GhostRider Android", "Authenticating to ghostrider app. Please wait...");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(final JSONObject... authInfo) {
            String response;
            try {
                if(poConfig.isAgreedOnTermsAndConditions()) {
                    if(poConn.isDeviceConnected()) {
                        String lsResponse = WebClient.httpsPostJSon(webApi.getUrlAuthEmployee(), authInfo[0].toString(), headers.getHeaders());
                        if(lsResponse == null){
                            response = AppConstants.SERVER_NO_RESPONSE();
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse);
                            String lsResult = loResponse.getString("result");
                            if (lsResult.equalsIgnoreCase("success")) {
                                saveAuthInfo(loResponse);
                                response = lsResponse;
                                Thread.sleep(1000);

                                JSONObject loMenu = new JSONObject();
                                // M-> MENU
                                // B-> Button
//                            loMenu.put("obj_type", "M");
                                String lsRole = WebClient.httpsPostJSon(webApi.getRequestUserAccess(), loMenu.toString(), headers.getHeaders());
                                if (lsRole == null) {
                                    response = AppConstants.LOCAL_EXCEPTION_ERROR("Server no response while downloading authorize features.");
                                } else {
                                    loResponse = new JSONObject(lsRole);
                                    if (loResponse.getString("result").equalsIgnoreCase("success")) {
                                        JSONArray loArr = loResponse.getJSONArray("payload");
                                        if(!poRole.SaveEmployeeRole(loArr)){
                                            response = AppConstants.LOCAL_EXCEPTION_ERROR("Unable to same employee authorize features");
                                        } else {
                                            response = AppConstants.APPROVAL_CODE_GENERATED("User authorize.");
                                        }
                                    } else {
                                        response = lsRole;
                                    }
                                }
                            } else {
                                response = lsResponse;
                            }
                        }
                    } else {
                        response = AppConstants.NO_INTERNET();
                    }
                } else {
                    response = AppConstants.LOCAL_EXCEPTION_ERROR("Please agree on terms and conditions before login.");
                }
            } catch (IOException e) {
                e.printStackTrace();
                response = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage() + "\n Please check your internet.");
            } catch (Exception e) {
                e.printStackTrace();
                response = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
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
            employeeInfo.setSlfieLog(jsonInfo.getString("cSlfieLog"));
            employeeInfo.setDeptIDxx(jsonInfo.getString("sDeptIDxx"));
            employeeInfo.setPositnID(jsonInfo.getString("sPositnID"));
            employeeInfo.setEmpLevID(jsonInfo.getString("sEmpLevID"));
            employeeInfo.setAllowUpd(jsonInfo.getString("cAllowUpd"));
            employeeInfo.setEmployID(jsonInfo.getString("sEmployID"));
            employeeInfo.setDeviceID(poDevID.getDeviceID());
            employeeInfo.setModelIDx(Build.MODEL);
            employeeInfo.setMobileNo(poConfig.getMobileNo());
            employeeInfo.setLoginxxx(new AppConstants().DATE_MODIFIED);
            employeeInfo.setSessionx(AppConstants.CURRENT_DATE);
            REmployee.insertEmployee(employeeInfo);

            String lsClientx = jsonInfo.getString("sClientID");
            String lsUserIDx = jsonInfo.getString("sUserIDxx");
            String lsLogNoxx = jsonInfo.getString("sLogNoxxx");
            String lsBranchx = jsonInfo.getString("sBranchCD");
            String lsBranchN = jsonInfo.getString("sBranchNm");
            String lsDeptIDx = jsonInfo.getString("sDeptIDxx");
            String lsEmpIDxx = jsonInfo.getString("sEmployID");
            String lsPostIDx = jsonInfo.getString("sPositnID");
            String lsEmpLvlx = jsonInfo.getString("sEmpLevID");
            sessionManager.initUserSession(lsUserIDx, lsClientx, lsLogNoxx, lsBranchx, lsBranchN, lsDeptIDx, lsEmpIDxx, lsPostIDx, lsEmpLvlx, "1");
        }
    }
}