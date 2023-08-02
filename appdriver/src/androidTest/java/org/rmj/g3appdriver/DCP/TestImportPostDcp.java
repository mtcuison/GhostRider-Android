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
import org.rmj.g3appdriver.GCircle.Apps.Dcp.pojo.ImportParams;
import org.rmj.g3appdriver.GCircle.Apps.Dcp.model.LRDcp;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestImportPostDcp {
    private static final String TAG = TestImportPostDcp.class.getSimpleName();

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
    }

    @Test
    public void test01DownloadDCP() {
        LRDcp loSys = new LRDcp(instance);
        if(!loSys.DownloadCollection(
                new ImportParams(
                        "M04108000065",
                        "2023-06-08",
                        "1"))){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test01PostDcp() {
        LRDcp loSys = new LRDcp(instance);
        String lsTransNo = loSys.PostCollection("Sample Remarksx");
        if(lsTransNo == null){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else if(!loSys.PostDcpMaster(lsTransNo)){
            message = loSys.getMessage();
            Log.e(TAG, message);
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
