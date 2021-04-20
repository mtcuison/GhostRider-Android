package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;

@Entity(tableName = "LR_DCP_Remittance", primaryKeys = {"sTransNox", "nEntryNox"})
public class EDCP_Remittance {

    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;

    @NonNull
    @ColumnInfo(name = "nEntryNox")
    private String EntryNox;
    @ColumnInfo(name = "dTransact")
    private String Transact = AppConstants.CURRENT_DATE;
    @ColumnInfo(name = "cPaymForm")
    private String PaymForm = "";
    @ColumnInfo(name = "cRemitTyp")
    private String RemitTyp = "";
    @ColumnInfo(name = "sCompnyNm")
    private String CompnyNm = "";
    @ColumnInfo(name = "sBankAcct")
    private String BankAcct = "";
    @ColumnInfo(name = "sReferNox")
    private String ReferNox = "";
    @ColumnInfo(name = "nAmountxx")
    private String Amountxx = "0";
    @ColumnInfo(name = "cSendStat")
    private String SendStat = "0";
    @ColumnInfo(name = "dDateSent")
    private String DateSent = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EDCP_Remittance() {
    }

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
}
