package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONObject;
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
import org.rmj.g3appdriver.dev.Device.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.WebClient;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class NewLocationTrackingApi {
    private static final String TAG = NewLocationTrackingApi.class.getSimpleName();
    private static final String LOCAL_LOGIN = "http://192.168.10.141/security/mlogin.php";
    private static final String LOCAL_COORDINATES_TRACKER = "http://192.168.10.141/integsys/dcp/dcp_coordinates_receiver.php";


    private Application instance;

    private AppConfigPreference poConfig;
    private SessionManager poSession;
    private HttpHeaders poHeaders;
    private DcpManager poDcp;

    private REmployee poUser;
    private Telephony poTlphny;

    private boolean isSuccess = false;

    @Before
    public void setup() throws Exception{
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
    public void test02SubmitLocations() throws Exception{
        List<Location> loLocations = new ArrayList<>();
        loLocations.add(new Location("2022-10-25 15:46:49", "120.2915605", "14.8457769"));
        loLocations.add(new Location("2022-10-25 14:28:12", "120.3502166", "14.8453051"));
        loLocations.add(new Location("2022-10-25 14:22:35", "120.3502166", "14.8453051"));
        loLocations.add(new Location("2022-10-25 14:17:31", "120.3347221", "14.8491802"));

        JSONObject loJson = new JSONObject();
        JSONArray laDetail = new JSONArray();
        for(int x= 0; x < loLocations.size(); x++){
            JSONObject loDetail = new JSONObject();
            loDetail.put("dTransact", loLocations.get(x).dTransact);
            loDetail.put("nLatitude", loLocations.get(x).nLatitude);
            loDetail.put("nLongitud", loLocations.get(x).nLngitude);
            laDetail.put(loDetail);
        }
        loJson.put("detail", laDetail);

        String lsResponse = WebClient.httpPostJSon(LOCAL_COORDINATES_TRACKER,
                loJson.toString(), poHeaders.getHeaders());
        if(lsResponse == null){
            isSuccess = false;
        } else {
            isSuccess = true;
            Log.d(TAG, lsResponse);
            JSONObject loResponse = new JSONObject(lsResponse);
        }

        assertTrue(isSuccess);
    }

    class Location{
        public String dTransact;
        public String nLatitude;
        public String nLngitude;

        public Location(String dTransact, String nLatitude, String nLngitude) {
            this.dTransact = dTransact;
            this.nLatitude = nLatitude;
            this.nLngitude = nLngitude;
        }
    }
}
