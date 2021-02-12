package org.rmj.g3appdriver.dev;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

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