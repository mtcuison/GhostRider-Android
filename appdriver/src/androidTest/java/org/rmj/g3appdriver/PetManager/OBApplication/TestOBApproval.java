package org.rmj.g3appdriver.PetManager.OBApplication;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeOB;

import static org.junit.Assert.assertTrue;

import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Apps.PetManager.pojo.OBApprovalInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestOBApproval {
    private static final String TAG = TestOBApproval.class.getSimpleName();

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
    public void test01DownloadOBList() throws Exception{
        if(!poSys.ImportApplications()){
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
        OBApprovalInfo loApp = new OBApprovalInfo();
        loApp.setTransNox("MX0122000067");
        loApp.setAppldTox("2022-09-21");
        loApp.setAppldFrx("2022-09-21");
        loApp.setDateAppv("2022-09-21");
        loApp.setTranStat("1");
        loApp.setApproved(poUser.getEmployeeID());
        String lsTransNox = poSys.SaveApproval(loApp);
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
        if(!poSys.UploadApproval(transno)){
            message = poSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
