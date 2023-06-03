package org.rmj.g3appdriver.GFacility;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
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
import org.rmj.g3appdriver.FacilityTrack.Personnel.PersonnelMaster;
import org.rmj.g3appdriver.FacilityTrack.pojo.Personnel;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestPersonnelMaster {
    private static final String TAG = TestPersonnelMaster.class.getSimpleName();

    private Application instance;
    private PersonnelMaster poSys;

    private boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        Log.d(TAG, "Test started...");
        instance = ApplicationProvider.getApplicationContext();
        poSys = new PersonnelMaster(instance);
    }

    @After
    public void tearDown() throws Exception {
        Log.d(TAG, "Test ended...");
    }

    @Test
    public void test01GetPersonnelList() {
        poSys.GetPersonnelList().observeForever(personnels -> {
            if(personnels == null){
                return;
            }

            if(personnels.size() == 0){
                return;
            }

            for(int x = 0; x < personnels.size(); x++){
                Log.d(TAG, "Personnel ID: " + personnels.get(x).getPersonnelID());
                Log.d(TAG, "Personnel Name: " + personnels.get(x).getPersonnelName());
                Log.d(TAG, "Personnel Status: " + personnels.get(x).getPersonnelStatus());
                Log.d(TAG, "Warehouse Duty: " + personnels.get(x).getWarehouseDuty());
            }

            isSuccess = true;
        });

        assertTrue(isSuccess);
    }
}
