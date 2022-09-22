package org.rmj.g3appdriver.DataImport;

import android.app.Application;
import android.util.Log;

import static org.junit.Assert.assertTrue;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Repositories.RApprovalCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBankInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.RFileCode;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcBrand;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcCategory;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModel;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModelPrice;
import org.rmj.g3appdriver.GRider.Database.Repositories.ROccupation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RRelation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RSysConfig;
import org.rmj.g3appdriver.etc.AppConfigPreference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ImportData {
    private static final String TAG = ImportData.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test00LoginUser() throws Exception{
        isSuccess = new REmployee(instance).AuthenticateUser(new REmployee.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"));
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
        RApprovalCode loSys = new RApprovalCode(instance);
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
}
