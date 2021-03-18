package org.rmj.guanzongroup.onlinecreditapplication.Model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class SpouseSelfEmployedInfoModelTest extends TestCase {
    private SpouseSelfEmployedInfoModel infoModel;

    @Before
    public void setUp() {
        infoModel = new SpouseSelfEmployedInfoModel();

        infoModel.setsBizIndustry("1");
        infoModel.setsBizName("Guanzon Group of Companies");
        infoModel.setsBizAddress("3213");
        infoModel.setsProvId("5435");
        infoModel.setsTownId("4324423");
        infoModel.setsBizType("2");
        infoModel.setsBizSize("1");
        infoModel.setsBizYrs("5");
        infoModel.setsMonthOrYear("0");
        infoModel.setsGrossMonthly("50000");
        infoModel.setsMonthlyExps("20000");
    }

    @Test
    public void test_isSpouseInfoValid() {
        assertTrue(infoModel.isSpouseInfoValid());
    }
}
