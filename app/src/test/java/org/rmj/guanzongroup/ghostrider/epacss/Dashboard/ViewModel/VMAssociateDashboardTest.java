/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 10/18/21, 8:47 AM
 * project file last modified : 10/18/21, 8:47 AM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Dashboard.ViewModel;

//import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMAssociateDashboard;
//import org.robolectric.RobolectricTestRunner;
//import org.robolectric.annotation.Config;

//@RunWith(RobolectricTestRunner.class)
//@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class VMAssociateDashboardTest {
    private VMAssociateDashboard mViewModel;

    @Before
    public void setUp() {
//        mViewModel = new VMAHDashboard(ApplicationProvider.getApplicationContext());
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
    public void testGetVersionInfo() {
        mViewModel.getVersionInfo().observeForever(s -> Assert.assertNotNull(s));
    }

    @Test
    public void testGetUserBranchInfo() {
        mViewModel.getUserBranchInfo().observeForever(eBranchInfo -> Assert.assertNotNull(eBranchInfo));
    }

    @Test
    public void testGetCurrentLogTimeIfExist() {
//        mViewModel.getCurrentLogTimeIfExist().observeForever(selfies -> Assert.assertNotNull(selfies));
    }


}
