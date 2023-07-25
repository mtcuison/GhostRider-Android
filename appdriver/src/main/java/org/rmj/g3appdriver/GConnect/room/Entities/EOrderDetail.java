package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "MarketPlace_Order_Detail", primaryKeys = {"sTransNox","nEntryNox"})
public class EOrderDetail {

    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;

    @NonNull
    @ColumnInfo(name = "nEntryNox")
    private String EntryNox;

    @ColumnInfo(name = "sStockIDx")
    private String StockIDx;
    @ColumnInfo(name = "nQuantity")
    private String Quantity;
    @ColumnInfo(name = "nUnitPrce")
    private String UnitPrce;
    @ColumnInfo(name = "nDiscount")
    private String Discount;
    @ColumnInfo(name = "nAddDiscx")
    private String AddDiscx;
    @ColumnInfo(name = "nApproved")
    private String Approved;
    @ColumnInfo(name = "nIssuedxx")
    private String Issuedxx;
    @ColumnInfo(name = "nCancelld")
    private String Cancelld;
    @ColumnInfo(name = "sReferNox")
    private String ReferNox;
    @ColumnInfo(name = "sNotesxxx")
    private String Notesxxx;
    @ColumnInfo(name = "cReviewed")
    private String Reviewed;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EOrderDetail() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(String entryNox) {
        EntryNox = entryNox;
    }

    public String getStockIDx() {
        return StockIDx;
    }

    public void setStockIDx(String stockIDx) {
        StockIDx = stockIDx;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getUnitPrce() {
        return UnitPrce;
    }

    public void setUnitPrce(String unitPrce) {
        UnitPrce = unitPrce;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getAddDiscx() {
        return AddDiscx;
    }

    public void setAddDiscx(String addDiscx) {
        AddDiscx = addDiscx;
    }

    public String getApproved() {
        return Approved;
    }

    public void setApproved(String approved) {
        Approved = approved;
    }

    public String getIssuedxx() {
        return Issuedxx;
    }

    public void setIssuedxx(String issuedxx) {
        Issuedxx = issuedxx;
    }

    public String getCancelld() {
        return Cancelld;
    }

    public void setCancelld(String cancelld) {
        Cancelld = cancelld;
    }

    public String getReferNox() {
        return ReferNox;
    }

    public void setReferNox(String referNox) {
        ReferNox = referNox;
    }

    public String getNotesxxx() {
        return Notesxxx;
    }

    public void setNotesxxx(String notesxxx) {
        Notesxxx = notesxxx;
    }

    public String getReviewed() {
        return Reviewed;
    }

    public void setReviewed(String reviewed) {
        Reviewed = reviewed;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
