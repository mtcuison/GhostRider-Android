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

package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.view.View;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;

public class LoanApplication {

    private String GoCasNoxx;
    private String sTransNox;
    private String sBranchCd;
    private String dTransact;
    private String DetlInfox;
    private String sClientNm;
    private String Model;
    private String Down;
    private String DownPerc;
    private String IsWithCI;
    private String cUnitAppl;
    private String cSendStat;
    private String cTranStat;
    private String dDateSent;
    private String cCaptured;
    private String dApproved;
    private String sFileLoct;

    public LoanApplication() {
    }

    public String getGOCasNumber() {
        if(GoCasNoxx == null || GoCasNoxx.equalsIgnoreCase("null")){
            return "GOCas not available";
        }
        return GoCasNoxx;
    }

    public void setGOCasNumber(String goCasNoxx) {
        GoCasNoxx = goCasNoxx;
    }

    public String getTransNox() {
        return sTransNox;
    }

    public void setTransNox(String sTransNox) {
        this.sTransNox = sTransNox;
    }

    public String getBranchCode() {
        return sBranchCd;
    }

    public void setBranchCode(String sBranchCd) {
        this.sBranchCd = sBranchCd;
    }

    public String getDateTransact() {
        return new FormatUIText().getParseDateTime(dTransact);
    }

    public void setDateTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public String getDetailInfo() {
        return DetlInfox;
    }

    public void setDetailInfo(String detlInfox) {
        DetlInfox = detlInfox;
    }

    public String getClientName() {
        return sClientNm;
    }

    public void setClientName(String sClientNm) {
        this.sClientNm = sClientNm;
    }

    public String getModel() {
        return Model;
    }

    public void setModel(String model) {
        Model = model;
    }

    public String getDownPayment() {
        return Down;
    }

    public void setDownPayment(String down) {
        Down = down;
    }

    public String getDownpaymentPercentage() {
        return DownPerc;
    }

    public void setDownpaymentPercentage(String downPerc) {
        DownPerc = downPerc;
    }

    public String getLoanCIResult() {
        return IsWithCI;
    }

    public void setLoanCIResult(String isWithCI) {
        IsWithCI = isWithCI;
    }

    public String getUnitApplied() {
        return cUnitAppl;
    }

    public void setUnitApplied(String cUnitAppl) {
        this.cUnitAppl = cUnitAppl;
    }

    public String getcCaptured() {
        return cCaptured;
    }

    public void setcCaptured(String cCaptured) {
        this.cCaptured = cCaptured;
    }

    public int getSendStatus() {
        if(cSendStat.equalsIgnoreCase("1")){
            return View.VISIBLE;
        }
        return View.GONE;
    }

    public void setSendStatus(String cSendStat) {
        this.cSendStat = cSendStat;
    }

    public String getTransactionStatus() {
        if(cTranStat.equalsIgnoreCase("0")){
            return "Waiting for approval.";
        } else if(cTranStat.isEmpty()){
            return "Waiting for approval.";
        }
        return getCIStat();
    }

    public void setTransactionStatus(String cTranStat) {
        this.cTranStat = cTranStat;
    }

    public String getDateSent() {
        return new FormatUIText().getParseDateTime(dDateSent);
    }

    public void setDateSent(String dDateSent) {
        this.dDateSent = dDateSent;
    }

    public String getDateApproved() {
        if(!dApproved.equalsIgnoreCase("null")) {
            return FormatUIText.getParseDateTime(dApproved);
        }
        return "";
    }

    public void setDateApproved(String dApproved) {
        this.dApproved = dApproved;
    }

    public int getExportStatus(){
        if(GoCasNoxx == null || GoCasNoxx.equalsIgnoreCase("null")){
            return View.GONE;
        }
        return View.VISIBLE;
    }

    private String getCIStat(){
        if(IsWithCI.equalsIgnoreCase("1")){
            return "For C.I";
        }
        return "Approve";
    }

    public int getVoidStatus(){
        if(cSendStat.equalsIgnoreCase("1")){
            return View.GONE;
        }
        return View.VISIBLE;
    }

    public int captureCustomerPhoto(){
        if(cCaptured.equalsIgnoreCase("1")){
            return View.GONE;
        }
        return View.VISIBLE;
    }

    public String getsFileLoct() {
        return sFileLoct;
    }

    public void setsFileLoct(String fileLoct) {
        this.sFileLoct = fileLoct;
    }
}
