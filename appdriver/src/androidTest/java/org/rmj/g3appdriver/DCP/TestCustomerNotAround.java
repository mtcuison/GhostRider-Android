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
import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.obj.CNA;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.CustomerNotAround;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.ImportParams;
import org.rmj.g3appdriver.etc.AppConfigPreference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestCustomerNotAround {
    private static final String TAG = TestCustomerNotAround.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01DownloadDcp() throws Exception{
        LRDcp loSys = new LRDcp(instance);
        if(!loSys.DownloadCollection(
                new ImportParams(
                        "M00120001593",
                        "2023-01-28",
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
    public void test02SaveCNA() {
        CNA loSys = new CNA(instance);
        CustomerNotAround loCNA = new CustomerNotAround();
        loCNA.setTransNox("M01423000050");
        loCNA.setAccountNo("M014190208");
        loCNA.setEntryNox("2");
        loCNA.setRemarksx("Sample entry");
        loCNA.setFileName("Sample");
        loCNA.setFilePath("Sample");
        loCNA.setLatitude(String.valueOf(121.0000));
        loCNA.setLongtude(String.valueOf(-13.0988));

        if(!loSys.SaveTransaction(loCNA)){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test03ValidateEntry() {
        CustomerNotAround loCNA = new CustomerNotAround();
        loCNA.setTransNox("M01423000050");
        loCNA.setAccountNo("M172200006");
        loCNA.setEntryNox("1");
        loCNA.setRemarksx("");
        loCNA.setFileName("Sample");
        loCNA.setFilePath("Sample");
        loCNA.setLongtude(String.valueOf(121.0000));
        loCNA.setLongtude(String.valueOf(-13.0988));

        if(!loCNA.isDataValid()){
            Log.d(TAG, loCNA.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
