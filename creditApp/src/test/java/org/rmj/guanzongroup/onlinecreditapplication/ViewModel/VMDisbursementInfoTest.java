package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.os.Build;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.rmj.g3appdriver.utils.CodeGenerator;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@RunWith(JUnit4.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMDisbursementInfoTest implements ViewModelCallBack {
    private String transnox;
    private VMDisbursementInfo mViewModel;
    private DisbursementInfoModel infoModels;


    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        infoModels = new DisbursementInfoModel();
        mViewModel =  mock(VMDisbursementInfo.class);
        transnox = new CodeGenerator().generateTransNox();
        infoModels.setTransNo(transnox);
        infoModels.setElctX("1000");
        infoModels.setWaterX("1000");
        infoModels.setFoodX("3000");
        infoModels.setLoans("5000");
        infoModels.setBankN("BDO");
        infoModels.setStypeX("1");
        infoModels.setCcBnk("BDO");
        infoModels.setLimitCC("20000");
        infoModels.setYearS("2");
    }
    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_setCreditApplicantInfo() throws Exception{
        doReturn(true).when(mViewModel).SubmitApplicationInfo(infoModels, VMDisbursementInfoTest.this);
        assertTrue(mViewModel.SubmitApplicationInfo(infoModels, VMDisbursementInfoTest.this));
    }

    @Override
    public void onSaveSuccessResult(String args) {
        System.out.print("Success");
    }

    @Override
    public void onFailedResult(String message) {
        System.out.print(message);
    }
}