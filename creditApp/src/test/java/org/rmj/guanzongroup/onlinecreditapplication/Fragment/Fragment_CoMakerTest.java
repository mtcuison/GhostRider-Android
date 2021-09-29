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
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_CoMakerTest {

    private CoMakerModel infoModel;
    private VMCoMaker mViewModel;
    private String lName, fName, mName, suffix, nName,bDate, bTown, fbAcct, srcIncome, coRelation;
    private String primaryContact, secondaryContact, tertiaryContact;
    private String primarySimStats, secondarySimStats, tertiarySimStats;
    private String primaryContactPlan, secondaryContactPlan, tertiaryContactPlan;
    private String TransNox;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        mViewModel = new VMCoMaker(ApplicationProvider.getApplicationContext());
        infoModel = new CoMakerModel();
//        mViewModel =  mock(VMCoMaker.class);
        TransNox = "Z3TXCBMCHCAO";
        lName = "Sabiniano";
        fName = "Jonathan";
        mName = "Tamayo";
        suffix = "";
        nName= "";
        bDate= "03/06/1990";
        bTown= "0335";
        fbAcct= "";
        srcIncome = "2";
        coRelation = "2";
        primaryContact = "09452086661";
        secondaryContact = "";
        tertiaryContact = "";
        primarySimStats  = "0";
        secondarySimStats  = "0";
        tertiarySimStats  = "0";
        primaryContactPlan  = "0";
        secondaryContactPlan  = "0";
        tertiaryContactPlan  = "0";

        mViewModel.setTownID(bTown);
        mViewModel.setTransNox(TransNox);
        infoModel = new CoMakerModel(lName, fName, mName, suffix, nName,bDate, bTown, fbAcct, srcIncome, coRelation);

        if(!Objects.requireNonNull(primaryContact.isEmpty())) {
            if(Integer.parseInt(primarySimStats) == 1) {
                infoModel.setCoMobileNo(primaryContact, primarySimStats, Integer.parseInt(primaryContactPlan));
            } else {
                infoModel.setCoMobileNo(primaryContact, primarySimStats, 0);
            }
        }
        if(!Objects.requireNonNull(secondaryContact.isEmpty())) {
            if(Integer.parseInt(secondarySimStats) == 1) {
                infoModel.setCoMobileNo(secondaryContact, secondarySimStats, Integer.parseInt(secondaryContactPlan));
            } else {
                infoModel.setCoMobileNo(secondaryContact, secondarySimStats, 0);
            }
        }
        if(!Objects.requireNonNull(tertiaryContact.isEmpty())) {
            if(Integer.parseInt(tertiarySimStats)  == 1) {
                infoModel.setCoMobileNo(tertiaryContact, tertiarySimStats, Integer.parseInt(tertiaryContactPlan));
            } else {
                infoModel.setCoMobileNo(tertiaryContact, tertiarySimStats, 0);
            }
        }
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_setTransNox() {
        mViewModel.setTransNox(TransNox);
        assertEquals(TransNox, mViewModel.psTranNo.getValue());
        System.out.print("TransNo: " + mViewModel.psTranNo.getValue());

    }
    @Test
    public void test_submitCoMaker(){
        try {
            assertTrue(mViewModel.SubmitComaker(infoModel,callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @Test
    public void test_getInfoModels(){
        lName = "Sabiniano";
        fName = "Jonathan";
        mName = "Tamayo";
        suffix = "";
        nName= "";
        bDate= "03/06/1990";
        bTown= "0335";
        fbAcct= "";
        srcIncome = "2";
        coRelation = "2";
        primaryContact = "09452086661";
        secondaryContact = "";
        tertiaryContact = "";
        primarySimStats  = "0";
        secondarySimStats  = "0";
        tertiarySimStats  = "0";
        primaryContactPlan  = "0";
        secondaryContactPlan  = "0";
        tertiaryContactPlan  = "0";

        Assert.assertEquals(fName,infoModel.getCoFrstName());
        Assert.assertEquals(mName,infoModel.getCoMiddName());
        Assert.assertEquals(lName,infoModel.getCoLastName());
        Assert.assertEquals(suffix,infoModel.getCoSuffix());
        Assert.assertEquals(bDate,infoModel.getCoBrthDate());
        Assert.assertEquals(bTown,infoModel.getCoBrthPlce());

        Assert.assertEquals(fbAcct,infoModel.getCoFbAccntx());
        Assert.assertEquals(srcIncome,infoModel.getCoIncomeSource());
        Assert.assertEquals(coRelation,infoModel.getCoBorrowerRel());
        for (int x = 0; x < infoModel.getCoMobileNoQty(); x++){
            Assert.assertEquals(primaryContact,infoModel.getCoMobileNo(x));
            System.out.print("contact no" + infoModel.getCoMobileNo(x) + "\n");
        }

        System.out.print("\nFirstname: " + infoModel.getCoFrstName() + "\n");
        System.out.print("Middle Name:" + infoModel.getCoMiddName() + "\n");
        System.out.print("Lastname" + infoModel.getCoLastName() + "\n");
        System.out.print("Suffix" + infoModel.getCoSuffix() + "\n");
        System.out.print("BirthDate" + infoModel.getCoBrthDate() + "\n");
        System.out.print("BirthPlace" + infoModel.getCoBrthPlce() + "\n");
        System.out.print("Fb account" + infoModel.getCoFbAccntx() + "\n");
        System.out.print("source income" + infoModel.getCoIncomeSource() + "\n");
        System.out.print("comaker relation" + infoModel.getCoBorrowerRel() + "\n");

    }
}