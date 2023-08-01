package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Panalo_Reward")
public class EPanaloReward {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sPanaloCD")
    private String PanaloCD = "";
    @ColumnInfo(name = "cTranStat")
    private String TranStat = "";
    @ColumnInfo(name = "dModified")
    private String Modified = "";
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = "";

    public EPanaloReward() {
    }

    @NonNull
    public String getPanaloCD() {
        return PanaloCD;
    }

    public void setPanaloCD(@NonNull String panaloCD) {
        PanaloCD = panaloCD;
    }

    public String getTranStat() {
        return TranStat;
    }

    public void setTranStat(String tranStat) {
        TranStat = tranStat;
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
