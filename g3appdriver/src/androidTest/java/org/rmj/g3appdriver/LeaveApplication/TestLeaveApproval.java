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
public class TestLeaveApproval {
    private static final String TAG = TestLeaveApproval.class.getSimpleName();

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
    public void test02DownloadLeave() throws Exception{
        if(!poSys.DownloadLeaveApplications()){
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
        REmployeeLeave.LeaveApprovalInfo loDetail = new REmployeeLeave.LeaveApprovalInfo();
        loDetail.setTransNox("MX0122000041");
        loDetail.setTranStat("3");
        loDetail.setAppldFrx("2022-09-20");
        loDetail.setAppldTox("2022-09-20");
        loDetail.setWithOPay("0");
        loDetail.setWithPayx("1");
        loDetail.setApproved("M00119001131");
        String lsTransNox = poSys.SaveLeaveApproval(loDetail);
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
        if(!poSys.PostLeaveApproval(transno)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
