package org.rmj.g3appdriver.Itinerary;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.room.Entities.EItinerary;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.Itinerary.Obj.EmployeeItinerary;

import java.util.ArrayList;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestItinerary {
    private static final String TAG = TestItinerary.class.getSimpleName();

    private Application instance;

    private EmployeeItinerary poSys;
    private EmployeeMaster poUser;

    private boolean isSuccess = false;
    private static String transno, message;
    private static List<JSONObject> poList = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        Log.d(TAG, "Test started!");
        instance = ApplicationProvider.getApplicationContext();
        poUser = new EmployeeMaster(instance);
        poSys = new EmployeeItinerary(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01LoginAccount() {
        if(poUser.IsSessionValid()){
            isSuccess = true;
            Log.e(TAG, "Session valid");
        } else if(!poUser.AuthenticateUser(new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"))){
            message = poUser.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test02CreateItinerary() {
        EmployeeItinerary.ItineraryEntry loEntry = new EmployeeItinerary.ItineraryEntry();
        loEntry.setTimeStrt("9:00:00");
        loEntry.setTimeEndx("10:00:00");
        loEntry.setLocation("Sample location");
        loEntry.setRemarksx("Sample remarks");
        loEntry.setTransact(AppConstants.CURRENT_DATE());

        transno = poSys.SaveItinerary(loEntry);
        if(transno == null){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test03UploadItinerary() throws Exception{
        if(!poSys.UploadItinerary(transno)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
        Thread.sleep(1000);
    }

    @Test
    public void test04DownloadItinerary() throws Exception {
        String lsDateFrom = "2022-08-11";
        String lsDateThru = "2022-08-31";
        if(!poSys.DownloadItinerary(lsDateFrom, lsDateThru)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
        Thread.sleep(1000);
    }

    @Test
    public void test05DownloadItineraryUsers() throws Exception {
        poList = poSys.GetEmployeeList();
        if(poList == null){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            for(int x = 0; x < poList.size(); x++){
                Log.d(TAG, poList.get(x).getString("sUserName"));
            }
            isSuccess = true;
        }
        assertTrue(isSuccess);
        Thread.sleep(1000);
    }

    @Test
    public void test06DownloadItineraryOfUser() throws Exception{
        String lsUser = poList.get(0).getString("sEmployID");
        List<EItinerary> loList = poSys.GetItineraryListForEmployee(lsUser, "", "");
        if(loList == null){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            for(int x = 0; x < loList.size(); x++){
                Log.d(TAG, loList.get(x).getTransNox());
            }
            isSuccess = true;
        }
        Thread.sleep(1000);
        assertTrue(isSuccess);
    }

    @After
    public void tearDown() throws Exception {
        Log.d(TAG, "Test finished!");
    }
}
