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

import org.rmj.g3appdriver.etc.AppConstants;

@Entity(tableName = "Branch_Opening", primaryKeys = {"sBranchCD", "dTransact", "sTimeOpen"})
public class EBranchOpenMonitor {

    @NonNull
    @ColumnInfo(name = "sBranchCD")
    private String BranchCD;

    @NonNull
    @ColumnInfo(name = "dTransact")
    private String Transact;

    @NonNull
    @ColumnInfo(name = "sTimeOpen")
    private String TimeOpen;

    @ColumnInfo(name = "sOpenNowx")
    private String OpenNowx;
    @ColumnInfo(name = "dSendDate")
    private String SendDate;
    @ColumnInfo(name = "dNotified")
    private String Notified;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = AppConstants.DATE_MODIFIED();

    public EBranchOpenMonitor() {
    }

    public String getBranchCD() {
        return BranchCD;
    }

    public void setBranchCD(String branchCD) {
        BranchCD = branchCD;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getTimeOpen() {
        return TimeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        TimeOpen = timeOpen;
    }

    public String getOpenNowx() {
        return OpenNowx;
    }

    public void setOpenNowx(String openNowx) {
        OpenNowx = openNowx;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public String getNotified() {
        return Notified;
    }

    public void setNotified(String notified) {
        Notified = notified;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

}
