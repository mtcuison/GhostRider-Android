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
        infoModel.setCoMobileNo("09452086664", "0", 0);
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
    public void test_isDataValid() {
        Assert.assertTrue(infoModel.isCoMakerInfoValid());
    }
}