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

    private static final String TRANSACTION_NO = "Z3TXCBMCHCAO";
    private static final String TEST_STRING = "ABCDE12345";
    private static final String STRING_ZERO = "0";
    private static final String STRING_ONE = "1";
    private static final String PENSION_AMOUNT = "10,000";
    private static final String RETIREMENT_YEAR = "1999";
    private static final String OTHER_INCOME_SOURCE = "BUSINESS";
    private static final String OTHER_INCOME_AMOUNT = "25,000";

    private boolean isPension, isTransNo;

    @Mock
    ViewModelCallBack callback;

    @Before
    public void setUp() {
        infoModel = new SpousePensionInfoModel();
        mViewModel = new VMSpousePensionInfo(ApplicationProvider.getApplicationContext());

        isTransNo =mViewModel.setTransNox(TRANSACTION_NO);
        isPension = mViewModel.setPensionSec(STRING_ZERO);
        infoModel.setsPensionAmt(PENSION_AMOUNT);
        infoModel.setsRetirementYr(RETIREMENT_YEAR);
        infoModel.setsOtherSrc(OTHER_INCOME_SOURCE);
        infoModel.setsOtherSrcIncx(OTHER_INCOME_AMOUNT);
    }

    @Test
    public void test_setTransNox() {
        assertTrue(isTransNo);
    }

    @Test
    public void test_setPensionSec() {
        assertTrue(isPension);
    }

//    @Test
//    public void test_Save() {
//        assertTrue(mViewModel.Save(infoModel, callback));
//    }
}
