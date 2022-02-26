/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/16/21, 9:28 AM
 * project file last modified : 10/16/21, 9:28 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.os.Build;

import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class VMAreaPerformanceMonitoringTest {

    private VMAreaPerfromanceMonitoring mViewModel;

    @Before
    public void setUp() {
        mViewModel = new VMAreaPerfromanceMonitoring(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        mViewModel = null;
    }

    @Test
    public void testGetAreaNameFromCode() {
//        mViewModel.getAreaNameFromCode("M001").observeForever(new Observer<String>() {
//            @Override
//            public void onChanged(String s) {
//                Assert.assertNotNull(s);
//            }
//        });
    }

    @Test
    public void testGetAreaPerformanceInfoList() {
        mViewModel.getAreaPerformanceInfoList().observeForever(new Observer<List<EAreaPerformance>>() {
            @Override
            public void onChanged(List<EAreaPerformance> eAreaPerformances) {
                Assert.assertNotNull(eAreaPerformances);
            }
        });
    }

    @Test
    public void testGetAreaBranchesSalesPerformance() {
        mViewModel.getAreaBranchesSalesPerformance("202101").observeForever(new Observer<List<EBranchPerformance>>() {
            @Override
            public void onChanged(List<EBranchPerformance> eBranchPerformances) {
                Assert.assertNotNull(eBranchPerformances);
            }
        });
    }

    

}
