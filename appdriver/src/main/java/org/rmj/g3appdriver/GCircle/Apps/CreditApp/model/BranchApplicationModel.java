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

package org.rmj.g3appdriver.GCircle.Apps.CreditApp.model;

import org.rmj.g3appdriver.etc.FormatUIText;

import java.io.Serializable;

public class BranchApplicationModel implements Serializable {
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

    private String IsWithCI;
    public BranchApplicationModel() {
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
    public String getTransactionStatus() {
        if(cTranStat.equalsIgnoreCase("0") || cTranStat.isEmpty()){
            return "Waiting for approval.";
        } else if(cTranStat.equalsIgnoreCase("1")){
            return "For C.I";
        }else{
            return "Approve";
        }

    }
    private String getCIStat(){
        if(IsWithCI.equalsIgnoreCase("1")){
            return "For C.I";
        }
        return "Approve";
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

    public void setIsWithCI(String isWithCI) {
        IsWithCI = isWithCI;
    }
    public String getLoanCIResult() {
        return IsWithCI;
    }
}
