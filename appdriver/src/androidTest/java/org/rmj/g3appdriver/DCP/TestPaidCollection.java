package org.rmj.g3appdriver.DCP;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.obj.PAY;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.ImportParams;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.PaidDCP;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestPaidCollection {
    private static final String TAG = TestPaidCollection.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test00LoginAccount() {
        EmployeeMaster loSys = new EmployeeMaster(instance);
        if(!loSys.AuthenticateUser(
                new EmployeeMaster.UserAuthInfo(
                        "mikegarcia8748@gmail.com",
                        "123456",
                        "09171870011"))){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test01DownloadDcp() throws Exception{
        LRDcp loSys = new LRDcp(instance);
        if(!loSys.DownloadCollection(
                new ImportParams(
                        "M00120001593",
                        "2022-02-22",
                        "1"))){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        Thread.sleep(1000);

        assertTrue(isSuccess);
    }

    @Test
    public void test02SavePaidCollection() {
        PAY loSys = new PAY(instance);
        PaidDCP loPaid = new PaidDCP();
        loPaid.setTransNo("M01422000120");
        loPaid.setAccntNo("M057170036");
        loPaid.setAmountx(5000);
        loPaid.setBankNme("");
        loPaid.setCheckDt("");
        loPaid.setCheckNo("");
        loPaid.setDscount(100);
        loPaid.setEntryNo("1");
        loPaid.setTotAmnt(4900);
        loPaid.setPayment("1");
        loPaid.setPrNoxxx("10000");
        loPaid.setRemarks("Sample remarks");

        String lsResult = loSys.SavePaidTransaction(loPaid);
        if(lsResult == null){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else if(!loSys.UploadPaidTransaction(lsResult)){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
