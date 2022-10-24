package org.rmj.g3appdriver.CreditApp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.LoanInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.CreditAppInstance;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestPersonalInfo {
    private static final String TAG = TestPersonalInfo.class.getSimpleName();
    private Application instance;

    private CreditOnlineApplication creditApp;

    private boolean isSuccess = false;
    private static String message;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(false);
        creditApp = new CreditOnlineApplication(instance);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test01ValidatePurchaseInfo() {
        isSuccess = false;
        LoanInfo loLoan = new LoanInfo();
        loLoan.setdTargetDte("2022-10-24");
        loLoan.setsAppTypex("0");
        loLoan.setsBranchCde("M001");
        loLoan.setsCustTypex("1");
        loLoan.setsDownPaymt(10000);
        loLoan.setsMonthlyAm(1500);
        loLoan.setsModelIDxx("M123131231");
        loLoan.setsBrandIDxx("123123123");
        loLoan.setsAccTermxx(12);

        if(!loLoan.isDataValid()){
            message = loLoan.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test02CreateApplication() {
        isSuccess = false;
        LoanInfo loLoan = new LoanInfo();
        loLoan.setdTargetDte("2022-10-24");
        loLoan.setsAppTypex("0");
        loLoan.setsBranchCde("M001");
        loLoan.setsCustTypex("1");
        loLoan.setsDownPaymt(10000);
        loLoan.setsMonthlyAm(1500);
        loLoan.setsModelIDxx("M123131231");
        loLoan.setsBrandIDxx("123123123");
        loLoan.setsAccTermxx(12);

        String lsResult = creditApp.CreateApplication(loLoan);

        if(lsResult == null){
            message = creditApp.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
            message = lsResult;
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test03CheckApplication() {
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Client_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getDetlInfo());
            assertNotNull(app);
        });
    }

    @Test
    public void test04SetupClientInfo() {
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Client_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getDetlInfo());
            assertNotNull(app);
        });
    }
}
