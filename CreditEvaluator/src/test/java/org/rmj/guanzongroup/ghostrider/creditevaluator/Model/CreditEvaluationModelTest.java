package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

import android.app.Application;
import android.os.Build;


import androidx.test.runner.AndroidJUnit4;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.CIConstantsTest;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

@RunWith(AndroidJUnit4.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE, application = Application.class)
public class CreditEvaluationModelTest {
    private String sTransNox;
    private String dTransact;
    private String sCredInvx;
    private String sCompnyNm;
    private String sSpouseNm;
    private String sAddressx;
    private String sMobileNo;
    private String sQMAppCde;
    private String sModelNme;
    private String nDownPaym;
    private String nAcctTerm;
    private String cTranStat;
    private String dTimeStmp;
    private List<CreditEvaluationModel> infoList;
    private String FAKE_DATA;
    @Before
    public void setUp() throws Exception {
        infoList = CIConstantsTest.getDataList();

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getFAKEDATA() {

        for (int i = 0; i < infoList.size(); i++) {

            CreditEvaluationModel loan = new CreditEvaluationModel();
            sTransNox = infoList.get(i).getsTransNox();
            dTransact = infoList.get(i).getdTransact();
            sCredInvx = infoList.get(i).getsCredInvx();
            sCompnyNm = infoList.get(i).getsCompnyNm();
            sSpouseNm = infoList.get(i).getsSpouseNm();
            sAddressx = infoList.get(i).getsAddressx();
            sMobileNo = infoList.get(i).getsMobileNo();
            sQMAppCde = infoList.get(i).getsQMAppCde();
            sModelNme = infoList.get(i).getsModelNme();
            nDownPaym = infoList.get(i).getnDownPaym();
            nAcctTerm = infoList.get(i).getnAcctTerm();
            cTranStat = infoList.get(i).getcTranStat();
            dTimeStmp = infoList.get(i).getdTimeStmp();
//              MODEL INITIALIZATION
            loan.setsTransNox(sTransNox);
            loan.setdTransact(dTransact);
            loan.setsCredInvx(sCredInvx);
            loan.setsCompnyNm(sCompnyNm);
            loan.setsSpouseNm(sSpouseNm);
            loan.setsAddressx(sAddressx);
            loan.setsMobileNo(sMobileNo);
            loan.setsQMAppCde(sQMAppCde);
            loan.setsModelNme(sModelNme);
            loan.setnDownPaym(nDownPaym);
            loan.setnAcctTerm(nAcctTerm);
            loan.setcTranStat(cTranStat);
            loan.setdTimeStmp(dTimeStmp);

//              ASSERTION
            Assert.assertEquals(sTransNox, loan.getsTransNox());
            Assert.assertEquals(dTransact, infoList.get(i).getdTransact());
            Assert.assertEquals(sCredInvx, loan.getsCredInvx());
            Assert.assertEquals(sCompnyNm, loan.getsCompnyNm());
            Assert.assertEquals(sSpouseNm, loan.getsSpouseNm());
            Assert.assertEquals(sAddressx, loan.getsAddressx());
            Assert.assertEquals(sMobileNo, loan.getsMobileNo());
            Assert.assertEquals(sQMAppCde, loan.getsQMAppCde());
            Assert.assertEquals(sModelNme, loan.getsModelNme());
            Assert.assertEquals(nDownPaym, loan.getnDownPaym());
            Assert.assertEquals(nAcctTerm, loan.getnAcctTerm());
            Assert.assertEquals(cTranStat, loan.getcTranStat());
            Assert.assertEquals(dTimeStmp, infoList.get(i).getdTimeStmp());

//                PRINT CLIENT DETAIL
            System.out.println("sTransNox = " +  loan.getsTransNox());
            System.out.println("dTransact = " +  infoList.get(i).getdTransact());
            System.out.println("sCredInvx = " +  loan.getsCredInvx());
            System.out.println("sCompnyNm = " +  loan.getsCompnyNm());
            System.out.println("sSpouseNm = " +  loan.getsSpouseNm());
            System.out.println("sAddressx = " +  loan.getsAddressx());
            System.out.println("sMobileNo = " +  loan.getsMobileNo());
            System.out.println("sQMAppCde = " +  loan.getsQMAppCde());
            System.out.println("sModelNme  = " +  loan.getsModelNme());
            System.out.println("nDownPaym = " +  loan.getnDownPaym());
            System.out.println("nAcctTerm = " +  loan.getnAcctTerm());
            System.out.println("cTranStat = " +  loan.getcTranStat());
            System.out.println("dTimeStmp = " + infoList.get(i).getdTimeStmp() + "\n");
        }

    }


}