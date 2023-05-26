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

@Entity(tableName = "GLocator_Sys_log")
public class EGLocatorSysLog {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sLoctnIDx")
    private String LoctnIDx = "";
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx = "";
    @ColumnInfo(name = "sDeviceID")
    private String DeviceID = "";
    @ColumnInfo(name = "dTransact")
    private String Transact = "";
    @ColumnInfo(name = "nLongitud")
    private Double Longitud = 0.0;
    @ColumnInfo(name = "nLatitude")
    private Double Latitude = 0.0;
    @ColumnInfo(name = "cSendStat")
    private String SendStat = "0";
    @ColumnInfo(name = "cGpsEnbld")
    private String GpsEnbld;
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

    public EGLocatorSysLog() {
    }

    @NonNull
    public String getLoctnIDx() {
        return LoctnIDx;
    }

    public void setLoctnIDx(@NonNull String sLoctnIDx) {
        this.LoctnIDx = sLoctnIDx;
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public Double getLongitud() {
        return Longitud;
    }

    public void setLongitud(Double longitud) {
        Longitud = longitud;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    public String getGpsEnbld() {
        return GpsEnbld;
    }

    public void setGpsEnbld(String gpsEnbld) {
        GpsEnbld = gpsEnbld;
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
