/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 7/10/21 9:01 AM
 * project file last modified : 7/10/21 9:01 AM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Service;

import org.junit.Test;

import static org.junit.Assert.*;

public class GLocatorServiceTest {

    @Test
    public void onStartCommand() {
        long interval = Long.parseLong("10");
        interval = interval * 60000;
        assertEquals(600000, interval);
    }
}