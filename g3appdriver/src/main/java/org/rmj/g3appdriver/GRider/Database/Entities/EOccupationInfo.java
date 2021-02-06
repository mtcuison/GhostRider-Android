package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Occupation_Info")
public class EOccupationInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sOccptnID")
    private String OccptnID;
    @ColumnInfo(name = "sOccptnNm")
    private String OccptnNm;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EOccupationInfo() {
    }

    @NonNull
    public String getOccptnID() {
        return OccptnID;
    }

    public void setOccptnID(@NonNull String occptnID) {
        OccptnID = occptnID;
    }

    public String getOccptnNm() {
        return OccptnNm;
    }

    public void setOccptnNm(String occptnNm) {
        OccptnNm = occptnNm;
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
