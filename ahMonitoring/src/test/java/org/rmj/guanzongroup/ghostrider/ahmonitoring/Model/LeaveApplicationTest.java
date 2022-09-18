/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/22/21 11:24 AM
 * project file last modified : 6/22/21 11:24 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Model;

import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;

import static org.junit.Assert.*;

public class LeaveApplicationTest {

    public REmployeeLeave.LeaveApplication poLeave;

    @Before
    public void setUp() throws Exception {
        poLeave = new REmployeeLeave.LeaveApplication();
    }

    @Test
    public void isDataValid() {
        poLeave.setLeaveType("0");
        poLeave.setRemarksxx("Sample");
        poLeave.setNoOfDaysx(0);
        poLeave.setDateFromx("2021-06-22");
        poLeave.setDateThrux("2021-06-22");

        assertTrue(poLeave.isDataValid());
    }
}