package org.rmj.g3appdriver.FacilityTrack.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Building_Visit", primaryKeys = {"sWHouseID", "dTransact"})
public class EBuildingVisit {

    @NonNull
    @ColumnInfo(name = "sWHouseID")
    private String WHouseID = "";

    @NonNull
    @ColumnInfo(name = "dTransact")
    private String Transact = "";

    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx = "";
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx = "";
    @ColumnInfo(name = "cSendStat")
    private String SendStat = "";
    @ColumnInfo(name = "dModified")
    private String Modified = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EBuildingVisit() {
    }

    @NonNull
    public String getWHouseID() {
        return WHouseID;
    }

    public void setWHouseID(@NonNull String WHouseID) {
        this.WHouseID = WHouseID;
    }

    @NonNull
    public String getTransact() {
        return Transact;
    }

    public void setTransact(@NonNull String transact) {
        Transact = transact;
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
