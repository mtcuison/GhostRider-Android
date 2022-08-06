package org.guanzongroup.com.itinerary;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RItinerary;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebClient;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestCreateItinerary {
    private static final String TAG = TestCreateItinerary.class.getSimpleName();

    private static final String LOCAL_LOGIN = "http://192.168.10.141/security/mlogin.php";

    private Application instance;
    private RItinerary poSystem;
    private SessionManager poSession;
    private Telephony poTlphny;
    private AppConfigPreference poConfig;
    private REmployee poUser;

    private static String TransNox;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        AppConfigPreference loConfig = AppConfigPreference.getInstance(instance);
        loConfig.setTestCase(true);
        poSystem = new RItinerary(instance);
        poSession = new SessionManager(instance);
        poTlphny = new Telephony(instance);
        poConfig = AppConfigPreference.getInstance(instance);
        poUser = new REmployee(instance);
    }

    @Test
    public void test01LoginAccount() throws Exception{
        boolean isSuccess;
        //Login Account for Authorization
        JSONObject params = new JSONObject();
        params.put("user", "mikegarcia8748@gmail.com");
        params.put("pswd", "123456");
        String lsResponse = WebClient.httpPostJSon(LOCAL_LOGIN,
                params.toString(), HttpHeaders.getInstance(instance).getHeaders());
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
    public void test02CreateItinerary() throws Exception{
        RItinerary.Itinerary loDetail = new RItinerary.Itinerary();
        loDetail.setdTimeStrt("2022-08-06 10:53:56");
        loDetail.setdTimeEndx("2022-08-06 01:53:56");
        loDetail.setsLocation("Pedritos Tapuac");
        loDetail.setsRemarksx("Sample Entry");
        boolean isSuccess = poSystem.SaveItinerary(loDetail);
        if(isSuccess){
            TransNox = poSystem.getTransNox();
        } else {
            Log.e(TAG, poSystem.getMessage());
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test03UploadItinerary() throws Exception{
        boolean isSuccess = poSystem.UploadItinerary(TransNox);
        if(isSuccess){
            Log.d(TAG, "Uploaded successfully");
        } else {
            Log.e(TAG, poSystem.getMessage());
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test04UploadItinerary() throws Exception{
        boolean isSuccess = poSystem.DownloadItinerary();
        if(isSuccess){
            Log.d(TAG, "Uploaded successfully");
        } else {
            Log.e(TAG, poSystem.getMessage());
        }
        assertTrue(isSuccess);
    }


    @Test
    public void test05UploadItinerary() throws Exception{
        poSystem.GetItineraryList().observeForever(eItineraries -> {
           for(int x = 0; x < eItineraries.size(); x++){
               Log.d(TAG, eItineraries.get(x).getTransNox());
           }
        });
    }
}