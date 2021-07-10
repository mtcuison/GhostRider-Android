/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VMCollectionListTest {

    private Application app;
    private VMCollectionList mViewModel;

    @Before
    public void setUp() throws Exception {
        app = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        mViewModel = new VMCollectionList(app);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void downloadDcp() {
        boolean hasRemitted;
        int paid = 0;
        String remittance = null;
        if(paid > 0){
            if(remittance == null){
                hasRemitted = false;
            } else if (!remittance.equalsIgnoreCase("0")
            || !remittance.equalsIgnoreCase("0.0")){
                hasRemitted = false;
            } else {
                hasRemitted = true;
            }
        } else {
            hasRemitted = true;
        }
        assertTrue(hasRemitted);
    }

    @Test
    public void testHalfRemittance(){
        boolean hasRemitted;
        int paid = 1;
        String remittance = "4424.0";
        if(paid > 0){
            if(remittance == null){
                hasRemitted = false;
            } else if (!remittance.equalsIgnoreCase("0")
                    || !remittance.equalsIgnoreCase("0.0")){
                hasRemitted = false;
            } else {
                hasRemitted = true;
            }
        } else {
            hasRemitted = true;
        }
        assertTrue(hasRemitted);
    }

    @Test
    public void testNoPaidCollection(){
        boolean hasRemitted;
        int paid = 0;
        String remittance = null;
        if(paid > 0){
            if(remittance == null){
                hasRemitted = false;
            } else if (!remittance.equalsIgnoreCase("0")
                    || !remittance.equalsIgnoreCase("0.0")){
                hasRemitted = false;
            } else {
                hasRemitted = true;
            }
        } else {
            hasRemitted = true;
        }
        assertTrue(hasRemitted);
    }
}