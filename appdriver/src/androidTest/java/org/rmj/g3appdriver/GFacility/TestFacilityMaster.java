package org.rmj.g3appdriver.GFacility;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.FacilityTrack.Itinerary.FacilityMaster;
import org.rmj.g3appdriver.FacilityTrack.pojo.Facility;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestFacilityMaster {
    private static final String TAG = TestFacilityMaster.class.getSimpleName();

    private Application instance;
    private FacilityMaster poSys;

    private boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        Log.d(TAG, "Test ended...");
        instance = ApplicationProvider.getApplicationContext();
        poSys = new FacilityMaster(instance);
    }

    @After
    public void tearDown() throws Exception {
        Log.d(TAG, "Test ended...");
    }

    @Test
    public void test01GetFacilities() {
        poSys.GetFacilities().observeForever(facilities -> {
            if(facilities == null){
                return;
            }

            if(facilities.size() == 0){
                return;
            }

            for(int x = 0; x < facilities.size(); x++){
                Log.d(TAG, "Facility ID: " + facilities.get(x).getWarehouseID());
                Log.d(TAG, "Facility Name: " + facilities.get(x).getWarehouseName());
                Log.d(TAG, "Last Checked: " + facilities.get(x).getLastCheck());
            }

            isSuccess = true;
        });

        assertTrue(isSuccess);
    }

    @Test
    public void test02GetItinerary() {

    }
}
