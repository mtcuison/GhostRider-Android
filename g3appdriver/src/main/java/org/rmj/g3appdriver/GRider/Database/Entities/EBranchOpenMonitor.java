package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;

@Entity(tableName = "Branch_Opening", primaryKeys = {"sBranchCD", "dTransact", "sTimeOpen"})
public class EBranchOpenMonitor {

    @PrimaryKey
    @ColumnInfo(name = "sBranchCD")
    private String BranchCD;
    @ColumnInfo(name = "dTransact")
    private String Transact;
    @ColumnInfo(name = "sTimeOpen")
    private String TimeOpen;
    @ColumnInfo(name = "sOpenNowx")
    private String OpenNowx;
    @ColumnInfo(name = "dSendDate")
    private String SendDate;
    @ColumnInfo(name = "dNotified")
    private String Notified;
    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp = AppConstants.DATE_MODIFIED;

    public EBranchOpenMonitor() {
    }

    public String getBranchCD() {
        return BranchCD;
    }

    public void setBranchCD(String branchCD) {
        BranchCD = branchCD;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getTimeOpen() {
        return TimeOpen;
    }

    public void setTimeOpen(String timeOpen) {
        TimeOpen = timeOpen;
    }

    public String getOpenNowx() {
        return OpenNowx;
    }

    public void setOpenNowx(String openNowx) {
        OpenNowx = openNowx;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String sendDate) {
        SendDate = sendDate;
    }

    public String getNotified() {
        return Notified;
    }

    public void setNotified(String notified) {
        Notified = notified;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
