package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseEmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.TestConstants;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseEmploymentInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_SpouseEmploymentInfoTest extends TestCase {
    private VMSpouseEmploymentInfo mViewModel;
    private SpouseEmploymentInfoModel infoModel;
    private boolean cTransNox, cSectorxx;

    @Before
    public void setUp() {
        mViewModel = new VMSpouseEmploymentInfo(TestConstants.APPLICATION);
        infoModel = new SpouseEmploymentInfoModel();

        mViewModel.setDetailInfo(TestConstants.getDummyCreditApp());
        cTransNox = mViewModel.setTransNox(TestConstants.TRANSACTION_NO);
        cSectorxx = mViewModel.setEmploymentSector(TestConstants.STRING_TWO);
        mViewModel.setPsCmpLvl(TestConstants.STRING_ONE);
    }

    @Test
    public void test_setTransNox() {
        assertTrue(cTransNox);
    }

    @Test
    public void test_setEmploymentSector() {
        assertTrue(cSectorxx);
    }

    @Test
    public void test_setPsCmpLvl() {
        mViewModel.getPsCmpLvl().observeForever(s -> {
            assertEquals(TestConstants.STRING_ONE, s);
        });
    }


}
