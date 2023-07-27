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
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.ClientResidence;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestResidenceInfo {
    private static final String TAG = TestResidenceInfo.class.getSimpleName();

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
    public void test04SetupResidenceHouseOwn() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Residence_Info);

        ClientResidence loDetail = new ClientResidence();
        loDetail.setTransNox(message);
        loDetail.setLandMark("Sample");
        loDetail.setHouseNox("231");
        loDetail.setAddress1("Baybay");
        loDetail.setAddress2("Sitio Tawi-Tawi");
        loDetail.setBarangayID("1100170");
        loDetail.setMunicipalID("0346");
        loDetail.setProvinceID("01");
        loDetail.setHouseOwn("0");
        loDetail.setHouseType("0");
        loDetail.setHouseHold("0");
        loDetail.setHasGarage("0");
        loDetail.setIsYear(0);
        loDetail.setLenghtOfStay(25);
        loDetail.setOneAddress(true);

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
    public void test05CheckResidenceHouseOwn() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Residence_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getResidnce());

            ClientResidence loDetail = (ClientResidence) loApp.Parse(app);

            if(loDetail != null){
                Log.d(TAG, "Landmark: " + loDetail.getLandMark());
                Log.d(TAG, "House No: " + loDetail.getHouseNox());
                Log.d(TAG, "Address 1: " + loDetail.getAddress1());
                Log.d(TAG, "Address 2: " + loDetail.getAddress2());
                Log.d(TAG, "Town ID: " + loDetail.getMunicipalID());
                Log.d(TAG, "Barangay ID: " + loDetail.getBarangayID());
                Log.d(TAG, "Municipal: " + loDetail.getMunicipalNm());
                Log.d(TAG, "Province: " + loDetail.getProvinceNm());
                Log.d(TAG, "Barangay: " + loDetail.getBarangayName());
                Log.d(TAG, "House Ownership: " + loDetail.getHouseOwn());
                Log.d(TAG, "Owner Relation: " + loDetail.getOwnerRelation());
                Log.d(TAG, "House Hold: " + loDetail.getHouseHold());
                Log.d(TAG, "House Type: " + loDetail.getHouseType());
                Log.d(TAG, "Rented House Hold: " + loDetail.getHouseHold());
                Log.d(TAG, "Monthly Expense: " + loDetail.getMonthlyExpenses());
                Log.d(TAG, "Length Of Stay: " + loDetail.getLenghtofStay());
                Log.d(TAG, "Has Garage: " + loDetail.getHasGarage());
                Log.d(TAG, "Permanent Landmark: " + loDetail.getPermanentLandMark());
                Log.d(TAG, "Permanent House No: " + loDetail.getPermanentHouseNo());
                Log.d(TAG, "Permanent Address 1: " + loDetail.getPermanentAddress1());
                Log.d(TAG, "Permanent Address 2: " + loDetail.getPermanentAddress2());
                Log.d(TAG, "Permanent Barangay ID: " + loDetail.getPermanentBarangayID());
                Log.d(TAG, "Permanent Town ID: " + loDetail.getPermanentMunicipalID());
                Log.d(TAG, "Permanent Town: " + loDetail.getPermanentMunicipalNm());
                Log.d(TAG, "Permanent Province: " + loDetail.getPermanentProvinceNm());
                Log.d(TAG, "Permanent Barangay: " + loDetail.getBarangayName());

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test06SetupResidenceRent() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Residence_Info);

        ClientResidence loDetail = new ClientResidence();
        loDetail.setTransNox(message);
        loDetail.setLandMark("Sample");
        loDetail.setHouseNox("231");
        loDetail.setAddress1("Baybay");
        loDetail.setAddress2("Sitio Tawi-Tawi");
        loDetail.setBarangayID("1100170");
        loDetail.setMunicipalID("0346");
        loDetail.setProvinceID("01");
        loDetail.setHouseOwn("1");
        loDetail.setMonthlyExpenses(4000);
        loDetail.setHouseType("0");
        loDetail.setHouseHold("2");
        loDetail.setHasGarage("1");
        loDetail.setIsYear(1);
        loDetail.setLenghtOfStay(25);
        loDetail.setOneAddress(true);

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
    public void test07CheckResidenceInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Residence_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getResidnce());

            ClientResidence loDetail = (ClientResidence) loApp.Parse(app);

            if(loDetail != null){
                Log.d(TAG, "Landmark: " + loDetail.getLandMark());
                Log.d(TAG, "House No: " + loDetail.getHouseNox());
                Log.d(TAG, "Address 1: " + loDetail.getAddress1());
                Log.d(TAG, "Address 2: " + loDetail.getAddress2());
                Log.d(TAG, "Town ID: " + loDetail.getMunicipalID());
                Log.d(TAG, "Barangay ID: " + loDetail.getBarangayID());
                Log.d(TAG, "Municipal: " + loDetail.getMunicipalNm());
                Log.d(TAG, "Province: " + loDetail.getProvinceNm());
                Log.d(TAG, "Barangay: " + loDetail.getBarangayName());
                Log.d(TAG, "House Ownership: " + loDetail.getHouseOwn());
                Log.d(TAG, "Owner Relation: " + loDetail.getOwnerRelation());
                Log.d(TAG, "House Hold: " + loDetail.getHouseHold());
                Log.d(TAG, "House Type: " + loDetail.getHouseType());
                Log.d(TAG, "Rented House Hold: " + loDetail.getHouseHold());
                Log.d(TAG, "Monthly Expense: " + loDetail.getMonthlyExpenses());
                Log.d(TAG, "Length Of Stay: " + loDetail.getLenghtofStay());
                Log.d(TAG, "Has Garage: " + loDetail.getHasGarage());
                Log.d(TAG, "Permanent Landmark: " + loDetail.getPermanentLandMark());
                Log.d(TAG, "Permanent House No: " + loDetail.getPermanentHouseNo());
                Log.d(TAG, "Permanent Address 1: " + loDetail.getPermanentAddress1());
                Log.d(TAG, "Permanent Address 2: " + loDetail.getPermanentAddress2());
                Log.d(TAG, "Permanent Barangay ID: " + loDetail.getPermanentBarangayID());
                Log.d(TAG, "Permanent Town ID: " + loDetail.getPermanentMunicipalID());
                Log.d(TAG, "Permanent Town: " + loDetail.getPermanentMunicipalNm());
                Log.d(TAG, "Permanent Province: " + loDetail.getPermanentProvinceNm());
                Log.d(TAG, "Permanent Barangay: " + loDetail.getBarangayName());

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test08SetupResidenceCareTaker() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Residence_Info);

        ClientResidence loDetail = new ClientResidence();
        loDetail.setTransNox(message);
        loDetail.setLandMark("Sample");
        loDetail.setHouseNox("231");
        loDetail.setAddress1("Baybay");
        loDetail.setAddress2("Sitio Tawi-Tawi");
        loDetail.setBarangayID("1100170");
        loDetail.setMunicipalID("0346");
        loDetail.setProvinceID("01");
        loDetail.setHouseOwn("2");
        loDetail.setOwnerRelation("2");
        loDetail.setMonthlyExpenses(4000);
        loDetail.setHouseType("1");
        loDetail.setHouseHold("2");
        loDetail.setHasGarage("1");
        loDetail.setIsYear(1);
        loDetail.setLenghtOfStay(25);
        loDetail.setOneAddress(true);

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
    public void test09CheckResidenceCareTaker() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Residence_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getResidnce());

            ClientResidence loDetail = (ClientResidence) loApp.Parse(app);

            if(loDetail != null){
                Log.d(TAG, "Landmark: " + loDetail.getLandMark());
                Log.d(TAG, "House No: " + loDetail.getHouseNox());
                Log.d(TAG, "Address 1: " + loDetail.getAddress1());
                Log.d(TAG, "Address 2: " + loDetail.getAddress2());
                Log.d(TAG, "Town ID: " + loDetail.getMunicipalID());
                Log.d(TAG, "Barangay ID: " + loDetail.getBarangayID());
                Log.d(TAG, "Municipal: " + loDetail.getMunicipalNm());
                Log.d(TAG, "Province: " + loDetail.getProvinceNm());
                Log.d(TAG, "Barangay: " + loDetail.getBarangayName());
                Log.d(TAG, "House Ownership: " + loDetail.getHouseOwn());
                Log.d(TAG, "Owner Relation: " + loDetail.getOwnerRelation());
                Log.d(TAG, "House Hold: " + loDetail.getHouseHold());
                Log.d(TAG, "House Type: " + loDetail.getHouseType());
                Log.d(TAG, "Rented House Hold: " + loDetail.getHouseHold());
                Log.d(TAG, "Monthly Expense: " + loDetail.getMonthlyExpenses());
                Log.d(TAG, "Length Of Stay: " + loDetail.getLenghtofStay());
                Log.d(TAG, "Has Garage: " + loDetail.getHasGarage());
                Log.d(TAG, "Permanent Landmark: " + loDetail.getPermanentLandMark());
                Log.d(TAG, "Permanent House No: " + loDetail.getPermanentHouseNo());
                Log.d(TAG, "Permanent Address 1: " + loDetail.getPermanentAddress1());
                Log.d(TAG, "Permanent Address 2: " + loDetail.getPermanentAddress2());
                Log.d(TAG, "Permanent Barangay ID: " + loDetail.getPermanentBarangayID());
                Log.d(TAG, "Permanent Town ID: " + loDetail.getPermanentMunicipalID());
                Log.d(TAG, "Permanent Town: " + loDetail.getPermanentMunicipalNm());
                Log.d(TAG, "Permanent Province: " + loDetail.getPermanentProvinceNm());
                Log.d(TAG, "Permanent Barangay: " + loDetail.getBarangayName());

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }
}
