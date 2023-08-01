package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "MC_Model_Color", primaryKeys = {"sModelIDx", "sColorIDx"})
public class EMCColor {

    @NonNull
    @ColumnInfo(name = "sModelIDx")
    private String ModelIDx = "";

    @NonNull
    @ColumnInfo(name = "sColorIDx")
    private String ColorIDx = "";

    @ColumnInfo(name = "sColorNme")
    private String ColorNme = "";

    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EMCColor() {
    }

    @NonNull
    public String getModelIDx() {
        return ModelIDx;
    }

    public void setModelIDx(@NonNull String modelIDx) {
        ModelIDx = modelIDx;
    }

    @NonNull
    public String getColorIDx() {
        return ColorIDx;
    }

    public void setColorIDx(@NonNull String colorIDx) {
        ColorIDx = colorIDx;
    }

    public String getColorNme() {
        return ColorNme;
    }

    public void setColorNme(String colorNme) {
        ColorNme = colorNme;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
