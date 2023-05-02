package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Barangay_Info")
public class EBarangayInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sBrgyIDxx")
    private String BrgyIDxx;
    @ColumnInfo(name = "sBrgyName")
    private String BrgyName;
    @ColumnInfo(name = "sTownIDxx")
    private String TownIDxx;
    @ColumnInfo(name = "cHasRoute")
    private String HasRoute;
    @ColumnInfo(name = "cBlackLst")
    private String BlackLst;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EBarangayInfo() {
    }

    @NonNull
    public String getBrgyIDxx() {
        return BrgyIDxx;
    }

    public void setBrgyIDxx(@NonNull String brgyIDxx) {
        BrgyIDxx = brgyIDxx;
    }

    public String getBrgyName() {
        return BrgyName;
    }

    public void setBrgyName(String brgyName) {
        BrgyName = brgyName;
    }

    public String getTownIDxx() {
        return TownIDxx;
    }

    public void setTownIDxx(String townIDxx) {
        TownIDxx = townIDxx;
    }

    public String getHasRoute() {
        return HasRoute;
    }

    public void setHasRoute(String hasRoute) {
        HasRoute = hasRoute;
    }

    public String getBlackLst() {
        return BlackLst;
    }

    public void setBlackLst(String blackLst) {
        BlackLst = blackLst;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
        RecdStat = recdStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
