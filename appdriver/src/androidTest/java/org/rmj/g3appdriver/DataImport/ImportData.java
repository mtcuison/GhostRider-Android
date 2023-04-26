package org.rmj.g3appdriver.DataImport;

import android.app.Application;
import android.util.Log;

import static org.junit.Assert.assertTrue;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RAreaPerformance;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RBankInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RBranch;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RBranchPerformance;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RCountry;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RFileCode;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RMcBrand;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RMcCategory;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RMcModel;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RMcModelPrice;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RMcTermCategory;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.ROccupation;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RProvince;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RRelation;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RSysConfig;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RTown;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.ApprovalCode.ApprovalCode;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ImportData {
    private static final String TAG = ImportData.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test00LoginUser() throws Exception{
        isSuccess = new EmployeeMaster(instance).AuthenticateUser(new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"));
        assertTrue(isSuccess);
        Thread.sleep(1000);
    }

    @Test
    public void test01ImportBankInfo() throws Exception{
        RBankInfo loSys = new RBankInfo(instance);
        if(!loSys.ImportBankInfo()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
    }

    @Test
    public void test02ImportJobTitle() {
        ROccupation loSys = new ROccupation(instance);
        if(!loSys.ImportJobTitles()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test03ImportRelations() {
        RRelation loSys = new RRelation(instance);
        if(!loSys.ImportRelations()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test04ImportSCARequest() {
        ApprovalCode loSys = new ApprovalCode(instance);
        if(!loSys.ImportSCARequest()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test05ImportSysConfig() {
        RSysConfig loSys = new RSysConfig(instance);
        if(!loSys.ImportSysConfig()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test06ImportBarangay() {
        RBarangay loSys = new RBarangay(instance);
        if(!loSys.ImportBarangay()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test07ImportBranch() {
        RBranch loSys = new RBranch(instance);
        if(!loSys.ImportBranches()) {
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test08ImportBrand() {
        RMcBrand loSys = new RMcBrand(instance);
        if(!loSys.ImportMCBrands()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test09ImportMcModel() {
        RMcModel loSys = new RMcModel(instance);
        if(!loSys.ImportMCModel()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test10ImportMcCategory() {
        RMcCategory loSys = new RMcCategory(instance);
        if(!loSys.ImportMcCategory()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test11ImportCountry() {
        RCountry loSys = new RCountry(instance);
        if(!loSys.ImportCountry()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test12ImportFileCode() {
        RFileCode loSys = new RFileCode(instance);
        if(!loSys.ImportFileCode()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test13ImportMcModelPrice() {
        RMcModelPrice loSys = new RMcModelPrice(instance);
        if(!loSys.ImportMcModelPrice()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test14ImportTermCategory() {
        RMcTermCategory loSys = new RMcTermCategory(instance);
        if(!loSys.ImportMcTermCategory()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test15ImportTown() {
        RTown loSys = new RTown(instance);
        if(!loSys.ImportTown()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test16ImportProvince() {
        RProvince loSys = new RProvince(instance);
        if(!loSys.ImportProvince()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test17ImportAreaPerformance() {
        RAreaPerformance loSys = new RAreaPerformance(instance);
        if(!loSys.ImportData()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test18ImportBranchPerformance() {
        RBranchPerformance loSys = new RBranchPerformance(instance);
        if(!loSys.ImportData()){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
