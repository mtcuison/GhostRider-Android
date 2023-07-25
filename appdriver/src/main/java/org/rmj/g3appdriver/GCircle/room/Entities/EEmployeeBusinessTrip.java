/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 8/13/21 10:28 AM
 * project file last modified : 8/13/21 10:28 AM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Employee_Business_Trip")
public class EEmployeeBusinessTrip {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "xEmployee")
    private String Employee;
    @ColumnInfo(name = "sFullName")
    private String FullName;
    @ColumnInfo(name = "sBranchNm")
    private String BranchNm;
    @ColumnInfo(name = "sDeptName")
    private String DeptName;
    @ColumnInfo(name = "sPositnNm")
    private String PositnNm;
    @ColumnInfo(name = "dDateFrom")
    private String DateFrom;
    @ColumnInfo(name = "dDateThru")
    private String DateThru;
    @ColumnInfo(name = "sDestinat")
    private String Destinat;
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "sApproved")
    private String Approved;
    @ColumnInfo(name = "dApproved")
    private String Dapprove;
    @ColumnInfo(name = "dAppldFrx")
    private String AppldFrx;
    @ColumnInfo(name = "dAppldTox")
    private String AppldTox;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;
    @ColumnInfo(name = "cSendStat")
    private String SendStat;
    @ColumnInfo(name = "sModified")
    private String Modified;
    @ColumnInfo(name = "dModified")
    private String DModfied;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EEmployeeBusinessTrip() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String sTransNox) {
        this.TransNox = sTransNox;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getEmployee() {
        return Employee;
    }

    public void setEmployee(String employee) {
        Employee = employee;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
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

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {
        Approved = approved;
    }

    public String getDapprove() {
        return Dapprove;
    }

    public void setDapprove(String dapprove) {
        Dapprove = dapprove;
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

    public String getDestinat() {
        return Destinat;
    }

    public void setDestinat(String destinat) {
        Destinat = destinat;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getDModfied() {
        return DModfied;
    }

    public void setDModfied(String DModfied) {
        this.DModfied = DModfied;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
