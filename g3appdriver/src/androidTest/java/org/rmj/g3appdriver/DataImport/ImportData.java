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
import org.rmj.g3appdriver.GRider.Database.Repositories.RBankInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.ROccupation;
import org.rmj.g3appdriver.etc.AppConfigPreference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ImportData {
    private static final String TAG = ImportData.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String transno, message;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test00LoginUser() throws Exception{
        new REmployee(instance).AuthenticateUser(new REmployee.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"));
        Thread.sleep(1000);
    }

    @Test
    public void test01ImportBankInfo() throws Exception{
        RBankInfo loBank = new RBankInfo(instance);
        if(!loBank.ImportBankInfo()){
            message = loBank.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
        Thread.sleep(1000);
    }

    @Test
    public void test02ImportJobTitle() {
        ROccupation loJob = new ROccupation(instance);
        if(!loJob.ImportJobTitles()){
            message = loJob.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test03ImportRelations() {

    }
}
