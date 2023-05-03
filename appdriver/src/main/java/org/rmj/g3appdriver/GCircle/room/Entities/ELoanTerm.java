package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Installment_Term")
public class ELoanTerm {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "nTermCode")
    private Integer TermCode;
    @ColumnInfo(name = "sTermDesc")
    private String TermDesc;
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ELoanTerm() {

    }

    @NonNull
    public Integer getTermCode() {
        return TermCode;
    }

    public void setTermCode(@NonNull Integer termCode) {
        TermCode = termCode;
    }

    public String getTermDesc() {
        return TermDesc;
    }

    public void setTermDesc(String termDesc) {
        TermDesc = termDesc;
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
