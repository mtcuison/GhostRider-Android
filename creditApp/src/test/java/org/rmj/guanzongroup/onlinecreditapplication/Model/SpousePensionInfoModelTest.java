package org.rmj.guanzongroup.onlinecreditapplication.Model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class SpousePensionInfoModelTest extends TestCase {

    private SpousePensionInfoModel testObj;
    private static final String EMPTY_STRING= "";
    private static final String STRING_ZERO = "0";
    private static final String STRING_ONE = "1";
    private static final String FAKE_AMOUNT = "12000";
    private static final String FAKE_YEAR = "1900";
    private static final String FAKE_INCOME_SOURCE = "Business";

    @Before
    public void setUp() {
        testObj = new SpousePensionInfoModel();
        testObj.setsPensionSector(STRING_ZERO);
        testObj.setsPensionAmt(FAKE_AMOUNT);
        testObj.setsRetirementYr(FAKE_YEAR);
        testObj.setsOtherSrc(FAKE_INCOME_SOURCE);
        testObj.setsOtherSrcIncx(FAKE_AMOUNT);
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
