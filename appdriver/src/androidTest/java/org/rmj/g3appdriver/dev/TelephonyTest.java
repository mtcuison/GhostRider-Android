/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.dev;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.dev.Device.Telephony;

import static org.junit.Assert.*;

public class TelephonyTest {

    Telephony telephony;

    @Before
    public void setUp() throws Exception {
        telephony = new Telephony(InstrumentationRegistry.getInstrumentation().getContext());
    }

    @Test
    public void getDeviceID() {
        String lsResult = telephony.getFormattedMobileNo("+639270359402");
        assertEquals("09270359402", lsResult);
    }
}