package org.rmj.g3appdriver.Bullseye;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.room.Entities.EAreaPerformance;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.BullsEye.obj.AreaPerformance;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class AreaPerformanceTest {
    private static final String TAG = AreaPerformanceTest.class.getSimpleName();

    private Application instance;
    private AreaPerformance poSys;

    private EmployeeMaster poMaster;

    private boolean isSuccess = false;
    private String message;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(false);
        this.poSys = new AreaPerformance(instance);
        this.poMaster = new EmployeeMaster(instance);
        if(poMaster.AuthenticateUser(new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"))){
            Log.e(TAG, "Login success.");
        } else {
            Log.e(TAG, poMaster.getMessage());
        }
    }

    @Test
    public void test01ImportAreaPerformance() {
        if(poSys.ImportData()){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test02GetMCSales() {
        poSys.GetCurrentMCSalesPerformance().observeForever(performance -> {
            Log.d(TAG, "MC sales performance: " + performance);
            assertNotNull(performance);
        });
    }

    @Test
    public void test03GetSPSales() {
        poSys.GetCurentSPSalesPerformance().observeForever(performance -> {
            Log.d(TAG, "SP sales performance: " + performance);
            assertNotNull(performance);
        });
    }

    @Test
    public void test04JobOrder() {
        poSys.GetJobOrderPerformance().observeForever(performance -> {
            Log.d(TAG, "SP sales performance: " + performance);
            assertNotNull(performance);
        });
    }

    @Test
    public void test05GetPeriodicPerformance() {
        poSys.GetPeriodicAreaPerformance().observeForever(new Observer<List<EAreaPerformance>>() {
            @Override
            public void onChanged(List<EAreaPerformance> performances) {
                for(int x = 0; x < performances.size(); x++){
                    Log.d(TAG, "Performance Period" + performances.get(x).getPeriodxx());
                }
            }
        });
    }
}
