package org.rmj.g3appdriver.GConnect.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(tableName = "App_User_Mobile", primaryKeys = {"sUserIDxx", "dTransact"})
public class EMobileInfo {

    @NonNull
    @ColumnInfo (name = "sUserIDxx")
    private String UserIDxx;
    @NonNull
    @ColumnInfo (name = "dTransact")
    private String Transact;
    @ColumnInfo (name = "sMobileNo")
    private String MobileNo;
    @ColumnInfo (name = "cUserVrfd")
    private int UserVrfd;
    @ColumnInfo (name = "dUserVrfd")
    private String DateVrfd;
    @ColumnInfo (name = "dVerified")
    private String Verified;
    @ColumnInfo (name = "cRecdStat")
    private int RecdStat; //0->Deactivated;
                            // 1->Verified By Customer Thru OTP;
                            // 2->Verified By Guanzon;
                            // 3->Invalid as Verified By Guanzon;
                            // 4->Removed By Customer
    @ColumnInfo (name = "dTimeStmp")
    private String TimeStmp;

    public EMobileInfo() {
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

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public int getUserVrfd() {
        return UserVrfd;
    }

    public void setUserVrfd(int userVrfd) {
        UserVrfd = userVrfd;
    }

    public String getDateVrfd() {
        return DateVrfd;
    }

    public void setDateVrfd(String dateVrfd) {
        DateVrfd = dateVrfd;
    }

    public String getVerified() {
        return Verified;
    }

    public void setVerified(String verified) {
        Verified = verified;
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
