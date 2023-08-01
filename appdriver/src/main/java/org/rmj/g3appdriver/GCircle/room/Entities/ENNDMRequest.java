/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/17/21 9:06 AM
 * project file last modified : 5/17/21 9:06 AM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "NNDM_Event_Request")
public class ENNDMRequest {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sSourceNo")
    private String SourceNo;
    @ColumnInfo(name = "sSourceCD")
    private String SourceCD;
    @ColumnInfo(name = "sReqstTyp")
    private String ReqstTyp;
    @ColumnInfo(name = "sJsonData")
    private String JsonData;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;
    @ColumnInfo(name = "dCreatedx")
    private String Createdx;
    @ColumnInfo(name = "dReceived")
    private String Received;
    @ColumnInfo(name = "dPostedxx")
    private String Postedxx;

    public ENNDMRequest() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getSourceNo() {
        return SourceNo;
    }

    public void setSourceNo(String sourceNo) {
        SourceNo = sourceNo;
    }

    public String getSourceCD() {
        return SourceCD;
    }

    public void setSourceCD(String sourceCD) {
        SourceCD = sourceCD;
    }

    public String getReqstTyp() {
        return ReqstTyp;
    }

    public void setReqstTyp(String reqstTyp) {
        ReqstTyp = reqstTyp;
    }

    public String getJsonData() {
        return JsonData;
    }

    public void setJsonData(String jsonData) {
        JsonData = jsonData;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getCreatedx() {
        return Createdx;
    }

    public void setCreatedx(String createdx) {
        Createdx = createdx;
    }

    public String getReceived() {
        return Received;
    }

    public void setReceived(String received) {
        Received = received;
    }

    public String getPostedxx() {
        return Postedxx;
    }

    public void setPostedxx(String postedxx) {
        Postedxx = postedxx;
    }
}
