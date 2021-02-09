package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Town_Info")
public class ETownInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTownIDxx")
    private String TownIDxx;
    @ColumnInfo(name = "sTownName")
    private String TownName;
    @ColumnInfo(name = "sZippCode")
    private String ZippCode;
    @ColumnInfo(name = "sProvIDxx")
    private String ProvIDxx;
    @ColumnInfo(name = "sProvCode")
    private String ProvCode;
    @ColumnInfo(name = "sMuncplCd")
    private String MuncplCd;
    @ColumnInfo(name = "cHasRoute")
    private String HasRoute;
    @ColumnInfo(name = "cBlackLst")
    private String BlackLst;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ETownInfo() {
    }

    @NonNull
    public String getTownIDxx() {
        return TownIDxx;
    }

    public void setTownIDxx(@NonNull String townIDxx) {
        TownIDxx = townIDxx;
    }

    public String getTownName() {
        return TownName;
    }

    public void setTownName(String townName) {
        TownName = townName;
    }

    public String getZippCode() {
        return ZippCode;
    }

    public void setZippCode(String zippCode) {
        ZippCode = zippCode;
    }

    public String getProvIDxx() {
        return ProvIDxx;
    }

    public void setProvIDxx(String provIDxx) {
        ProvIDxx = provIDxx;
    }

    public String getProvCode() {
        return ProvCode;
    }

    public void setProvCode(String provCode) {
        ProvCode = provCode;
    }

    public String getMuncplCd() {
        return MuncplCd;
    }

    public void setMuncplCd(String muncplCd) {
        MuncplCd = muncplCd;
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
