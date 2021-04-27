/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_CODE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_COMPANY;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING_AMOUNT;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_TWO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ZERO;

public class SpouseEmploymentInfoModelTest extends TestCase {
    private static final SpouseEmploymentInfoModel infoModel = new SpouseEmploymentInfoModel();

    @Before
    public void setUp() {
        infoModel.setSector(STRING_ZERO);
        infoModel.setCompanyLvl(STRING_ONE);
        infoModel.setEmployeeLvl(STRING_TWO);
        infoModel.setBizIndustry(STRING_TWO);
        infoModel.setCompanyName(FAKE_COMPANY);
        infoModel.setCompAddress(FAKE_STRING);
        infoModel.setCompProvince(FAKE_CODE);
        infoModel.setCompTown(FAKE_CODE);
        infoModel.setJobTitle(STRING_TWO);
        infoModel.setJobSpecific(FAKE_STRING);
        infoModel.setEmploymentStat("R");
        infoModel.setLengthOfService(STRING_TWO);
        infoModel.setMonthOrYear(STRING_ONE);
        infoModel.setGrossMonthly(FAKE_STRING_AMOUNT);
        infoModel.setCompTelNox("5421234");
        infoModel.setUniformedPersonnel(STRING_ZERO);
        infoModel.setMilitaryPersonnel(STRING_ONE);
        infoModel.setCountry(FAKE_CODE);
    }

    @Test
    public void testGetSector() {
        assertEquals(STRING_ZERO, infoModel.getSector());
    }

    @Test
    public void testGetCompanyLvl() {
        infoModel.setSector(STRING_ONE);
        assertEquals(STRING_ONE, infoModel.getCompanyLevel());
    }

    @Test
    public void testGetGovernmentLevel() {
        assertEquals(STRING_ONE, infoModel.getGovermentLevel());
    }

    @Test
    public void testGetOfwRegion() {
        infoModel.setSector(STRING_TWO);
        assertEquals(STRING_ONE, infoModel.getOFWRegion());
    }

    @Test
    public void testGetEmployeeLevel() {
        assertEquals(STRING_TWO, infoModel.getEmployeeLvl());
    }

    @Test
    public void testGetWorkCategory() {
        infoModel.setSector(STRING_TWO);
        assertEquals(STRING_TWO, infoModel.getWorkCategory());
    }

    @Test
    public void testGetBizIndustry() {
        assertEquals(STRING_TWO, infoModel.getBizIndustry());
    }

    @Test
    public void testGetCompanyName() {
        assertEquals(FAKE_COMPANY, infoModel.getCompanyName());
    }

    @Test
    public void testGetCompAddress() {
        assertEquals(FAKE_STRING, infoModel.getCompAddress());
    }

    @Test
    public void testGetCompProvince() {
        assertEquals(FAKE_CODE, infoModel.getCompProvince());
    }

    @Test
    public void testGetCompTown() {
        assertEquals(FAKE_CODE, infoModel.getCompTown());
    }

    @Test
    public void testGetJobTitle() {
        assertEquals(STRING_TWO, infoModel.getJobTitle());
    }

    @Test
    public void testGetJobSpecific() {
        assertEquals(FAKE_STRING, infoModel.getJobSpecific());
    }

    @Test
    public void testGetEmploymentStat() {
        assertEquals("R", infoModel.getEmploymentStat());
    }

    @Test
    public void testGetLengthOfService() {
        double lnService = Double.parseDouble(STRING_TWO);
        assertEquals(lnService, infoModel.getLengthOfService() );
    }

    @Test
    public void testGetGrossMonthly() {
        long lnSalary = Long.parseLong(FAKE_STRING_AMOUNT.replace(",",""));
        assertEquals(lnSalary, infoModel.getGrossMonthly());
    }

    @Test
    public void testGetCompTelNox() {
        assertEquals("5421234", infoModel.getCompTelNox());
    }

    @Test
    public void testGetUniformedPersonnel() {
        assertEquals(STRING_ZERO, infoModel.getUniformedPersonnel());
    }

    @Test
    public void testGetMilitaryPersonnel() {
        assertEquals(STRING_ONE, infoModel.getMilitaryPersonnel());
    }

    @Test
    public void testGetCountry() {
        assertEquals(FAKE_CODE, infoModel.getCountry());
    }

    @Test
    public void testIsSpouseEmploymentInfoValid() {
        assertTrue(infoModel.isSpouseEmploymentInfoValid());
    }

}
