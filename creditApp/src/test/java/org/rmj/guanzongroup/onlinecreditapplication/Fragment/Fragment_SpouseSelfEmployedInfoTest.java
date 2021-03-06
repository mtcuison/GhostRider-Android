/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;
import android.widget.ArrayAdapter;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseSelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.TestConstants;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseSelfEmployedInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.displayArrayAdapterItem;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest = Config.NONE)
public class Fragment_SpouseSelfEmployedInfoTest extends TestCase {
    private SpouseSelfEmployedInfoModel infoModel;
    private VMSpouseSelfEmployedInfo mViewModel;
    private boolean cTransNox, cBizIndsx, cProvIdxx, cTownIdxx, cBizType, cBizSize, cMosYrxx;

    @Before
    public void setUp() {
        infoModel = new SpouseSelfEmployedInfoModel();
        mViewModel = new VMSpouseSelfEmployedInfo(TestConstants.APPLICATION);

        cTransNox = mViewModel.setTransNox(TestConstants.TRANSACTION_NO);
        mViewModel.setDetailInfo(TestConstants.getDummyCreditApp());
        cBizIndsx = mViewModel.setBizIndustry(TestConstants.STRING_ONE);
        infoModel.setsBizName(TestConstants.FAKE_COMPANY);
        infoModel.setsBizAddress(TestConstants.FAKE_CODE);
        cProvIdxx = mViewModel.setProvinceID(TestConstants.FAKE_CODE);
        cTownIdxx = mViewModel.setTownID(TestConstants.FAKE_CODE);
        cBizType = mViewModel.setBizType(TestConstants.STRING_ZERO);
        cBizSize = mViewModel.setBizSize(TestConstants.STRING_TWO);
        infoModel.setsBizYrs(TestConstants.STRING_TWO);
        cMosYrxx = mViewModel.setMosOrYr(TestConstants.STRING_ZERO);
        infoModel.setsGrossMonthly(TestConstants.FAKE_STRING_AMOUNT);
        infoModel.setsMonthlyExps("25,000");
    }

    @Test
    public void testSetTransNox() {
        assertTrue(cTransNox);
    }

    @Test
    public void testSetBizIndustry() {
        assertTrue(cBizIndsx);
    }

    @Test
    public void testSetBizType() {
        assertTrue(cBizType);
    }

    @Test
    public void testSetBizSize() {
        assertTrue(cBizSize);
    }

    @Test
    public void testSetMosOrYr() {
        assertTrue(cMosYrxx);
    }

    @Test
    public void testSetProvinceId() {
        assertTrue(cProvIdxx);
    }

    @Test
    public void testSetTownId() {
        assertTrue(cTownIdxx);
    }

    @Test
    public void testGetNatureOfBusiness() {
        mViewModel.getNatureOfBusiness().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Nature of Business");
        });
    }

    @Test
    public void testGetTypeOfBusiness() {
        mViewModel.getTypeOfBusiness().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Type of Business");
        });
    }

    @Test
    public void testGetSizeOfBusiness() {
        mViewModel.getSizeOfBusiness().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Size of Business");
        });
    }

    @Test
    public void testGetLengthOfService() {
        mViewModel.getLengthOfService().observeForever(stringArrayAdapter -> {
            assertNotNull(stringArrayAdapter);
            displayArrayAdapterItem(stringArrayAdapter, "Length of Service");
        });
    }

    @Test
    public void testSave() {
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

}
