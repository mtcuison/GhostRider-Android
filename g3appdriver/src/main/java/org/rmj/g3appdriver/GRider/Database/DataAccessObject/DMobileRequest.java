package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;

import java.util.List;

@Dao
public interface DMobileRequest {

    @Insert
    void insert(EMobileUpdate mobileUpdate);

    @Query("UPDATE Address_Update_Request " +
            "SET sTransNox =:TransNox " +
            ",cSendStat = '1', " +
            "dSendDate =:DateEntry, " +
            "dModified=:DateEntry, " +
            "dTimeStmp=:DateEntry " +
            "WHERE sTransNox=:oldTransNox")
    void updateMobileStatus(String TransNox, String oldTransNox, String DateEntry);

    @Query("DELETE FROM Mobile_Update_Request WHERE sTransNox = :sTransNox")
    void deleteMobileInfo(String sTransNox);

    @Query("SELECT * FROM Mobile_Update_Request")
    LiveData<List<EMobileUpdate>> getMobileRequestList();

    @Query("SELECT * FROM Mobile_Update_Request WHERE sClientID =:ClientID")
    LiveData<List<EMobileUpdate>> getMobileRequestListForClient(String ClientID);

    @Query("SELECT M.cReqstCDe AS mobileReqstCDe," +
            " M.sMobileNo," +
            " M.cPrimaryx AS mobilePrimaryx," +
            " M.sRemarksx AS mobileRemarksx" +
            " FROM LR_DCP_Collection_Detail as C," +
            " Mobile_Update_Request as M" +
            " WHERE C.sClientID = :sClientID AND" +
            " C.sRemCodex = \"CNA\" AND" +
            " M.sClientID = C.sClientID")
    LiveData<List<CNAMobileInfo>> getCNAMobileDataList(String sClientID);

    class CNAMobileInfo {
        public String mobileReqstCDe;
        public String sMobileNo;
        public String mobilePrimaryx;
        public String mobileRemarksx;
    }
}
