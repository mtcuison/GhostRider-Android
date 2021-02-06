package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Mobile_Update_Request")
public class EMobileUpdate {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sClientID")
    private String ClientID;
    @ColumnInfo(name = "cReqstCDe")
    private char ReqstCDe;
    @ColumnInfo(name = "sMobileNo")
    private String MobileNo;
    @ColumnInfo(name = "cPrimaryx")
    private char Primaryx;
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

    public EMobileUpdate() {
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

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public char getPrimaryx() {
        return Primaryx;
    }

    public void setPrimaryx(char primaryx) {
        Primaryx = primaryx;
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
