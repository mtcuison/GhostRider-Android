/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "App_Token_Info")
public class ETokenInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sAppToken")
    private String TokenInf;

    public ETokenInfo() {
    }

    @NonNull
    public String getTokenInf() {
        return TokenInf;
    }

    public void setTokenInf(@NonNull String tokenInf) {
        TokenInf = tokenInf;
    }
}
