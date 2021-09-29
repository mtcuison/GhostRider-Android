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

import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.APPLICATION;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_CODE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.TRANSACTION_NO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.getDummyCreditApp;

import android.os.Build;

import junit.framework.TestCase;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ResidenceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMResidenceInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest = Config.NONE)
public class Fragment_ResidenceInfoTest extends TestCase {
    private ResidenceInfoModel infoModel;
    private VMResidenceInfo mViewModel;
    private String cTransNox;
    private boolean cDetlInfo;

    @Before
    public void setUp() {
        infoModel = new ResidenceInfoModel();
        mViewModel = new VMResidenceInfo(APPLICATION);
        cDetlInfo = mViewModel.setGOCasDetailInfo(getDummyCreditApp());
        cTransNox = TRANSACTION_NO;
        mViewModel.setTransNox(TRANSACTION_NO);
        mViewModel.setProvinceID(FAKE_CODE);
        mViewModel.setTownID(FAKE_CODE);
        mViewModel.setBarangayID(FAKE_CODE);

        mViewModel.setPermanentProvinceID(FAKE_CODE);
        mViewModel.setPermanentTownID(FAKE_CODE);
        mViewModel.setPermanentBarangayID(FAKE_CODE);
        setInfoModel();
        setInfoModelPermanent();
    }



    @Test
    public void test_setDetailInfo() {
        assertTrue(cDetlInfo);
    }

    @Test
    public void test_Save() {
        try {
            assertTrue(mViewModel.SaveResidenceInfo(infoModel, new ViewModelCallBack() {
                @Override
                public void onSaveSuccessResult(String args) {
                    System.out.println(args);
                }

                @Override
                public void onFailedResult(String message) {
                    System.out.println(message);
                }
            }));
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }

    }

    @After
    public void tearDown() {
        infoModel = null;
        mViewModel = null;
    }

    private void setInfoModel() {
        infoModel.setLandMark(FAKE_STRING);
        infoModel.setHouseNox(STRING_ONE);
        infoModel.setAddress1(FAKE_STRING);
        infoModel.setAddress2(FAKE_STRING);
        infoModel.setHouseOwn("0");
        infoModel.setOwnerRelation("");
        infoModel.setHouseHold("0");
        infoModel.setHouseType("1");
        infoModel.setHasGarage("1");
        infoModel.setLenghtOfStay("10");
        infoModel.setIsYear("0");
    }
    private void setInfoModelPermanent() {
        infoModel.setPermanentLandMark(FAKE_STRING);
        infoModel.setPermanentHouseNo(STRING_ONE);
        infoModel.setPermanentAddress1(FAKE_STRING);
        infoModel.setPermanentAddress2(FAKE_STRING);
    }
}
