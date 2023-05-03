/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/21/21 9:51 AM
 * project file last modified : 5/21/21 9:51 AM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "xxxAppConfig")
public class EAppConfig {

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

    public EAppConfig() {
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
