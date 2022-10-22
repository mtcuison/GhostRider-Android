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

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.util.ASMifier;
import org.rmj.g3appdriver.lib.integsys.Dcp.AddressUpdate;

public class AddressUpdateTest {
    AddressUpdate addrsUpdate;
    String rqstCode, rqstCodeNm;

    @Before
    public void setUp() {
        addrsUpdate = new AddressUpdate();

        addrsUpdate.setRequestCode("0");
        addrsUpdate.setcAddrssTp("1");
        addrsUpdate.setHouseNumber("123");
        addrsUpdate.setAddress("Wall Street");
        addrsUpdate.setsProvIDxx("555");
        addrsUpdate.setTownID("0282");
        addrsUpdate.setBarangayID("1200050");
        addrsUpdate.setPrimaryStatus("1");
        addrsUpdate.setLatitude("");
        addrsUpdate.setLongitude("");
        addrsUpdate.setsRemarksx("Remarks");
    }

    @Test
    public void testIsDataValid() {
        Assert.assertTrue(addrsUpdate.isDataValid());
    }

}
