/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 7/2/21 11:31 AM
 * project file last modified : 7/2/21 11:30 AM
 */

package org.rmj.guanzongroup.ghostrider.epacss.Dashboard.Activity;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Activity_SplashScreenTest {

    public boolean isStarted;

    public int CollectionMaster;
    public String Cash_On_Hand;
    public int PostedCollection;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testStartingServiceParameter() throws Exception{
        CollectionMaster = 1;
        Cash_On_Hand = "1.0";
        PostedCollection = 0;

        assertTrue(isStarted);
    }
}