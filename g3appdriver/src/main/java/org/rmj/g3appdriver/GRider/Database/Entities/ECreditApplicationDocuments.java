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

package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Credit_Online_Application_Documents", primaryKeys = {"sTransNox", "sFileCode"})
public class ECreditApplicationDocuments implements Serializable {

    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @NonNull
    @ColumnInfo(name = "sFileCode")
    private String FileCode;
    @ColumnInfo(name = "nEntryNox")
    private int EntryNox;
    @ColumnInfo(name = "sImageNme")
    private String ImageNme;
    @ColumnInfo(name = "sFileLoc")
    private String FileLoc;
    @ColumnInfo(name = "sSendStat")
    private String SendStat;

    public ECreditApplicationDocuments() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

//    @NonNull
//    public String getDocTransNox() {
//        return DocTransNox;
//    }
//
//    public void setDocTransNox(@NonNull String transNox) {
//        DocTransNox = transNox;
//    }

    @NonNull
    public String getFileCode() {
        return FileCode;
    }

    public void setFileCode(String fileCode) {
        FileCode = fileCode;
    }

    public int getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(int entryNox) {
        EntryNox = entryNox;
    }

    public String getImageNme() {
        return ImageNme;
    }
    public void setImageNme(String imageNme) {
        ImageNme = imageNme;
    }

    public String getFileLoc() {
        return FileLoc;
    }
    public void setFileLoc(String fileLoc) {
        FileLoc = fileLoc;
    }

    public String getSendStat() {
        return SendStat;
    }
    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }
}
