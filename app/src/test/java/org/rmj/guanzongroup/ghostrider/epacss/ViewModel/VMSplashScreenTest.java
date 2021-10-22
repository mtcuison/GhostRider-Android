/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 10/18/21, 3:45 PM
 * project file last modified : 10/18/21, 3:45 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class VMSplashScreenTest extends TestCase {
    private VMSplashScreen mViewModel;

    @Before
    public void setUp() {
        mViewModel = new VMSplashScreen(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        mViewModel = null;
    }

    @Test
    public void testGetVersionInfo() {
        mViewModel.getVersionInfo().observeForever(s -> assertNotNull(s));
    }

    @Test
    public void testIsPermissionsGranted() {
        mViewModel.isPermissionsGranted().observeForever(aBoolean -> assertTrue(aBoolean));
    }

    @Test
    public void testGetPermisions() {
        mViewModel.getPermisions().observeForever(strings -> assertNotNull(strings));
    }

    @Test
    public void testGetSessionDate() {
        mViewModel.getSessionDate().observeForever(s -> assertNotNull(s));
    }

    @Test
    public void testGetSessionTime() {
        mViewModel.getSessionTime().observeForever(session -> assertNotNull(session));
    }

    @Test
    public void testGetLocatorDateTrigger() {
        mViewModel.getLocatorDateTrigger().observeForever(dataTrigger -> assertNotNull(dataTrigger));
    }

    @Test
    public void testGetEmployeeLevel() {
        mViewModel.getEmployeeLevel().observeForever(s -> assertNotNull(s));
    }

    @Test
    public void testGetAutoLogStatus() {
        assertNotNull(mViewModel.getAutoLogStatus());
    }

    @Test
    public void testIsSessionValid() {
        mViewModel.isSessionValid().observeForever(aBoolean -> assertTrue(aBoolean));
    }

    @Test
    public void testIsLoggedIn() {
        mViewModel.isLoggedIn().observeForever(aBoolean -> assertTrue(aBoolean));
    }

}
