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

@Entity(tableName = "Notification_Info_Recepient")
public
class ENotificationRecipient {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sAppRcptx")
    private String AppRcptx;
    @ColumnInfo(name = "sRecpntID")
    private String RecpntID;
    @ColumnInfo(name = "sRecpntNm")
    private String RecpntNm;
    @ColumnInfo(name = "sGroupIDx")
    private String GroupIDx;
    @ColumnInfo(name = "sGroupNmx")
    private String GroupNmx;
    @ColumnInfo(name = "cMonitorx")
    private String Monitorx;
    @ColumnInfo(name = "cMesgStat")
    private String MesgStat;
    @ColumnInfo(name = "cStatSent")
    private String StatSent;
    @ColumnInfo(name = "dSentxxxx")
    private String Sentxxxx;
    @ColumnInfo(name = "dReceived")
    private String Received;
    @ColumnInfo(name = "dReadxxxx")
    private String Readxxxx;
    @ColumnInfo(name = "dLastUpdt")
    private String LastUpdt;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ENotificationRecipient() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getAppRcptx() {
        return AppRcptx;
    }

    public void setAppRcptx(String appRcptx) {
        AppRcptx = appRcptx;
    }

    public String getRecpntID() {
        return RecpntID;
    }

    public void setRecpntID(String recpntID) {
        RecpntID = recpntID;
    }

    public String getRecpntNm() {
        return RecpntNm;
    }

    public void setRecpntNm(String recpntNm) {
        RecpntNm = recpntNm;
    }

    public String getGroupIDx() {
        return GroupIDx;
    }

    public void setGroupIDx(String groupIDx) {
        GroupIDx = groupIDx;
    }

    public String getGroupNmx() {
        return GroupNmx;
    }

    public void setGroupNmx(String groupNmx) {
        GroupNmx = groupNmx;
    }

    public String getMonitorx() {
        return Monitorx;
    }

    public void setMonitorx(String monitorx) {
        Monitorx = monitorx;
    }

    public String getMesgStat() {
        return MesgStat;
    }

    public void setMesgStat(String mesgStat) {
        MesgStat = mesgStat;
    }

    public String getStatSent() {
        return StatSent;
    }

    public void setStatSent(String statSent) {
        StatSent = statSent;
    }

    public String getSentxxxx() {
        return Sentxxxx;
    }

    public void setSentxxxx(String sentxxxx) {
        Sentxxxx = sentxxxx;
    }

    public String getReceived() {
        return Received;
    }

    public void setReceived(String received) {
        Received = received;
    }

    public String getReadxxxx() {
        return Readxxxx;
    }

    public void setReadxxxx(String readxxxx) {
        Readxxxx = readxxxx;
    }

    public String getLastUpdt() {
        return LastUpdt;
    }

    public void setLastUpdt(String lastUpdt) {
        LastUpdt = lastUpdt;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
