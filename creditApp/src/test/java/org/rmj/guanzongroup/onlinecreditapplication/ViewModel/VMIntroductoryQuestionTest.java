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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.FAKE_STRING;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.STRING_ONE;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PurchaseInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMIntroductoryQuestionTest {

    private PurchaseInfoModel infoModel;
    private VMIntroductoryQuestion mViewModel;
    private String sAppTypex;
    private String sCustTypex;
    private String dTargetDte;
    private String sBranchCde;
    private String sBrandIDxx;
    private String sModelIDxx;
    private double sDownPaymt;
    private int sAccTermxx;
    private double sMonthlyAm;

    private String TransNox;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        mViewModel = new VMIntroductoryQuestion(ApplicationProvider.getApplicationContext());
        infoModel = new PurchaseInfoModel();
        sBranchCde = "M001";
        sAppTypex = "0";
        sCustTypex = "0";
        dTargetDte = "September 30, 2021";
        sBrandIDxx = "YAMAHA";
        sModelIDxx = "M00121063";
        sDownPaymt = 30000;
        sAccTermxx = 36;
        sMonthlyAm = 4885;

    }

    @After
    public void tearDown() {
        infoModel = null;
        mViewModel = null;
    }
    @Test
    public void test_createNewApplication(){
        setInfoModel();
        mViewModel.CreateNewApplication(infoModel, new ViewModelCallBack() {
            @Override
            public void onSaveSuccessResult(String args) {
                System.out.println(args);
            }

            @Override
            public void onFailedResult(String message) {
                System.out.println(message);
            }
        });
    }
    private void setInfoModel() {
        infoModel.setsCustTypex(sCustTypex);
        infoModel.setdTargetDte(dTargetDte);
        infoModel.setsModelIDxx(sModelIDxx);
        infoModel.setsBrandIDxx(sBrandIDxx);
        infoModel.setsAppTypex(sAppTypex);
        infoModel.setsDownPaymt(sDownPaymt);
        infoModel.setsMonthlyAm(sMonthlyAm);
        infoModel.setsAccTermxx(sAccTermxx);
        infoModel.setsBranchCde(sBranchCde);
        mViewModel.setBanchCde(sBranchCde);
    }
}