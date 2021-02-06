package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Credit_Applicant_Info")
public class ECreditApplicantInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sClientNm")
    private String ClientNm;
    @ColumnInfo(name = "sDetlInfo")
    private String DetlInfo;
    @ColumnInfo(name = "sAppMeans")
    private String AppMeans;
    @ColumnInfo(name = "sSpsMEans")
    private String SpsMeans;

    public ECreditApplicantInfo() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getClientNm() {
        return ClientNm;
    }

    public void setClientNm(String clientNm) {
        ClientNm = clientNm;
    }

    public String getDetlInfo() {
        return DetlInfo;
    }

    public void setDetlInfo(String detlInfo) {
        DetlInfo = detlInfo;
    }

    public String getAppMeans() {
        return AppMeans;
    }

    public void setAppMeans(String appMeans) {
        AppMeans = appMeans;
    }

    public String getSpsMeans() {
        return SpsMeans;
    }

    public void setSpsMeans(String spsMeans) {
        SpsMeans = spsMeans;
    }
}
