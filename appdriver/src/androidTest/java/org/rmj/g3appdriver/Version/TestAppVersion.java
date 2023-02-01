package org.rmj.g3appdriver.Version;

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
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.Version.AppVersion;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestAppVersion {
    private static final String TAG = TestAppVersion.class.getSimpleName();

    private Application instance;

    private EmployeeMaster poMaster;

    private AppVersion poVersion;

    private boolean isSuccess = false;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poMaster = new EmployeeMaster(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01LoginAccount() throws Exception {
        if(poMaster.AuthenticateUser(new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"))){
            isSuccess = true;
        } else {
            Log.e(TAG, poMaster.getMessage());
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
        isSuccess = false;
    }

    @Test
    public void test02SubmitAppVersion() throws Exception {
        if(poVersion.SubmitUserAppVersion()){
            isSuccess = true;
        } else {
            Log.e(TAG, poVersion.getMessage());
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
        isSuccess = false;
    }
}
