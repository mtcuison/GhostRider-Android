package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Employee_Log_Selfie")
public class ELog_Selfie {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sEmployID")
    private String EmployID;
    @ColumnInfo(name = "dLogTimex")
    private String LogTimex;
    @ColumnInfo(name = "nLatitude")
    private String Latitude;
    @ColumnInfo(name = "nLongitud")
    private String Longitud;
    @ColumnInfo(name = "cSendStat")
    private String SendStat;
    @ColumnInfo(name = "dSendDate")
    private String SendDate;

    public ELog_Selfie() {
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
}
