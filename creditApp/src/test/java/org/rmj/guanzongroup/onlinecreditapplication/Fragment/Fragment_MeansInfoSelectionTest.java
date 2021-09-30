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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.rmj.guanzongroup.onlinecreditapplication.TestConstants.getDummyCreditApp;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMMeansInfoSelection;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_MeansInfoSelectionTest {

    private VMMeansInfoSelection.MeansInfo infoModel;
    private VMMeansInfoSelection mViewModel;
    private String TransNox, employeed, sEmployeed, financexx, pensionxx;


    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        mViewModel = new VMMeansInfoSelection(ApplicationProvider.getApplicationContext());
        infoModel = new VMMeansInfoSelection.MeansInfo();
//        mViewModel =  mock(VMCoMaker.class);
        mViewModel.setGOCasDetailInfo(getDummyCreditApp());
        TransNox = "Z3TXCBMCHCAO";
        employeed = "1";
        sEmployeed = "0";
        financexx = "0";
        pensionxx = "0";

        setInfoModel();
    }

    @After
    public void tearDown() throws Exception {

    }
    @Test
    public void test_saveMeansInfo(){
        mViewModel.SaveMeansInfo(infoModel, new ViewModelCallBack() {
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
        infoModel.setEmployed(employeed);
        infoModel.setSelfEmployed(sEmployeed);
        infoModel.setFinance(financexx);
        infoModel.setPension(pensionxx);
    }
  
}