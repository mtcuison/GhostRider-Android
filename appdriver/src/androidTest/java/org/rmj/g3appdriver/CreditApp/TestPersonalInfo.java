package org.rmj.g3appdriver.CreditApp;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestPersonalInfo {
    private static final String TAG = TestPersonalInfo.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }

    @After
    public void tearDown() throws Exception {
        Log.d(TAG, "Test finished");
    }

    @Test
    public void test01CreatedApp() {

    }

    @Test
    public void test02PersonalInfo() {
//        CreditApp loApp = CreditOnlineApplication.get
    }
}
