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

@Entity(tableName = "Client_Update_Request")
public class EClientUpdate {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sClientID") //Primary Key
    private String ClientID;
    @ColumnInfo(name = "sSourceCd") //DCPa
    private String SourceCd;
    @ColumnInfo(name = "sSourceNo") //sTransNox ng DCP Master
    private String SourceNo;
    @ColumnInfo(name = "sDtlSrcNo") //sAcctNoxx ng DCP Detail
    private String DtlSrcNo;
    @ColumnInfo(name = "sLastName")
    private String LastName;
    @ColumnInfo(name = "sFrstName")
    private String FrstName;
    @ColumnInfo(name = "sMiddName")
    private String MiddName;
    @ColumnInfo(name = "sSuffixNm")
    private String SuffixNm;
    @ColumnInfo(name = "sHouseNox")
    private String HouseNox;
    @ColumnInfo(name = "sAddressx")
    private String Addressx;
    @ColumnInfo(name = "sBarangay")
    private String Barangay;
    @ColumnInfo(name = "sTownIDxx")
    private String TownIDxx;
    @ColumnInfo(name = "cGenderxx")
    private String Genderxx;
    @ColumnInfo(name = "cCivlStat")
    private String CivlStat;
    @ColumnInfo(name = "dBirthDte")
    private String BirthDte;
    @ColumnInfo(name = "dBirthPlc")
    private String BirthPlc;
    @ColumnInfo(name = "sLandline")
    private String Landline;
    @ColumnInfo(name = "sMobileNo")
    private String MobileNo;
    @ColumnInfo(name = "sEmailAdd")
    private String EmailAdd;
    @ColumnInfo(name = "sImageNme")
    private String ImageNme;
    @ColumnInfo(name = "cSendStat")
    private String SendStat;
    @ColumnInfo(name = "dModified")
    private String Modified;

    public EClientUpdate() {
    }

    @NonNull
    public String getClientID() {
        return ClientID;
    }

    public void setClientID(@NonNull String sClientID) {
        this.ClientID = sClientID;
    }

    public String getSourceCd() {
        return SourceCd;
    }

    public void setSourceCd(String sourceCd) {
        SourceCd = sourceCd;
    }

    public String getSourceNo() {
        return SourceNo;
    }

    public void setSourceNo(String sourceNo) {
        SourceNo = sourceNo;
    }

    public String getDtlSrcNo() {
        return DtlSrcNo;
    }

    public void setDtlSrcNo(String dtlSrcNo) {
        DtlSrcNo = dtlSrcNo;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFrstName() {
        return FrstName;
    }

    public void setFrstName(String frstName) {
        FrstName = frstName;
    }

    public String getMiddName() {
        return MiddName;
    }

    public void setMiddName(String middName) {
        MiddName = middName;
    }

    public String getSuffixNm() {
        return SuffixNm;
    }

    public void setSuffixNm(String suffixNm) {
        SuffixNm = suffixNm;
    }

    public String getHouseNox() {
        return HouseNox;
    }

    public void setHouseNox(String houseNox) {
        HouseNox = houseNox;
    }

    public String getAddressx() {
        return Addressx;
    }

    public void setAddressx(String addressx) {
        Addressx = addressx;
    }
    public String getBarangay() {
        return Barangay;
    }

    public void setBarangay(String barangay) {
        Barangay = barangay;
    }
    public String getTownIDxx() {
        return TownIDxx;
    }

    public void setTownIDxx(String townIDxx) {
        TownIDxx = townIDxx;
    }

    public String getGenderxx() {
        return Genderxx;
    }

    public void setGenderxx(String genderxx) {
        Genderxx = genderxx;
    }

    public String getCivlStat() {
        return CivlStat;
    }

    public void setCivlStat(String civlStat) {
        CivlStat = civlStat;
    }

    public String getBirthDte() {
        return BirthDte;
    }

    public void setBirthDte(String birthDte) {
        BirthDte = birthDte;
    }

    public String getBirthPlc() {
        return BirthPlc;
    }

    public void setBirthPlc(String birthPlc) {
        BirthPlc = birthPlc;
    }

    public String getLandline() {
        return Landline;
    }

    public void setLandline(String landline) {
        Landline = landline;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getEmailAdd() {
        return EmailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getImageNme() {
        return ImageNme;
    }

    public void setImageNme(String imageNme) {
        ImageNme = imageNme;
    }

    public String getSendStat() {
        return SendStat;
    }

    public void setSendStat(String sendStat) {
        SendStat = sendStat;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }
}
