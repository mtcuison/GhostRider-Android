/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Date;
import java.util.List;

public class REmployee {
    private static final String TAG = REmployee.class.getSimpleName();

    private final Application instance;

    private final DEmployeeInfo poDao;
    private final DEmployeeRole roleDao;

    private final LiveData<EEmployeeInfo> employeeInfo;
    private final SessionManager poSession;
    private final WebApi webApi;
    private final HttpHeaders headers;
    private final AppConfigPreference poConfig;
    private final Telephony poDevID;
    
    private String message;

    public REmployee(Application application){
        this.instance = application;
        this.poDao = GGC_GriderDB.getInstance(application).EmployeeDao();
        this.roleDao = GGC_GriderDB.getInstance(application).employeeRoleDao();
        this.employeeInfo = poDao.getEmployeeInfo();
        this.poSession = new SessionManager(application);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.webApi = new WebApi(poConfig.getTestStatus());
        this.headers = HttpHeaders.getInstance(instance);
        this.poDevID = new Telephony(instance);
    }
    
    public String getMessage(){
        return message;
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return employeeInfo;
    }

    public EEmployeeInfo getUserNonLiveData() { return poDao.getEmployeeInfoNonLiveData(); }

    public String getUserAreaCode(){
        return poDao.getUserAreaCode();
    }

    public LiveData<String> getUserAreaCodeForDashboard(){
        return poDao.getUserAreaCodeForDashboard();
    }

    public void insertEmployee(EEmployeeInfo employeeInfo){
        new InsertEmployeeTask(poDao).execute(employeeInfo);
    }

    public LiveData<EEmployeeInfo> getEmployeeInfo(){
        return employeeInfo;
    }

    public LiveData<String> getUserID(){
        return poDao.getUserID();
    }

    public LiveData<String> getClientID(){
        return poDao.getClientID();
    }

    public LiveData<String> getEmployID(){
        return poDao.getEmployID();
    }

    public String getEmployeeID(){
        return poDao.getEmployeeID();
    }

    public LiveData<String> getUserBranchID(){
        return poDao.getSBranchID();
    }

    public LiveData<String> getLogNumber(){
        return poDao.getLogNumber();
    }

    public LiveData<List<EEmployeeRole>> getEmployeeRoles(){
        return roleDao.getEmployeeRoles();
    }

    public LiveData<List<EEmployeeRole>> getChildRoles(){
        return roleDao.getChildRoles();
    }

    public LiveData<DEmployeeInfo.Session> getSessionTime(){
        return poDao.getSessionTime();
    }

    public LiveData<String> getSessionDate(){
        return poDao.getSessionDate();
    }

    private static class InsertEmployeeTask extends AsyncTask<EEmployeeInfo, Void, Void>{
        private DEmployeeInfo employeeDao;

        public InsertEmployeeTask(DEmployeeInfo employeeDao){
            this.employeeDao = employeeDao;
        }

        @Override
        protected Void doInBackground(EEmployeeInfo... eEmployeeInfos) {
            employeeDao.deleteAllEmployeeInfo();
            employeeDao.SaveNewEmployeeSession(eEmployeeInfos[0]);
            return null;
        }
    }

    public void LogoutUserSession(){
        poSession.initUserLogout();
        new DeleteUserTask(instance).execute();
    }

    public static class DeleteUserTask extends AsyncTask<Void, Void, Void>{
        private DEmployeeInfo employeeDao;
        private DEmployeeRole empRoleDao;

        public DeleteUserTask(Application instance) {
            this.employeeDao =  GGC_GriderDB.getInstance(instance).EmployeeDao();
            this.empRoleDao = GGC_GriderDB.getInstance(instance).employeeRoleDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            employeeDao.deleteAllEmployeeInfo();
            empRoleDao.DeleteEmployeeRole();
            return null;
        }
    }
    
    public boolean AuthenticateUser(UserAuthInfo foVal){
        try{
            JSONObject params = new JSONObject();
            params.put("user", foVal.getEmail());
            params.put("pswd", foVal.getPassword());

            String lsResponse = WebClient.httpsPostJSon(
                    webApi.getUrlAuthEmployee(poConfig.isBackUpServer()),
                    params.toString(),
                    headers.getHeaders());
            if(lsResponse == null){
                message = "No server response.";
                return false;
            } else {
                Log.d(TAG, lsResponse);
                JSONObject loResponse = new JSONObject(lsResponse);
                String lsResult = loResponse.getString("result");
                if (lsResult.equalsIgnoreCase("success")) {
                    EEmployeeInfo employeeInfo = new EEmployeeInfo();
                    employeeInfo.setClientID(loResponse.getString("sClientID"));
                    employeeInfo.setBranchCD(loResponse.getString("sBranchCD"));
                    employeeInfo.setBranchNm(loResponse.getString("sBranchNm"));
                    employeeInfo.setLogNoxxx(loResponse.getString("sLogNoxxx"));
                    employeeInfo.setUserIDxx(loResponse.getString("sUserIDxx"));
                    employeeInfo.setEmailAdd(loResponse.getString("sEmailAdd"));
                    employeeInfo.setUserName(loResponse.getString("sUserName"));
                    employeeInfo.setUserLevl(loResponse.getString("nUserLevl"));
                    employeeInfo.setSlfieLog(loResponse.getString("cSlfieLog"));
                    employeeInfo.setDeptIDxx(loResponse.getString("sDeptIDxx"));
                    employeeInfo.setPositnID(loResponse.getString("sPositnID"));
                    employeeInfo.setEmpLevID(loResponse.getString("sEmpLevID"));
                    employeeInfo.setAllowUpd(loResponse.getString("cAllowUpd"));
                    employeeInfo.setEmployID(loResponse.getString("sEmployID"));
                    employeeInfo.setDeviceID(poDevID.getDeviceID());
                    employeeInfo.setModelIDx(Build.MODEL);
                    employeeInfo.setMobileNo(poConfig.getMobileNo());
                    employeeInfo.setLoginxxx(new AppConstants().DATE_MODIFIED);
                    employeeInfo.setSessionx(AppConstants.CURRENT_DATE);
                    poDao.SaveNewEmployeeSession(employeeInfo);

                    String lsClientx = loResponse.getString("sClientID");
                    String lsUserIDx = loResponse.getString("sUserIDxx");
                    String lsLogNoxx = loResponse.getString("sLogNoxxx");
                    String lsBranchx = loResponse.getString("sBranchCD");
                    String lsBranchN = loResponse.getString("sBranchNm");
                    String lsDeptIDx = loResponse.getString("sDeptIDxx");
                    String lsEmpIDxx = loResponse.getString("sEmployID");
                    String lsPostIDx = loResponse.getString("sPositnID");
                    String lsEmpLvlx = loResponse.getString("sEmpLevID");
                    poSession.initUserSession(lsUserIDx, lsClientx, lsLogNoxx, lsBranchx, lsBranchN, lsDeptIDx, lsEmpIDxx, lsPostIDx, lsEmpLvlx, "1");
                    message = "Login success";
                    return true;
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    Log.e(TAG, lsMessage);
                    message = lsMessage;
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public boolean GetUserAuthorizeAccess(){
        try{
            JSONObject params = new JSONObject();
            String lsResponse = WebClient.httpsPostJSon(
                    webApi.getRequestUserAccess(poConfig.isBackUpServer()),
                    params.toString(),
                    headers.getHeaders());
            if (lsResponse == null) {
                message = "No server response.";
                return false;
            } else {
                JSONObject loResponse = new JSONObject(lsResponse);
                if (loResponse.getString("result").equalsIgnoreCase("success")) {
                    JSONArray loArr = loResponse.getJSONArray("payload");
                    for(int x = 0; x < loArr.length(); x++){
                        JSONObject loJson = loArr.getJSONObject(x);
                        EEmployeeRole loRole = roleDao.GetEmployeeRole(loJson.getString("sObjectNm"));
                        if(loRole == null){
                            EEmployeeRole loDetail = new EEmployeeRole();
                            loDetail.setObjectNm(loJson.getString("sObjectNm"));
                            loDetail.setObjectDs(loJson.getString("sObjectDs"));
                            loDetail.setObjectTP(loJson.getString("cObjectTP"));
                            loDetail.setParentxx(loJson.getString("sParentxx"));
                            loDetail.setHasChild(loJson.getString("cHasChild"));
                            loDetail.setSeqnceCd(loJson.getString("sSeqnceCD"));
                            loDetail.setRecdStat(loJson.getString("cRecdStat"));
                            loDetail.setTimeStmp(loJson.getString("dTimeStmp"));
                            roleDao.InsertEmployeeRole(loDetail);
                            Log.d(TAG, "Authorize feature has been save.");
                        } else {
                            Date ldDate1 = SQLUtil.toDate(loRole.getTimeStmp(), SQLUtil.FORMAT_TIMESTAMP);
                            Date ldDate2 = SQLUtil.toDate((String) loJson.get("dTimeStmp"), SQLUtil.FORMAT_TIMESTAMP);

                            if (!ldDate1.equals(ldDate2)){
                                //create update statement
                                roleDao.updateEmployeeRole(loJson.getString("sObjectNm"),
                                        loJson.getString("cObjectTP"),
                                        loJson.getString("sObjectDs"),
                                        loJson.getString("sParentxx"),
                                        loJson.getString("cHasChild"),
                                        loJson.getString("sSeqnceCD"),
                                        loJson.getString("cRecdStat"),
                                        loJson.getString("dTimeStmp"));
                                Log.d(TAG, "Authorize feature has been updated.");
                            }
                        }
                    }
                    message = "Authorize features for user has been imported successfully.";
                    return true;
                } else {
                    JSONObject loError = loResponse.getJSONObject("error");
                    String lsMessage = loError.getString("message");
                    Log.e(TAG, lsMessage);
                    message = lsMessage;
                    return false;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return false;
        }
    }

    public static class UserAuthInfo {
        private final String Email;
        private final String Password;
        private final String MobileNo;
        private boolean needMobileNo;
        private String message;

        public UserAuthInfo(String email, String password, String mobileNo) {
            Email = email;
            Password = password;
            MobileNo = mobileNo;
        }

        public String getEmail() {
            return Email;
        }

        public String getPassword() {
            return Password;
        }

        public String getMobileNo() {
            return MobileNo;
        }

        public void setMobileValidity(boolean needMobile){
            needMobileNo = needMobile;
        }

        public String getMessage(){
            return message;
        }

        public boolean isAuthInfoValid(){
            if(!isEmailValid()){
                return false;
            }
            if(!isPasswordValid()){
                return false;
            }
            return isMobileNoValid();
        }

        private boolean isEmailValid(){
            if(Email.isEmpty()){
                message = "Please enter your email";
                return false;
            }
            return true;
        }

        private boolean isPasswordValid(){
            if(Password.isEmpty()) {
                message = "Please enter password";
                return false;
            }
            return true;
        }

        private boolean isMobileNoValid(){
            if(needMobileNo){
                if(MobileNo.isEmpty()){
                    message = "Please enter mobile no.";
                    return false;
                }
                if(MobileNo.length()!=11){
                    message = "Mobile number must be 11 characters";
                    return false;
                }
                if(!MobileNo.substring(0, 2).equalsIgnoreCase("09")){
                    message = "Mobile number must start with '09'";
                    return false;
                }
            }
            return true;
        }
    }
}
