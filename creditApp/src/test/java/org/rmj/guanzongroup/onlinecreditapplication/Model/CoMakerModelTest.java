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

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import android.os.Build;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;
@RunWith(JUnit4.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class CoMakerModelTest {
    private CoMakerModel infoModel;
    private List<CoMakerModel.CoMakerMobileNo> mobileNoList;
    private String fname, lname, mname, suffix, nname, bdate, bplace, fb, income, rel;
    private String message;
    @Before
    public void setUp() throws Exception {
        infoModel = new CoMakerModel();
        fname = "Jonathan";
        lname = "Sabiniano";
        mname = "Tamayo";
        suffix = "";
        nname = "";
        bdate = "03/06/1990";
        bplace = "0335";
        fb = "Jonathan Sabiniano";
        income = "1";
        rel = "1";

        infoModel = new CoMakerModel(lname,
                fname,
                mname,
                suffix,
                nname,
                bdate,
                bplace,
                fb,
                income,
                rel);
        mobileNoList = new ArrayList<>();
        infoModel.setCoFrstName(fname);
        infoModel.setCoMiddName(mname);
        infoModel.setCoLastName(lname);
        infoModel.setCoSuffix(suffix);
        infoModel.setCoNickName(nname);
        infoModel.setcoBrthDate(bdate);
        infoModel.setcoBrthPlce(bplace);
        infoModel.setCoFbAccntx(fb);
        infoModel.setCoBorrowerRel(rel);
        infoModel.setCoIncomeSource(income);
        infoModel.setCoMobileNo("09452086661", "0", 0);
        infoModel.setCoMobileNo("09452086661", "0", 0);
        infoModel.setCoMobileNo("09452086663", "0", 0);

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void test_getCoMobileNoQty(){
        Assert.assertEquals(3,infoModel.getCoMobileNoQty());
    }
    @Test
    public void test_isValidContact(){
        Assert.assertTrue(infoModel.isContactValid());
        Assert.assertEquals(3,infoModel.getCoMobileNoQty());
        for (int i = 0; i < infoModel.getCoMobileNoQty(); i++){
            String contact = "";
            if (i == 0){
                contact = "Primary Contact ";
            }
            if (i == 1){
                contact = "Secondary Contact ";
            }
            if (i == 2){
                contact = "Tertiary Contact ";
            }
            System.out.println(contact + infoModel.getCoMobileNo(i));
        }
    }
    @Test
    public void test_isValidCoMakerRelation() {
        Assert.assertTrue(infoModel.isBorrowerRel());
        Assert.assertEquals(rel,infoModel.getCoBorrowerRel());
        System.out.println("Relation Ship index :" + infoModel.getCoBorrowerRel());
    }
    @Test
    public void test_isValidLastName() {
        Assert.assertTrue(infoModel.isLastNameValid());
        Assert.assertEquals(lname,infoModel.getCoLastName());
        System.out.println("LastName :" + infoModel.getCoLastName());
    }
    @Test
    public void test_isValidFirstName() {
        Assert.assertTrue(infoModel.isFrstNameValid());
        Assert.assertEquals(fname,infoModel.getCoFrstName());
        System.out.println("FirstName :" + infoModel.getCoFrstName());
    }

    @Test
    public void test_isValidMiddleName() {
        Assert.assertTrue(infoModel.isMiddNameValid());
        Assert.assertEquals(mname,infoModel.getCoMiddName());
        System.out.println("Middle Name :" + infoModel.getCoMiddName());
    }
    @Test
    public void test_isValidBirthDate() {
        Assert.assertTrue(infoModel.isBirthdateValid());
        Assert.assertEquals(bdate,infoModel.getCoBrthDate());
        System.out.println("Birth Date :" + infoModel.getCoBrthDate());
    }

    @Test
    public void test_isValidBirthPlace() {
        Assert.assertTrue(infoModel.isBirthPlaceValid());
        Assert.assertEquals(bplace,infoModel.getCoBrthPlce());
        System.out.println("TownID :" + infoModel.getCoBrthPlce());
    }

    @Test
    public void test_isValidIncomeSource() {
        Assert.assertTrue(infoModel.isIncomeSource());
        Assert.assertEquals(income,infoModel.getCoIncomeSource());
        System.out.println("Income Source index :" + infoModel.getCoIncomeSource());
    }

    @Test
    public void test_isDataValid() {
        Assert.assertTrue(infoModel.isCoMakerInfoValid());
    }
}