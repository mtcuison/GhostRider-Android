package org.rmj.g3appdriver.GRider.Database.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Client_Info_Master")
public class EClientInfo {

    @NonNull
    @PrimaryKey
    @ColumnInfo(name = "sUserIDxx")
    protected String UserIDxx;
    @ColumnInfo(name = "sEmailAdd")
    protected String EmailAdd;
    @ColumnInfo(name = "sUserName")
    protected String UserName;
    @ColumnInfo(name = "dLoginxxx")
    protected String Loginxxx;
    @ColumnInfo(name = "sMobileNo")
    protected String MobileNo;
    @ColumnInfo(name = "dDateMmbr")
    protected String DateMmbr;

    public EClientInfo() {
    }

    @NonNull
    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(@NonNull String userIDxx) {
        UserIDxx = userIDxx;
    }

    public String getEmailAdd() {
        return EmailAdd;
    }

    public void setEmailAdd(String emailAdd) {
        EmailAdd = emailAdd;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getLoginxxx() {
        return Loginxxx;
    }

    public void setLoginxxx(String loginxxx) {
        Loginxxx = loginxxx;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getDateMmbr() {
        return DateMmbr;
    }

    public void setDateMmbr(String dateMmbr) {
        DateMmbr = dateMmbr;
    }
}
