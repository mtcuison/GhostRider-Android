package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestCheckDCP {
    private static final String TAG = TestCheckDCP.class.getSimpleName();

    private DcpManager poDcp;

    @Before
    public void setUp() throws Exception {
        poDcp = new DcpManager(ApplicationProvider.getApplicationContext());
    }

    @Test
    public void test01() throws Exception{

    }
}
