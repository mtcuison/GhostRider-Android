package org.rmj.g3appdriver.FacilityTrack.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Warehouse")
public class EWarehouse {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sWHouseID")
    private String WHouseID = "";
    @ColumnInfo(name = "sWHouseNm")
    private String WHouseNm = "";
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd = "";
    @ColumnInfo(name = "dLastChck")
    private String LastChck = "";
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat = "";
    @ColumnInfo(name = "sModified")
    private String Modified = "";
    @ColumnInfo(name = "dModified")
    private String DateMdfy = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EWarehouse() {
    }

    @NonNull
    public String getWHouseID() {
        return WHouseID;
    }

    public void setWHouseID(@NonNull String WHouseID) {
        this.WHouseID = WHouseID;
    }

    public String getWHouseNm() {
        return WHouseNm;
    }

    public void setWHouseNm(String WHouseNm) {
        this.WHouseNm = WHouseNm;
    }

    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(String branchCd) {
        BranchCd = branchCd;
    }

    public String getLastChck() {
        return LastChck;
    }

    public void setLastChck(String lastChck) {
        LastChck = lastChck;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
        RecdStat = recdStat;
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
