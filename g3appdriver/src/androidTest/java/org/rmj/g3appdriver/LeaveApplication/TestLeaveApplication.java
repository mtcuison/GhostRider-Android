package org.rmj.g3appdriver.LeaveApplication;


import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;
import org.rmj.g3appdriver.etc.AppConfigPreference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestLeaveApplication {
    private static final String TAG = TestLeaveApplication.class.getSimpleName();

    private Application instance;

    private REmployeeLeave poSys;
    private REmployee poUser;

    private boolean isSuccess = false;
    private static String transno, message;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poUser = new REmployee(instance);
        poSys = new REmployeeLeave(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01LoginAccount() {
        if(!poUser.AuthenticateUser(new REmployee.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"))){
            message = poUser.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test02SaveLeave() {
        REmployeeLeave.LeaveApplication loDetail = new REmployeeLeave.LeaveApplication();
        loDetail.setBranchNme("GMC Dagupan");
        loDetail.setLeaveType("0");
        loDetail.setDateFromx("September 20, 2022");
        loDetail.setDateThrux("September 20, 2022");
        loDetail.setRemarksxx("Sample entry");
        loDetail.setEmploName("Garcia, Michael");
        loDetail.setNoOfDaysx(1);
        String lsResult = poSys.SaveLeaveApplication(loDetail);
        if(lsResult == null){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            transno = lsResult;
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test03PostLeave() {
        if(!poSys.UploadLeaveApplication(transno)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
