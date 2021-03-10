package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Bank_Info")
public class EBankInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sBankIDxx")
    private String BankIDxx;
    @ColumnInfo(name = "sBankName")
    private String BankName;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EBankInfo() {
    }

    @NonNull
    public String getBankIDxx() {
        return BankIDxx;
    }

    public void setBankIDxx(@NonNull String bankIDxx) {
        BankIDxx = bankIDxx;
    }

    public String getBankName() {
        return BankName;
    }

    public void setBankName(String bankName) {
        BankName = bankName;
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
