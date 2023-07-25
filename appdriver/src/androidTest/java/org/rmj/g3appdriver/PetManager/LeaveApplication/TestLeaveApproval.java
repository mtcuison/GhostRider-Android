package org.rmj.g3appdriver.PetManager.LeaveApplication;


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
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeLeave;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.pojo.LeaveApprovalInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestLeaveApproval {
    private static final String TAG = TestLeaveApproval.class.getSimpleName();

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
    public void test02DownloadLeave() throws Exception{
        if(!poSys.ImportApplications()){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
        Thread.sleep(1000);
    }

    @Test
    public void test03SaveApproval() {
        LeaveApprovalInfo loDetail = new LeaveApprovalInfo();
        loDetail.setTransNox("MX0122000041");
        loDetail.setTranStat("3");
        loDetail.setAppldFrx("2022-09-20");
        loDetail.setAppldTox("2022-09-20");
        loDetail.setWithOPay(0);
        loDetail.setWithPayx(1);
        loDetail.setApproved("M00119001131");
        String lsTransNox = poSys.SaveApproval(loDetail);
        if(lsTransNox == null){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            transno = lsTransNox;
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test04PostLeaveApproval() {
        if(!poSys.UploadApproval(transno)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
