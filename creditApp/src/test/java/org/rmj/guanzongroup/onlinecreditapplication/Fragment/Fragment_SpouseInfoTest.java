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

import androidx.core.widget.TextViewCompat;

import junit.framework.TestCase;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSpouseInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.APPLICATION;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_CODE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_DATE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_NUMBER;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_TWO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ZERO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.TRANSACTION_NO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.displayArrayAdapterItem;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.displayValue;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.getDummyCreditApp;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest = Config.NONE)
public class Fragment_SpouseInfoTest extends TestCase {
    private static final SpouseInfoModel infoModel = new SpouseInfoModel();
    private static final VMSpouseInfo mViewModel = new VMSpouseInfo(APPLICATION);
    private boolean cDetlInfo, cTransNox, cProvIdxx, cTownIdxx, cCitizenx;
    private String pnMobile1 = "09111111111",
            pnMobile2 = "09222222222",
            pnMobile3 = "09333333333";

    @Before
    public void setUp() {
        cDetlInfo = mViewModel.setDetailInfo(getDummyCreditApp());
        cTransNox = mViewModel.setTransNox(TRANSACTION_NO);
        cProvIdxx = mViewModel.setProvinceID(FAKE_CODE);
        cTownIdxx = mViewModel.setTownID(FAKE_CODE);
        cCitizenx = mViewModel.setCitizenship(STRING_TWO);
        mViewModel.setLsMobile1(pnMobile1);
        mViewModel.setLsMobile2(pnMobile2);
        mViewModel.setLsMobile3(pnMobile3);
    }

    @Test
    public void test_setTransNox() {
        assertTrue(cTransNox);
    }

    @Test
    public void test_setDetailInfo() {
        assertTrue(cDetlInfo);
    }

    @Test
    public void test_getMobileNoType() {
        mViewModel.getMobileNoType().observeForever(value -> {
            assertNotNull(value);
            displayArrayAdapterItem(value, "Mobile No. Type");
        });
    }

    @Test
    public void test_setProvinceID() {
        assertTrue(cProvIdxx);
    }

    @Test
    public void test_setTownID() {
        assertTrue(cTownIdxx);
    }

    @Test
    public void test_setCitizenship() {
        assertTrue(cCitizenx);
    }

    @Test
    public void test_getMobileNo1() {
        mViewModel.getMobileNo1().observeForever(mobile1 -> {
            displayValue("Mobile No. 1", mobile1);
            assertEquals(pnMobile1, mobile1);
        });
    }

    @Test
    public void test_getMobileNo2() {
        mViewModel.getMobileNo2().observeForever(mobile2 -> {
            displayValue("Mobile No. 2", mobile2);
            assertEquals(pnMobile2, mobile2);
        });
    }

    @Test
    public void test_getMobileNo3() {
        mViewModel.getMobileNo3().observeForever(mobile3 -> {
            displayValue("Mobile No.3", mobile3);
            assertEquals(pnMobile3, mobile3);
        });
    }

    @Test
    public void test_Save() {
        setInfoModel();
        assertTrue(mViewModel.Save(infoModel, new ViewModelCallBack() {
            @Override
            public void onSaveSuccessResult(String args) {
                displayValue("TEST SUCCESS", args);
            }

            @Override
            public void onFailedResult(String message) {
                displayValue("TEST ERROR", message);
            }
        }));
    }

    private static void setInfoModel() {
        infoModel.setLastName(FAKE_STRING);
        infoModel.setFrstName(FAKE_STRING);
        infoModel.setMiddName(FAKE_STRING);
        infoModel.setSuffix("Jr.");
        infoModel.setNickName(FAKE_STRING);
        infoModel.setBirthDate(FAKE_DATE);
        infoModel.setBirthPlace(FAKE_CODE);
        infoModel.setCitizenx(STRING_ONE);
        infoModel.setMobileNo("09111111111", STRING_ZERO, 0);
        infoModel.setPhoneNo(FAKE_NUMBER);
        infoModel.setEmailAdd(FAKE_STRING);
        infoModel.setFBacct(FAKE_STRING);
        infoModel.setVbrAcct(FAKE_NUMBER);
        infoModel.setProvNme(FAKE_CODE);
        infoModel.setTownNme(FAKE_CODE);
    }
}
