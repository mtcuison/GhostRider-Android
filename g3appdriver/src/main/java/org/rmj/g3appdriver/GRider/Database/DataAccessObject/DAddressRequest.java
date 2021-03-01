package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;

import java.util.List;

@Dao
public interface DAddressRequest {

    @Insert
    void insert(EAddressUpdate addressUpdate);

    @Query("DELETE FROM Address_Update_Request WHERE sTransNox = :sTransNox")
    void deleteAddressInfo(String sTransNox);

    @Query("SELECT * FROM Address_Update_Request")
    LiveData<List<EAddressUpdate>> getAddressRequestList();

    @Query("UPDATE Address_Update_Request " +
            "SET sTransNox =:TransNox " +
            ",cSendStat = '1', " +
            "dSendDate =:DateEntry, " +
            "dModified=:DateEntry, " +
            "dTimeStmp=:DateEntry " +
            "WHERE sTransNox=:oldTransNox")
    void updateAddressStatus(String TransNox, String oldTransNox, String DateEntry);

    @Query("SELECT AU.sTransNox, " +
            "AU.sClientID, " +
            "AU.cReqstCDe, " +
            "AU.cAddrssTp, " +
            "AU.sHouseNox, " +
            "AU.sAddressx, " +
            "AU.sTownIDxx, " +
            "AU.sBrgyIDxx, " +
            "AU.cPrimaryx, " +
            "AU.sRemarksx, " +
            "B.sBrgyName, " +
            "T.sTownName, " +
            "P.sProvName " +
            "FROM Address_Update_Request AS AU, " +
            "Barangay_Info AS B, " +
            "Town_Info AS T, " +
            "Province_Info AS P " +
            "WHERE B.sBrgyIDxx = AU.sBrgyIDxx " +
            "AND T.sTownIDxx = AU.sTownIDxx " +
            "AND P.sProvIDxx = T.sProvIDxx")
    LiveData<List<CustomerAddressInfo>> getAddressNames();

    class CustomerAddressInfo {
        public String sTransNox;
        public String sClientID;
        public String cReqstCDe;
        public String cAddrssTp;
        public String sHouseNox;
        public String sAddressx;
        public String sTownIDxx;
        public String sBrgyIDxx;
        public String cPrimaryx;
        public String sRemarksx;
        public String sBrgyName;
        public String sTownName;
        public String sProvName;
    }
}
