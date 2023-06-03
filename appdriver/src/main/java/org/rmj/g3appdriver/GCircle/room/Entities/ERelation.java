/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/14/21 3:58 PM
 * project file last modified : 5/14/21 3:58 PM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Relation")
public class ERelation {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sRelatnID")
    private String RelatnID;

    @ColumnInfo(name = "sRelatnDs")
    private String RelatnDs;

    @ColumnInfo(name = "cRecdStats")
    private String RecdStats;

    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;
    @NonNull
    public String getRelatnID() {
        return RelatnID;
    }

    public void setRelatnID(@NonNull String relatnID) {
        RelatnID = relatnID;
    }

    public String getRelatnDs() {
        return RelatnDs;
    }

    public void setRelatnDs(String relatnDs) {
        RelatnDs = relatnDs;
    }

    public String getRecdStats() {
        return RecdStats;
    }

    public void setRecdStats(String recdStats) {
        RecdStats = recdStats;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

}
