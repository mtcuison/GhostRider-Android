package org.rmj.g3appdriver.PetManager.OBApplication;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.etc.AppConstants;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeOB;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.pojo.OBApplication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestOBApplication {
    private static final String TAG = TestOBApplication.class.getSimpleName();

    private Application instance;

    private EmployeeMaster poUser;

    private EmployeeOB poSys;

    private boolean isSuccess = false;
    private static String transno, message;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poUser = new EmployeeMaster(instance);
        poSys = new EmployeeOB(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01SaveBusinessTrip() {
        OBApplication loApp = new OBApplication();
        loApp.setTransact(AppConstants.CURRENT_DATE());
        loApp.setEmployID(poUser.getEmployeeID());
        loApp.setDateFrom("2022-09-21");
        loApp.setDateThru("2022-09-21");
        loApp.setAppldTox("2022-09-21");
        loApp.setAppldFrx("2022-09-21");
        loApp.setDestinat("M001");
        loApp.setRemarksx("sample entry");
        String lsTransNox = poSys.SaveApplication(loApp);
        if(lsTransNox == null){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            transno = lsTransNox;
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test02PostOBApplication() {
        if(!poSys.UploadApplication(transno)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
