package org.rmj.guanzongroup.onlinecreditapplication.Model;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.Before;
import org.junit.Test;

import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_CODE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_COMPANY;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING_AMOUNT;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_TWO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ZERO;

public class SpouseSelfEmployedInfoModelTest extends TestCase {
    private static final SpouseSelfEmployedInfoModel infoModel = new SpouseSelfEmployedInfoModel();

    @Before
    public void setUp() {
        infoModel.setsBizIndustry(STRING_ONE);
        infoModel.setsBizName(FAKE_COMPANY);
        infoModel.setsBizAddress(FAKE_STRING);
        infoModel.setsProvId(FAKE_CODE);
        infoModel.setsTownId(FAKE_CODE);
        infoModel.setsBizType(STRING_TWO);
        infoModel.setsBizSize(STRING_ZERO);
        infoModel.setsBizYrs(STRING_TWO);
        infoModel.setsMonthOrYear(STRING_ZERO);
        infoModel.setsGrossMonthly(FAKE_STRING_AMOUNT);
        infoModel.setsMonthlyExps(FAKE_STRING_AMOUNT);
    }

    @Test
    public void test_isSpouseInfoValid() {
        assertTrue(infoModel.isSpouseInfoValid());
    }

    @Test
    public void testGetsBizIndustry() {
        assertEquals(STRING_ONE, infoModel.getsBizIndustry());
    }

    @Test
    public void testGetsBizName() {
        assertEquals(FAKE_COMPANY, infoModel.getsBizName());
    }

    @Test
    public void testGetsBizAddress() {
        assertEquals(FAKE_STRING, infoModel.getsBizAddress());
    }

    @Test
    public void testGetsProvId() {
        assertEquals(FAKE_CODE, infoModel.getsProvId());
    }

    @Test
    public void testGetsTownId() {
        assertEquals(FAKE_CODE, infoModel.getsTownId());
    }

    @Test
    public void testGetsBizType() {
        assertEquals(STRING_TWO, infoModel.getsBizType());
    }

    @Test
    public void testGetsBizSize() {
        assertEquals(STRING_ZERO, infoModel.getsBizSize());
    }

    @Test
    public void testGetsBizYrs() {
        double lnService = (Double.parseDouble(STRING_TWO)) / 12;
        assertEquals(lnService, infoModel.getsBizYrs());
    }

    @Test
    public void testGetsMonthOrYear() {
        assertEquals(STRING_ZERO, infoModel.getsMonthOrYear());
    }

    @Test
    public void testGetsGrossMonthly() {
        long lnSalary = Long.parseLong(FAKE_STRING_AMOUNT.replace(",",""));
        assertEquals(lnSalary, infoModel.getsGrossMonthly());
    }

    @Test
    public void testGetsMonthlyExps() {
        long lnExpense = Long.parseLong(FAKE_STRING_AMOUNT.replace(",",""));
        assertEquals(lnExpense, infoModel.getsMonthlyExps());
    }

}
