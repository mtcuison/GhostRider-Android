package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "FB_Raffle_Transaction_Basis", primaryKeys = {"sDivision", "sTableNme", "sReferCde"})
public
class ERaffleBasis {

    @NonNull
    @ColumnInfo(name = "sDivision")
    private String Division;

    @NonNull
    @ColumnInfo(name = "sTableNme")
    private String TableNme;

    @NonNull
    @ColumnInfo(name = "sReferCde")
    private String ReferCde;
    @ColumnInfo(name = "sReferNme")
    private String ReferNme;
    @ColumnInfo(name = "cCltInfox")
    private String CltInfox;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dModified")
    private String Modified;

    public ERaffleBasis() {
    }

    @NonNull
    public String getDivision() {
        return Division;
    }

    public void setDivision(@NonNull String division) {
        Division = division;
    }

    @NonNull
    public String getTableNme() {
        return TableNme;
    }

    public void setTableNme(@NonNull String tableNme) {
        TableNme = tableNme;
    }

    @NonNull
    public String getReferCde() {
        return ReferCde;
    }

    public void setReferCde(@NonNull String referCde) {
        ReferCde = referCde;
    }

    public String getReferNme() {
        return ReferNme;
    }

    public void setReferNme(String referNme) {
        ReferNme = referNme;
    }

    public String getCltInfox() {
        return CltInfox;
    }

    public void setCltInfox(String cltInfox) {
        CltInfox = cltInfox;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
        RecdStat = recdStat;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }
}
