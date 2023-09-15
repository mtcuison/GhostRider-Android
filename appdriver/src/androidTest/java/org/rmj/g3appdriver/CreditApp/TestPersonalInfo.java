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
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditApp;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.LoanInfo;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Personal;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestPersonalInfo {
    private static final String TAG = TestPersonalInfo.class.getSimpleName();

    private CreditOnlineApplication creditApp;

    private boolean isSuccess = false;
    private static String message;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        Application instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
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
        loLoan.setTargetDte("2022-10-24");
        loLoan.setAppTypex("0");
        loLoan.setBranchCde("M001");
        loLoan.setCustTypex("1");
        loLoan.setDownPaymt(10000);
        loLoan.setMonthlyAm(1500);
        loLoan.setModelIDxx("M123131231");
        loLoan.setBrandIDxx("123123123");
        loLoan.setAccTermxx(12);

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
        loLoan.setTargetDte("2022-10-24");
        loLoan.setAppTypex("0");
        loLoan.setBranchCde("M001");
        loLoan.setCustTypex("1");
        loLoan.setDownPaymt(10000);
        loLoan.setMonthlyAm(1500);
        loLoan.setModelIDxx("M123131231");
        loLoan.setBrandIDxx("123123123");
        loLoan.setAccTermxx(12);

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
    public void test03CheckPurchaseInfo() {
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Client_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getPurchase());
            assertNotNull(app);
        });
    }

    @Test
    public void test04SetupClientInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Client_Info);

        Personal loDetail = new Personal();
        loDetail.setTransNox(message);
        loDetail.setLastName("Garcia");
        loDetail.setFrstName("Michael");
        loDetail.setMiddName("Permison");
        loDetail.setSuffix("");
        loDetail.setBrthDate("1996-11-26");
        loDetail.setBrthPlce("0346");
        loDetail.setGender("0");
        loDetail.setCvlStats("0");
        loDetail.setCitizenx("01");
//        loDetail.setMobileNo("09171870011", "1", 0);
        loDetail.setEmailAdd("mikegarcia8748@gmail.com");
        loDetail.setPhoneNox("");
        loDetail.setFbAccntx("sample");
        loDetail.setVbrAccnt("09171870011");

        if(loApp.Validate(loDetail) == 0){
            message = loApp.getMessage();
            Log.e(TAG, message);
            assertTrue(isSuccess);
            return;
        }

        String lsResult = loApp.Save(loDetail);

        if(lsResult == null){
            message = loApp.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test05CheckClientInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Client_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getApplInfo());

            Personal loClient = (Personal) loApp.Parse(app);

            if(loClient != null){
                Log.d(TAG, "Last Name: " + loClient.getLastName());
                Log.d(TAG, "First Name: " + loClient.getFrstName());
                Log.d(TAG, "Middle Name: " + loClient.getMiddName());
                Log.d(TAG, "Suffix: " + loClient.getSuffix());
                Log.d(TAG, "Birth Date: " + loClient.getBrthDate());
                Log.d(TAG, "Birth Place: " + loClient.getBrthPlce());
                Log.d(TAG, "Birth Place: " + loClient.getBirthPlc());
                Log.d(TAG, "Mother Maiden Name: " + loClient.getMotherNm());
                Log.d(TAG, "Gender: " + loClient.getGender());
                Log.d(TAG, "Citizenship: " + loClient.getCitizenx());
                Log.d(TAG, "Citizenship: " + loClient.getCtznShip());
                Log.d(TAG, "Civil Status: " + loClient.getCvlStats());

//                for(int x = 0; x < loClient.getMobileNoQty(); x++){
//                    Log.d(TAG, "Mobile No: " + loClient.getMobileNo(x));
//                    Log.d(TAG, "Is Mobile Postpaid: " + loClient.getPostPaid(x));
//                    Log.d(TAG, "Postpaid year: " + loClient.getPostYear(x));
//                }
                Log.d(TAG, "Email Add:" + loClient.getEmailAdd());
                Log.d(TAG, "Phone No: " + loClient.getPhoneNox());
                Log.d(TAG, "Facebook: " + loClient.getFbAccntx());
                Log.d(TAG, "Viber: " + loClient.getVbrAccnt());
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }
}
