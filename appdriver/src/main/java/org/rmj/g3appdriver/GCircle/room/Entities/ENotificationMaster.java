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

@Entity(tableName = "Notification_Info_Master")
public class ENotificationMaster {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sMesgIDxx")
    private String MesgIDxx;
    @ColumnInfo(name = "sParentxx")
    private String Parentxx;
    @ColumnInfo(name = "dCreatedx")
    private String Createdx;
    @ColumnInfo(name = "sAppSrcex")
    private String AppSrcex;
    @ColumnInfo(name = "sCreatrID")
    private String CreatrID;
    @ColumnInfo(name = "sCreatrNm")
    private String CreatrNm;
    @ColumnInfo(name = "sMsgTitle")
    private String MsgTitle;
    @ColumnInfo(name = "sMessagex")
    private String Messagex;
    @ColumnInfo(name = "sURLxxxxx")
    private String URLxxxxx;
    @ColumnInfo(name = "sDataSndx")
    private String DataSndx;
    @ColumnInfo(name = "sMsgTypex")
    private String MsgTypex;
    @ColumnInfo(name = "cSentxxxx")
    private String Sentcxxx;
    @ColumnInfo(name = "dSentxxxx")
    private String Sentdxxx;

    public ENotificationMaster() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getMesgIDxx() {
        return MesgIDxx;
    }

    public void setMesgIDxx(String mesgIDxx) {
        MesgIDxx = mesgIDxx;
    }

    public String getParentxx() {
        return Parentxx;
    }

    public void setParentxx(String parentxx) {
        Parentxx = parentxx;
    }

    public String getCreatedx() {
        return Createdx;
    }

    public void setCreatedx(String createdx) {
        Createdx = createdx;
    }

    public String getAppSrcex() {
        return AppSrcex;
    }

    public void setAppSrcex(String appSrcex) {
        AppSrcex = appSrcex;
    }

    public String getCreatrID() {
        return CreatrID;
    }

    public void setCreatrID(String creatrID) {
        CreatrID = creatrID;
    }

    public String getCreatrNm() {
        return CreatrNm;
    }

    public void setCreatrNm(String creatrNm) {
        CreatrNm = creatrNm;
    }

    public String getMsgTitle() {
        return MsgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        MsgTitle = msgTitle;
    }

    public String getMessagex() {
        return Messagex;
    }

    public void setMessagex(String messagex) {
        Messagex = messagex;
    }

    public String getURLxxxxx() {
        return URLxxxxx;
    }

    public void setURLxxxxx(String URLxxxxx) {
        this.URLxxxxx = URLxxxxx;
    }

    public String getDataSndx() {
        return DataSndx;
    }

    public void setDataSndx(String dataSndx) {
        DataSndx = dataSndx;
    }

    public String getMsgTypex() {
        return MsgTypex;
    }

    public void setMsgTypex(String msgTypex) {
        MsgTypex = msgTypex;
    }

    public String getSentcxxx() {
        return Sentcxxx;
    }

    public void setSentcxxx(String sentcxxx) {
        Sentcxxx = sentcxxx;
    }

    public String getSentdxxx() {
        return Sentdxxx;
    }

    public void setSentdxxx(String sentdxxx) {
        Sentdxxx = sentdxxx;
    }
}
