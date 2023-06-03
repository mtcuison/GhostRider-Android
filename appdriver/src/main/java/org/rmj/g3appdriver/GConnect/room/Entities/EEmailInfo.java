package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "Client_Email_Info", primaryKeys = {"sUserIDxx", "dTransact"})
public class EEmailInfo {

    @NonNull
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx;

    @NonNull
    @ColumnInfo(name = "dTransact")
    private String Transact;

    @ColumnInfo(name = "sEmailAdd")
    private String EmailAdd;

    @ColumnInfo(name = "cVerified")
    private int IsVerifd;

    @ColumnInfo(name = "dVerified")
    private String DateVrfd;

    @ColumnInfo(name = "cRecdStat")
    private int RecdStat;

    @ColumnInfo(name = "dTimeStmp")
    private String TimeStmp;

    public EEmailInfo() {

    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getTransact() {
        return Transact;
    }

    public void setTransact(String transact) {
        Transact = transact;
    }

    public String getEmailAdd() {
        return EmailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public int getIsVerifd() {
        return IsVerifd;
    }

    public void setIsVerifd(int isVerifd) {
        IsVerifd = isVerifd;
    }

    public String getDateVrfd() {
        return DateVrfd;
    }

    public void setDateVrfd(String dateVrfd) {
        DateVrfd = dateVrfd;
    }

    public int getRecdStat() {
        return RecdStat;
    }

    public void setRecdStat(int recdStat) {
        RecdStat = recdStat;
    }

    public String getTimeStmp() {
        return TimeStmp;
    }

    public void setTimeStmp(String timeStmp) {
        TimeStmp = timeStmp;
    }
}
