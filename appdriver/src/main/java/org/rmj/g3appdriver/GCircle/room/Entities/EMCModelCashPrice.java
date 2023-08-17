package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity (tableName = "MC_Cash_Price", primaryKeys = {"sModelIDx", "sMCCatNme", "sModelNme"})
public class EMCModelCashPrice {

    @NonNull
    @ColumnInfo(name = "sModelIDx")
    private String ModelIDx = "";

    @NonNull
    @ColumnInfo(name = "sMCCatNme")
    private String MCCatNme = "";

    @NonNull
    @ColumnInfo(name = "sModelNme")
    private String ModelNme = "";

    @ColumnInfo(name = "sBrandNme")
    private String BrandNme = "";
    @ColumnInfo(name = "nSelPrice")
    private Double SelPrice = 0.0;
    @ColumnInfo(name = "nLastPrce")
    private Double LastPrce = 0.0;
    @ColumnInfo(name = "nDealrPrc")
    private Double DealrPrc = 0.0;
    @ColumnInfo(name = "dPricexxx")
    private String Pricexxx = "";
    @ColumnInfo(name = "sBrandIDx")
    private String BrandIDx = "";
    @ColumnInfo(name = "sMCCatIDx")
    private String MCCatIDx = "";

    public EMCModelCashPrice() {
    }

    public String getModelIDx() {
        return ModelIDx;
    }

    public void setModelIDx(String modelIDx) {
        ModelIDx = modelIDx;
    }

    public String getMCCatNme() {
        return MCCatNme;
    }

    public void setMCCatNme(String MCCatNme) {
        this.MCCatNme = MCCatNme;
    }

    public String getModelNme() {
        return ModelNme;
    }

    public void setModelNme(String modelNme) {
        ModelNme = modelNme;
    }

    public String getBrandNme() {
        return BrandNme;
    }

    public void setBrandNme(String brandNme) {
        BrandNme = brandNme;
    }

    public Double getSelPrice() {
        return SelPrice;
    }

    public void setSelPrice(Double selPrice) {
        SelPrice = selPrice;
    }

    public Double getLastPrce() {
        return LastPrce;
    }

    public void setLastPrce(Double lastPrce) {
        LastPrce = lastPrce;
    }

    public Double getDealrPrc() {
        return DealrPrc;
    }

    public void setDealrPrc(Double dealrPrc) {
        DealrPrc = dealrPrc;
    }

    public String getPricexxx() {
        return Pricexxx;
    }

    public void setPricexxx(String pricexxx) {
        Pricexxx = pricexxx;
    }

    public String getBrandIDx() {
        return BrandIDx;
    }

    public void setBrandIDx(String brandIDx) {
        BrandIDx = brandIDx;
    }

    public String getMCCatIDx() {
        return MCCatIDx;
    }

    public void setMCCatIDx(String MCCatIDx) {
        this.MCCatIDx = MCCatIDx;
    }
}
