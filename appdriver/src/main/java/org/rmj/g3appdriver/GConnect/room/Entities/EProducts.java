package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Product_Inventory")
public class EProducts {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sListngID")
    private String ListngID;
    @ColumnInfo(name = "sBriefDsc")
    private String BriefDsc;
    @ColumnInfo(name = "sDescript")
    private String Descript;
    @ColumnInfo(name = "sStockIDx")
    private String StockIDx;
    @ColumnInfo(name = "xBarCodex")
    private String BarCodex;
    @ColumnInfo(name = "xDescript")
    private String Descrptx;
    @ColumnInfo(name = "nRatingxx")
    private String Ratingxx;
    @ColumnInfo(name = "xBrandNme")
    private String BrandNme;
    @ColumnInfo(name = "sModelIDx")
    private String ModelIDx;
    @ColumnInfo(name = "xModelNme")
    private String ModelNme;
    @ColumnInfo(name = "sImagesxx")
    private String Imagesxx;
    @ColumnInfo(name = "xColorNme")
    private String ColorNme;
    @ColumnInfo(name = "xCategrNm")
    private String CategrNm;
    @ColumnInfo(name = "nTotalQty")
    private String TotalQty;
    @ColumnInfo(name = "nQtyOnHnd")
    private String QtyOnHnd;
    @ColumnInfo(name = "nResvOrdr")
    private String ResvOrdr;
    @ColumnInfo(name = "nSoldQtyx")
    private String SoldQtyx;
    @ColumnInfo(name = "nUnitPrce")
    private String UnitPrce;
    @ColumnInfo(name = "dListStrt")
    private String ListStrt;
    @ColumnInfo(name = "dListEndx")
    private String ListEndx;
    @ColumnInfo(name = "cAllwCrdt")
    private String AllwCrdt;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EProducts() {

    }

    @NonNull
    public String getListngID() {
        return ListngID;
    }

    public void setListngID(@NonNull String listngID) {
        ListngID = listngID;
    }

    public String getBriefDsc() {
        return BriefDsc;
    }

    public void setBriefDsc(String briefDsc) {
        BriefDsc = briefDsc;
    }

    public String getDescript() {
        return Descript;
    }

    public void setDescript(String descript) {
        Descript = descript;
    }

    public String getBarCodex() {
        return BarCodex;
    }

    public void setBarCodex(String barCodex) {
        BarCodex = barCodex;
    }

    public String getDescrptx() {
        return Descrptx;
    }

    public void setDescrptx(String descrptx) {
        Descrptx = descrptx;
    }

    public String getBrandNme() {
        return BrandNme;
    }

    public void setBrandNme(String brandNme) {
        BrandNme = brandNme;
    }

    public String getModelNme() {
        return ModelNme;
    }

    public void setModelNme(String modelNme) {
        ModelNme = modelNme;
    }

    public String getColorNme() {
        return ColorNme;
    }

    public void setColorNme(String colorNme) {
        ColorNme = colorNme;
    }

    public String getCategrNm() {
        return CategrNm;
    }

    public void setCategrNm(String categrNm) {
        CategrNm = categrNm;
    }

    public String getTotalQty() {
        return TotalQty;
    }

    public void setTotalQty(String totalQty) {
        TotalQty = totalQty;
    }

    public String getQtyOnHnd() {
        return QtyOnHnd;
    }

    public void setQtyOnHnd(String qtyOnHnd) {
        QtyOnHnd = qtyOnHnd;
    }

    public String getResvOrdr() {
        return ResvOrdr;
    }

    public void setResvOrdr(String resvOrdr) {
        ResvOrdr = resvOrdr;
    }

    public String getSoldQtyx() {
        return SoldQtyx;
    }

    public void setSoldQtyx(String soldQtyx) {
        SoldQtyx = soldQtyx;
    }

    public String getUnitPrce() {
        return UnitPrce;
    }

    public void setUnitPrce(String unitPrce) {
        UnitPrce = unitPrce;
    }

    public String getListStrt() {
        return ListStrt;
    }

    public void setListStrt(String listStrt) {
        ListStrt = listStrt;
    }

    public String getListEndx() {
        return ListEndx;
    }

    public void setListEndx(String listEndx) {
        ListEndx = listEndx;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }

    public String getImagesxx() {
        return Imagesxx;
    }

    public void setImagesxx(String imagesxx) {
        Imagesxx = imagesxx;
    }

    public String getRatingxx() {
        return Ratingxx;
    }

    public void setRatingxx(String ratingxx) {
        Ratingxx = ratingxx;
    }

    public String getStockIDx() {
        return StockIDx;
    }

    public void setStockIDx(String stockIDx) {
        StockIDx = stockIDx;
    }

    public String getAllwCrdt() {
        return AllwCrdt;
    }

    public void setAllwCrdt(String allwCrdt) {
        AllwCrdt = allwCrdt;
    }

    public String getModelIDx() {
        return ModelIDx;
    }

    public void setModelIDx(String modelIDx) {
        ModelIDx = modelIDx;
    }
}
