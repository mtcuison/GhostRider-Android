/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room.Entities;

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
