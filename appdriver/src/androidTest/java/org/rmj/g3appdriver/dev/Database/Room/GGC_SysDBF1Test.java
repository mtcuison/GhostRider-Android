package org.rmj.g3appdriver.dev.Database.Room;

import android.app.Application;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
//import org.rmj.g3appdriver.dev.Database.Room.model.GGC_SysDBF;
import org.rmj.g3appdriver.dev.etc.AppConfigPreference;

public class GGC_SysDBF1Test {

    private Application instance;

    private boolean isSuccess = false;
    private static String message;

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test01InitializeDatabase() {
        AppConfigPreference.getInstance(instance).setTemp_ProductID("gRider");

//        GGC_SysDBF loDB = (GGC_SysDBF) GGC_SysDBF.getInstance(instance);
    }
}