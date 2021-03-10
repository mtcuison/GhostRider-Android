package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMOtherInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
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
        mViewModel = new VMOtherInfo(ApplicationProvider.getApplicationContext());
        infoModel = new OtherInfoModel();
        refName = "Jonathan Sabiniano";
        refContact = "09452086661";
        refAddress = "Cawayan Bogtong";
        refTown = "20";
        infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_addReferences(){
        infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
        infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
        infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
        try{
            assertTrue(mViewModel.addReference(infoModel, listener));
            assertEquals(true, mViewModel.addReference(infoModel, listener));

        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void test_getCountReference(){
        infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
        infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
        infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
        assertEquals(true, mViewModel.isReferenceValid());

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

            assertTrue( mViewModel.SubmitOtherInfo(infoModel, callBack));
            assertEquals(true, mViewModel.SubmitOtherInfo(infoModel, callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

}