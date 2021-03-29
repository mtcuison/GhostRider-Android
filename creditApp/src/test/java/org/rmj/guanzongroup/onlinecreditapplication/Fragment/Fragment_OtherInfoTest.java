package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Build;
import android.util.Log;

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
public class Fragment_OtherInfoTest implements ViewModelCallBack, VMOtherInfo.ExpActionListener {
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
        //infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
        mViewModel.setTransNox( "Z3TXCBMCHCAO");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_addReferences(){
//            assertTrue(mViewModel.addReference(infoModel, listener));//
       // assertEquals(true, mViewModel.addReference(infoModel, Fragment_OtherInfoTest.this));


    }
    @Test
    public void test_getCountReference(){
//        infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
//        for (int i  = 0; i <= 3; i++){
//            mViewModel.addReference(infoModel, Fragment_OtherInfoTest.this);
//        }
//
//        System.out.println("Reference size = " + mViewModel.getPersonalReference().getValue().size());
//        assertEquals(true, mViewModel.isReferenceValid());

    }
    @Test
    public void test_submitOtherInfo(){

        infoModel.setUnitUser("1");
        infoModel.setUnitPrps("1");
        infoModel.setUnitPayr("1");
        infoModel.setUnitUser("1");
        infoModel.setCompanyInfoSource("Guanzon Group of Companies");
        for (int i  = 0; i <= 3; i++){
            //mViewModel.addReference(infoModel, Fragment_OtherInfoTest.this);
        }
        assertTrue( mViewModel.SubmitOtherInfo(infoModel, Fragment_OtherInfoTest.this));
        assertEquals(true, mViewModel.SubmitOtherInfo(infoModel, Fragment_OtherInfoTest.this));

    }

    @Override
    public void onSuccess(String message) {
        System.out.println(message);
    }

    @Override
    public void onFailed(String message) {
        System.out.println(message);
    }

    @Override
    public void onSaveSuccessResult(String args) {

        System.out.println(args);
    }

    @Override
    public void onFailedResult(String message) {
        System.out.println(message);
    }
}