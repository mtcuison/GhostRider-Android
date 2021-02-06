package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MC_Category")
public class EMcCategory {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sMcCatIDx")
    private String McCatIDx;
    @ColumnInfo(name = "sMcCatNme")
    private String McCatNme;
    @ColumnInfo(name = "nMiscChrg")
    private String MiscChrg;
    @ColumnInfo(name = "nRebatesx")
    private String Rebatesx;
    @ColumnInfo(name = "nEndMrtgg")
    private String EndMrtgg;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EMcCategory() {
    }

    @NonNull
    public String getMcCatIDx() {
        return McCatIDx;
    }

    public void setMcCatIDx(@NonNull String mcCatIDx) {
        McCatIDx = mcCatIDx;
    }

    public String getMcCatNme() {
        return McCatNme;
    }

    public void setMcCatNme(String mcCatNme) {
        McCatNme = mcCatNme;
    }

    public String getMiscChrg() {
        return MiscChrg;
    }

    public void setMiscChrg(String miscChrg) {
        MiscChrg = miscChrg;
    }

    public String getRebatesx() {
        return Rebatesx;
    }

    public void setRebatesx(String rebatesx) {
        Rebatesx = rebatesx;
    }

    public String getEndMrtgg() {
        return EndMrtgg;
    }

    public void setEndMrtgg(String endMrtgg) {
        EndMrtgg = endMrtgg;
    }

    public String getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(String recdStat) {
        RecdStat = recdStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
