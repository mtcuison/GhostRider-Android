package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Province_Info")
public class EProvinceInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sProvIDxx")
    private String ProvIDxx;
    @ColumnInfo(name = "sProvName")
    private String ProvName;
    @ColumnInfo(name = "cRecdStat")
    private char RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EProvinceInfo() {
    }

    @NonNull
    public String getProvIDxx() {
        return ProvIDxx;
    }

    public void setProvIDxx(@NonNull String provIDxx) {
        ProvIDxx = provIDxx;
    }

    public String getProvName() {
        return ProvName;
    }

    public void setProvName(String provName) {
        ProvName = provName;
    }

    public char getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(char recdStat) {
        RecdStat = recdStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
