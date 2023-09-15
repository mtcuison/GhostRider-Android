package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "Ganado_Online")
public class EGanadoOnline {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox = "";
    @ColumnInfo(name = "dTransact")
    private String Transact = "";
    @ColumnInfo(name = "cGanadoTp")
    private String GanadoTp = "";
    @ColumnInfo(name = "cPaymForm")
    private String PaymForm = "";
    @ColumnInfo(name = "sClientNm")
    private String ClientNm = "";
    @ColumnInfo(name = "sClntInfo")
    private String ClntInfo = "";
    @ColumnInfo(name = "sProdInfo")
    private String ProdInfo = "";
    @ColumnInfo(name = "sPaymInfo")
    private String PaymInfo = "";
    @ColumnInfo(name = "dTargetxx")
    private String Targetxx = "";
    @ColumnInfo(name = "dFollowUp")
    private String FollowUp = "";
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx = "";
    @ColumnInfo(name = "sReferdBy")
    private String ReferdBy = "";
    @ColumnInfo(name = "sRelatnID")
    private String RelatnID = "";
    @ColumnInfo(name = "nCashPrce")
    private Double CashPrce = 0.0;
    @ColumnInfo(name = "dPricexxx")
    private String Pricexxx = "";
    @ColumnInfo(name = "dCreatedx")
    private String Createdx = "";
    @ColumnInfo(name = "cTranStat")
    private String TranStat = "";
    @ColumnInfo(name = "cSendStat")
    private String SendStat = "0";
    @ColumnInfo(name = "dModified")
    private String Modified = "";
    @ColumnInfo(name = "dLastUpdt")
    private String LastUpdt = "";
    @ColumnInfo(name = "sBranchCD")
    private String BranchCD = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EGanadoOnline() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getGanadoTp() {
        return GanadoTp;
    }

    public void setGanadoTp(String ganadoTp) {
        GanadoTp = ganadoTp;
    }

    public String getPaymForm() {
        return PaymForm;
    }

    public void setPaymForm(String paymForm) {
        PaymForm = paymForm;
    }

    public String getClientNm() {
        return ClientNm;
    }

    public void setClientNm(String clientNm) {
        ClientNm = clientNm;
    }

    public String getClntInfo() {
        return ClntInfo;
    }

    public void setClntInfo(String clntInfo) {
        ClntInfo = clntInfo;
    }

    public String getProdInfo() {
        return ProdInfo;
    }

    public void setProdInfo(String prodInfo) {
        ProdInfo = prodInfo;
    }

    public String getPaymInfo() {
        return PaymInfo;
    }

    public void setPaymInfo(String paymInfo) {
        PaymInfo = paymInfo;
    }

    public String getTargetxx() {
        return Targetxx;
    }

    public void setTargetxx(String targetxx) {
        Targetxx = targetxx;
    }

    public String getFollowUp() {
        return FollowUp;
    }

    public void setFollowUp(String followUp) {
        FollowUp = followUp;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getReferdBy() {
        return ReferdBy;
    }

    public void setReferdBy(String referdBy) {
        ReferdBy = referdBy;
    }

    public String getRelatnID() {
        return RelatnID;
    }

    public void setRelatnID(String relatnID) {
        RelatnID = relatnID;
    }

    public String getCreatedx() {
        return Createdx;
    }

    public void setCreatedx(String createdx) {
        Createdx = createdx;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getLastUpdt() {
        return LastUpdt;
    }

    public void setLastUpdt(String lastUpdt) {
        LastUpdt = lastUpdt;
    }

    public String getBranchCD() {
        return BranchCD;
    }

    public void setBranchCD(String branchCD) {
        BranchCD = branchCD;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

    public Double getCashPrce() {
        return CashPrce;
    }

    public void setCashPrce(Double nCashPrce) {
        this.CashPrce = nCashPrce;
    }

    public String getPricexxx() {
        return Pricexxx;
    }

    public void setPricexxx(String pricexxx) {
        Pricexxx = pricexxx;
    }
}
