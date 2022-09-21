package org.rmj.g3appdriver.OBApplication;

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
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeBusinessTrip;
import org.rmj.g3appdriver.etc.AppConfigPreference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestOBApplication {
    private static final String TAG = TestOBApplication.class.getSimpleName();

    private Application instance;

    private REmployee poUser;

    private REmployeeBusinessTrip poSys;

    private boolean isSuccess = false;
    private static String transno, message;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poUser = new REmployee(instance);
        poSys = new REmployeeBusinessTrip(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01SaveBusinessTrip() {
        REmployeeBusinessTrip.OBApplication loApp = new REmployeeBusinessTrip.OBApplication();
        loApp.setTransact(AppConstants.CURRENT_DATE);
        loApp.setEmployID(poUser.getEmployeeID());
        loApp.setDateFrom("2022-09-21");
        loApp.setDateThru("2022-09-21");
        loApp.setAppldTox("2022-09-21");
        loApp.setAppldFrx("2022-09-21");
        loApp.setDestinat("M001");
        loApp.setRemarksx("sample entry");
        String lsTransNox = poSys.SaveOBApplication(loApp);
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
