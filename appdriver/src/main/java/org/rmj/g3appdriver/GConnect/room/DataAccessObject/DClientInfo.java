package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EClientInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EEmailInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EMobileInfo;

import java.util.List;

@Dao
public interface DClientInfo {

    @Insert
    void insert(EClientInfo eClientInfo);

    @Query("DELETE FROM Client_Profile_Info")
    void RemoveSessions();

    @Update
    void update(EClientInfo eClientInfo);

    @Query("SELECT * FROM Client_Profile_Info")
    EClientInfo GetUserInfo();

    @Query("SELECT * FROM Client_Profile_Info")
    LiveData<EClientInfo> getClientInfo();

    @Query("SELECT sClientID FROM Client_Profile_Info")
    String GetClientID();

    @Query("SELECT sUserIDxx FROM Client_Profile_Info")
    String GetUserID();

    @Query("DELETE FROM Client_Profile_Info")
    void LogoutAccount();

    @Query("DELETE FROM GCard_App_Master")
    void LogoutGcard();

    @Query("DELETE FROM MarketPlace_Cart")
    void LogoutItemCart();

    @Query("DELETE FROM GCard_App_Master")
    void LogoutGcardLedger();

    @Query("DELETE FROM Redeem_Item")
    void LogoutRedeemItem();

    @Query("DELETE FROM MC_Service")
    void LogoutServiceInfo();

    @Query("DELETE FROM MC_Serial_Registration")
    void LogoutMCSerial();

    @Query("DELETE FROM MarketPlace_Order_Master")
    void LogoutMasterPurchase();

    @Query("DELETE FROM MarketPlace_Order_Detail")
    void LogoutDetailPurchase();

    @Query("SELECT " +
            "a.sHouseNo1, " +
            "a.sAddress1, " +
            "a.cVerified, " +
            "(SELECT " +
            "sBrgyName " +
            "FROM Barangay_Info " +
            "WHERE sBrgyIDxx = a.sBrgyIDx1) AS sBrgyNme1, " +
            "(SELECT " +
            "sTownName " +
            "FROM Town_Info " +
            "WHERE sTownIDxx = a.sTownIDx1) AS sTownNme1, " +
            "(SELECT " +
            "sProvName " +
            "FROM Province_Info " +
            "WHERE sProvIDxx = (SELECT " +
            "sProvIDxx " +
            "FROM Town_Info " +
            "WHERE sTownIDxx = a.sTownIDx1)) AS sProvNme1, " +
            "a.sHouseNo2, " +
            "a.sAddress2, " +
            "(SELECT " +
            "sBrgyName " +
            "FROM Barangay_Info " +
            "WHERE sBrgyIDxx = a.sBrgyIDx2) AS sBrgyNme2, " +
            "(SELECT " +
            "sTownName " +
            "FROM Town_Info " +
            "WHERE sTownIDxx = a.sTownIDx2) AS sTownNme2, " +
            "(SELECT " +
            "sProvName " +
            "FROM Province_Info " +
            "WHERE sProvIDxx = (SELECT " +
            "sProvIDxx " +
            "FROM Town_Info " +
            "WHERE sTownIDxx = a.sTownIDx2)) AS sProvNme2 " +
            "FROM Client_Profile_Info a")
    LiveData<ClientBSAddress> getClientBSAddress();

    @Query("SELECT a.sHouseNo1 AS sHouseNox, " +
            "a.sAddress1 AS sAddressx, " +
            "a.sBrgyIDx1 AS sBrgyIDxx, " +
            "a.sTownIDx1 AS sTownIDxx, " +
            "(SELECT sBrgyName FROM Barangay_Info WHERE sBrgyIDxx = a.sBrgyIDx1) AS sBrgyName, " +
            "(SELECT sTownName FROM Town_Info WHERE sTownIDxx = a.sTownIDx1) AS sTownName " +
            "FROM Client_Profile_Info a")
    LiveData<oAddressUpdate> GetBillingAddressInfoForUpdate();

    @Query("SELECT a.sHouseNo2 AS sHouseNox, " +
            "a.sAddress2 AS sAddressx, " +
            "a.sBrgyIDx2 AS sBrgyIDxx, " +
            "a.sTownIDx2 AS sTownIDxx, " +
            "(SELECT sBrgyName FROM Barangay_Info WHERE sBrgyIDxx = a.sBrgyIDx2) AS sBrgyName, " +
            "(SELECT sTownName FROM Town_Info WHERE sTownIDxx = a.sTownIDx2) AS sTownName " +
            "FROM Client_Profile_Info a")
    LiveData<oAddressUpdate> GetShippingAddressInfoForUpdate();

    @Query("SELECT * FROM Client_Profile_Info")
    EClientInfo GetClientInfo();

    @Query("SELECT * FROM Client_Profile_Info WHERE sLastName != '' AND sFrstName != '' AND dBirthDte != ''")
    EClientInfo GetClientCompleteDetail();

    @Query("SELECT a.sUserIDxx, " +
            "a.sUserName, " +
            "a.sLastName, " +
            "a.sFrstName, " +
            "a.sMiddName, " +
            "a.sSuffixNm, " +
            "a.cGenderCd, " +
            "a.dBirthDte, " +
            "b.sTownName || ', ' || c.sProvName AS sBirthPlc, " +
            "a.cCvilStat, " +
            "a.sEmailAdd, " +
            "a.sMobileNo, " +
            "a.sImagePth, " +
            "a.cVerified FROM Client_Profile_Info a " +
            "LEFT JOIN Town_Info b ON a.sBirthPlc = b.sTownIDxx LEFT JOIN Province_Info c ON b.sProvIDxx = c.sProvIDxx")
    LiveData<ClientDetail> GetClientDetailForPreview();

    @Query("SELECT * FROM Client_Email_Info WHERE sEmailAdd =:args")
    EEmailInfo GetEmailInfo(String args);

    @Query("SELECT * FROM App_User_Mobile WHERE sMobileNo =:args")
    EMobileInfo GetMobileInfo(String args);

    class ClientBSAddress{
        public String sHouseNo1;
        public String sAddress1;
        public String sBrgyNme1;
        public String sTownNme1;
        public String sProvNme1;
        public String sHouseNo2;
        public String sAddress2;
        public String sBrgyNme2;
        public String sTownNme2;
        public String sProvNme2;
        public String cVerified;
    }

    class oAddressUpdate{
        public String sHouseNox;
        public String sAddressx;
        public String sBrgyIDxx;
        public String sTownIDxx;
        public String sBrgyName;
        public String sTownName;
    }

    class ClientDetail{
        public String sUserIDxx;
        public String sUserName;
        public String sLastName;
        public String sFrstName;
        public String sMiddName;
        public String sSuffixNm;
        public String cGenderCd;
        public String dBirthDte;
        public String sBirthPlc;
        public String cCvilStat;
        public String sEmailAdd;
        public String sMobileNo;
        public String sImagePth;
        public String cVerified;
    }
}
