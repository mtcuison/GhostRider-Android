package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Credit_Online_Application")
public class ECreditApplication {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sTransNox")
    private String TransNox;
    @ColumnInfo(name = "sBranchCd")
    private String BranchCd;
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "dTargetDt")
    private String TargetDt;
    @ColumnInfo(name = "sClientNm")
    private String ClientNm;
    @ColumnInfo(name = "sGOCASNox")
    private String GOCASNox;
    @ColumnInfo(name = "sGOCASNoF")
    private String GOCASNoF;
    @ColumnInfo(name = "cUnitAppl")
    private char UnitAppl;
    @ColumnInfo(name = "sSourceCD")
    private String SourceCD;
    @ColumnInfo(name = "sDetlInfo")
    private int DetlInfo;
    @ColumnInfo(name = "sCatInfox")
    private char CatInfox;
    @ColumnInfo(name = "sDesInfox")
    private String DesInfox;
    @ColumnInfo(name = "sQMatchNo")
    private String QMatchNo;
    @ColumnInfo(name = "sQMAppCde")
    private String QMAppCde;
    @ColumnInfo(name = "nCrdtScrx")
    private int CrdtScrx;
    @ColumnInfo(name = "cWithCIxx")
    private char WithCIxx;
    @ColumnInfo(name = "nDownPaym")
    private double DownPaym;
    @ColumnInfo(name = "nDownPayF")
    private double DownPayF;
    @ColumnInfo(name = "sRemarksx")
    private String Remarksx;
    @ColumnInfo(name = "dReceived")
    private String Received;
    @ColumnInfo(name = "sCreatedx")
    private String Createdx;
    @ColumnInfo(name = "dCreatedx")
    private String DateCreatedx;
    @ColumnInfo(name = "cSendStat")
    private char SendStat;
    @ColumnInfo(name = "sVerified")
    private String Verified;
    @ColumnInfo(name = "dVerified")
    private String DateVerified;
    @ColumnInfo(name = "dModified")
    private String Modified;
    @ColumnInfo(name = "cTranStat")
    private char TranStat;
    @ColumnInfo(name = "cDivision")
    private char Division;
    @ColumnInfo(name = "cApplStat")
    private char ApplStat;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public ECreditApplication() {
    }

    @NonNull
    public String getTransNox() {
        return TransNox;
    }

    public void setTransNox(@NonNull String transNox) {
        TransNox = transNox;
    }

    public String getBranchCd() {
        return BranchCd;
    }

    public void setBranchCd(String branchCd) {
        BranchCd = branchCd;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getTargetDt() {
        return TargetDt;
    }

    public void setTargetDt(String targetDt) {
        TargetDt = targetDt;
    }

    public String getClientNm() {
        return ClientNm;
    }

    public void setClientNm(String clientNm) {
        ClientNm = clientNm;
    }

    public String getGOCASNox() {
        return GOCASNox;
    }

    public void setGOCASNox(String GOCASNox) {
        this.GOCASNox = GOCASNox;
    }

    public String getGOCASNoF() {
        return GOCASNoF;
    }

    public void setGOCASNoF(String GOCASNoF) {
        this.GOCASNoF = GOCASNoF;
    }

    public char getUnitAppl() {
        return UnitAppl;
    }

    public void setUnitAppl(char unitAppl) {
        UnitAppl = unitAppl;
    }

    public String getSourceCD() {
        return SourceCD;
    }

    public void setSourceCD(String sourceCD) {
        SourceCD = sourceCD;
    }

    public int getDetlInfo() {
        return DetlInfo;
    }

    public void setDetlInfo(int detlInfo) {
        DetlInfo = detlInfo;
    }

    public char getCatInfox() {
        return CatInfox;
    }

    public void setCatInfox(char catInfox) {
        CatInfox = catInfox;
    }

    public String getDesInfox() {
        return DesInfox;
    }

    public void setDesInfox(String desInfox) {
        DesInfox = desInfox;
    }

    public String getQMatchNo() {
        return QMatchNo;
    }

    public void setQMatchNo(String QMatchNo) {
        this.QMatchNo = QMatchNo;
    }

    public String getQMAppCde() {
        return QMAppCde;
    }

    public void setQMAppCde(String QMAppCde) {
        this.QMAppCde = QMAppCde;
    }

    public int getCrdtScrx() {
        return CrdtScrx;
    }

    public void setCrdtScrx(int crdtScrx) {
        CrdtScrx = crdtScrx;
    }

    public char getWithCIxx() {
        return WithCIxx;
    }

    public void setWithCIxx(char withCIxx) {
        WithCIxx = withCIxx;
    }

    public double getDownPaym() {
        return DownPaym;
    }

    public void setDownPaym(double downPaym) {
        DownPaym = downPaym;
    }

    public double getDownPayF() {
        return DownPayF;
    }

    public void setDownPayF(double downPayF) {
        DownPayF = downPayF;
    }

    public String getRemarksx() {
        return Remarksx;
    }

    public void setRemarksx(String remarksx) {
        Remarksx = remarksx;
    }

    public String getReceived() {
        return Received;
    }

    public void setReceived(String received) {
        Received = received;
    }

    public String getCreatedx() {
        return Createdx;
    }

    public void setCreatedx(String createdx) {
        Createdx = createdx;
    }

    public String getDateCreatedx() {
        return DateCreatedx;
    }

    public void setDateCreatedx(String dateCreatedx) {
        DateCreatedx = dateCreatedx;
    }

    public char getSendStat() {
        return SendStat;
    }

    public void setSendStat(char sendStat) {
        SendStat = sendStat;
    }

    public String getVerified() {
        return Verified;
    }

    public void setVerified(String verified) {
        Verified = verified;
    }

    public String getDateVerified() {
        return DateVerified;
    }

    public void setDateVerified(String dateVerified) {
        DateVerified = dateVerified;
    }

    public String getModified() {
        return Modified;
    }

    public void setModified(String modified) {
        Modified = modified;
    }

    public char getTranStat() {
        return TranStat;
    }

    public void setTranStat(char tranStat) {
        TranStat = tranStat;
    }

    public char getDivision() {
        return Division;
    }

    public void setDivision(char division) {
        Division = division;
    }

    public char getApplStat() {
        return ApplStat;
    }

    public void setApplStat(char applStat) {
        ApplStat = applStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
