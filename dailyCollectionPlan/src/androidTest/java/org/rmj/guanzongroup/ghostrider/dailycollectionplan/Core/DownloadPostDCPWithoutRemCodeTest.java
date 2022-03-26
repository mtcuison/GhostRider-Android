package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core;

import static org.junit.Assert.*;

import android.app.Application;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.SQLUtil;
import org.rmj.g3appdriver.utils.SecUtil;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class DownloadPostDCPWithoutRemCodeTest {

    private static final String LOCAL_LOGIN = "http://192.168.10.141/security/mlogin.php";

    private Application instance;

    private AppConfigPreference poConfig;
    private SessionManager poSession;
    private HttpHeaders poHeaders;
    private DcpManager poDcp;

    private REmployee poUser;
    private Telephony poTlphny;

    private boolean isSuccess = false;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poConfig = AppConfigPreference.getInstance(instance);
        poHeaders = HttpHeaders.getInstance(instance);
        poSession = new SessionManager(instance);
        poDcp = new DcpManager(instance);
        poUser = new REmployee(instance);
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
                poUser.insertEmployee(employeeInfo);

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

                poSession.initUserSession(lsUserIDx, lsClientx, lsLogNoxx, lsBranchx, lsBranchN, lsDeptIDx, lsEmpIDxx, lsPostIDx, lsEmpLvlx, "1");
            } else {
                JSONObject loError = loResponse.getJSONObject("error");
                String lsMessage = loError.getString("message");
                isSuccess = false;
            }
        }
    }

    @Test
    public void test02DownloadDCP() throws Exception{
        poDcp.ImportDcpMaster("M00117001404", "2022-02-22", new DcpManager.OnActionCallback() {
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

//    @Test
    public void test03ValidatePostCollection() throws Exception{
        poDcp.ValidatePostCollection(new DcpManager.OnValidateCallback() {
            @Override
            public void OnSuccess(boolean hasNV, String args) {
                if(hasNV){
                    isSuccess = true;
                } else {
                    isSuccess = false;
                }
            }

            @Override
            public void OnFailed(String message) {
                isSuccess = false;
            }
        });
        assertTrue(isSuccess);
    }

//    @Test
    public void test04PostLRDCPCollectionWithoutRemCode() throws Exception{
        poDcp.PostLRDCPCollection(new DcpManager.OnActionCallback() {
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

//    @Test
    public void test05PostDcpMaster() throws Exception{
        poDcp.PostDcpMaster(new DcpManager.OnActionCallback() {
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