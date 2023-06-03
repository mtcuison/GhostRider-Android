package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Country_Info")
public class ECountryInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sCntryCde")
    private String CntryCde;
    @ColumnInfo(name = "sCntryNme")
    private String CntryNme;
    @ColumnInfo(name = "sNational")
    private String National;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;
    @ColumnInfo(name = "dLstUpdte")
    private String LstUpdte;

    public ECountryInfo() {
    }

    @NonNull
    public String getCntryCde() {
        return CntryCde;
    }

    public void setCntryCde(@NonNull String cntryCde) {
        CntryCde = cntryCde;
    }

    public String getCntryNme() {
        return CntryNme;
    }

    public void setCntryNme(String cntryNme) {
        CntryNme = cntryNme;
    }

    public String getNational() {
        return National;
    }

    public void setNational(String national) {
        National = national;
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

    public String getLstUpdte() {
        return LstUpdte;
    }

    public void setLstUpdte(String lstUpdte) {
        LstUpdte = lstUpdte;
    }
}
