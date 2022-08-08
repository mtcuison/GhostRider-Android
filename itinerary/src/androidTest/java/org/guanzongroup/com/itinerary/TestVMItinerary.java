package org.guanzongroup.com.itinerary;

import static org.junit.Assert.assertFalse;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.guanzongroup.com.itinerary.ViewModel.VMItinerary;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Repositories.RItinerary;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestVMItinerary {
    private static final String TAG = TestVMItinerary.class.getSimpleName();

    private Application instance;
    private VMItinerary mViewModel;

    boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        mViewModel = new VMItinerary(instance);
    }

    @Test
    public void test01CreateItinerary() throws Exception{
        RItinerary.Itinerary loDetail = new RItinerary.Itinerary();
        loDetail.setLocation("GMC san Fabian");
        loDetail.setTimeStrt("12:00:00");
        loDetail.setTimeEndx("02:00:00");
        loDetail.setRemarksx("Sample Entry");

        mViewModel.SaveItinerary(loDetail, new VMItinerary.OnActionCallback() {
            @Override
            public void OnLoad(String title, String message) {

            }

            @Override
            public void OnSuccess(String args) {
                Log.d(TAG, "Success. Message: " + args);
                isSuccess = true;
            }

            @Override
            public void OnFailed(String message) {
                Log.d(TAG, "Failed. Message: " + message);
            }
        });
        assertFalse(isSuccess);
    }

}
