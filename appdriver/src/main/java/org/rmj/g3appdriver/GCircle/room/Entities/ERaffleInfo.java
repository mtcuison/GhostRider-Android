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

@Entity(tableName = "PromoLocal_Detail", primaryKeys = {"sBranchCd", "dTransact"})
public class ERaffleInfo {

    @NonNull
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;

    @NonNull
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "sClientNm")
    private String ClientNm;
    @ColumnInfo(name = "sAddressx")
    private String Addressx;
    @ColumnInfo(name = "sTownIDxx")
    private String TownIDxx;
    @ColumnInfo(name = "sProvIDxx")
    private String ProvIDxx;
    @ColumnInfo(name = "sDocTypex")
    private String DocTypex;
    @ColumnInfo(name = "sDocNoxxx")
    private String DocNoxxx;
    @ColumnInfo(name = "sMobileNo")
    private String MobileNo;
    @ColumnInfo(name = "cSendStat")
    private char SendStat;
    @ColumnInfo(name = "sTimeStmp")
    private String TimeStmp;

    public ERaffleInfo() {
    }

    @NonNull
    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(@NonNull String branchCd) {
        BranchCd = branchCd;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getClientNm() {
        return ClientNm;
    }

    public void setClientNm(String clientNm) {
        ClientNm = clientNm;
    }

    public String getAddressx() {
        return Addressx;
    }

    public void setAddressx(String addressx) {
        Addressx = addressx;
    }

    public String getTownIDxx() {
        return TownIDxx;
    }

    public void setTownIDxx(String townIDxx) {
        TownIDxx = townIDxx;
    }

    public String getProvIDxx() {
        return ProvIDxx;
    }

    public void setProvIDxx(String provIDxx) {
        ProvIDxx = provIDxx;
    }

    public String getDocTypex() {
        return DocTypex;
    }

    public void setDocTypex(String docTypex) {
        DocTypex = docTypex;
    }

    public String getDocNoxxx() {
        return DocNoxxx;
    }

    public void setDocNoxxx(String docNoxxx) {
        DocNoxxx = docNoxxx;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public char getSendStat() {
        return SendStat;
    }

    public void setSendStat(char sendStat) {
        SendStat = sendStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
