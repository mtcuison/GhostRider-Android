package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIDisbursementInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIDisbursement;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIResidenceInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class Fragment_CIDisbursementInfoTest {

    private String ciDbmWater;
    private String ciDbmElectricity;
    private String ciDbmFood;
    private String ciDbmLoans;
    private String TransNox;

    private String ciDbmEducation;
    private String ciDbmOthers;
    private String ciDbmTotalExpenses;
    private CIDisbursementInfoModel infoModel;

    private VMCIDisbursement mViewModel;

    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        infoModel = new CIDisbursementInfoModel();
        mViewModel = new VMCIDisbursement(ApplicationProvider.getApplicationContext());
        TransNox = "C0YNQ2100035";
        mViewModel.setsTransNox(TransNox);
        ciDbmWater = "500";
        ciDbmElectricity = "1800";
        ciDbmFood = "3000";
        ciDbmLoans = "3000";
        ciDbmEducation = "400";
        ciDbmOthers = "200";
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_saveCIDisbursement(){
        try {
            mViewModel.setsTransNox(TransNox);

            infoModel.setCiDbmWater(ciDbmWater);
            infoModel.setCiDbmElectricity(ciDbmElectricity);
            infoModel.setCiDbmFood(ciDbmFood);
            infoModel.setCiDbmLoans(ciDbmLoans);
            infoModel.setCiDbmOthers(ciDbmOthers);
            infoModel.setCiDbmEducation(ciDbmEducation);
            assertTrue(mViewModel.saveCIDisbursement(infoModel,callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}