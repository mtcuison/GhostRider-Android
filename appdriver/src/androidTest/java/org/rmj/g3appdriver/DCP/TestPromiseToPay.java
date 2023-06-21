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
import org.rmj.g3appdriver.GCircle.Apps.Dcp.obj.PTP;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.ImportParams;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.PromiseToPay;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestPromiseToPay {
    private static final String TAG = TestPromiseToPay.class.getSimpleName();

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
    public void test02SavePtp() {
        PTP loSys = new PTP(instance);
        PromiseToPay loPtp = new PromiseToPay();
        loPtp.setTransNox("M01422000120");
        loPtp.setAccntNox("M014180113");
        loPtp.setBranchCd("M001");
        loPtp.setCollctNm("Garcia, Michael");
        loPtp.setEntryNox("2");
        loPtp.setPaymntxx("1");
        loPtp.setRemarks("Sample entry");
        loPtp.setTransact("2022-10-01");
        loPtp.setFileName("Sample");
        loPtp.setFilePath("Sample");
        loPtp.setLongtude(121.0000);
        loPtp.setLongtude(-13.0988);

        if(!loSys.SaveTransaction(loPtp)){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test03ValidateEntry() {
        PromiseToPay loPtp = new PromiseToPay();
        loPtp.setTransNox("M01422000120");
        loPtp.setAccntNox("M014180113");
        loPtp.setPaymntxx("1");
//        loPtp.setBranchCd("M001");
        loPtp.setCollctNm("Garcia, Michael");
        loPtp.setEntryNox("2");
        loPtp.setRemarks("Sample entry");
        loPtp.setTransact("2022-10-01");
        loPtp.setFileName("Sample");
        loPtp.setFilePath("Sample");
        loPtp.setLongtude(121.0000);
        loPtp.setLongtude(-13.0988);

        if(!loPtp.isDataValid()){
            Log.d(TAG, loPtp.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
