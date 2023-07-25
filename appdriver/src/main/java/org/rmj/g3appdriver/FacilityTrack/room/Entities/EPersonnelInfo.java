package org.rmj.g3appdriver.FacilityTrack.room.Entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_Info_Master")
public class EPersonnelInfo {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "sClientID")
    private String ClientID;
    @ColumnInfo(name = "sBranchCD")
    private String BranchCD;
    @ColumnInfo(name = "sBranchNm")
    private String BranchNm;
    @ColumnInfo(name = "sLogNoxxx")
    private String LogNoxxx;
    @ColumnInfo(name = "sUserIDxx")
    private String UserIDxx;
    @ColumnInfo(name = "sEmailAdd")
    private String EmailAdd;
    @ColumnInfo(name = "sUserName")
    private String UserName;
    @ColumnInfo(name = "nUserLevl")
    private String UserLevl;
    @ColumnInfo(name = "sDeptIDxx")
    private String DeptIDxx;
    @ColumnInfo(name = "sPositnID")
    private String PositnID;
    @ColumnInfo(name = "sEmpLevID")
    private Integer EmpLevID;
    @ColumnInfo(name = "cSlfieLog")
    private String SlfieLog;
    @ColumnInfo(name = "cAllowUpd")
    private String AllowUpd;
    @ColumnInfo(name = "dLoginxxx")
    private String Loginxxx;
    @ColumnInfo(name = "sMobileNo")
    private String MobileNo;
    @ColumnInfo(name = "dSessionx")
    private String Sessionx;
    @ColumnInfo(name = "sEmployID")
    private String EmployID;
    @ColumnInfo(name = "cPrivatex")
    private String Privatex;
    @ColumnInfo(name = "sDeviceID")
    private String DeviceID;
    @ColumnInfo(name = "sModelIDx")
    private String ModelIDx;

    public EPersonnelInfo() {
    }

    @NonNull
    public String getClientID() {
        return ClientID;
    }

    public void setClientID(@NonNull String clientID) {
        ClientID = clientID;
    }

    public String getBranchCD() {
        return BranchCD;
    }

    public void setBranchCD(String branchCD) {
        BranchCD = branchCD;
    }

    public String getBranchNm() {
        return BranchNm;
    }

    public void setBranchNm(String branchNm) {
        BranchNm = branchNm;
    }

    public String getLogNoxxx() {
        return LogNoxxx;
    }

    public void setLogNoxxx(String logNoxxx) {
        LogNoxxx = logNoxxx;
    }

    public String getUserIDxx() {
        return UserIDxx;
    }

    public void setUserIDxx(String userIDxx) {
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

    public String getUserLevl() {
        return UserLevl;
    }

    public void setUserLevl(String userLevl) {
        UserLevl = userLevl;
    }

    public String getDeptIDxx() {
        return DeptIDxx;
    }

    public void setDeptIDxx(String deptIDxx) {
        DeptIDxx = deptIDxx;
    }

    public String getPositnID() {
        return PositnID;
    }

    public void setPositnID(String positnID) {
        PositnID = positnID;
    }

    public Integer getEmpLevID() {
        return EmpLevID;
    }

    public void setEmpLevID(Integer empLevID) {
        EmpLevID = empLevID;
    }

    public String getAllowUpd() {
        return AllowUpd;
    }

    public void setAllowUpd(String allowUpd) {
        AllowUpd = allowUpd;
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

    public String getSessionx() {
        return Sessionx;
    }

    public void setSessionx(String sessionx) {
        Sessionx = sessionx;
    }


    public String getEmployID() {
        return EmployID;
    }

    public void setEmployID(String employID) {
        EmployID = employID;
    }

    public String getPrivatex() {
        return Privatex;
    }

    public void setPrivatex(String privatex) {
        Privatex = privatex;
    }

    public String getDeviceID() {
        return DeviceID;
    }

    public void setDeviceID(String deviceID) {
        DeviceID = deviceID;
    }

    public String getModelIDx() {
        return ModelIDx;
    }

    public void setModelIDx(String modelIDx) {
        ModelIDx = modelIDx;
    }

    public String getSlfieLog() {
        return SlfieLog;
    }

    public void setSlfieLog(String slfieLog) {
        SlfieLog = slfieLog;
    }

    @Entity(tableName = "Facility_Personnel")
    public static class EPersonnels {

        @PrimaryKey
        @NonNull
        @ColumnInfo(name = "sUserIDxx")
        private String UserIDxx = "";

        @ColumnInfo(name = "sUserName")
        private String UserName = "";

        public EPersonnels() {
        }

        @NonNull
        public String getUserIDxx() {
            return UserIDxx;
        }

        public void setUserIDxx(@NonNull String userIDxx) {
            UserIDxx = userIDxx;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String userName) {
            UserName = userName;
        }
    }
}
