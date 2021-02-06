package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MC_Branch_Performance")
public class EBranchPerformance {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;
    @ColumnInfo(name = "sBranchNm")
    private String BranchNm;
    @ColumnInfo(name = "nMCGoalxx")
    private int MCGoalxx;
    @ColumnInfo(name = "nSPGoalxx")
    private float SPGoalxx;
    @ColumnInfo(name = "nJOGoalxx")
    private int JOGoalxx;
    @ColumnInfo(name = "nLRGoalxx")
    private float LRGoalxx;
    @ColumnInfo(name = "nMCActual")
    private int MCActual;
    @ColumnInfo(name = "nSPActual")
    private float SPActual;
    @ColumnInfo(name = "nLRActual")
    private float LRActual;

    public EBranchPerformance() {
    }

    @NonNull
    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(@NonNull String branchCd) {
        BranchCd = branchCd;
    }

    public String getBranchNm() {
        return BranchNm;
    }

    public void setBranchNm(String branchNm) {
        BranchNm = branchNm;
    }

    public int getMCGoalxx() {
        return MCGoalxx;
    }

    public void setMCGoalxx(int MCGoalxx) {
        this.MCGoalxx = MCGoalxx;
    }

    public float getSPGoalxx() {
        return SPGoalxx;
    }

    public void setSPGoalxx(float SPGoalxx) {
        this.SPGoalxx = SPGoalxx;
    }

    public int getJOGoalxx() {
        return JOGoalxx;
    }

    public void setJOGoalxx(int JOGoalxx) {
        this.JOGoalxx = JOGoalxx;
    }

    public float getLRGoalxx() {
        return LRGoalxx;
    }

    public void setLRGoalxx(float LRGoalxx) {
        this.LRGoalxx = LRGoalxx;
    }

    public int getMCActual() {
        return MCActual;
    }

    public void setMCActual(int MCActual) {
        this.MCActual = MCActual;
    }

    public float getSPActual() {
        return SPActual;
    }

    public void setSPActual(float SPActual) {
        this.SPActual = SPActual;
    }

    public float getLRActual() {
        return LRActual;
    }

    public void setLRActual(float LRActual) {
        this.LRActual = LRActual;
    }
}
