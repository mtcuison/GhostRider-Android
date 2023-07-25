package org.rmj.g3appdriver.PetManager.LeaveApplication;


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
import org.rmj.g3appdriver.GCircle.Apps.PetManager.Obj.EmployeeLeave;
import org.rmj.g3appdriver.GCircle.room.Entities.EEmployeeLeave;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestImportLeaveApplication {
    private static final String TAG = TestImportLeaveApplication.class.getSimpleName();

    private Application instance;

    private EmployeeLeave poSys;

    private boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new EmployeeLeave(instance);
    }

    @Test
    public void test01ImportLeaveApplication() {
        if(poSys.ImportApplications()){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test02RetrieveLeaveApplications() {
        poSys.GetLeaveApplicationList().observeForever(new Observer<List<EEmployeeLeave>>() {
            @Override
            public void onChanged(List<EEmployeeLeave> eEmployeeLeaves) {
                if (eEmployeeLeaves == null){
                    return;
                }

                if(eEmployeeLeaves.size() == 0){
                    return;
                }

                for(int x = 0; x < eEmployeeLeaves.size(); x++){
                    Log.d(TAG, "Transaction number: " + eEmployeeLeaves.get(x).getTransNox()+ ", " +
                            "Status: " + eEmployeeLeaves.get(x).getTranStat() + ", " +
                            "Time Stamp: " + eEmployeeLeaves.get(x).getTimeStmp());
                }

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);
        isSuccess = false;
    }
}
