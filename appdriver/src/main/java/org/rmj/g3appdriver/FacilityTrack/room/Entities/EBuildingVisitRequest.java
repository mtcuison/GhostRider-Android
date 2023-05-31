package org.rmj.g3appdriver.FacilityTrack.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Building_Visit_Request", primaryKeys = {"sNFCIDxxx", "sUserIDxx", "dSchedule"})
public class EBuildingVisitRequest {

    @NonNull
    @ColumnInfo(name = "sNFCIDxxx")
    private String NFCIDxxx = "";

    @NonNull
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx = "";

    @NonNull
    @ColumnInfo(name = "dSchedule")
    private String Schedule = "";

    @ColumnInfo(name = "dVisitedx")
    private String Visitedx = "";
    @ColumnInfo(name = "cTranStat")
    private String TranStat = "";
    @ColumnInfo(name = "sModified")
    private String Modified = "";
    @ColumnInfo(name = "dModified")
    private String DateMdfy = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EBuildingVisitRequest() {
    }

    @NonNull
    public String getNFCIDxxx() {
        return NFCIDxxx;
    }

    public void setNFCIDxxx(@NonNull String NFCIDxxx) {
        this.NFCIDxxx = NFCIDxxx;
    }

    @NonNull
    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(@NonNull String userIDxx) {
        UserIDxx = userIDxx;
    }

    @NonNull
    public String getSchedule() {
        return Schedule;
    }

    public void setSchedule(@NonNull String schedule) {
        Schedule = schedule;
    }

    public String getVisitedx() {
        return Visitedx;
    }

    public void setVisitedx(String visitedx) {
        Visitedx = visitedx;
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

    public String getDateMdfy() {
        return DateMdfy;
    }

    public void setDateMdfy(String dateMdfy) {
        DateMdfy = dateMdfy;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
