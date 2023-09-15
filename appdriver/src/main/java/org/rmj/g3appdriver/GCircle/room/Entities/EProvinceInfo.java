/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

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
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

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

    public String getLstUpdte() {
        return LstUpdte;
    }

    public void setLstUpdte(String lstUpdte) {
        LstUpdte = lstUpdte;
    }
}
