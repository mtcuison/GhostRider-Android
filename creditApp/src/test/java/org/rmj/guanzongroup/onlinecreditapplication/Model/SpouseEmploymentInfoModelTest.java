package org.rmj.guanzongroup.onlinecreditapplication.Model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class SpouseEmploymentInfoModelTest extends TestCase {
    private SpouseEmploymentInfoModel infoModel;

    @Before
    public void setUp() {
        infoModel = new SpouseEmploymentInfoModel();

        infoModel.setCompanyLvl("1");
    }

    @Test
    public void test_getCompanyLevel() {

    }
}
