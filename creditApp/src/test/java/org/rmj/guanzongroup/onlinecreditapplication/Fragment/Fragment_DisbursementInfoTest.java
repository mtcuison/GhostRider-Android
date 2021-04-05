package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.g3appdriver.utils.CodeGenerator;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMDisbursementInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_DisbursementInfoTest implements ViewModelCallBack{
    private String transnox;
    private VMDisbursementInfo mViewModel;
    private DisbursementInfoModel infoModels;

    @Mock
    ViewModelCallBack callBack;

    @Before
    public void setUp() throws Exception {
        mViewModel = new VMDisbursementInfo(ApplicationProvider.getApplicationContext());
        infoModels = new DisbursementInfoModel();
        transnox = new CodeGenerator().generateTransNox();

        infoModels.setTransNo("Z3TXCBMCHCAO");
        infoModels.setElctX("1000");
        infoModels.setWaterX("1000");
        infoModels.setFoodX("3000");
        infoModels.setLoans("5000");
        infoModels.setBankN("BDO");
        infoModels.setStypeX("1");
        infoModels.setCcBnk("BDO");
        infoModels.setLimitCC("20000");
        infoModels.setYearS("2");
        mViewModel.setTransNox("Z3TXCBMCHCAO");

    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_setCreditApplicantInfo() throws Exception{

        Assert.assertEquals("Z3TXCBMCHCAO" ,mViewModel.getTransNox() );
        Assert.assertEquals(Double.parseDouble("1000") ,infoModels.getElctX(),0.0 );
        Assert.assertEquals(Double.parseDouble("1000") ,infoModels.getWaterX(),0.0 );
        Assert.assertEquals(Double.parseDouble("3000") ,infoModels.getFoodX(),0.0 );
        Assert.assertEquals(Double.parseDouble("5000") ,infoModels.getLoans() ,0.0);
        Assert.assertEquals("BDO" ,infoModels.getBankN() );
        Assert.assertEquals("1" ,infoModels.getStypeX() );
        Assert.assertEquals("BDO" ,infoModels.getCcBnk() );
        Assert.assertEquals(Double.parseDouble("20000") ,infoModels.getLimitCC() ,0.0);
        Assert.assertEquals(2 ,infoModels.getYearS());

        System.out.print("\nTransNox : " + mViewModel.getTransNox() + "\n");
        System.out.print("\nElectric Bill : " + infoModels.getElctX() + "\n");
        System.out.print("Water Bill : " + infoModels.getWaterX() + "\n");
        System.out.print("Food Allowance : " + infoModels.getFoodX() + "\n");
        System.out.print("Loans : " + infoModels.getLoans() + "\n");
        System.out.print("Bank Name : " + infoModels.getBankN() + "\n");
        System.out.print("Type index : " + infoModels.getStypeX() + "\n");
        System.out.print("Credit Card " + infoModels.getCcBnk() + "\n");
        System.out.print("Credit Card Limit" + infoModels.getLimitCC() + "\n");
        System.out.print("Credit Card Years" + infoModels.getYearS() + "\n");

        Assert.assertTrue(mViewModel.SubmitApplicationInfo(infoModels, Fragment_DisbursementInfoTest.this));
    }


    @Override
    public void onSaveSuccessResult(String args) {
        System.out.println("onSaveSuccessResult" + args);
    }

    @Override
    public void onFailedResult(String message) {
        System.out.println("onSaveSuccessResult" + message);
    }
}