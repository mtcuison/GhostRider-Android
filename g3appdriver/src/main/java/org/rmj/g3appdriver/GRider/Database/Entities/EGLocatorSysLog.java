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

package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "GLocator_Sys_log", primaryKeys = {"sUserIDxx", "sDeviceID", "dTransact"})
public class EGLocatorSysLog {

    @NonNull
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx;

    @NonNull
    @ColumnInfo(name = "sDeviceID")
    private String DeviceID;

    @NonNull
    @ColumnInfo(name = "dTransact")
    private String Transact = "";
    @ColumnInfo(name = "nLongitud")
    private String Longitud = "0.00";
    @ColumnInfo(name = "nLatitude")
    private String Latitude = "0.00";
    @ColumnInfo(name = "cSendStat")
    private String SendStat = "0";
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

    public EGLocatorSysLog() {
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(@NonNull String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(@NonNull String deviceID) {
        DeviceID = deviceID;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(@NonNull String transact) {
        Transact = transact;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

    public String getLstUpdte() {
        return LstUpdte;
    }

    public void setLstUpdte(String lstUpdte) {
        LstUpdte = lstUpdte;
    }
}
