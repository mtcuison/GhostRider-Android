package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoanUnitModelTest {
    private LoanUnitModel infoModel;

    private String luRemarks;
    private String luLastName;
    private String luFirstName;
    private String luMiddleName;
    private String luSuffix;
    private String luImgPath;

    private String luHouseNo;
    private String luStreet;
    private String luTown;
    private String luBrgy;

    private String luGender;
    private String luCivilStats;

    private String luBDate;
    private String luBPlace;

    private String luPhone;
    private String luMobile;
    private String luEmail;
    String imgPath = "/storage/emulated/0/Android/data/org.rmj.guanzongroup.ghostrider.epacss/files/DCP/LoanUnit/M00110006088_20210215_133927_1996682823072415579.jpg";
    @Before
    public void setUp() throws Exception {
        infoModel = new LoanUnitModel();
        infoModel.setLuLastName("Sabiniano");
        infoModel.setLuFirstName("Jonathan");
        infoModel.setLuMiddleName("Tamayo");
        infoModel.setLuSuffix("");
        //Address
        infoModel.setLuHouseNo("627");
        infoModel.setLuStreet("Ampongan");
        infoModel.setLuTown("0335");
        infoModel.setLuBrgy("1200145");
        infoModel.setLuBPlace("0335");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_isValidData() {
        assertTrue(infoModel.isValidFullName());
    }

    @Test
    public void test_isValidFullName() {
        assertTrue(infoModel.isValidFullName());
    }

    @Test
    public void test_isValidAddress() {
        assertTrue(infoModel.isValidAddress());
    }
}