/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "System_Code_Approval")
public class ECodeApproval {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "sSystemCD")
    private String SystemCD;
    @ColumnInfo(name = "sReqstdBy")
    private String ReqstdBy;
    @ColumnInfo(name = "dReqstdxx")
    private String Reqstdxx;
    @ColumnInfo(name = "cIssuedBy")
    private String IssuedBy;
    @ColumnInfo(name = "sMiscInfo")
    private String MiscInfo;
    @ColumnInfo(name = "sRemarks1")
    private String Remarks1;
    @ColumnInfo(name = "sRemarks2")
    private String Remarks2;
    @ColumnInfo(name = "sApprCode")
    private String ApprCode;
    @ColumnInfo(name = "sEntryByx")
    private String EntryByx;
    @ColumnInfo(name = "sApprvByx")
    private String ApprvByx;
    @ColumnInfo(name = "sReasonxx")
    private String Reasonxx;
    @ColumnInfo(name = "sReqstdTo")
    private String ReqstdTo;
    @ColumnInfo(name = "cSendxxxx")
    private String Sendxxxx;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ECodeApproval() {
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

    public String getSystemCD() {
        return SystemCD;
    }

    public void setSystemCD(String systemCD) {
        SystemCD = systemCD;
    }

    public String getReqstdBy() {
        return ReqstdBy;
    }

    public void setReqstdBy(String reqstdBy) {
        ReqstdBy = reqstdBy;
    }

    public String getReqstdxx() {
        return Reqstdxx;
    }

    public void setReqstdxx(String reqstdxx) {
        Reqstdxx = reqstdxx;
    }

    public String getIssuedBy() {
        return IssuedBy;
    }

    public void setIssuedBy(String issuedBy) {
        IssuedBy = issuedBy;
    }

    public String getMiscInfo() {
        return MiscInfo;
    }

    public void setMiscInfo(String miscInfo) {
        MiscInfo = miscInfo;
    }

    public String getRemarks1() {
        return Remarks1;
    }

    public void setRemarks1(String remarks1) {
        Remarks1 = remarks1;
    }

    public String getRemarks2() {
        return Remarks2;
    }

    public void setRemarks2(String remarks2) {
        Remarks2 = remarks2;
    }

    public String getApprCode() {
        return ApprCode;
    }

    public void setApprCode(String apprCode) {
        ApprCode = apprCode;
    }

    public String getEntryByx() {
        return EntryByx;
    }

    public void setEntryByx(String entryByx) {
        EntryByx = entryByx;
    }

    public String getApprvByx() {
        return ApprvByx;
    }

    public void setApprvByx(String apprvByx) {
        ApprvByx = apprvByx;
    }

    public String getReasonxx() {
        return Reasonxx;
    }

    public void setReasonxx(String reasonxx) {
        Reasonxx = reasonxx;
    }

    public String getReqstdTo() {
        return ReqstdTo;
    }

    public void setReqstdTo(String reqstdTo) {
        ReqstdTo = reqstdTo;
    }

    public String getSendxxxx() {
        return Sendxxxx;
    }

    public void setSendxxxx(String sendxxxx) {
        Sendxxxx = sendxxxx;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
