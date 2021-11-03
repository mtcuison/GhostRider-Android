/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/18/21, 10:59 AM
 * project file last modified : 10/18/21, 10:59 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class VMLeaveApprovalTest {

    int pnCredits;
    int pnNoDaysx;

    int pnWithPay;
    int pnWithOPy;

    @Before
    public void setUp() throws Exception {
        pnCredits = 9;
        pnNoDaysx = 2;
    }

    @Test
    public void calculateWithPay() {
        int lnWithPy = 2;
        int lnWOPayx = 1;

        if(pnNoDaysx > pnCredits){
            lnWithPy = pnNoDaysx - pnCredits;
            lnWOPayx = pnCredits - pnNoDaysx;
        } else if (pnCredits == 0) {
            lnWOPayx = pnNoDaysx;
            lnWithPy = 0;
        } else {
            lnWithPy = pnCredits - pnNoDaysx;
        }
        int lnDiff = lnWithPy - lnWOPayx;
        lnWithPy = lnWithPy - lnWOPayx;
        assertEquals(1, lnWithPy);
        assertEquals(1, lnDiff);
    }

    @Test
    public void calculateWithOPay() {
        int lnWithPy = 1;
        int lnWOPayx = 0;
        int lnDiff = lnWithPy - 0;
        lnWithPy = lnWithPy - lnWOPayx;
        assertEquals(1, lnWithPy);
    }
}