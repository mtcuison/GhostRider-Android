package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Notification_User")
public
class ENotificationUser {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx;
    @ColumnInfo(name = "sUserName")
    private String UserName;

    public ENotificationUser() {
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public String getUserName() {
        return UserName;
    }
}
