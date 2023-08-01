package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MC_Serial_Registration")
public class EMCSerialRegistration {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sSerialID")
    private String SerialID;

    @ColumnInfo(name = "sGCardNox")
    private String GCardNox;

    @ColumnInfo(name = "sEngineNo")
    private String EngineNo;

    @ColumnInfo(name = "sFrameNox")
    private String FrameNox;

    @ColumnInfo(name = "sModelNme")
    private String ModelNme;

    @ColumnInfo(name = "sFSEPStat")
    private String FSEPStat;

    @ColumnInfo(name = "cRegStatx")
    private String RegStatx;

    public EMCSerialRegistration() {
    }

    @NonNull
    public String getSerialID() {
        return SerialID;
    }

    public void setSerialID(@NonNull String serialID) {
        SerialID = serialID;
    }

    public String getGCardNox() {
        return GCardNox;
    }

    public void setGCardNox(String GCardNox) {
        this.GCardNox = GCardNox;
    }

    public String getEngineNo() {
        return EngineNo;
    }

    public void setEngineNo(String engineNo) {
        EngineNo = engineNo;
    }

    public String getFrameNox() {
        return FrameNox;
    }

    public void setFrameNox(String frameNox) {
        FrameNox = frameNox;
    }

    public String getModelNme() {
        return ModelNme;
    }

    public void setModelNme(String modelNme) {
        ModelNme = modelNme;
    }

    public String getFSEPStat() {
        return FSEPStat;
    }

    public void setFSEPStat(String FSEPStat) {
        this.FSEPStat = FSEPStat;
    }

    public String getRegStatx() {
        return RegStatx;
    }

    public void setRegStatx(String regStatx) {
        RegStatx = regStatx;
    }
}
