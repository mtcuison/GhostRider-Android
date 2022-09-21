package org.rmj.g3appdriver.OBApplication;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeBusinessTrip;

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
public class TestOBApproval {
    private static final String TAG = TestOBApproval.class.getSimpleName();

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
    public void test01DownloadOBList() throws Exception{
        if(!poSys.DownloadApplications()){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
        Thread.sleep(1000);
    }

    @Test
    public void test02ApproveOBApplication() {
        REmployeeBusinessTrip.OBApprovalInfo loApp = new REmployeeBusinessTrip.OBApprovalInfo();
        loApp.setTransNox("MX0122000067");
        loApp.setAppldTox("2022-09-21");
        loApp.setAppldFrx("2022-09-21");
        loApp.setDateAppv("2022-09-21");
        loApp.setTranStat("1");
        loApp.setApproved(poUser.getEmployeeID());
        String lsTransNox = poSys.SaveBusinessTripApproval(loApp);
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
    public void test03PostOBApproval() {
        if(!poSys.PostBusinessTripApproval(transno)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
