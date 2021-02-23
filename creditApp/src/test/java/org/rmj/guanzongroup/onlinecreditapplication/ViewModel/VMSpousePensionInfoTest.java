package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMSpousePensionInfoTest extends TestCase {

    VMSpousePensionInfo testObj;

    @Before
    public void setUp() {
        testObj = new VMSpousePensionInfo(ApplicationProvider.getApplicationContext());

    }

    @Test
    public void test() {

    }


    @After
    public void tearDown() {
        testObj = null;
    }
}
