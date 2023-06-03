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

@Entity(tableName = "EDocSys_File")
public class EFileCode {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sFileCode")
    private String FileCode;
    @ColumnInfo(name = "sBarrcode")
    private String Barrcode;
    @ColumnInfo(name = "sBriefDsc")
    private String BriefDsc;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "nEntryNox")
    private int EntryNox;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

    public EFileCode() {

    }

    @NonNull
    public String getFileCode() {
        return FileCode;
    }

    public void setFileCode(@NonNull String fileCode) {



        FileCode = fileCode;
    }

    public String getBarrcode() {
        return Barrcode;
    }

    public void setBarrcode(String barrcode) {
        Barrcode = barrcode;
    }

    public String getBriefDsc() {
        return BriefDsc;
    }

    public void setBriefDsc(String briefDsc) {
        BriefDsc = briefDsc;
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

    public int getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(int entryNox) {
        EntryNox = entryNox;
    }

    public String getLstUpdte() {
        return LstUpdte;
    }

    public void setLstUpdte(String lstUpdte) {
        LstUpdte = lstUpdte;
    }
}
