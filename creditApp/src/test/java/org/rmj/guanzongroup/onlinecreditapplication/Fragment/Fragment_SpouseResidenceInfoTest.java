package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseResidenceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseResidenceInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.APPLICATION;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_CODE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ZERO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.TRANSACTION_NO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.getDummyCreditApp;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest = Config.NONE)
public class Fragment_SpouseResidenceInfoTest extends TestCase {
    private SpouseResidenceInfoModel infoModel;
    private VMSpouseResidenceInfo mViewModel;
    private boolean cDetlInfo, cTransNox;

    @Before
    public void setUp() {
        infoModel = new SpouseResidenceInfoModel();
        mViewModel = new VMSpouseResidenceInfo(APPLICATION);

        cDetlInfo = mViewModel.setDetailInfo(getDummyCreditApp());
        cTransNox = mViewModel.setTransNox(TRANSACTION_NO);
        mViewModel.setProvinceID(FAKE_CODE);
        mViewModel.setTownID(FAKE_CODE);
        mViewModel.setBrgyID(FAKE_CODE);
    }

    @Test
    public void test_setTransNox() {
        assertTrue(cTransNox);
    }

    @Test
    public void test_getPsProvID() {
        mViewModel.getPsProvID().observeForever(value -> assertEquals(FAKE_CODE, value));
    }

    @Test
    public void test_getPsTownID() {
        mViewModel.getPsTownID().observeForever(value -> assertEquals(FAKE_CODE, value));
    }

    @Test
    public void test_getPsBrgyID() {
        mViewModel.getPsBrgyID().observeForever(value -> assertEquals(FAKE_CODE, value));
    }

    @Test
    public void test_setDetailInfo() {
        assertTrue(cDetlInfo);
    }

    @Test
    public void test_Save() {
        setInfoModel();
        assertTrue(mViewModel.Save(infoModel, new ViewModelCallBack() {
            @Override
            public void onSaveSuccessResult(String args) {
                System.out.println(args);
            }

            @Override
            public void onFailedResult(String message) {
                System.out.println(message);
            }
        }));
    }

    @After
    public void tearDown() {
        infoModel = null;
        mViewModel = null;
    }

    private void setInfoModel() {
        infoModel.setLandmark(FAKE_STRING);
        infoModel.setHouseNox(STRING_ONE);
        infoModel.setAddress1(FAKE_STRING);
        infoModel.setAddress2(FAKE_STRING);
    }
}
