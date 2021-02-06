package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "LR_DCP_Collection_Master")
public class EDCPCollectionMaster {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "sReferNox")
    private String ReferNox;
    @ColumnInfo(name = "xCollName")
    private String CollName;
    @ColumnInfo(name = "sRouteNme")
    private String RouteNme;
    @ColumnInfo(name = "dReferDte")
    private String ReferDte;
    @ColumnInfo(name = "cTranStat")
    private char TranStat;
    @ColumnInfo(name = "cDCPTypex")
    private char DCPTypex;
    @ColumnInfo(name = "nEntryNox")
    private String EntryNox;
    @ColumnInfo(name = "sBranchNm")
    private String BranchNm;
    @ColumnInfo(name = "sCollctID")
    private String CollctID;
    @ColumnInfo(name = "cSendStat")
    private char SendStat;
    @ColumnInfo(name = "dSendDate")
    private String SendDate;
    @ColumnInfo(name = "dModified")
    private String Modified;

    public EDCPCollectionMaster() {
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

    public String getReferNox() {
        return ReferNox;
    }

    public void setReferNox(String referNox) {
        ReferNox = referNox;
    }

    public String getCollName() {
        return CollName;
    }

    public void setCollName(String collName) {
        CollName = collName;
    }

    public String getRouteNme() {
        return RouteNme;
    }

    public void setRouteNme(String routeNme) {
        RouteNme = routeNme;
    }

    public String getReferDte() {
        return ReferDte;
    }

    public void setReferDte(String referDte) {
        ReferDte = referDte;
    }

    public char getTranStat() {
        return TranStat;
    }

    public void setTranStat(char tranStat) {
        TranStat = tranStat;
    }

    public char getDCPTypex() {
        return DCPTypex;
    }

    public void setDCPTypex(char DCPTypex) {
        this.DCPTypex = DCPTypex;
    }

    public String getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(String entryNox) {
        EntryNox = entryNox;
    }

    public String getBranchNm() {
        return BranchNm;
    }

    public void setBranchNm(String branchNm) {
        BranchNm = branchNm;
    }

    public String getCollctID() {
        return CollctID;
    }

    public void setCollctID(String collctID) {
        CollctID = collctID;
    }

    public char getSendStat() {
        return SendStat;
    }

    public void setSendStat(char sendStat) {
        SendStat = sendStat;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }
}
