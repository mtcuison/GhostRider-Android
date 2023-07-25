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
import org.rmj.g3appdriver.GCircle.Apps.Dcp.obj.OTH;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.OtherRemCode;
import org.rmj.g3appdriver.etc.AppConfigPreference;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestOthers {
    private static final String TAG = TestOthers.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

//    @Test
//    public void test01DownloadDcp() throws Exception{
//        LRDcp loSys = new LRDcp(instance);
//        if(!loSys.DownloadCollection(
//                new ImportParams(
//                        "M00120001593",
//                        "2023-01-28",
//                        "1"))){
//            message = loSys.getMessage();
//            Log.e(TAG, message);
//        } else {
//            isSuccess = true;
//        }
//        Thread.sleep(1000);
//
//        assertTrue(isSuccess);
//    }

    @Test
    public void test02SaveOth() {
        OTH loSys = new OTH(instance);
        OtherRemCode loOth = new OtherRemCode();
        loOth.setTransNox("M01423000050");
        loOth.setAccountNo("M014190208");
        loOth.setEntryNox("2");
        loOth.setRemarksx("Sample entry");
        loOth.setFileName("Sample");
        loOth.setFilePath("Sample");
        loOth.setLatitude(String.valueOf(121.0000));
        loOth.setLongtude(String.valueOf(-13.0988));

        if(!loSys.SaveTransaction(loOth)){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test03ValidateEntry() {
        OtherRemCode loOth = new OtherRemCode();
        loOth.setTransNox("M01423000050");
        loOth.setAccountNo("M172200006");
        loOth.setEntryNox("1");
        loOth.setRemarksx("");
        loOth.setFileName("Sample");
        loOth.setFilePath("Sample");
        loOth.setLongtude(String.valueOf(121.0000));
        loOth.setLongtude(String.valueOf(-13.0988));

        if(!loOth.isDataValid()){
            Log.d(TAG, loOth.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
