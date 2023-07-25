package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Inventory_Count_Master")
public class EInventoryMaster {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd = "";
    @ColumnInfo(name = "dTransact")
    private String Transact = "";
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx = "";
    @ColumnInfo(name = "nEntryNox")
    private int EntryNox = 0;
    @ColumnInfo(name = "sRqstdByx")
    private String RqstdByx = "";
    @ColumnInfo(name = "sVerified")
    private String VerifyBy = "";
    @ColumnInfo(name = "dVerified")
    private String DateVrfy = "";
    @ColumnInfo(name = "sApproved")
    private String ApprveBy = "";
    @ColumnInfo(name = "dApproved")
    private String DateAppv = "";
    @ColumnInfo(name = "cTranStat")
    private String TranStat = "";
    @ColumnInfo(name = "sModified")
    private String Modifier = "";
    @ColumnInfo(name = "dModified")
    private String Modified = "";

    public EInventoryMaster() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(String branchCd) {
        BranchCd = branchCd;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public int getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(int entryNox) {
        EntryNox = entryNox;
    }

    public String getRqstdByx() {
        return RqstdByx;
    }

    public void setRqstdByx(String rqstdByx) {
        RqstdByx = rqstdByx;
    }

    public String getVerifyBy() {
        return VerifyBy;
    }

    public void setVerifyBy(String verifyBy) {
        VerifyBy = verifyBy;
    }

    public String getDateVrfy() {
        return DateVrfy;
    }

    public void setDateVrfy(String dateVrfy) {
        DateVrfy = dateVrfy;
    }

    public String getApprveBy() {
        return ApprveBy;
    }

    public void setApprveBy(String apprveBy) {
        ApprveBy = apprveBy;
    }

    public String getDateAppv() {
        return DateAppv;
    }

    public void setDateAppv(String dateAppv) {
        DateAppv = dateAppv;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
    }

    public String getModifier() {
        return Modifier;
    }

    public void setModifier(String modifier) {
        Modifier = modifier;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }
}
