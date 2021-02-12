package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CoMakerModelTest {
    private CoMakerModel infoModel;
    private List<CoMakerModel.CoMakerMobileNo> mobileNoList;
    @Before
    public void setUp() throws Exception {
        infoModel = new CoMakerModel("Sabiniano",
                "Jonathan",
                "Tamayo",
                "",
                "",
                "03/06/1990",
                "20",
                "Jonathan Sabiniano",
                "2",
                "2");
        mobileNoList = new ArrayList<>();
        infoModel.setCoMobileNo("09452086661", "0", 0);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_getCoMobileNoQty(){
        assertEquals(1,infoModel.getCoMobileNoQty());
    }
    @Test
    public void test_isCoMakerInfoValid() {
        assertTrue(infoModel.isCoMakerInfoValid());
    }
}