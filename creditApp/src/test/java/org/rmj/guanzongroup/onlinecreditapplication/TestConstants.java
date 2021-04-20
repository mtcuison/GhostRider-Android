package org.rmj.guanzongroup.onlinecreditapplication;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;

public class TestConstants {
    public static final Application APPLICATION = ApplicationProvider.getApplicationContext();
    public static final String TRANSACTION_NO = "M021210014";
    public static final String STRING_ZERO = "0";
    public static final String STRING_ONE = "1";
    public static final String STRING_TWO = "2";
    public static final String FAKE_STRING = "ABCDE12345";
    public static final String FAKE_JSON = "{\"key\":\"value\"}";
    public static final String FAKE_CODE = "00123";
    public static final String FAKE_NUMBER = "0123456789";
    public static final String FAKE_STRING_AMOUNT = "50,000";
    public static final String FAKE_DATE = "01-01-1990";
    public static final String FAKE_YEAR = "1999";
    public static final String FAKE_COMPANY = "Guanzon Group of Companies";
    public static final double FAKE_DBLE_AMOUNT = 20000.00;

    public static ECreditApplicantInfo getDummyCreditApp() {
        ECreditApplicantInfo loCredit = new ECreditApplicantInfo();
        loCredit.setTransNox(TRANSACTION_NO);
        loCredit.setClientNm(FAKE_NUMBER);
        loCredit.setDetlInfo(FAKE_JSON);
        loCredit.setPurchase(FAKE_STRING);
        loCredit.setApplInfo(FAKE_JSON);
        loCredit.setResidnce(FAKE_JSON);
        loCredit.setAppMeans(FAKE_JSON);
        loCredit.setEmplymnt(FAKE_JSON);
        loCredit.setBusnInfo(FAKE_JSON);
        loCredit.setFinancex(FAKE_JSON);
        loCredit.setPensionx(FAKE_JSON);
        loCredit.setOtherInc(FAKE_JSON);
        loCredit.setSpousexx(FAKE_JSON);
        loCredit.setSpsResdx(FAKE_JSON);
        loCredit.setSpsMeans(FAKE_JSON);
        loCredit.setSpsEmplx(FAKE_JSON);
        loCredit.setSpsBusnx(FAKE_JSON);
        loCredit.setSpsPensn(FAKE_JSON);
        loCredit.setDisbrsmt(FAKE_JSON);
        loCredit.setDependnt(FAKE_JSON);
        loCredit.setProperty(FAKE_JSON);
        loCredit.setOthrInfo(FAKE_JSON);
        loCredit.setComakerx(FAKE_JSON);
        loCredit.setCmResidx(FAKE_JSON);
        loCredit.setIsSpouse(STRING_ONE);
        loCredit.setIsComakr(STRING_ONE);
        loCredit.setBranchCd(FAKE_CODE);
        loCredit.setAppliedx(STRING_ONE);
        loCredit.setTransact(FAKE_DATE);
        loCredit.setCreatedx(FAKE_DATE);
        loCredit.setDownPaym(FAKE_DBLE_AMOUNT);
        loCredit.setTranStat(STRING_ZERO);
        return loCredit;
    }

}
