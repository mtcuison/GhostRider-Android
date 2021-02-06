package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MC_Brand")
public class EMcBrand {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sBrandIDx")
    private String BrandIDx;
    @ColumnInfo(name = "sBrandNme")
    private String BrandNme;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EMcBrand() {
    }

    @NonNull
    public String getBrandIDx() {
        return BrandIDx;
    }

    public void setBrandIDx(@NonNull String brandIDx) {
        BrandIDx = brandIDx;
    }

    public String getBrandNme() {
        return BrandNme;
    }

    public void setBrandNme(String brandNme) {
        BrandNme = brandNme;
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
