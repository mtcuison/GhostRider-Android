package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpousePensionInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.TestConstants;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpousePensionInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_SpousePensionInfoTest extends TestCase {
    private SpousePensionInfoModel infoModel;
    private VMSpousePensionInfo mViewModel;
    private boolean cPension, cTransNox, cDetlInfo;

    @Before
    public void setUp() {
        infoModel = new SpousePensionInfoModel();
        mViewModel = new VMSpousePensionInfo(TestConstants.APPLICATION);

        cDetlInfo = mViewModel.setDetailInfo(TestConstants.getDummyCreditApp());
        cTransNox = mViewModel.setTransNox(TestConstants.TRANSACTION_NO);
        cPension = mViewModel.setPensionSec(TestConstants.STRING_ZERO);
        infoModel.setsPensionAmt(TestConstants.FAKE_STRING_AMOUNT);
        infoModel.setsRetirementYr(TestConstants.FAKE_YEAR);
        infoModel.setsOtherSrc(TestConstants.FAKE_STRING);
        infoModel.setsOtherSrcIncx(TestConstants.FAKE_STRING_AMOUNT);
    }

//    @Test
//    public void test_setDetailInfo() { assertTrue(cDetlInfo); }

    @Test
    public void test_setTransNox() {
        assertTrue(cTransNox);
    }

    @Test
    public void test_setPensionSec() {
        assertTrue(cPension);
    }

    @Test
    public void test_Save() {
        assertTrue(mViewModel.Save(infoModel, new ViewModelCallBack() {
            @Override
            public void onSaveSuccessResult(String args) {
                System.out.print(args);
            }

            @Override
            public void onFailedResult(String message) {
                System.out.print(message);
            }
        }));
    }
}
