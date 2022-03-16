package org.rmj.guanzongroup.ghostrider.ahmonitoring.Core;

import static org.junit.Assert.*;

import android.app.Application;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebClient;

public class SelfieLogTest {

    private static final String LOCAL_LOGIN = "http://192.168.10.141/security/mlogin.php";

    private Application instance;

    private boolean isSuccess = false;

    private SelfieLog poSelfie;
    private SessionManager poUser;
    private AppConfigPreference poConfig;
    private HttpHeaders poHeaders;
    private REmployee poEmp;
    private Telephony poTlphny;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSelfie = new SelfieLog(instance);
        poUser = new SessionManager(instance);
        poConfig = AppConfigPreference.getInstance(instance);
        poHeaders = HttpHeaders.getInstance(instance);
        poEmp = new REmployee(instance);
        poTlphny = new Telephony(instance);
        poConfig.setTestCase(true);
        poConfig.setMobileNo("09171870011");
        poConfig.setTemp_ProductID("gRider");
        poConfig.setAppToken("f7qNSw8TRPWHSCga0g8YFF:APA91bG3i_lBPPWv9bbRasNzRH1XX1y0vzp6Ct8S_a-yMPDvSmud8FEVPMr26zZtBPHq2CmaIw9Rx0MZmf3sbuK44q3vQemUBoPPS4Meybw8pnTpcs3p0VbiTuoLHJtdncC6BgirJxt3");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test01LoginAccount() throws Exception{
        //Login Account for Authorization
        JSONObject params = new JSONObject();
        params.put("user", "mikegarcia8748@gmail.com");
        params.put("pswd", "123456");
        String lsResponse = WebClient.httpPostJSon(LOCAL_LOGIN,
                params.toString(), poHeaders.getHeaders());
        if(lsResponse == null){
            isSuccess = false;
        } else {
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
                employeeInfo.setDeptIDxx(loResponse.getString("sDeptIDxx"));
                employeeInfo.setPositnID(loResponse.getString("sPositnID"));
                employeeInfo.setEmpLevID(loResponse.getString("sEmpLevID"));
                employeeInfo.setAllowUpd(loResponse.getString("cAllowUpd"));
                employeeInfo.setEmployID(loResponse.getString("sEmployID"));
                employeeInfo.setDeviceID(poTlphny.getDeviceID());
                employeeInfo.setModelIDx(Build.MODEL);
                employeeInfo.setMobileNo(poConfig.getMobileNo());
                employeeInfo.setLoginxxx(new AppConstants().DATE_MODIFIED);
                employeeInfo.setSessionx(AppConstants.CURRENT_DATE);
                poEmp.insertEmployee(employeeInfo);

                String lsClientx = loResponse.getString("sClientID");
                String lsUserIDx = loResponse.getString("sUserIDxx");
                String lsLogNoxx = loResponse.getString("sLogNoxxx");
                String lsBranchx = loResponse.getString("sBranchCD");
                String lsBranchN = loResponse.getString("sBranchNm");
                String lsDeptIDx = loResponse.getString("sDeptIDxx");
                String lsEmpIDxx = loResponse.getString("sEmployID");
                String lsPostIDx = loResponse.getString("sPositnID");
                String lsEmpLvlx = loResponse.getString("sEmpLevID");
                isSuccess = true;

                poUser.initUserSession(lsUserIDx, lsClientx, lsLogNoxx, lsBranchx, lsBranchN, lsDeptIDx, lsEmpIDxx, lsPostIDx, lsEmpLvlx, "1");
            } else {
                JSONObject loError = loResponse.getJSONObject("error");
                String lsMessage = loError.getString("message");
                isSuccess = false;
            }
        }
    }

    @Test
    public void test02SaveSelfieLog() throws Exception{
        EImageInfo loImage = new EImageInfo();
        loImage.setTransNox("MX0012123431");
        loImage.setFileCode("0021");
        loImage.setSourceNo(poUser.getClientId());
        loImage.setDtlSrcNo(poUser.getUserID());
        loImage.setSourceCD("LOGa");
        loImage.setImageNme("sample");
        loImage.setFileLoct("sample");
        loImage.setLatitude("120.00");
        loImage.setLongitud("10.00");
        loImage.setMD5Hashx("sample");
        loImage.setCaptured(new AppConstants().DATE_MODIFIED);
        poSelfie.setImageInfo(loImage);

        ELog_Selfie loSelfie = new ELog_Selfie();
        loSelfie.setTransNox("MX0012123431");
        loSelfie.setBranchCd(poUser.getBranchCode());
        loSelfie.setEmployID(poUser.getEmployeeID());
        loSelfie.setLogTimex(new AppConstants().DATE_MODIFIED);
        loSelfie.setLatitude("120.00");
        loSelfie.setLongitud("10.00");
        loSelfie.setSendStat("0");

        poSelfie.setSelfieLogInfo(loSelfie);

        poSelfie.PostSelfieInfo(new SelfieLog.OnActionCallback() {
            @Override
            public void OnSuccess(String args) {
                isSuccess = true;
            }

            @Override
            public void OnFailed(String message) {
                isSuccess = false;
            }
        });
        assertTrue(isSuccess);
    }

    @Test
    public void test03RequestInventory() throws Exception{
        poSelfie.RequestInventoryStocks("", new SelfieLog.OnActionCallback() {
            @Override
            public void OnSuccess(String args) {
                isSuccess = true;
            }

            @Override
            public void OnFailed(String message) {
                isSuccess = false;
            }
        });
        assertTrue(isSuccess);
    }
}