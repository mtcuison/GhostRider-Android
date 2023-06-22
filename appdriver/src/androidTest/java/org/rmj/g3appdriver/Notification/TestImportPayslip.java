package org.rmj.g3appdriver.Notification;

import static org.junit.Assert.assertTrue;

import android.app.Application;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.lib.Notifications.Obj.Payslip;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestImportPayslip {
    private static final String TAG = TestImportPayslip.class.getSimpleName();

    private Application instance;
    private Payslip poSys;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new Payslip(instance);
    }

    @Test
    public void test01ImportPayslip() {
        boolean isSuccess = false;
        if(poSys.ImportPayslipNotifications()){
           isSuccess = true;
        }

        assertTrue(isSuccess);
    }
}
