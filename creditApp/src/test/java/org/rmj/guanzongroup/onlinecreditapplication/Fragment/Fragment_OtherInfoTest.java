package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.util.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMOtherInfo;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Fragment_OtherInfoTest   {
    String refName;
    String refContact;
    String refAddress;
    String refTown;
    private OtherInfoModel infoModel;
    private VMOtherInfo mViewModel;
    @Mock
    VMOtherInfo.ExpActionListener listener;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        infoModel = new OtherInfoModel();
        mViewModel =  mock(VMOtherInfo.class);
        refName = "Jonathan Sabiniano";
        refContact = "09452086661";
        refAddress = "Cawayan Bogtong";
        refTown = "20";
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_addReferences(){
        OtherInfoModel otherInfos = new OtherInfoModel(refName, refAddress, refTown, refContact);
        try{
            when(mViewModel.addReference(otherInfos,listener)).thenReturn(true);
            assertTrue(mViewModel.addReference(otherInfos, listener));
            assertEquals(true, mViewModel.addReference(otherInfos, listener));

        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void test_submitOtherInfo(){
        try{
            infoModel.setUnitUserModel("1");
            infoModel.setUserBuyerModel("1");
            infoModel.setUserUnitPurposeModel("1");
            infoModel.setMonthlyPayerModel("1");
            infoModel.setPayer2BuyerModel("1");

            infoModel.setSourceModel("1");
            infoModel.setCompanyInfoSourceModel("");

            when(mViewModel.SubmitOtherInfo(infoModel, callBack)).thenReturn(true);
            assertTrue( mViewModel.SubmitOtherInfo(infoModel, callBack));
            assertEquals(true, mViewModel.SubmitOtherInfo(infoModel, callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}