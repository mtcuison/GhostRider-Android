/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Employee_Log_Selfie")
public class ESelfieLog {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sEmployID")
    private String EmployID;
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "dLogTimex")
    private String LogTimex;
    @ColumnInfo(name = "nLatitude")
    private String Latitude;
    @ColumnInfo(name = "nLongitud")
    private String Longitud;
    @ColumnInfo(name = "sImageIDx")
    private String ImageIDx;
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "cSendStat")
    private String SendStat = "0";
    @ColumnInfo(name = "dSendDate")
    private String SendDate;
    @ColumnInfo(name = "cReqCCntx")
    private String ReqCCntx = "0";
    @ColumnInfo(name = "cReqRSIxx")
    private String ReqRSIxx = "0";

    public ESelfieLog() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getEmployID() {
        return EmployID;
    }

    public void setEmployID(String employID) {
        EmployID = employID;
    }

    public String getLogTimex() {
        return LogTimex;
    }

    public void setLogTimex(String logTimex) {
        LogTimex = logTimex;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(String branchCd) {
        BranchCd = branchCd;
    }

    public String getReqCCntx() {
        return ReqCCntx;
    }

    public void setReqCCntx(String reqCCntx) {
        ReqCCntx = reqCCntx;
    }

    public String getReqRSIxx() {
        return ReqRSIxx;
    }

    public void setReqRSIxx(String reqRSIxx) {
        ReqRSIxx = reqRSIxx;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getImageIDx() {
        return ImageIDx;
    }

    public void setImageIDx(String imageIDx) {
        ImageIDx = imageIDx;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }
}
