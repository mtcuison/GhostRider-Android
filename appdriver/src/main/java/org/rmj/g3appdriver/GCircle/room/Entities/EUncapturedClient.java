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

@Entity(tableName = "Uncaptured_Client_Image")
public class EUncapturedClient {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sClientNm")
    private String ClientNm;
    @ColumnInfo(name = "sSourceNo")
    private String SourceNo;

    public EUncapturedClient() {
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public void setClientNm(String clientNm) {
        ClientNm = clientNm;
    }

    public void setSourceNo(String sourceNo) {
        SourceNo = sourceNo;
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public String getClientNm() {
        return ClientNm;
    }

    public String getSourceNo() {
        return SourceNo;
    }
}
