package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Redeem_Item")
public class ERedeemItemInfo {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sPromoIDx")
    private String PromoIDx;

    @ColumnInfo(name = "sBatchNox")
    private String BatchNox;

    @ColumnInfo(name = "sTransNox")
    private String TransNox;

    @ColumnInfo(name = "sGCardNox")
    private String GCardNox;

    @ColumnInfo(name = "nItemQtyx")
    private int ItemQtyx;

    @ColumnInfo(name = "nPointsxx")
    private double Pointsxx;

    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;

    @ColumnInfo(name = "sReferNox")
    private String ReferNox;

    @ColumnInfo(name = "dOrderedx")
    private String Orderedx;

    @ColumnInfo(name = "dPlacOrdr")
    private String PlacOrdr;

    @ColumnInfo(name = "dPickupxx")
    private String Pickupxx;

    @ColumnInfo(name = "cTranStat")
    private String TranStat;

    @ColumnInfo(name = "cPlcOrder")
    private String PlcOrder;

    @ColumnInfo(name = "cNotified")
    private String Notified;

    public ERedeemItemInfo(){}

    @NonNull
    public String getPromoIDx() {
        return PromoIDx;
    }

    public void setPromoIDx(@NonNull String promoIDx) {
        PromoIDx = promoIDx;
    }

    public String getBatchNox() {
        return BatchNox;
    }

    public void setBatchNox(String batchNox) {
        BatchNox = batchNox;
    }

    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(String transNox) {
        TransNox = transNox;
    }

    public String getGCardNox() {
        return GCardNox;
    }

    public void setGCardNox(String GCardNox) {
        this.GCardNox = GCardNox;
    }

    public int getItemQtyx() {
        return ItemQtyx;
    }

    public void setItemQtyx(int itemQtyx) {
        ItemQtyx = itemQtyx;
    }

    public double getPointsxx() {
        return Pointsxx;
    }

    public void setPointsxx(double pointsxx) {
        Pointsxx = pointsxx;
    }

    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(String branchCd) {
        BranchCd = branchCd;
    }

    public String getReferNox() {
        return ReferNox;
    }

    public void setReferNox(String referNox) {
        ReferNox = referNox;
    }

    public String getOrderedx() {
        return Orderedx;
    }

    public void setOrderedx(String orderedx) {
        Orderedx = orderedx;
    }

    public String getPlacOrdr() {
        return PlacOrdr;
    }

    public void setPlacOrdr(String placOrdr) {
        PlacOrdr = placOrdr;
    }

    public String getPickupxx() {
        return Pickupxx;
    }

    public void setPickupxx(String pickupxx) {
        Pickupxx = pickupxx;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getPlcOrder() {
        return PlcOrder;
    }

    public void setPlcOrder(String plcOrder) {
        PlcOrder = plcOrder;
    }

    public String getNotified() {
        return Notified;
    }

    public void setNotified(String notified) {
        Notified = notified;
    }
}
