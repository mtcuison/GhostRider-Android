package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Address_Update_Request")
public class EAddressUpdate {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sClientID")
    private String ClientID;
    @ColumnInfo(name = "cReqstCDe")
    private char ReqstCDe;
    @ColumnInfo(name = "cAddrssTp")
    private char AddrssTp;
    @ColumnInfo(name = "sHouseNox")
    private String HouseNox;
    @ColumnInfo(name = "sAddressx")
    private String Addressx;
    @ColumnInfo(name = "sTownIDxx")
    private String TownIDxx;
    @ColumnInfo(name = "sBrgyIDxx")
    private String BrgyIDxx;
    @ColumnInfo(name = "cPrimaryx")
    private char Primaryx;
    @ColumnInfo(name = "nLatitude")
    private String Latitude;
    @ColumnInfo(name = "nLongitud")
    private String Longitud;
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "cTranStat")
    private char TranStat;
    @ColumnInfo(name = "cSendStat")
    private char SendStat;
    @ColumnInfo(name = "dSendDate")
    private String SendDate;
    @ColumnInfo(name = "dModified")
    private String Modified;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EAddressUpdate() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getClientID() {
        return ClientID;
    }

    public void setClientID(String clientID) {
        ClientID = clientID;
    }

    public char getReqstCDe() {
        return ReqstCDe;
    }

    public void setReqstCDe(char reqstCDe) {
        ReqstCDe = reqstCDe;
    }

    public char getAddrssTp() {
        return AddrssTp;
    }

    public void setAddrssTp(char addrssTp) {
        AddrssTp = addrssTp;
    }

    public String getHouseNox() {
        return HouseNox;
    }

    public void setHouseNox(String houseNox) {
        HouseNox = houseNox;
    }

    public String getAddressx() {
        return Addressx;
    }

    public void setAddressx(String addressx) {
        Addressx = addressx;
    }

    public String getTownIDxx() {
        return TownIDxx;
    }

    public void setTownIDxx(String townIDxx) {
        TownIDxx = townIDxx;
    }

    public String getBrgyIDxx() {
        return BrgyIDxx;
    }

    public void setBrgyIDxx(String brgyIDxx) {
        BrgyIDxx = brgyIDxx;
    }

    public char getPrimaryx() {
        return Primaryx;
    }

    public void setPrimaryx(char primaryx) {
        Primaryx = primaryx;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitud() {
        return Longitud;
    }

    public void setLongitud(String longitud) {
        Longitud = longitud;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public char getTranStat() {
        return TranStat;
    }

    public void setTranStat(char tranStat) {
        TranStat = tranStat;
    }

    public char getSendStat() {
        return SendStat;
    }

    public void setSendStat(char sendStat) {
        SendStat = sendStat;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
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
}
