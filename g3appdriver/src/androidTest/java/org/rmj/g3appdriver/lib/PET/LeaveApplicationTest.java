package org.rmj.g3appdriver.lib.PET;

import static org.junit.Assert.*;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.account.AccountAuthentication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class LeaveApplicationTest {
    private static final String TAG = LeaveApplicationTest.class.getSimpleName();

    private Application instance;
    private AccountAuthentication poAuth;
    private PetManager poPet;

    private static boolean isSuccess = false;
    private static String psTransNo;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poAuth = new AccountAuthentication(instance);
        poPet = new PetManager(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test01LoginAccount() throws Exception{
        poAuth.LoginAccount("mikegarcia8748@gmail.com", "123456", "09171870011", new AccountAuthentication.OnActionCallback() {
            @Override
            public void OnSuccess(String message) {
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
    public void test02ApplyLeave() throws Exception{
        EEmployeeLeave loLeave = new EEmployeeLeave();
        loLeave.setDateFrom("2022-03-31");
        loLeave.setDateThru("2022-03-31");
        loLeave.setNoDaysxx("1");
        loLeave.setPurposex("Sample");
        loLeave.setEqualHrs("8");
        loLeave.setLeaveTyp("1");

        poPet.SaveLeaveApplication(loLeave, new PetManager.OnActionCallback() {
            @Override
            public void OnSuccess(String message) {
                psTransNo = message;
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
    public void test02PostLeaveApplication() throws Exception{
        poPet.PostLeaveApplication(psTransNo, new PetManager.OnActionCallback() {
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