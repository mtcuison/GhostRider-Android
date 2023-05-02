package org.rmj.g3appdriver.lib.Account.gCircle.obj;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.dev.Api.GCircleApi;
import org.rmj.g3appdriver.dev.Api.HttpHeaders;
import org.rmj.g3appdriver.dev.Api.WebClient;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.DataAccessObject.DEmployeeRole;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EEmployeeRole;
import org.rmj.g3appdriver.dev.Database.GCircle.GGC_GCircleDB;
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Account.gCircle.EmployeeSession;
import org.rmj.g3appdriver.lib.Account.pojo.UserAuthInfo;

import java.util.Date;

public class EmployeeAuthentication implements iAuth {
    private static final String TAG = EmployeeAuthentication.class.getSimpleName();

    private final Application instance;

    private final DEmployeeInfo poDao;
    private final DEmployeeRole roleDao;
    private final EmployeeSession poSession;
    private final GCircleApi poApi;
    private final HttpHeaders poHeaders;
    private final AppConfigPreference poConfig;
    private final Telephony poDevID;

    private String message;

    public EmployeeAuthentication(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GCircleDB.getInstance(instance).EmployeeDao();
        this.roleDao = GGC_GCircleDB.getInstance(instance).employeeRoleDao();
        this.poSession = new EmployeeSession(instance);
        this.poConfig = AppConfigPreference.getInstance(instance);
        this.poApi = new GCircleApi(instance);
        this.poHeaders = HttpHeaders.getInstance(instance);
        this.poDevID = new Telephony(instance);
    }

    @Override
    public int DoAction(Object args) {
        try{
            UserAuthInfo loInfo = (UserAuthInfo) args;
            if(!loInfo.isAuthInfoValid()){
                message = loInfo.getMessage();
                return 0;
            }

            if(poConfig.getMobileNo().isEmpty()){
                poConfig.setMobileNo(loInfo.getMobileNo());
                Log.d(TAG, "Mobile number has been initialized.");
            }

            JSONObject params = new JSONObject();
            params.put("user", loInfo.getEmail());
            params.put("pswd", loInfo.getPassword());

            String lsResponse = WebClient.sendRequest(
                    poApi.getUrlAuthEmployee(),
                    params.toString(),
                    poHeaders.getHeaders());
            if(lsResponse == null){
                message = "No server response.";
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                message = loError.getString("message");
                return 0;
            }

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
            employeeInfo.setEmpLevID(loResponse.getInt("sEmpLevID"));
            employeeInfo.setAllowUpd(loResponse.getString("cAllowUpd"));
            employeeInfo.setEmployID(loResponse.getString("sEmployID"));
            employeeInfo.setDeviceID(poDevID.getDeviceID());
            employeeInfo.setModelIDx(Build.MODEL);
            employeeInfo.setMobileNo(poConfig.getMobileNo());
            employeeInfo.setLoginxxx(new AppConstants().DATE_MODIFIED());
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

            Thread.sleep(1000);

            return GetUserAuthorizeAccess();
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return 0;
        }
    }

    @Override
    public String getMessage() {
        return message;
    }

    private int GetUserAuthorizeAccess(){
        try{
            JSONObject params = new JSONObject();
            String lsResponse = WebClient.sendRequest(
                    poApi.getRequestUserAccess(),
                    params.toString(),
                    poHeaders.getHeaders());
            if (lsResponse == null) {
                message = "No server response.";
                return 0;
            }

            JSONObject loResponse = new JSONObject(lsResponse);
            String lsResult = loResponse.getString("result");
            if (lsResult.equalsIgnoreCase("error")) {
                JSONObject loError = loResponse.getJSONObject("error");
                String lsMessage = loError.getString("message");
                Log.e(TAG, lsMessage);
                message = lsMessage;
                return 0;
            }

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

                        loRole.setObjectNm(loJson.getString("sObjectNm"));
                        loRole.setObjectTP(loJson.getString("cObjectTP"));
                        loRole.setObjectDs(loJson.getString("sObjectDs"));
                        loRole.setParentxx(loJson.getString("sParentxx"));
                        loRole.setHasChild(loJson.getString("cHasChild"));
                        loRole.setSeqnceCd(loJson.getString("sSeqnceCD"));
                        loRole.setRecdStat(loJson.getString("cRecdStat"));
                        loRole.setTimeStmp(loJson.getString("dTimeStmp"));
                        roleDao.UpdateRole(loRole);
                        Log.d(TAG, "Authorize feature has been updated.");
                    }
                }
            }
            message = "Authorize features for user has been imported successfully.";
            return 1;
        } catch (Exception e){
            e.printStackTrace();
            message = e.getMessage();
            return 0;
        }
    }
}
