/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.imgCapture
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.imgcapture;

import android.app.Activity;
import android.content.Context;
import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GRider.Etc.GeoLocator;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.mock;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class GeoLocatorTest extends TestCase {

    private Context context = ApplicationProvider.getApplicationContext();
    private Activity activity;

    GeoLocator poLocator;

    @Before
    public void setUp() {
        activity = mock(Activity.class);
        poLocator = new GeoLocator(context, activity);
    }

    @Test
    public void test_getLattitude() {
        assertNotNull(poLocator.getLattitude());
    }

    @Test
    public void test_getLongitude() {
        assertNotNull(poLocator.getLongitude());
    }
}
