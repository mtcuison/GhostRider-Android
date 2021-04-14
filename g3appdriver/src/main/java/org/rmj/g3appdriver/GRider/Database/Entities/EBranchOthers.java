package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Branch_Others")
public class EBranchOthers {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTimeOpen")
    private String TimeOpen;
    @ColumnInfo(name = "sOpenRcpt")
    private String OpenRcpt;

    public EBranchOthers(@NonNull String timeOpen, String openRcpt) {
        TimeOpen = timeOpen;
        OpenRcpt = openRcpt;
    }

    @NonNull
    public String getTimeOpen() {
        return TimeOpen;
    }

    public String getOpenRcpt() {
        return OpenRcpt;
    }
}
