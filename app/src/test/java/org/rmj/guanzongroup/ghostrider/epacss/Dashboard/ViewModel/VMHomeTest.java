/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 10/18/21, 9:45 AM
 * project file last modified : 10/18/21, 9:45 AM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Dashboard.ViewModel;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class VMHomeTest {
    private VMHome mViewModel;

    @Before
    public void setUp() {
        mViewModel = new VMHome(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        mViewModel = null;
    }

    @Test
    public void testGetEmployeeInfo() {
        mViewModel.getEmployeeInfo().observeForever(eEmployeeInfo -> Assert.assertNotNull(eEmployeeInfo));
    }

    @Test
    public void testGetMobileNo() {
        mViewModel.getMobileNo().observeForever(s -> Assert.assertNotNull(s));
    }

    @Test
    public void testGetAreaPerformanceInfoList() {
        mViewModel.getAreaPerformanceInfoList().observeForever(eAreaPerformances -> Assert.assertNotNull(eAreaPerformances));
    }

    @Test
    public void testGetUserAreaCodeForDashboard() {
        mViewModel.getUserAreaCodeForDashboard().observeForever(s -> Assert.assertNotNull(s));
    }

    @Test
    public void testGetBranchPerformance() {
        mViewModel.getBranchPerformance().observeForever(eBranchPerformances -> Assert.assertNotNull(eBranchPerformances));
    }

    @Test
    public void testGetAreaPerformanceDashboard() {
        mViewModel.getAreaPerformanceDashboard().observeForever(eAreaPerformances -> Assert.assertNotNull(eAreaPerformances));
    }

    @Test
    public void testGetBranchOpeningInfoForDashBoard() {
        mViewModel.getBranchOpeningInfoForDashBoard().observeForever(branchInfos -> Assert.assertNotNull(branchInfos));
    }

    @Test
    public void testGetBranchAreaCode() {
        mViewModel.getBranchAreaCode("GMC Dagupan -Honda").observeForever(s -> Assert.assertNotNull(s));
    }


}
