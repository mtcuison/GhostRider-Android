/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 6/4/21 4:45 PM
 * project file last modified : 6/4/21 4:45 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model;

import androidx.annotation.NonNull;

import org.rmj.g3appdriver.etc.AppConstants;

import java.util.Objects;

public class CollectionRemittanceInfoModel {
    private String TransNox;

    private String EntryNox;
    private String Transact = AppConstants.CURRENT_DATE();
    private String PaymForm;
    private String RemitTyp;
    private String CompnyNm;
    private String BankAcct;
    private String ReferNox = "";
    private String Amountxx;
    private String SendStat;
    private String DateSent;
    private String TimeStmp;
    private String message;

    private String psCltCashx;

    public String getPsCltCashx() {
        return psCltCashx;
    }

    public void setPsCltCashx(String psCltCashx) {
        this.psCltCashx = psCltCashx;
    }

    public String getPsCltCheck() {
        return psCltCheck;
    }

    public void setPsCltCheck(String psCltCheck) {
        this.psCltCheck = psCltCheck;
    }

    private String psCltCheck;
    private String psBranch;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    private boolean isCheck = false;
    public CollectionRemittanceInfoModel() {
    }
    public String getMessage() { return message; }
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(String entryNox) {
        EntryNox = entryNox;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getPaymForm() {
        return PaymForm;
    }

    public void setPaymForm(String paymForm) {
        PaymForm = paymForm;
    }

    public String getRemitTyp() {
        return RemitTyp;
    }

    /**
     *
     * @param remitTyp
     *  <p>0 Branch</p>
     *  <p>1 Bank</p>
     *  <p>2 Payment Partners</p>
     */
    public void setRemitTyp(String remitTyp) {
        RemitTyp = remitTyp;
    }

    public String getCompnyNm() {
        return CompnyNm;
    }

    /**
     *
     * @param compnyNm
     *  <p>Branch Name Or Bank Name</p>
     *  <p>Ex. GMC Dagupan / BDO/ Cebuana</p>
     */
    public void setCompnyNm(String compnyNm) {
        CompnyNm = compnyNm;
    }

    public String getBankAcct() {
        return BankAcct;
    }

    public void setBankAcct(String bankAcct) {
        BankAcct = bankAcct;
    }

    public String getReferNox() {
        return ReferNox;
    }

    public void setReferNox(String referNox) {
        ReferNox = referNox;
    }

    public String getAmountxx() {
        return Amountxx;
    }

    public void setAmountxx(String amountxx) {
        Amountxx = amountxx;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    public String getDateSent() {
        return DateSent;
    }

    public void setDateSent(String dateSent) {
        DateSent = dateSent;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
    
    public boolean isDataValid(){
        if (this.RemitTyp.equalsIgnoreCase("0")){
            if (this.psBranch == null) {
                message = "Please enter branch";
                return false;
            } else if (Objects.requireNonNull(this.Amountxx.isEmpty())) {
                message = "Please enter amount";
                return false;
            } else if (this.Amountxx.equalsIgnoreCase("0.0")) {
                message = "Unable to remit 0 amount";
                return false;
            } else if (this.Amountxx.equalsIgnoreCase("0")) {
                message = "Unable to remit 0 amount";
                return false;
            } else if(!isCheck) {
                if(psCltCashx.equalsIgnoreCase("0") ||
                        psCltCashx.equalsIgnoreCase("0.0") ||
                        psCltCashx.equalsIgnoreCase("0.00")) {
                    message = "Unable to remit. Cash on hand is empty.";
                    return false;
                } else if(parseDouble(this.Amountxx) > parseDouble(psCltCashx)) {
                    message = "Unable to remit. Cash remittance is greater than cash on hand.";
                    return false;
                }
            } else if(isCheck) {
                if(psCltCheck.equalsIgnoreCase("0") ||
                        psCltCheck.equalsIgnoreCase("0.0") ||
                        psCltCheck.equalsIgnoreCase("0.00")) {
                    message = "Unable to remit. Check on hand is empty.";
                    return false;
                } else if(parseDouble(this.Amountxx) > parseDouble(psCltCheck)) {
                    message = "Unable to remit. Check remittance is greater than check on hand.";
                    return false;
                }
            }
        }else if (this.RemitTyp.equalsIgnoreCase("1")) {
            if (this.BankAcct.isEmpty()) {
                message = "Please enter bank";
                return false;
            } else if (Objects.requireNonNull(this.CompnyNm.isEmpty())) {
                message = "Please enter account no";
                return false;
            } else if (Objects.requireNonNull(this.ReferNox.isEmpty())) {
                message = "Please enter reference no";
                return false;
            } else if (Objects.requireNonNull(this.Amountxx.isEmpty())) {
                message = "Please enter amount";
                return false;
            } else if (this.Amountxx.equalsIgnoreCase("0.0")) {
                message = "Unable to remit 0 amount";
                return false;
            } else if (this.Amountxx.equalsIgnoreCase("0")) {
                message = "Unable to remit 0 amount";
                return false;
            } else if(!isCheck) {
                if(psCltCashx.equalsIgnoreCase("0") ||
                        psCltCashx.equalsIgnoreCase("0.0") ||
                        psCltCashx.equalsIgnoreCase("0.00")) {
                    message = "Unable to remit. Cash on hand is empty.";
                    return false;
                } else if(parseDouble(this.Amountxx) > parseDouble(psCltCashx)) {
                    message = "Unable to remit. Cash remittance is greater than cash on hand.";
                    return false;
                }
            } else if(isCheck) {
                if(psCltCheck.equalsIgnoreCase("0") ||
                        psCltCheck.equalsIgnoreCase("0.0") ||
                        psCltCheck.equalsIgnoreCase("0.00")) {
                    message = "Unable to remit. Check on hand is empty.";
                    return false;
                } else if(parseDouble(this.Amountxx) > parseDouble(psCltCheck)) {
                    message = "Unable to remit. Check remittance is greater than check on hand.";
                    return false;
                }
            }
        }else {
            if (Objects.requireNonNull(this.ReferNox.isEmpty())) {
               message = "Please enter reference no";
                return false;
            } else if (Objects.requireNonNull(this.Amountxx.isEmpty())) {
                message = "Please enter amount";
                return false;
            } else if (this.Amountxx.equalsIgnoreCase("0.0") || this.Amountxx.equalsIgnoreCase("0")) {
                message = "Unable to remit 0 amount";
                return false;
            } else if(!isCheck) {
                if(psCltCashx.equalsIgnoreCase("0") ||
                        psCltCashx.equalsIgnoreCase("0.0") ||
                        psCltCashx.equalsIgnoreCase("0.00")) {
                    message = "Unable to remit. Cash on hand is empty.";
                    return false;
                } else if(parseDouble(this.Amountxx) > parseDouble(psCltCashx)) {
                    message = "Unable to remit. Cash remittance is greater than cash on hand.";
                    return false;
                }
            } else if(isCheck) {
                if(psCltCheck.equalsIgnoreCase("0") ||
                        psCltCheck.equalsIgnoreCase("0.0") ||
                        psCltCheck.equalsIgnoreCase("0.00")) {
                    message = "Unable to remit. Check on hand is empty.";
                    return false;
                } else if(parseDouble(this.Amountxx) > parseDouble(psCltCheck)) {
                    message = "Unable to remit. Check remittance is greater than check on hand.";
                    return false;
                }
            }
        }
        return true;
    }
    private static double parseDouble(String fsNumber) {
        String lsNumber = fsNumber.replace(",","");
        return Double.parseDouble(lsNumber);
    }

    public String getPsBranch() {
        return psBranch;
    }

    public void setPsBranch(String psBranch) {
        this.psBranch = psBranch;
    }
}
