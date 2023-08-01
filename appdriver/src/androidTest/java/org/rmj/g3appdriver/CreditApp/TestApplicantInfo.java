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
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditOnlineApplication;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.LoanInfo;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditAppInstance;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Business;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.CoMaker;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.CoMakerResidence;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Dependent;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Employment;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Financier;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.OtherReference;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Personal;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Pension;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.ClientResidence;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.ClientSpouseInfo;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Disbursement;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Properties;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.model.Reference;
import org.rmj.gocas.base.GOCASApplication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestApplicantInfo {
    private static final String TAG = TestApplicantInfo.class.getSimpleName();

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

    @Test
    public void test06SetupResidenceInfo() {
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
    public void test08SetupEmploymentInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Employed_Info);
        Employment loDetail = new Employment();
        loDetail.setTransNox(message);
        loDetail.setEmploymentSector("1");
        loDetail.setCompanyLevel("0");
        loDetail.setBusinessNature("WholeSale/Retail");
        loDetail.setCompanyName("Guanzon Group");
        loDetail.setCompanyAddress("Kawasaki Building");
        loDetail.setProvinceID("01");
        loDetail.setTownID("0314");

        loDetail.setEmployeeLevel("0");
        loDetail.setJobTitle("M009093");
        loDetail.setSpecificJob("Senior Programmer");
        loDetail.setEmployeeStatus("R");
        loDetail.setLengthOfService(9);
        loDetail.setIsYear("0");
        loDetail.setMonthlyIncome(20000);
        loDetail.setContact("09171870011");

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
    public void test09CheckEmploymentInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Employed_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.e(TAG, app.getEmplymnt());

            Employment loDetail = (Employment) loApp.Parse(app);

            if(loDetail != null){
                Log.d(TAG, "Employment Sector: " + loDetail.getEmploymentSector());
                Log.d(TAG, "Is Uniform Personnel: " + loDetail.getUniformPersonal());
                Log.d(TAG, "Is Military Personnel: " + loDetail.getMilitaryPersonal());
                Log.d(TAG, "Government Level: " + loDetail.getGovermentLevel());
                Log.d(TAG, "Overseas Region: " + loDetail.getOfwRegion());
                Log.d(TAG, "Company Level: " + loDetail.getCompanyLevel());
                Log.d(TAG, "Employee Level: " + loDetail.getEmployeeLevel());
                Log.d(TAG, "Ofw Work Category: " + loDetail.getOfwWorkCategory());
                Log.d(TAG, "Ofw Country: " + loDetail.getCountry());
                Log.d(TAG, "Ofw Country Name: " + loDetail.getsCountryN());
                Log.d(TAG, "Business Nature: " + loDetail.getBusinessNature());
                Log.d(TAG, "Company Name: " + loDetail.getCompanyName());
                Log.d(TAG, "Company Address: " + loDetail.getCompanyAddress());
                Log.d(TAG, "Town ID: " + loDetail.getTownID());
                Log.d(TAG, "Town Name: " + loDetail.getsTownName());
                Log.d(TAG, "Province ID: " + loDetail.getProvinceID());
                Log.d(TAG, "Province Name: " + loDetail.getsProvName());
                Log.d(TAG, "Job Title: " + loDetail.getJobTitle());
                Log.d(TAG, "Job Title Name: " + loDetail.getsJobNamex());
                Log.d(TAG, "Specific Job: " + loDetail.getSpecificJob());
                Log.d(TAG, "Employment Status: " + loDetail.getEmployeeStatus());
                Log.d(TAG, "Length Of Service: " + loDetail.getLengthOfService());
                Log.d(TAG, "Monthly Income: " + loDetail.getMonthlyIncome());
                Log.d(TAG, "Company Contact: " + loDetail.getContact());

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test10SetupSelfEmployed() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Self_Employed_Info);

        Business loDetail = new Business();
        loDetail.setTransNox(message);
        loDetail.setNatureOfBusiness("Agriculture");
        loDetail.setNameOfBusiness("Rice Mill");
        loDetail.setBusinessAddress("Sample");
        loDetail.setTown("0346");
        loDetail.setTypeOfBusiness("0");
        loDetail.setSizeOfBusiness("0");
        loDetail.setIsYear("1");
        loDetail.setLengthOfService(25);
        loDetail.setMonthlyExpense(5000);
        loDetail.setMonthlyIncome(30000);

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
    public void test11CheckSelfEmployed() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Self_Employed_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Business loDetail = (Business) loApp.Parse(app);

            if(loDetail != null) {
                Log.d(TAG, "Nature of Business: " + loDetail.getNatureOfBusiness());
                Log.d(TAG, "Name of Business: " + loDetail.getNameOfBusiness());
                Log.d(TAG, "Business Address: " + loDetail.getBusinessAddress());
                Log.d(TAG, "Town: " + loDetail.getTown());
                Log.d(TAG, "Type of Business: " + loDetail.getTypeOfBusiness());
                Log.d(TAG, "Size of Business: " + loDetail.getSizeOfBusiness());
                Log.d(TAG, "Length of Service: " + loDetail.getLenghtOfService());
                Log.d(TAG, "Monthly Expense: " + loDetail.getMonthlyExpense());
                Log.d(TAG, "Monthly Income: " + loDetail.getMonthlyIncome());

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test12SetupFinancier() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Financier_Info);

        Financier loDetail = new Financier();
        loDetail.setTransNox(message);
        loDetail.setCountry("01");
        loDetail.setFinancierName("Sample");
        loDetail.setFacebook("Sample FB");
        loDetail.setFinancierRelation("0");
        loDetail.setMobileNo("09171870011");
        loDetail.setEmail("sample email");
        loDetail.setRangeOfIncome(10000);

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
    public void test13CheckFinancier() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Financier_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getFinancex());

            Financier loDetail = (Financier) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test14SetupPension() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Pension_Info);

        Pension loDetail = new Pension();
        loDetail.setTransNox(message);
        loDetail.setPensionSector("0");
        loDetail.setPensionIncomeRange(10000);
        loDetail.setRetirementYear("1996");
        loDetail.setNatureOfIncome("Freelance");
        loDetail.setRangeOfIncom(10000);

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
    public void test15CheckPension() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Pension_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getPensionx());

            Pension loDetail = (Pension) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test16SetupDisbursement() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Disbursement_Info);

        Disbursement loDetail = new Disbursement();
        loDetail.setTransNox(message);
        loDetail.setWaterExp(1000);
        loDetail.setFoodExps(500);
        loDetail.setElectric(800);
        loDetail.setLoanExps(1000);
        loDetail.setBankName("BDO");
        loDetail.setAcctType("0");
        loDetail.setCrdtBank("BDO");
        loDetail.setCrdtLimt(50000);
        loDetail.setCrdtYear(2010);

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
    public void test17CheckDisbursement() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Disbursement_Info);

        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getDisbrsmt());

            Disbursement loDetail = (Disbursement) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test18SetupSpouseInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Spouse_Info);

        ClientSpouseInfo loDetail = new ClientSpouseInfo();

        loDetail.setTransNox(message);
        loDetail.setLastName("Garcia");
        loDetail.setFrstName("Michael");
        loDetail.setMiddName("Permison");
        loDetail.setSuffix("");
        loDetail.setBrthDate("1996-11-26");
        loDetail.setBrthPlce("0346");
//        loDetail.setCvlStats("0");
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
    public void test19CheckSpouseInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Spouse_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getSpousexx());

            ClientSpouseInfo loDetail = (ClientSpouseInfo) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test20SetupSpouseEmployment() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Spouse_Employment_Info);
        Employment loDetail = new Employment();
        loDetail.setTransNox(message);
        loDetail.setEmploymentSector("1");
        loDetail.setCompanyLevel("0");
        loDetail.setBusinessNature("WholeSale/Retail");
        loDetail.setCompanyName("Guanzon Group");
        loDetail.setCompanyAddress("Kawasaki Building");
        loDetail.setProvinceID("01");
        loDetail.setTownID("0314");

        loDetail.setEmployeeLevel("0");
        loDetail.setJobTitle("M009093");
        loDetail.setSpecificJob("Senior Programmer");
        loDetail.setEmployeeStatus("R");
        loDetail.setLengthOfService(9);
        loDetail.setIsYear("0");
        loDetail.setMonthlyIncome(20000);
        loDetail.setContact("09171870011");

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
    public void test21CheckSpouseEmployment() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Spouse_Employment_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.e(TAG, app.getSpsEmplx());

            Employment loDetail = (Employment) loApp.Parse(app);

            if(loDetail != null){
                Log.d(TAG, "Employment Sector: " + loDetail.getEmploymentSector());
                Log.d(TAG, "Is Uniform Personnel: " + loDetail.getUniformPersonal());
                Log.d(TAG, "Is Military Personnel: " + loDetail.getMilitaryPersonal());
                Log.d(TAG, "Government Level: " + loDetail.getGovermentLevel());
                Log.d(TAG, "Overseas Region: " + loDetail.getOfwRegion());
                Log.d(TAG, "Company Level: " + loDetail.getCompanyLevel());
                Log.d(TAG, "Employee Level: " + loDetail.getEmployeeLevel());
                Log.d(TAG, "Ofw Work Category: " + loDetail.getOfwWorkCategory());
                Log.d(TAG, "Ofw Country: " + loDetail.getCountry());
                Log.d(TAG, "Ofw Country Name: " + loDetail.getsCountryN());
                Log.d(TAG, "Business Nature: " + loDetail.getBusinessNature());
                Log.d(TAG, "Company Name: " + loDetail.getCompanyName());
                Log.d(TAG, "Company Address: " + loDetail.getCompanyAddress());
                Log.d(TAG, "Town ID: " + loDetail.getTownID());
                Log.d(TAG, "Town Name: " + loDetail.getsTownName());
                Log.d(TAG, "Province ID: " + loDetail.getProvinceID());
                Log.d(TAG, "Province Name: " + loDetail.getsProvName());
                Log.d(TAG, "Job Title: " + loDetail.getJobTitle());
                Log.d(TAG, "Job Title Name: " + loDetail.getsJobNamex());
                Log.d(TAG, "Specific Job: " + loDetail.getSpecificJob());
                Log.d(TAG, "Employment Status: " + loDetail.getEmployeeStatus());
                Log.d(TAG, "Length Of Service: " + loDetail.getLengthOfService());
                Log.d(TAG, "Monthly Income: " + loDetail.getMonthlyIncome());
                Log.d(TAG, "Company Contact: " + loDetail.getContact());

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test22SetupSpouseBusiness() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Spouse_Self_Employed_Info);

        Business loDetail = new Business();
        loDetail.setTransNox(message);
        loDetail.setNatureOfBusiness("Agriculture");
        loDetail.setNameOfBusiness("Rice Mill");
        loDetail.setBusinessAddress("Sample");
        loDetail.setTown("0346");
        loDetail.setTypeOfBusiness("0");
        loDetail.setSizeOfBusiness("0");
        loDetail.setIsYear("1");
        loDetail.setLengthOfService(25);
        loDetail.setMonthlyExpense(5000);
        loDetail.setMonthlyIncome(30000);

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
    public void test23CheckSpouseBusiness() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Spouse_Self_Employed_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Business loDetail = (Business) loApp.Parse(app);

            if(loDetail != null) {
                Log.d(TAG, "Nature of Business: " + loDetail.getNatureOfBusiness());
                Log.d(TAG, "Name of Business: " + loDetail.getNameOfBusiness());
                Log.d(TAG, "Business Address: " + loDetail.getBusinessAddress());
                Log.d(TAG, "Town: " + loDetail.getTown());
                Log.d(TAG, "Type of Business: " + loDetail.getTypeOfBusiness());
                Log.d(TAG, "Size of Business: " + loDetail.getSizeOfBusiness());
                Log.d(TAG, "Length of Service: " + loDetail.getLenghtOfService());
                Log.d(TAG, "Monthly Expense: " + loDetail.getMonthlyExpense());
                Log.d(TAG, "Monthly Income: " + loDetail.getMonthlyIncome());

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test24SetupSpousePension() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Spouse_Pension_Info);

        Pension loDetail = new Pension();
        loDetail.setTransNox(message);
        loDetail.setPensionSector("0");
        loDetail.setPensionIncomeRange(10000);
        loDetail.setRetirementYear("1996");
        loDetail.setNatureOfIncome("Freelance");
        loDetail.setRangeOfIncom(10000);

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
    public void test25CheckSpousePension() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Spouse_Pension_Info);

        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getSpOthInc());

            Pension loDetail = (Pension) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test26SetupOtherInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Other_Info);

        OtherReference loDetail = new OtherReference();
        loDetail.setTransNox(message);
        loDetail.setSource("0");
        loDetail.setsPurposex("0");
        loDetail.setsPyr2Buyr("sample");
        loDetail.setsUnitPayr("0");
        loDetail.setsUnitUser("0");
        loDetail.AddReference(new Reference("sample", "sample1", "0346", "", "09123456789"));
        loDetail.AddReference(new Reference("sample1", "sample11", "0346", "", "09987456321"));
        loDetail.AddReference(new Reference("sample2", "sample12", "0346", "", "09365214789"));

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
    public void test27CheckOtherInfo() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Other_Info);
        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getOthrInfo());

            OtherReference loDetail = (OtherReference) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test28SetupCoMaker() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.CoMaker_Info);

        CoMaker loDetail = new CoMaker();
        loDetail.setTransNox(message);
        loDetail.setRelation("0");
        loDetail.setIncomexx("0");
        loDetail.setLastName("Garcia");
        loDetail.setFrstName("Michael");
        loDetail.setMiddName("Permison");
        loDetail.setNickName("Mike");
        loDetail.setBrthDate("1996-11-26");
        loDetail.setBrthPlce("0346");
//        loDetail.setMobileNo("09123456789", "0", 0);

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
    public void test29CheckCoMaker() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.CoMaker_Info);

        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getComakerx());

            CoMaker loDetail = (CoMaker) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test30SetupCoMakerResidence() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.CoMaker_Residence_Info);

        CoMakerResidence loDetail = new CoMakerResidence();
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
    public void test31CheckCoMakerResidence() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.CoMaker_Residence_Info);

        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getCmResidx());

            CoMakerResidence loDetail = (CoMakerResidence) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test32SetupDependents() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Dependent_Info);

        Dependent loDetail = new Dependent();
        loDetail.setTransNox(message);
        Dependent.DependentInfo loInfo = new Dependent.DependentInfo();
        loInfo.setFullName("Sample");
        loInfo.setRelation("0");
        loInfo.setDpdntAge(22);
        loInfo.setStudentx("1");
        loInfo.setSchoolNm("sample");
        loInfo.setSchlAddx("sample");
        loInfo.setSchlTown("0346");
        loInfo.setSchoolTp("1");
        loInfo.setEduLevel("3");
        loInfo.setSchoolar("1");
        loInfo.setEmployed("0");
        loInfo.setEmpSctor("");
        loInfo.setCompName("");
        loInfo.setHouseHld("1");
        loInfo.setDependnt("1");
        loInfo.setMarriedx("0");
//        loDetail.Add(loInfo);

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
    public void test33CheckDependents() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Dependent_Info);

        loApp.GetApplication(message).observeForever(app -> {
            Log.d(TAG, app.getDependnt());

            Dependent loDetail = (Dependent) loApp.Parse(app);

            if(loDetail != null){
                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test34SetupProperties() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Properties_Info);

        Properties loDetail = new Properties();
        loDetail.setTransNox(message);
        loDetail.setPs2Wheelsx("0");
        loDetail.setPs3Wheelsx("0");
        loDetail.setPs4Wheelsx("0");
        loDetail.setPsFridgexx("0");
        loDetail.setPsAirConxx("0");
        loDetail.setPsTelevsnx("0");
        loDetail.setPsLot1Addx("sample");
        loDetail.setPsLot2Addx("sample");
        loDetail.setPsLot3Addx("sample");

//        if(loApp.Validate(loDetail) == 0){
//            message = loApp.getMessage();
//            Log.e(TAG, message);
//            assertTrue(isSuccess);
//            return;
//        }

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
    public void test35CompileGOCas() {
        isSuccess = false;
        CreditApp loApp = creditApp.getInstance(CreditAppInstance.Application_Info);
        loApp.GetApplication(message).observeForever(app -> {
            GOCASApplication loDetail = (GOCASApplication) loApp.Parse(app);
            Log.d(TAG, loDetail.toJSONString());
        });
    }
}
