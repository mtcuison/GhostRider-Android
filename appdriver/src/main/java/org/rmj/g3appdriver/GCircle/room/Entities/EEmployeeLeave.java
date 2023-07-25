/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/19/21 9:53 AM
 * project file last modified : 6/19/21 9:53 AM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.rmj.g3appdriver.etc.AppConstants;

@Entity(tableName = "Employee_Leave")
public class EEmployeeLeave {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox = "";
    @ColumnInfo(name = "dTransact")
    private String Transact = "";
    @ColumnInfo(name = "sEmployNm")
    private String EmployNm = "";
    @ColumnInfo(name = "sEmployID")
    private String EmployID = "";
    @ColumnInfo(name = "sBranchNm")
    private String BranchNm = "";
    @ColumnInfo(name = "sDeptName")
    private String DeptName = "";
    @ColumnInfo(name = "sPositnNm")
    private String PositnNm = "";
    @ColumnInfo(name = "dDateFrom")
    private String DateFrom = "";
    @ColumnInfo(name = "dDateThru")
    private String DateThru = "";
    @ColumnInfo(name = "nNoDaysxx")
    private Integer NoDaysxx = 0;
    @ColumnInfo(name = "sPurposex")
    private String Purposex = "";
    @ColumnInfo(name = "cLeaveTyp")
    private String LeaveTyp = "";
    @ColumnInfo(name = "dAppldFrx")
    private String AppldFrx = "";
    @ColumnInfo(name = "dAppldTox")
    private String AppldTox = "";
    @ColumnInfo(name = "sEntryByx")
    private String EntryByx = "";
    @ColumnInfo(name = "dEntryDte")
    private String EntryDte = "";
    @ColumnInfo(name = "nWithPayx")
    private Integer WithPayx = 0;
    @ColumnInfo(name = "nWithOPay")
    private Integer WithOPay = 0;
    @ColumnInfo(name = "nEqualHrs")
    private Double EqualHrs = 0.00;
    @ColumnInfo(name = "sApproved")
    private String Approved = "";
    @ColumnInfo(name = "dApproved")
    private String DApproved = "";
    @ColumnInfo(name = "cSentStat")
    private String SentStat = "";
    @ColumnInfo(name = "cAppvSent")
    private String AppvSent = "";
    @ColumnInfo(name = "dSendDate")
    private String SendDate = "";
    @ColumnInfo(name = "nLveCredt")
    private Integer LveCredt = 0;
    @ColumnInfo(name = "cTranStat")
    private String TranStat = "";
    @ColumnInfo(name = "dModified")
    private String Modified = new AppConstants().DATE_MODIFIED;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = new AppConstants().DATE_MODIFIED;

    public EEmployeeLeave() {
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

    public String getEmployID() {
        return EmployID;
    }

    public void setEmployID(String employID) {
        EmployID = employID;
    }

    public String getBranchNm() {
        return BranchNm;
    }

    public void setBranchNm(String branchNm) {
        BranchNm = branchNm;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getPositnNm() {
        return PositnNm;
    }

    public void setPositnNm(String positnNm) {
        PositnNm = positnNm;
    }

    public String getDateFrom() {
        return DateFrom;
    }

    public void setDateFrom(String dateFrom) {
        DateFrom = dateFrom;
    }

    public String getDateThru() {
        return DateThru;
    }

    public void setDateThru(String dateThru) {
        DateThru = dateThru;
    }

    public Integer getNoDaysxx() {
        return NoDaysxx;
    }

    public void setNoDaysxx(Integer noDaysxx) {
        NoDaysxx = noDaysxx;
    }

    public String getPurposex() {
        return Purposex;
    }

    public void setPurposex(String purposex) {
        Purposex = purposex;
    }

    public String getLeaveTyp() {
        return LeaveTyp;
    }

    public void setLeaveTyp(String leaveTyp) {
        LeaveTyp = leaveTyp;
    }

    public String getAppldFrx() {
        return AppldFrx;
    }

    public void setAppldFrx(String appldFrx) {
        AppldFrx = appldFrx;
    }

    public String getAppldTox() {
        return AppldTox;
    }

    public void setAppldTox(String appldTox) {
        AppldTox = appldTox;
    }

    public String getEntryByx() {
        return EntryByx;
    }

    public void setEntryByx(String entryByx) {
        EntryByx = entryByx;
    }

    public String getEntryDte() {
        return EntryDte;
    }

    public void setEntryDte(String entryDte) {
        EntryDte = entryDte;
    }

    public Integer getWithOPay() {
        return WithOPay;
    }

    public void setWithOPay(Integer withOPay) {
        WithOPay = withOPay;
    }

    public Double getEqualHrs() {
        return EqualHrs;
    }

    public void setEqualHrs(Double equalHrs) {
        EqualHrs = equalHrs;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {
        Approved = approved;
    }

    public String getDApproved() {
        return DApproved;
    }

    public void setDApproved(String DApproved) {
        this.DApproved = DApproved;
    }

    public String getSentStat() {
        return SentStat;
    }

    public void setSentStat(String sentStat) {
        SentStat = sentStat;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public Integer getLveCredt() {
        return LveCredt;
    }

    public void setLveCredt(Integer lveCredt) {
        LveCredt = lveCredt;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public Integer getWithPayx() {
        return WithPayx;
    }

    public void setWithPayx(Integer withPayx) {
        WithPayx = withPayx;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

    public String getEmployNm() {
        return EmployNm;
    }

    public void setEmployNm(String employNm) {
        EmployNm = employNm;
    }

    public String getAppvSent() {
        return AppvSent;
    }

    public void setAppvSent(String appvSent) {
        AppvSent = appvSent;
    }
}
