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

@Entity(tableName = "Mc_Model_Price")
public class EMcModelPrice {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sModelIDx")
    private String ModelIDx;
    @ColumnInfo(name = "nSelPrice")
    private String SelPrice;
    @ColumnInfo(name = "nLastPrce")
    private String LastPrce;
    @ColumnInfo(name = "nDealrPrc")
    private String DealrPrc;
    @ColumnInfo(name = "nMinDownx")
    private String MinDownx;
    @ColumnInfo(name = "sMCCatIDx")
    private String MCCatIDx;
    @ColumnInfo(name = "dPricexxx")
    private String Pricexxx;
    @ColumnInfo(name = "dInsPrice")
    private String InsPrice;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

    public EMcModelPrice() {
    }

    @NonNull
    public String getModelIDx() {
        return ModelIDx;
    }

    public void setModelIDx(@NonNull String modelIDx) {
        ModelIDx = modelIDx;
    }

    public String getSelPrice() {
        return SelPrice;
    }

    public void setSelPrice(String selPrice) {
        SelPrice = selPrice;
    }

    public String getLastPrce() {
        return LastPrce;
    }

    public void setLastPrce(String lastPrce) {
        LastPrce = lastPrce;
    }

    public String getDealrPrc() {
        return DealrPrc;
    }

    public void setDealrPrc(String dealrPrc) {
        DealrPrc = dealrPrc;
    }

    public String getMinDownx() {
        return MinDownx;
    }

    public void setMinDownx(String minDownx) {
        MinDownx = minDownx;
    }

    public String getMCCatIDx() {
        return MCCatIDx;
    }

    public void setMCCatIDx(String MCCatIDx) {
        this.MCCatIDx = MCCatIDx;
    }

    public String getPricexxx() {
        return Pricexxx;
    }

    public void setPricexxx(String pricexxx) {
        Pricexxx = pricexxx;
    }

    public String getInsPrice() {
        return InsPrice;
    }

    public void setInsPrice(String insPrice) {
        InsPrice = insPrice;
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

    public String getLstUpdte() {
        return LstUpdte;
    }

    public void setLstUpdte(String lstUpdte) {
        LstUpdte = lstUpdte;
    }
}
