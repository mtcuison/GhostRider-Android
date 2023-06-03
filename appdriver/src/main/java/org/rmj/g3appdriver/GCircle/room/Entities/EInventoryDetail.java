package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity (tableName = "Inventory_Count_Detail", primaryKeys = {"sTransNox", "nEntryNox", "sBarrCode"})
public class EInventoryDetail {

    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox = "";

    @NonNull
    @ColumnInfo(name = "nEntryNox")
    private int EntryNox = 0;

    @NonNull
    @ColumnInfo(name = "sBarrCode")
    private String BarrCode = "";
    @ColumnInfo(name = "sPartsIDx")
    private String PartsIDx = "";
    @ColumnInfo(name = "sDescript")
    private String Descript = "";
    @ColumnInfo(name = "sWHouseID")
    private String WHouseID = "";
    @ColumnInfo(name = "sWHouseNm")
    private String WHouseNm = "";
    @ColumnInfo(name = "sSectnIDx")
    private String SectnIDx = "";
    @ColumnInfo(name = "sSectnNme")
    private String SectnNme = "";
    @ColumnInfo(name = "sBinIDxxx")
    private String BinIDxxx = "";
    @ColumnInfo(name = "sBinNamex")
    private String BinNamex = "";
    @ColumnInfo(name = "nQtyOnHnd")
    private int QtyOnHnd = 0;
    @ColumnInfo(name = "nActCtr01")
    private int ActCtr01 = 0;
    @ColumnInfo(name = "nActCtr02")
    private int ActCtr02 = 0;
    @ColumnInfo(name = "nActCtr03")
    private int ActCtr03 = 0;
    @ColumnInfo(name = "nLedgerNo")
    private String LedgerNo = "";
    @ColumnInfo(name = "nBegQtyxx")
    private String BegQtyxx = "";
    @ColumnInfo(name = "cTranStat")
    private String TranStat = "0";
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx = "";
    @ColumnInfo(name = "dModified")
    private String Modified = "";

    public EInventoryDetail() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public int getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(int entryNox) {
        EntryNox = entryNox;
    }

    public String getPartsIDx() {
        return PartsIDx;
    }

    public void setPartsIDx(String partsIDx) {
        PartsIDx = partsIDx;
    }

    public String getWHouseID() {
        return WHouseID;
    }

    public void setWHouseID(String WHouseID) {
        this.WHouseID = WHouseID;
    }

    public String getSectnIDx() {
        return SectnIDx;
    }

    public void setSectnIDx(String sectnIDx) {
        SectnIDx = sectnIDx;
    }

    public String getBinIDxxx() {
        return BinIDxxx;
    }

    public void setBinIDxxx(String binIDxxx) {
        BinIDxxx = binIDxxx;
    }

    public int getQtyOnHnd() {
        return QtyOnHnd;
    }

    public void setQtyOnHnd(int qtyOnHnd) {
        QtyOnHnd = qtyOnHnd;
    }

    public int getActCtr01() {
        return ActCtr01;
    }

    public void setActCtr01(int actCtr01) {
        ActCtr01 = actCtr01;
    }

    public int getActCtr02() {
        return ActCtr02;
    }

    public void setActCtr02(int actCtr02) {
        ActCtr02 = actCtr02;
    }

    public int getActCtr03() {
        return ActCtr03;
    }

    public void setActCtr03(int actCtr03) {
        ActCtr03 = actCtr03;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public String getBarrCode() {
        return BarrCode;
    }

    public void setBarrCode(String barrCode) {
        BarrCode = barrCode;
    }

    public String getDescript() {
        return Descript;
    }

    public void setDescript(String descript) {
        Descript = descript;
    }

    public String getWHouseNm() {
        return WHouseNm;
    }

    public void setWHouseNm(String WHouseNm) {
        this.WHouseNm = WHouseNm;
    }

    public String getSectnNme() {
        return SectnNme;
    }

    public void setSectnNme(String sectnNme) {
        SectnNme = sectnNme;
    }

    public String getBinNamex() {
        return BinNamex;
    }

    public void setBinNamex(String binNamex) {
        BinNamex = binNamex;
    }

    public String getLedgerNo() {
        return LedgerNo;
    }

    public void setLedgerNo(String ledgerNo) {
        LedgerNo = ledgerNo;
    }

    public String getBegQtyxx() {
        return BegQtyxx;
    }

    public void setBegQtyxx(String begQtyxx) {
        BegQtyxx = begQtyxx;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }
}

