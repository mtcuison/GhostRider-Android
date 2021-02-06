package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "MC_Term_Category", primaryKeys = {"sMCCatIDx", "nAcctTerm"})
public class EMcTermCategory {

    @NonNull
    @ColumnInfo(name = "sMCCatIDx")
    private String MCCatIDx;

    @NonNull
    @ColumnInfo(name = "nAcctTerm")
    private String AcctTerm;
    @ColumnInfo(name = "nAcctThru")
    private String AcctThru;
    @ColumnInfo(name = "nFactorRt")
    private String FactorRt;
    @ColumnInfo(name = "dPricexxx")
    private String Pricexxx;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EMcTermCategory() {
    }

    @NonNull
    public String getMCCatIDx() {
        return MCCatIDx;
    }

    public void setMCCatIDx(@NonNull String MCCatIDx) {
        this.MCCatIDx = MCCatIDx;
    }

    public String getAcctTerm() {
        return AcctTerm;
    }

    public void setAcctTerm(String acctTerm) {
        AcctTerm = acctTerm;
    }

    public String getAcctThru() {
        return AcctThru;
    }

    public void setAcctThru(String acctThru) {
        AcctThru = acctThru;
    }

    public String getFactorRt() {
        return FactorRt;
    }

    public void setFactorRt(String factorRt) {
        FactorRt = factorRt;
    }

    public String getPricexxx() {
        return Pricexxx;
    }

    public void setPricexxx(String pricexxx) {
        Pricexxx = pricexxx;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
