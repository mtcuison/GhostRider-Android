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
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalReferenceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMOtherInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

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


    private ArrayList<PersonalReferenceInfoModel> arrayList;
    private  PersonalReferenceInfoModel poRefInfo;
    @Mock
    VMOtherInfo.ExpActionListener listener;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        mViewModel = new VMOtherInfo(ApplicationProvider.getApplicationContext());
        infoModel = new OtherInfoModel();
        refName = "Jonathan Sabiniano";
        refContact = "0945208666";
        refAddress = "Cawayan Bogtong";
        refTown = "20";
        //infoModel = new OtherInfoModel(refName, refAddress, refTown, refContact);
        mViewModel.setTransNox( "C10A02100005");
        infoModel.setTransNox("C10A02100005");
        arrayList = new ArrayList<>();
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
//        int i = 0;
//        for (int x = 0; x < 3; x++){
//            i++;
//            infoModel = new OtherInfoModel();
//            infoModel.setUnitUser("0");
//            infoModel.setUnitPrps("2");
//            infoModel.setUnitPayr("0");
//            infoModel.setSource("0");
//            infoModel.setPayrRltn("2");
//            poRefInfo = new PersonalReferenceInfoModel("Jonathan Sabiniano", "Cawayan Bogtong", "0335", "09452086661");
//            arrayList.add(poRefInfo);
//        }
//        infoModel.setPersonalReferences(arrayList);
        infoModel.setUnitUser("0");
        infoModel.setUnitPrps("2");
        infoModel.setUnitPayr("0");
        infoModel.setSource("5");
        infoModel.setPayrRltn("2");
        infoModel.setCompanyInfoSource("Guanzon Group of Companies");
        for (int i  = 0; i <= 3; i++){
            poRefInfo = new PersonalReferenceInfoModel(refName, refAddress, refTown, refContact + i);
            mViewModel.addReference(poRefInfo, new VMOtherInfo.AddPersonalInfoListener() {
                @Override
                public void OnSuccess() {
                    arrayList.add(poRefInfo);
                    System.out.println("Add Success");
                }

                @Override
                public void onFailed(String message) {
                    System.out.println(message);
                }
            });

        }
        infoModel.setPersonalReferences(arrayList);
       // assertTrue( mViewModel.SubmitOtherInfo(infoModel, Fragment_OtherInfoTest.this));

        mViewModel.setTransNox( "C10A02100005");
        infoModel.setTransNox("C10A02100005");
        Assert.assertTrue(mViewModel.SubmitOtherInfo(infoModel, Fragment_OtherInfoTest.this));

    }

    @Override
    public void onSuccess(String message) {
        System.out.println("onSuccess " + message);
    }

    @Override
    public void onFailed(String message) {
        System.out.println("onFailed " + message);
    }

    @Override
    public void onSaveSuccessResult(String args) {

        System.out.println("onSaveSuccessResult " + args);
    }

    @Override
    public void onFailedResult(String message) {
        System.out.println("onFailedResult " + message);
    }
}