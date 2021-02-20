package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.objectweb.asm.util.ASMifier;

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
