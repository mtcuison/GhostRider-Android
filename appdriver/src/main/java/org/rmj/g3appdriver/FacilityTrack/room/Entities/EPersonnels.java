package org.rmj.g3appdriver.FacilityTrack.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Facility_Personnel")
public class EPersonnels {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx = "";

    @ColumnInfo(name = "sUserName")
    private String UserName = "";

    public EPersonnels() {
    }

    @NonNull
    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(@NonNull String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }
}
