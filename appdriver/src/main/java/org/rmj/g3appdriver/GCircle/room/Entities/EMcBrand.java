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

package org.rmj.g3appdriver.GCircle.room.Entities;

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
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

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

    public String getLstUpdte() {
        return LstUpdte;
    }

    public void setLstUpdte(String lstUpdte) {
        LstUpdte = lstUpdte;
    }
}
