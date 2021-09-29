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

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMCoMaker;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMPersonalInfo;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Fragment_PersonalInfoTest {

    private PersonalInfoModel infoModel;
    private VMPersonalInfo mViewModel;
    private String lName, fName, mName, suffix, nName,bDate, bTown,gender, civil, fbAcct, srcIncome, coRelation;
    private String primaryContact, secondaryContact, tertiaryContact;
    private String primarySimStats, secondarySimStats, tertiarySimStats;
    private String primaryContactPlan, secondaryContactPlan, tertiaryContactPlan;
    private String TransNox;

    private String Citizenx;
    private String MotherNm;

    private String PhoneNox;
    private String EmailAdd;
    private String FbAccntx;
    private String VbrAccnt;
    private String ImgPath;

    //for save instance state
    private String ProvNme;
    private String TownNme;

    private String message;
    @Mock
    ViewModelCallBack callBack;
    @Before
    public void setUp() throws Exception {
        mViewModel = new VMPersonalInfo(ApplicationProvider.getApplicationContext());
        infoModel = new PersonalInfoModel();
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
        gender = "0";
        civil = "0";
        Citizenx = "01";
        MotherNm = "";
        primaryContact = "09452086661";
        secondaryContact = "";
        tertiaryContact = "";
        primarySimStats  = "0";
        secondarySimStats  = "0";
        tertiarySimStats  = "0";
        primaryContactPlan  = "0";
        secondaryContactPlan  = "0";
        tertiaryContactPlan  = "0";


    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test_setTransNox() {
        mViewModel.setTransNox(TransNox);
        assertEquals(TransNox, mViewModel.TRANSNOX.getValue());
        System.out.print("TransNo: " + mViewModel.TRANSNOX.getValue());

    }
    @Test
    public void test_submitPersonalInfo(){
        try {
            infoModel.setLastName(lName);
            infoModel.setFrstName(fName);
            infoModel.setMiddName(mName);
            infoModel.setSuffix(suffix);
            infoModel.setNickName(nName);
            infoModel.setBrthDate(bDate);
            infoModel.setMotherNm("");
            infoModel.setGender(gender);
            infoModel.setCvlStats(civil);
            infoModel.clearMobileNo();

            mViewModel.setTownID(bTown);
            mViewModel.setTransNox(TransNox);
            mViewModel.setGender(gender);
            mViewModel.setCvlStats(civil);
            mViewModel.setCitizenship(Citizenx);
            if(!Objects.requireNonNull(primaryContact.isEmpty())) {
                if(Integer.parseInt(primarySimStats) == 1) {
                    infoModel.setMobileNo(primaryContact, primarySimStats, Integer.parseInt(primaryContactPlan));
                } else {
                    infoModel.setMobileNo(primaryContact, primarySimStats, 0);
                }
            }
            if(!Objects.requireNonNull(secondaryContact.isEmpty())) {
                if(Integer.parseInt(secondarySimStats) == 1) {
                    infoModel.setMobileNo(secondaryContact, secondarySimStats, Integer.parseInt(secondaryContactPlan));
                } else {
                    infoModel.setMobileNo(secondaryContact, secondarySimStats, 0);
                }
            }
            if(!Objects.requireNonNull(tertiaryContact.isEmpty())) {
                if(Integer.parseInt(tertiarySimStats)  == 1) {
                    infoModel.setMobileNo(tertiaryContact, tertiarySimStats, Integer.parseInt(tertiaryContactPlan));
                } else {
                    infoModel.setMobileNo(tertiaryContact, tertiarySimStats, 0);
                }
            }
            assertTrue(mViewModel.SavePersonalInfo(infoModel, callBack));
        }catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}