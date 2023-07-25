package org.rmj.g3appdriver.Knox;

import static org.junit.Assert.assertNotNull;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.Apps.knox.Obj.KnoxGetPin;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestKnoxPIN {
    private static final String TAG = TestKnoxPIN.class.getSimpleName();

    private Application instance;

    private KnoxGetPin poSys;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new KnoxGetPin(instance);
    }

    @Test
    public void test01GetPIN() {
        String lsResult = poSys.GetResult("350226693440809");
        if(lsResult == null){
            Log.d(TAG, poSys.getMessage());
        } else {
            Log.d(TAG, "PIN: " + lsResult);
        }

        assertNotNull(lsResult);
    }
}
