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

@Entity(tableName = "Credit_Online_Application_List_CI")
public class ECIEvaluation {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;

    @ColumnInfo(name = "sCredInvx")
    private String CredInvx;

    @ColumnInfo(name = "sLandMark")
    private String LandMark;

    @ColumnInfo(name = "cOwnershp")
    private String Ownershp;

    @ColumnInfo(name = "cOwnOther")
    private String OwnOther;

    @ColumnInfo(name = "cHouseTyp")
    private String HouseTyp;

    @ColumnInfo(name = "cGaragexx")
    private String Garagexx;

    @ColumnInfo(name = "nLatitude")
    private String Latitude;

    @ColumnInfo(name = "nLongitud")
    private String Longitud;

    @ColumnInfo(name = "cHasOther")
    private String HasOther;

    @ColumnInfo(name = "cHasRecrd")
    private String HasRecrd;


    @ColumnInfo(name = "sRemRecrd")
    private String RemRecrd;

    @ColumnInfo(name = "sNeighbr1")
    private String Neighbr1;
    @ColumnInfo(name = "sAddress1")
    private String Address1;
    @ColumnInfo(name = "sReltnCD1")
    private String ReltnCD1;
    @ColumnInfo(name = "sMobileN1")
    private String MobileN1;
    @ColumnInfo(name = "cFeedBck1")
    private String FeedBck1;
    @ColumnInfo(name = "sFeedBck1")
    private String FBRemrk1;

    @ColumnInfo(name = "sNeighbr2")
    private String Neighbr2;
    @ColumnInfo(name = "sAddress2")
    private String Address2;
    @ColumnInfo(name = "sReltnCD2")
    private String ReltnCD2;
    @ColumnInfo(name = "sMobileN2")
    private String MobileN2;
    @ColumnInfo(name = "cFeedBck2")
    private String FeedBck2;
    @ColumnInfo(name = "sFBRemrk2")
    private String FBRemrk2;

    @ColumnInfo(name = "sNeighbr3")
    private String Neighbr3;
    @ColumnInfo(name = "sAddress3")
    private String Address3;
    @ColumnInfo(name = "sReltnCD3")
    private String ReltnCD3;
    @ColumnInfo(name = "sMobileN3")
    private String MobileN3;
    @ColumnInfo(name = "cFeedBck3")
    private String FeedBck3;
    @ColumnInfo(name = "sFBRemrk3")
    private String FBRemrk3;

    @ColumnInfo(name = "nWaterBil")
    private String WaterBil;
    @ColumnInfo(name = "nElctrcBl")
    private String ElctrcBl;
    @ColumnInfo(name = "nFoodAllw")
    private String FoodAllw;
    @ColumnInfo(name = "nLoanAmtx")
    private String LoanAmtx;
    @ColumnInfo(name = "nEducExpn")
    private String EducExpn;
    @ColumnInfo(name = "nOthrExpn")
    private String OthrExpn;

    @ColumnInfo(name = "cGamblerx")
    private String Gamblerx;
    @ColumnInfo(name = "cWomanizr")
    private String Womanizr;
    @ColumnInfo(name = "cHvyBrwer")
    private String HvyBrwer;
    @ColumnInfo(name = "cWithRepo")
    private String WithRepo;
    @ColumnInfo(name = "cWithMort")
    private String WithMort;
    @ColumnInfo(name = "cArrogant")
    private String Arrogant;
    @ColumnInfo(name = "sOtherBad")
    private String OtherBad;

    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;
    @ColumnInfo(name = "dApproved")
    private String Approved;
    @ColumnInfo(name = "dReceived")
    private String Received;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ECIEvaluation() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }
    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }
    public String getCredInvx() {
        return CredInvx;
    }

    public void setCredInvx(String credInvx) {
        CredInvx = credInvx;
    }

    public String getLandMark() {
        return LandMark;
    }

    public void setLandMark(String landMark) {
        LandMark = landMark;
    }

    public String getOwnershp() {
        return Ownershp;
    }

    public void setOwnershp(String ownershp) {
        Ownershp = ownershp;
    }

    public String getOwnOther() {
        return OwnOther;
    }

    public void setOwnOther(String ownOther) {
        OwnOther = ownOther;
    }

    public String getHouseTyp() {
        return HouseTyp;
    }

    public void setHouseTyp(String houseTyp) {
        HouseTyp = houseTyp;
    }

    public String getGaragexx() {
        return Garagexx;
    }

    public void setGaragexx(String garagexx) {
        Garagexx = garagexx;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getHasOther() {
        return HasOther;
    }

    public void setHasOther(String hasOther) {
        HasOther = hasOther;
    }

    public String getHasRecrd() {
        return HasRecrd;
    }

    public void setHasRecrd(String hasRecrd) {
        HasRecrd = hasRecrd;
    }
    public String getRemRecrd() {
        return RemRecrd;
    }

    public void setRemRecrd(String remRecrd) {
        RemRecrd = remRecrd;
    }
    public String getNeighbr1() {
        return Neighbr1;
    }

    public void setNeighbr1(String neighbr1) {
        Neighbr1 = neighbr1;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String address1) {
        Address1 = address1;
    }

    public String getReltnCD1() {
        return ReltnCD1;
    }

    public void setReltnCD1(String reltnCD1) {
        ReltnCD1 = reltnCD1;
    }

    public String getMobileN1() {
        return MobileN1;
    }

    public void setMobileN1(String mobileN1) {
        MobileN1 = mobileN1;
    }

    public String getFeedBck1() {
        return FeedBck1;
    }

    public void setFeedBck1(String feedBck1) {
        FeedBck1 = feedBck1;
    }

    public String getFBRemrk1() {
        return FBRemrk1;
    }

    public void setFBRemrk1(String FBRemrk1) {
        this.FBRemrk1 = FBRemrk1;
    }

    public String getNeighbr2() {
        return Neighbr2;
    }

    public void setNeighbr2(String neighbr2) {
        Neighbr2 = neighbr2;
    }

    public String getAddress2() {
        return Address2;
    }

    public void setAddress2(String address2) {
        Address2 = address2;
    }

    public String getReltnCD2() {
        return ReltnCD2;
    }

    public void setReltnCD2(String reltnCD2) {
        ReltnCD2 = reltnCD2;
    }

    public String getMobileN2() {
        return MobileN2;
    }

    public void setMobileN2(String mobileN2) {
        MobileN2 = mobileN2;
    }

    public String getFeedBck2() {
        return FeedBck2;
    }

    public void setFeedBck2(String feedBck2) {
        FeedBck2 = feedBck2;
    }

    public String getFBRemrk2() {
        return FBRemrk2;
    }

    public void setFBRemrk2(String FBRemrk2) {
        this.FBRemrk2 = FBRemrk2;
    }

    public String getNeighbr3() {
        return Neighbr3;
    }

    public void setNeighbr3(String neighbr3) {
        Neighbr3 = neighbr3;
    }

    public String getAddress3() {
        return Address3;
    }

    public void setAddress3(String address3) {
        Address3 = address3;
    }

    public String getReltnCD3() {
        return ReltnCD3;
    }

    public void setReltnCD3(String reltnCD3) {
        ReltnCD3 = reltnCD3;
    }

    public String getMobileN3() {
        return MobileN3;
    }

    public void setMobileN3(String mobileN3) {
        MobileN3 = mobileN3;
    }

    public String getFeedBck3() {
        return FeedBck3;
    }

    public void setFeedBck3(String feedBck3) {
        FeedBck3 = feedBck3;
    }

    public String getFBRemrk3() {
        return FBRemrk3;
    }

    public void setFBRemrk3(String FBRemrk3) {
        this.FBRemrk3 = FBRemrk3;
    }

    public String getWaterBil() {
        return WaterBil;
    }

    public void setWaterBil(String waterBil) {
        WaterBil = waterBil;
    }

    public String getElctrcBl() {
        return ElctrcBl;
    }

    public void setElctrcBl(String elctrcBl) {
        ElctrcBl = elctrcBl;
    }

    public String getFoodAllw() {
        return FoodAllw;
    }

    public void setFoodAllw(String foodAllw) {
        FoodAllw = foodAllw;
    }

    public String getLoanAmtx() {
        return LoanAmtx;
    }

    public void setLoanAmtx(String loanAmtx) {
        LoanAmtx = loanAmtx;
    }

    public String getEducExpn() {
        return EducExpn;
    }

    public void setEducExpn(String educExpn) {
        EducExpn = educExpn;
    }

    public String getOthrExpn() {
        return OthrExpn;
    }

    public void setOthrExpn(String othrExpn) {
        OthrExpn = othrExpn;
    }

    public String getGamblerx() {
        return Gamblerx;
    }

    public void setGamblerx(String gamblerx) {
        Gamblerx = gamblerx;
    }

    public String getWomanizr() {
        return Womanizr;
    }

    public void setWomanizr(String womanizr) {
        Womanizr = womanizr;
    }

    public String getHvyBrwer() {
        return HvyBrwer;
    }

    public void setHvyBrwer(String hvyBrwer) {
        HvyBrwer = hvyBrwer;
    }

    public String getWithRepo() {
        return WithRepo;
    }

    public void setWithRepo(String withRepo) {
        WithRepo = withRepo;
    }

    public String getWithMort() {
        return WithMort;
    }

    public void setWithMort(String withMort) {
        WithMort = withMort;
    }

    public String getArrogant() {
        return Arrogant;
    }

    public void setArrogant(String arrogant) {
        Arrogant = arrogant;
    }

    public String getOtherBad() {
        return OtherBad;
    }

    public void setOtherBad(String otherBad) {
        OtherBad = otherBad;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {
        Approved = approved;
    }

    public String getReceived() {
        return Received;
    }

    public void setReceived(String received) {
        Received = received;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }


}
