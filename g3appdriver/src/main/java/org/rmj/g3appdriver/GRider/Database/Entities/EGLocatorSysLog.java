package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

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
    private String Transact;

    @ColumnInfo(name = "nLongitud")
    private String Longitud;

    @ColumnInfo(name = "nLatitude")
    private String Latitude;

    @ColumnInfo(name = "cSendStat")
    private String SendStat;

    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EGLocatorSysLog() {
    }

    @NonNull
    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(@NonNull String userIDxx) {
        UserIDxx = userIDxx;
    }

    @NonNull
    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(@NonNull String deviceID) {
        DeviceID = deviceID;
    }

    @NonNull
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

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
