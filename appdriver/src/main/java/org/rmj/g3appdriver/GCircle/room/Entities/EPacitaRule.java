package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Pacita_Rule")
public class EPacitaRule {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "nEntryNox")
    private Integer EntryNox = 0;
    @ColumnInfo(name = "sFieldNmx")
    private String FieldNmx = "";
    @ColumnInfo(name = "nMaxValue")
    private Double MaxValue = 0.0;
    @ColumnInfo(name = "cParentxx")
    private String Parentxx = "";
    @ColumnInfo(name = "cRecdStat")
    private String RecdStat = "";
    @ColumnInfo(name = "dModified")
    private String Modified = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EPacitaRule() {
    }

    @NonNull
    public Integer getEntryNox() {
        return EntryNox;
    }

    public void setEntryNox(@NonNull Integer entryNox) {
        EntryNox = entryNox;
    }

    public String getFieldNmx() {
        return FieldNmx;
    }

    public void setFieldNmx(String fieldNmx) {
        FieldNmx = fieldNmx;
    }

    public Double getMaxValue() {
        return MaxValue;
    }

    public void setMaxValue(Double maxValue) {
        MaxValue = maxValue;
    }

    public String getParentxx() {
        return Parentxx;
    }

    public void setParentxx(String parentxx) {
        Parentxx = parentxx;
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

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
