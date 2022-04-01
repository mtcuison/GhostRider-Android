package org.rmj.g3appdriver.lib.PET;

import static org.junit.Assert.assertTrue;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.account.AccountAuthentication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ApplicationApproval {
    private static final String TAG = ApplicationApproval.class.getSimpleName();

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
    public void test02ApproveLeave() throws Exception{
        poPet.SaveLeaveApproval("MX0122000019", "1", new PetManager.OnActionCallback() {
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
    public void test03ApproveBusinessTrip() throws Exception{
        poPet.SaveOBApproval("MX0122000004", "1", new PetManager.OnActionCallback() {
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
    public void test04PostLeaveApproval() throws Exception{
        poPet.PostLeaveApproval("MX0122000019", new PetManager.OnActionCallback() {
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
    public void test04PostOBApproval() throws Exception{
        poPet.PostLeaveApproval("MX0122000004", new PetManager.OnActionCallback() {
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
}
