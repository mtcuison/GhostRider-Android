package org.rmj.g3appdriver.DCP;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.ImportParams;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestImportPostDcp {
    private static final String TAG = TestImportPostDcp.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(false);
    }

    @Test
    public void test00LoginUser() {
        EmployeeMaster loSys = new EmployeeMaster(instance);
        if(!loSys.AuthenticateUser(
                new EmployeeMaster.UserAuthInfo(
                        "mikegarcia8748@gmail.com",
                        "123456",
                        "09171870011"))){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test01DownloadDCP() {
        LRDcp loSys = new LRDcp(instance);
        if(!loSys.DownloadCollection(
                new ImportParams(
                        "M00119001584",
                        "2022-10-03",
                        "1"))){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

//    @Test
    public void test01PostDcp() {
        LRDcp loSys = new LRDcp(instance);
        String lsTransNo = loSys.PostCollection("Sample Remarksx");
        if(lsTransNo == null){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else if(!loSys.PostDcpMaster(lsTransNo)){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
