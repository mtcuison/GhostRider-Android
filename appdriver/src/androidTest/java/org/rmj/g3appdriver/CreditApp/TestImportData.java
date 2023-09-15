package org.rmj.g3appdriver.CreditApp;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.room.Repositories.RMcBrand;
import org.rmj.g3appdriver.GCircle.room.Repositories.RMcCategory;
import org.rmj.g3appdriver.GCircle.room.Repositories.RMcModel;
import org.rmj.g3appdriver.GCircle.room.Repositories.RMcModelPrice;
import org.rmj.g3appdriver.GCircle.room.Repositories.RMcTermCategory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestImportData {
    private static final String TAG = TestImportData.class.getSimpleName();

    private Application instance;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void test01ImportBrands() {
        boolean isSuccess = false;
        RMcBrand loSys = new RMcBrand(instance);
        if(!loSys.ImportMCBrands()){
            Log.e(TAG, loSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test02ImportModels() {
        boolean isSuccess = false;
        RMcModel loSys = new RMcModel(instance);
        if(!loSys.ImportMCModel()){
            Log.e(TAG, loSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test03ImportModelPrice() {
        boolean isSuccess = false;
        RMcModelPrice loSys = new RMcModelPrice(instance);
        if(!loSys.ImportMcModelPrice()){
            Log.e(TAG, loSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test04ImportMCCategory() {
        boolean isSuccess = false;
        RMcCategory loSys = new RMcCategory(instance);
        if(!loSys.ImportMcCategory()){
            Log.e(TAG, loSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test05ImportMcTermCategory() {
        boolean isSuccess = false;
        RMcTermCategory loSys = new RMcTermCategory(instance);
        if(!loSys.ImportMcTermCategory()){
            Log.e(TAG, loSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test06ImportMcColor() {
        boolean isSuccess = false;
        RMcModel loSys = new RMcModel(instance);
        if(!loSys.ImportModelColor()){
            Log.e(TAG, loSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }


    @Test
    public void test06ImportMcPrices() {
        boolean isSuccess = false;
        RMcModel loSys = new RMcModel(instance);
        if(!loSys.ImportCashPrices()){
            Log.e(TAG, loSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }
}
