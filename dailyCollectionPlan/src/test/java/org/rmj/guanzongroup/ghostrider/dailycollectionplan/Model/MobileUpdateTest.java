package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

public class MobileUpdateTest extends TestCase {
    private MobileUpdate infoModel;

    private static final String STRING_ZERO = "0";
    private static final String STRING_ONE = "1";
    private static final String STRING_TWO = "2";
    private static final String FAKE_MOBILE = "09123456789";
    private static final String FAKE_REMARKS = "Fake Remarks";

    @Before
    public void setUp() {
        infoModel = new MobileUpdate(STRING_TWO, FAKE_MOBILE, STRING_ONE, FAKE_REMARKS);
    }

    @Test
    public void testIsDataValid() {
        assertTrue(infoModel.isDataValid());
    }
}