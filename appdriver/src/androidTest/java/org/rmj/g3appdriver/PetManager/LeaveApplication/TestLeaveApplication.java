package org.rmj.g3appdriver.PetManager.LeaveApplication;


import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeLeave;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.pojo.LeaveApplication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestLeaveApplication {
    private static final String TAG = TestLeaveApplication.class.getSimpleName();

    private Application instance;

    private EmployeeLeave poSys;
    private EmployeeMaster poUser;

    private boolean isSuccess = false;
    private static String transno, message;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poUser = new EmployeeMaster(instance);
        poSys = new EmployeeLeave(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01LoginAccount() {
        if(!poUser.AuthenticateUser(new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"))){
            message = poUser.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test02SaveLeave() {
        LeaveApplication loDetail = new LeaveApplication();
        loDetail.setBranchNme("GMC Dagupan");
        loDetail.setLeaveType("0");
        loDetail.setDateFromx("September 20, 2022");
        loDetail.setDateThrux("September 20, 2022");
        loDetail.setRemarksxx("Sample entry");
        loDetail.setEmploName("Garcia, Michael");
        loDetail.setNoOfDaysx(1);
        String lsResult = poSys.SaveApplication(loDetail);
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
        if(!poSys.UploadApplication(transno)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
