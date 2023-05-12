package org.rmj.g3appdriver.GConnect.room.Entities;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MC_Service")
public class EServiceInfo {
    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sGCardNox")
    private String GCardNox;

    @ColumnInfo(name = "sSerialID")
    private String SerialID;

    @ColumnInfo(name = "sEngineNo")
    private String EngineNo;

    @ColumnInfo(name = "sFrameNox")
    private String FrameNox;

    @ColumnInfo(name = "sBrandNme")
    private String BrandNme;

    @ColumnInfo(name = "sModelNme")
    private String ModelNme;

    @ColumnInfo(name = "cFSEPStat")
    private String FSEPStat;

    @ColumnInfo(name = "dPurchase")
    private String Purchase;

    @ColumnInfo(name = "nYellowxx")
    private int Yellowxx;

    @ColumnInfo(name = "nWhitexxx")
    private int Whitexxx;

    @ColumnInfo(name = "nYlwCtrxx")
    private int YlwCtrxx;

    @ColumnInfo(name = "nWhtCtrxx")
    private int WhtCtrxx;

    @ColumnInfo(name = "dLastSrvc")
    private String LastSrvc;

    @ColumnInfo(name = "nMIlAgexx")
    private int MIlAgexx;

    @ColumnInfo(name = "dNxtRmnds")
    private String  NxtRmnds;

    public EServiceInfo() {
    }

    @NonNull
    public String getGCardNox() {
        return GCardNox;
    }

    public void setGCardNox(@NonNull String GCardNox) {
        this.GCardNox = GCardNox;
    }

    public String getSerialID() {
        return SerialID;
    }

    public void setSerialID(String serialID) {
        SerialID = serialID;
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

    public String getBrandNme() {
        return BrandNme;
    }

    public void setBrandNme(String brandNme) {
        BrandNme = brandNme;
    }

    public String getModelNme() {
        return ModelNme;
    }

    public void setModelNme(String modelNme) {
        ModelNme = modelNme;
    }

    public String  getFSEPStat() {
        return FSEPStat;
    }

    public void setFSEPStat(String FSEPStat) {
        this.FSEPStat = FSEPStat;
    }

    public String getPurchase() {
        return Purchase;
    }

    public void setPurchase(String purchase) {
        Purchase = purchase;
    }

    public int getYellowxx() {
        return Yellowxx;
    }

    public void setYellowxx(int yellowxx) {
        Yellowxx = yellowxx;
    }

    public int getWhitexxx() {
        return Whitexxx;
    }

    public void setWhitexxx(int whitexxx) {
        Whitexxx = whitexxx;
    }

    public int getYlwCtrxx() {
        return YlwCtrxx;
    }

    public void setYlwCtrxx(int ylwCtrxx) {
        YlwCtrxx = ylwCtrxx;
    }

    public int getWhtCtrxx() {
        return WhtCtrxx;
    }

    public void setWhtCtrxx(int whtCtrxx) {
        WhtCtrxx = whtCtrxx;
    }

    public String getLastSrvc() {
        return LastSrvc;
    }

    public void setLastSrvc(String lastSrvc) {
        LastSrvc = lastSrvc;
    }

    public int getMIlAgexx() {
        return MIlAgexx;
    }

    public void setMIlAgexx(int MIlAgexx) {
        this.MIlAgexx = MIlAgexx;
    }

    public String getNxtRmnds() {
        return NxtRmnds;
    }

    public void setNxtRmnds(String nxtRmnds) {
        NxtRmnds = nxtRmnds;
    }
}
