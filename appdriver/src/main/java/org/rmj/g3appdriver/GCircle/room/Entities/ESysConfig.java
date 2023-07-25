/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/26/21 4:16 PM
 * project file last modified : 4/26/21 4:16 PM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "xxxSysConfig")
public class ESysConfig {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sConfigCd")
    private String ConfigCd;
    @ColumnInfo(name = "sConfigDs")
    private String ConfigDs;
    @ColumnInfo(name = "sConfigVl")
    private String ConfigVl;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ESysConfig() {
    }

    @NonNull
    public String getConfigCd() {
        return ConfigCd;
    }

    public void setConfigCd(@NonNull String configCd) {
        ConfigCd = configCd;
    }

    public String getConfigDs() {
        return ConfigDs;
    }

    public void setConfigDs(String configDs) {
        ConfigDs = configDs;
    }

    public String getConfigVl() {
        return ConfigVl;
    }

    public void setConfigVl(String configVl) {
        ConfigVl = configVl;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
