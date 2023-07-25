/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 9/2/21, 11:03 AM
 * project file last modified : 9/2/21, 11:03 AM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.rmj.g3appdriver.etc.AppConstants;

@Entity (tableName = "xxxAOEmpRole")
public class EEmployeeRole {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sObjectNm")
    private String ObjectNm = "";
    @ColumnInfo(name = "sObjectDs")
    private String ObjectDs = "";
    @ColumnInfo(name = "cObjectTp")
    private String ObjectTP = "";
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat = "";
    @ColumnInfo(name = "sParentxx")
    private String Parentxx = "";
    @ColumnInfo(name = "sSeqnceCd")
    private String SeqnceCd = "";
    @ColumnInfo(name = "cHasChild")
    private String HasChild = "";
    @ColumnInfo(name = "dModified")
    private String Modified = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = AppConstants.DATE_MODIFIED();

    public EEmployeeRole() {
    }

    @NonNull
    public String getObjectNm() {
        return ObjectNm;
    }

    public void setObjectNm(@NonNull String objectNm) {
        ObjectNm = objectNm;
    }

    public String getObjectDs() {
        return ObjectDs;
    }

    public void setObjectDs(String objectDs) {
        ObjectDs = objectDs;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
        RecdStat = recdStat;
    }

    public String getParentxx() {
        return Parentxx;
    }

    public void setParentxx(String parentxx) {
        Parentxx = parentxx;
    }

    public String getSeqnceCd() {
        return SeqnceCd;
    }

    public void setSeqnceCd(String seqnceCd) {
        SeqnceCd = seqnceCd;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

    public String getObjectTP() {
        return ObjectTP;
    }

    public void setObjectTP(String objectTP) {
        ObjectTP = objectTP;
    }

    public String getHasChild() {
        return HasChild;
    }

    public void setHasChild(String hasChild) {
        HasChild = hasChild;
    }
}
