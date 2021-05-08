/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/3/21 2:33 PM
 * project file last modified : 5/3/21 2:33 PM
 */

package org.rmj.g3appdriver.etc;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AppConfigPreferenceTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void setIsAppFirstLaunch() {
        int lnDef = 1999;
        String lsVar = String.format("%08d", lnDef); // var is "001"
        int lnDef1 = 9899;
        String lsVar1 = String.format("%08d", lnDef1); // var is "099"
        assertEquals("00001999", lsVar);
        assertEquals("00009899", lsVar1);
        assertEquals(1999, Integer.parseInt(lsVar));
    }
}