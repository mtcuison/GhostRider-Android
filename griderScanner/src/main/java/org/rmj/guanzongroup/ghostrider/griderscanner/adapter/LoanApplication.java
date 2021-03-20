package org.rmj.guanzongroup.ghostrider.griderscanner.adapter;

import android.annotation.SuppressLint;
import android.view.View;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

public class LoanApplication implements Serializable {
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

    public LoanApplication() {
    }


//    public String getDateSent() {
//        return new FormatUIText().getParseDateTime(dDateSent);
//    }
//
//    public void setDateSent(String dDateSent) {
//        this.dDateSent = dDateSent;
//    }

//    public String getDateApproved() throws ParseException {
////        @SuppressLint("SimpleDateFormat") Date parseDate = new SimpleDateFormat("MMMM dd, yyyy").parse(dApproved);
////        @SuppressLint("SimpleDateFormat") String lsDate = new SimpleDateFormat("HH:mm:ss").format(Objects.requireNonNull(dApproved));
//
//        return new FormatUIText().getParseDateTime(dApproved);
//    }


    public void setsTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getsTransNox() {
        return sTransNox;
    }

    public String getdTransact() {
        return  new FormatUIText().formatGOCasBirthdate(dTransact);

    }

    public void setdTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public String getsCredInvx() {
        return sCredInvx;
    }

    public void setsCredInvx(String sCredInvx) {
        this.sCredInvx = sCredInvx;
    }

    public String getsCompnyNm() {
        return sCompnyNm;
    }

    public void setsCompnyNm(String sCompnyNm) {
        this.sCompnyNm = sCompnyNm;
    }

    public String getsSpouseNm() {
        return sSpouseNm;
    }

    public void setsSpouseNm(String sSpouseNm) {
        this.sSpouseNm = sSpouseNm;
    }

    public String getsAddressx() {
        return sAddressx;
    }

    public void setsAddressx(String sAddressx) {
        this.sAddressx = sAddressx;
    }

    public String getsMobileNo() {
        return sMobileNo;
    }

    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }

    public String getsQMAppCde() {
        return sQMAppCde;
    }

    public void setsQMAppCde(String sQMAppCde) {
        this.sQMAppCde = sQMAppCde;
    }

    public String getsModelNme() {
        return sModelNme;
    }

    public void setsModelNme(String sModelNme) {
        this.sModelNme = sModelNme;
    }

    public String getnDownPaym() {
        return nDownPaym;
    }

    public void setnDownPaym(String nDownPaym) {
        this.nDownPaym = nDownPaym;
    }

    public String getnAcctTerm() {
        return nAcctTerm;
    }

    public void setnAcctTerm(String nAcctTerm) {
        this.nAcctTerm = nAcctTerm;
    }

    public String getcTranStat() {
        return cTranStat;
    }

    public void setcTranStat(String cTranStat) {
        this.cTranStat = cTranStat;
    }

    public String getdTimeStmp() {
        return  new FormatUIText().getParseUIDateTime(dTimeStmp);
    }

    public void setdTimeStmp(String dTimeStmp) {
        this.dTimeStmp = dTimeStmp;
    }
}
