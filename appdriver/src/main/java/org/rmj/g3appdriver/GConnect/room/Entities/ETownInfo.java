/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.GConnect.room.Entities;

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
