package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "PromoLocal_Detail", primaryKeys = {"sBranchCd", "dTransact"})
public class ERaffleInfo {

    @NonNull
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;

    @NonNull
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "sClientNm")
    private String ClientNm;
    @ColumnInfo(name = "sDocTypex")
    private String DocTypex;
    @ColumnInfo(name = "sDocNoxxx")
    private String DocNoxxx;
    @ColumnInfo(name = "sMobileNo")
    private String MobileNo;
    @ColumnInfo(name = "cSendStat")
    private char SendStat;
    @ColumnInfo(name = "sTimeStmp")
    private String TimeStmp;

    public ERaffleInfo() {
    }

    @NonNull
    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(@NonNull String branchCd) {
        BranchCd = branchCd;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getClientNm() {
        return ClientNm;
    }

    public void setClientNm(String clientNm) {
        ClientNm = clientNm;
    }

    public String getDocTypex() {
        return DocTypex;
    }

    public void setDocTypex(String docTypex) {
        DocTypex = docTypex;
    }

    public String getDocNoxxx() {
        return DocNoxxx;
    }

    public void setDocNoxxx(String docNoxxx) {
        DocNoxxx = docNoxxx;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public char getSendStat() {
        return SendStat;
    }

    public void setSendStat(char sendStat) {
        SendStat = sendStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
