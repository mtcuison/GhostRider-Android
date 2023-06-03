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

package org.rmj.g3appdriver.GCircle.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "App_Token_Info")
public class ETokenInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTokenIDx")
    private String TokenIDx;
    @ColumnInfo(name = "sTokenInf")
    private String TokenInf;
    @ColumnInfo(name = "sDescript")
    private String Descript;
    @ColumnInfo(name = "sTokenTpe")
    private String TokenTpe;
    @ColumnInfo(name = "dGeneratd")
    private String Generatd;
    @ColumnInfo(name = "dExpirexx")
    private String Expirexx;
    @ColumnInfo(name = "dModified")
    private String Modified;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ETokenInfo() {
    }

    @NonNull
    public String getTokenIDx() {
        return TokenIDx;
    }

    public void setTokenIDx(@NonNull String tokenIDx) {
        TokenIDx = tokenIDx;
    }

    public String getTokenInf() {
        return TokenInf;
    }

    public void setTokenInf(String tokenInf) {
        TokenInf = tokenInf;
    }

    public String getDescript() {
        return Descript;
    }

    public void setDescript(String descript) {
        Descript = descript;
    }

    public String getGeneratd() {
        return Generatd;
    }

    public void setGeneratd(String generatd) {
        Generatd = generatd;
    }

    public String getExpirexx() {
        return Expirexx;
    }

    public void setExpirexx(String expirexx) {
        Expirexx = expirexx;
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

    public String getTokenTpe() {
        return TokenTpe;
    }

    public void setTokenTpe(String tokenTpe) {
        TokenTpe = tokenTpe;
    }
}
