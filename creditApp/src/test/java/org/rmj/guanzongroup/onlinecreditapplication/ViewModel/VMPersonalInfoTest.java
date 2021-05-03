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

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_CODE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ZERO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.TRANSACTION_NO;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.displayValue;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.getDummyCreditApp;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest = Config.NONE)
public class VMPersonalInfoTest extends TestCase {
    private static final VMPersonalInfo mViewModel = new VMPersonalInfo(ApplicationProvider.getApplicationContext());
    private static final PersonalInfoModel infoModel = new PersonalInfoModel();
    private boolean cTransNox;

    @Before
    public void setUp() {
        cTransNox = mViewModel.setTransNox(TRANSACTION_NO);
        mViewModel.setGOCasDetailInfo(getDummyCreditApp());
        mViewModel.setGender(STRING_ONE);
        mViewModel.setCvlStats(STRING_ONE);
        mViewModel.setCitizenship(FAKE_CODE);
        mViewModel.setTownID(FAKE_CODE);
    }

    @Test
    public void test_setTransNox() {
        assertTrue(cTransNox);
    }

//    @Test
//    public void test_savePersonalInfo() {
//        setInfoModel();
//        assertTrue(mViewModel.SavePersonalInfo(infoModel, new ViewModelCallBack() {
//            @Override
//            public void onSaveSuccessResult(String args) {
//                displayValue("Success Message", args);
//            }
//
//            @Override
//            public void onFailedResult(String message) {
//                displayValue("Error Message", message);
//            }
//        }));
//    }

    private void setInfoModel() {
        infoModel.setLastName("Dela Cruz");
        infoModel.setFrstName("Juan");
        infoModel.setMiddName("Sison");
        infoModel.setNickName("John");
        infoModel.setBrthDate("01/01/1995");
        infoModel.setBrthPlce(FAKE_CODE);
        infoModel.setGender(STRING_ONE);
        infoModel.setCvlStats(STRING_ZERO);
        infoModel.setCitizenx(FAKE_CODE);
        infoModel.setMotherNm("Jesusa Dela Cruz");
        infoModel.setMobileNo("09111111111", STRING_ZERO, 0);
    }
}