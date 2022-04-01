package org.rmj.g3appdriver.lib.account;

import static org.junit.Assert.*;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.PET.ApplicationApproval;
import org.rmj.g3appdriver.lib.PET.PetManager;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class CreateAccountTest {
    private static final String TAG = CreateAccountTest.class.getSimpleName();

    private Application instance;
    private AccountAuthentication poAuth;

    private static boolean isSuccess = false;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poAuth = new AccountAuthentication(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01CreateAccount() {
        AccountCredentials loAccount = new AccountCredentials();
        loAccount.setLastName("Sample");
        loAccount.setFrstName("Account");
        loAccount.setMiddName("Entry");
        loAccount.setMobileNo("09123456789");
        loAccount.setEmailAdd("sampleAccountEntry@gmail.com");
        loAccount.setPassword("123456");
        loAccount.setPasswrd2("123456");
        poAuth.CreateAccount(loAccount, new AccountAuthentication.OnActionCallback() {
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