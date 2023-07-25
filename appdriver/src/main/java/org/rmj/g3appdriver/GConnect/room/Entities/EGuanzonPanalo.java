package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Guanzon_Panalo")
public class EGuanzonPanalo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sPanaloQC")
    private String PanaloQC;
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx;
    @ColumnInfo(name = "sPanaloCD")
    private String PanaloCD;
    @ColumnInfo(name = "sPanaloDs")
    private String PanaloDs;
    @ColumnInfo(name = "sAcctNmbr")
    private String AcctNmbr;
    @ColumnInfo(name = "nAmountxx")
    private Double Amountxx = 0.0;
    @ColumnInfo(name = "sItemCode")
    private String ItemCode;
    @ColumnInfo(name = "sItemDesc")
    private String ItemDesc;
    @ColumnInfo(name = "nItemQtyx")
    private Integer ItemQtyx = 0;
    @ColumnInfo(name = "nRedeemxx")
    private Double Redeemxx = 0.0;
    @ColumnInfo(name = "dExpiryDt")
    private String ExpiryDt;
    @ColumnInfo(name = "sBranchNm")
    private String BranchNm;
    @ColumnInfo(name = "dRedeemxx")
    private String Redeemed;
    @ColumnInfo(name = "cTranStat")
    private String TranStat;

    public EGuanzonPanalo() {
    }

    @NonNull
    public String getPanaloQC() {
        return PanaloQC;
    }

    public void setPanaloQC(@NonNull String panaloQC) {
        PanaloQC = panaloQC;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getPanaloCD() {
        return PanaloCD;
    }

    public void setPanaloCD(String panaloCD) {
        PanaloCD = panaloCD;
    }

    public String getPanaloDs() {
        return PanaloDs;
    }

    public void setPanaloDs(String panaloDs) {
        PanaloDs = panaloDs;
    }

    public String getAcctNmbr() {
        return AcctNmbr;
    }

    public void setAcctNmbr(String acctNmbr) {
        AcctNmbr = acctNmbr;
    }

    public Double getAmountxx() {
        return Amountxx;
    }

    public void setAmountxx(Double amountxx) {
        Amountxx = amountxx;
    }

    public String getItemCode() {
        return ItemCode;
    }

    public void setItemCode(String itemCode) {
        ItemCode = itemCode;
    }

    public String getItemDesc() {
        return ItemDesc;
    }

    public void setItemDesc(String itemDesc) {
        ItemDesc = itemDesc;
    }

    public Integer getItemQtyx() {
        return ItemQtyx;
    }

    public void setItemQtyx(Integer itemQtyx) {
        ItemQtyx = itemQtyx;
    }

    public Double getRedeemxx() {
        return Redeemxx;
    }

    public void setRedeemxx(Double redeemxx) {
        Redeemxx = redeemxx;
    }

    public String getExpiryDt() {
        return ExpiryDt;
    }

    public void setExpiryDt(String expiryDt) {
        ExpiryDt = expiryDt;
    }

    public String getBranchNm() {
        return BranchNm;
    }

    public void setBranchNm(String branchNm) {
        BranchNm = branchNm;
    }

    public String getRedeemed() {
        return Redeemed;
    }

    public void setRedeemed(String redeemed) {
        Redeemed = redeemed;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }
}
