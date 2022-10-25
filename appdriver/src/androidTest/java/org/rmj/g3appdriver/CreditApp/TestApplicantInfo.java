package org.rmj.g3appdriver.CreditApp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditApp;
import org.rmj.g3appdriver.lib.integsys.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.lib.integsys.CreditApp.LoanInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.Obj.CreditAppInstance;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientInfo;
import org.rmj.g3appdriver.lib.integsys.CreditApp.model.ClientResidence;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestApplicantInfo {
    private static final String TAG = TestApplicantInfo.class.getSimpleName();
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
        ClientInfo loInfo = new ClientInfo();
        loInfo.setTransNox(message);
        loInfo.setLastName("Garcia");
        loInfo.setFrstName("Michael");
        loInfo.setMiddName("Permison");
        loInfo.setSuffix("");
        loInfo.setBrthDate("1996-11-26");
        loInfo.setBrthPlce("0346");
        loInfo.setGender("0");
        loInfo.setCvlStats("0");
        loInfo.setCitizenx("01");
        loInfo.setMobileNo("09171870011", "1", 0);
        loInfo.setEmailAdd("mikegarcia8748@gmail.com");
        loInfo.setPhoneNox("");
        loInfo.setFbAccntx("sample");
        loInfo.setVbrAccnt("09171870011");

        if(loApp.Validate(loInfo) == 0){
            message = loApp.getMessage();
            Log.e(TAG, message);
            assertTrue(isSuccess);
            return;
        }

        if(!loApp.Save(loInfo)){
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

            ClientInfo loClient = (ClientInfo) loApp.Parse(app);

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

                for(int x = 0; x < loClient.getMobileNoQty(); x++){
                    Log.d(TAG, "Mobile No: " + loClient.getMobileNo(x));
                    Log.d(TAG, "Is Mobile Postpaid: " + loClient.getPostPaid(x));
                    Log.d(TAG, "Postpaid year: " + loClient.getPostYear(x));
                }
                Log.d(TAG, "Email Add:" + loClient.getEmailAdd());
                Log.d(TAG, "Phone No: " + loClient.getPhoneNox());
                Log.d(TAG, "Facebook: " + loClient.getFbAccntx());
                Log.d(TAG, "Viber: " + loClient.getVbrAccnt());
                isSuccess = true;
            }
        });
        assertTrue(isSuccess);
    }

    @Test
    public void test06SetupResidenceInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Residence_Info);
        ClientResidence loDetail = new ClientResidence();
        loDetail.setHouseNox("231");
        loDetail.setAddress1("Baybay");
        loDetail.setAddress2("Sitio Tawi-Tawi");
        loDetail.setBarangayID("1100170");
        loDetail.setMunicipalID("0346");
        loDetail.setProvinceID("01");
        loDetail.setHouseOwn("0");
        loDetail.setHouseType("0");
        loDetail.setHasGarage("0");
        loDetail.setOneAddress(true);
        loApp.Save(loDetail);
    }
}
