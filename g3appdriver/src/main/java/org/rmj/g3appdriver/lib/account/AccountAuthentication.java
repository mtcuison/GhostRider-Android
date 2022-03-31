package org.rmj.g3appdriver.lib.account;

import android.app.Application;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;

public class AccountAuthentication {
    private static final String TAG = AccountAuthentication.class.getSimpleName();

    private final Application instance;

    public interface OnActionCallback{
        void OnSuccess(String message);
        void OnFailed(String message);
    }

    public AccountAuthentication(Application application) {
        this.instance = application;
    }

    public void LoginAccount(String fsEmail, String fsPassword, String fsMobileNo, OnActionCallback callback){
        try{
            WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());

            if(fsEmail.trim().isEmpty()){
                callback.OnFailed("Please enter email.");
            } else if(fsPassword.trim().isEmpty()){
                callback.OnFailed("Please enter password.");
            } else if(fsMobileNo.trim().isEmpty()){
                callback.OnFailed("Please enter mobile no.");
            } else {
                AppConfigPreference.getInstance(instance).setMobileNo(fsMobileNo);
                JSONObject params = new JSONObject();
                params.put("user", fsEmail);
                params.put("pswd", fsPassword);

                String lsResponse = WebClient.sendRequest(loApi.getUrlAuthEmployee(),
                        params.toString(),
                        HttpHeaders.getInstance(instance).getHeaders());

                if(AppConfigPreference.getInstance(instance).getTestStatus()){
                    Log.d(TAG, loApi.getUrlAuthEmployee());
                }

                if(lsResponse == null){
                    Log.d(TAG, "Account authentication error. Message : Server no Response.");
                    callback.OnFailed("Unable to login account. Server no Response.");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if(!lsResult.equalsIgnoreCase("success")){
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        Log.d(TAG, "Account authentication error. Message : " + lsMessage);
                        callback.OnFailed(lsMessage);
                    } else {
                        SessionManager loSession = new SessionManager(instance);
                        EEmployeeInfo employeeInfo = new EEmployeeInfo();
                        employeeInfo.setClientID(loResponse.getString("sClientID"));
                        employeeInfo.setBranchCD(loResponse.getString("sBranchCD"));
                        employeeInfo.setBranchNm(loResponse.getString("sBranchNm"));
                        employeeInfo.setLogNoxxx(loResponse.getString("sLogNoxxx"));
                        employeeInfo.setUserIDxx(loResponse.getString("sUserIDxx"));
                        employeeInfo.setEmailAdd(loResponse.getString("sEmailAdd"));
                        employeeInfo.setUserName(loResponse.getString("sUserName"));
                        employeeInfo.setUserLevl(loResponse.getString("nUserLevl"));
                        employeeInfo.setDeptIDxx(loResponse.getString("sDeptIDxx"));
                        employeeInfo.setPositnID(loResponse.getString("sPositnID"));
                        employeeInfo.setEmpLevID(loResponse.getString("sEmpLevID"));
                        employeeInfo.setAllowUpd(loResponse.getString("cAllowUpd"));
                        employeeInfo.setEmployID(loResponse.getString("sEmployID"));
                        employeeInfo.setDeviceID(new Telephony(instance).getDeviceID());
                        employeeInfo.setModelIDx(Build.MODEL);
                        employeeInfo.setMobileNo(AppConfigPreference.getInstance(instance).getMobileNo());
                        employeeInfo.setLoginxxx(new AppConstants().DATE_MODIFIED);
                        employeeInfo.setSessionx(AppConstants.CURRENT_DATE);
                        new REmployee(instance).insertEmployee(employeeInfo);
                        Log.d(TAG, "User account info has been save to local database.");

                        String lsClientx = loResponse.getString("sClientID");
                        String lsUserIDx = loResponse.getString("sUserIDxx");
                        String lsLogNoxx = loResponse.getString("sLogNoxxx");
                        String lsBranchx = loResponse.getString("sBranchCD");
                        String lsBranchN = loResponse.getString("sBranchNm");
                        String lsDeptIDx = loResponse.getString("sDeptIDxx");
                        String lsEmpIDxx = loResponse.getString("sEmployID");
                        String lsPostIDx = loResponse.getString("sPositnID");
                        String lsEmpLvlx = loResponse.getString("sEmpLevID");
                        loSession.initUserSession(lsUserIDx, lsClientx, lsLogNoxx, lsBranchx, lsBranchN, lsDeptIDx, lsEmpIDxx, lsPostIDx, lsEmpLvlx, "1");
                        callback.OnSuccess("Login success");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }

    public void CreateAccount(AccountCredentials foCredentials, OnActionCallback callback){
        try {
            if (!foCredentials.isDataValid()) {
                callback.OnFailed(foCredentials.getMessage());
            } else {
                WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());
                String lsResponse = WebClient.sendRequest(loApi.getUrlCreateAccount(),
                        foCredentials.getParameters(),
                        HttpHeaders.getInstance(instance).getHeaders());

                if(lsResponse == null){
                    Log.d(TAG, "Create account error. Message : Server no Response.");
                    callback.OnFailed("Unable to create account. Server no Response.");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if(!lsResult.equalsIgnoreCase("success")){
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        Log.d(TAG, "Create account error. Message : " + lsMessage);
                        callback.OnFailed(lsMessage);
                    } else {
                        callback.OnSuccess("A verification email has been sent to your email account. Please check your inbox or spam folder.");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();

            callback.OnFailed(e.getMessage());
        }
    }

    public void RequestPassword(String fsEmail, OnActionCallback callback){
        try{
            if(fsEmail.trim().isEmpty()){
                callback.OnFailed("Please enter your email");
            } else if(TextUtils.isEmpty(fsEmail) &&
                    Patterns.EMAIL_ADDRESS.matcher(fsEmail).matches()){
                callback.OnFailed("Please enter valid email");
            } else {
                WebApi loApi = new WebApi(AppConfigPreference.getInstance(instance).getTestStatus());

                JSONObject params = new JSONObject();
                params.put("email", fsEmail);

                String lsResponse = WebClient.sendRequest(loApi.getUrlForgotPassword(),
                        params.toString(),
                        HttpHeaders.getInstance(instance).getHeaders());

                if(lsResponse == null){
                    Log.d(TAG, "Password retrieve error. Message : Server no Response.");
                    callback.OnFailed("Unable to create account. Server no Response.");
                } else {
                    JSONObject loResponse = new JSONObject(lsResponse);
                    String lsResult = loResponse.getString("result");
                    if(!lsResult.equalsIgnoreCase("success")){
                        JSONObject loError = loResponse.getJSONObject("error");
                        String lsMessage = loError.getString("message");
                        Log.d(TAG, "Password retrieve error. Message : " + lsMessage);
                        callback.OnFailed(lsMessage);
                    } else {
                        callback.OnSuccess("You'll be receiving an email from MIS, Please check your email account.");
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed(e.getMessage());
        }
    }
}
