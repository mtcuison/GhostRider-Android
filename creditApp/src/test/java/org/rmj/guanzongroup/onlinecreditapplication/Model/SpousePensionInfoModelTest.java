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

public class SpousePensionInfoModelTest extends TestCase {

    private SpousePensionInfoModel testObj;
    private static final String EMPTY_STRING= "";
    private static final String STRING_ZERO = "0";
    private static final String STRING_ONE = "1";
    private static final long FAKE_AMOUNT = 12000;
    private static final int FAKE_YEAR = 1900;
    private static final String FAKE_INCOME_SOURCE = "Business";

    @Before
    public void setUp() {
        testObj = new SpousePensionInfoModel();
        testObj.setsPensionSector(STRING_ZERO);
        testObj.setsPensionAmt(String.valueOf(FAKE_AMOUNT));
        testObj.setsRetirementYr(String.valueOf(FAKE_YEAR));
        testObj.setsOtherSrc(FAKE_INCOME_SOURCE);
        testObj.setsOtherSrcIncx(String.valueOf(FAKE_AMOUNT));
    }

    @Test
    public void test_getPensionSector() {
        assertEquals(STRING_ZERO, testObj.getsPensionSector());
    }

    @Test
    public void test_getPensionAmt() {
        assertEquals(FAKE_AMOUNT, testObj.getsPensionAmt());
    }

    @Test
    public void test_getRetirementYr() {
        assertEquals(FAKE_YEAR, testObj.getsRetirementYr());
    }

    @Test
    public void test_getOtherSrc() {
        assertEquals(FAKE_INCOME_SOURCE, testObj.getsOtherSrc());
    }

    @Test
    public void test_getOtherSrcIncx() {
        assertEquals(FAKE_AMOUNT,testObj.getsOtherSrcIncx());
    }

    @Test
    public void test_isPensionDataValid() {
        assertTrue(testObj.isPensionDataValid());
    }

}
