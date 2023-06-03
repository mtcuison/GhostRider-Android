package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.rmj.g3appdriver.GConnect.room.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ECountryInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ETownInfo;

import java.util.List;

@Dao
public interface DAddress {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveBarangay(List<EBarangayInfo> foValue);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveTown(List<ETownInfo> foValue);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveProvince(List<EProvinceInfo> foValue);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void SaveCountry(List<ECountryInfo> foValue);

    @Query("SELECT * FROM Barangay_Info WHERE sTownIDxx =:TownID")
    LiveData<List<EBarangayInfo>> GetBarangayList(String TownID);

    @Query("SELECT * FROM Country_Info")
    LiveData<List<ECountryInfo>> GetCountryList();

    @Query("SELECT sBrgyName FROM Barangay_Info WHERE sBrgyIDxx =:fsBrgyID")
    LiveData<String> GetBrgyName(String fsBrgyID);

    @Query("SELECT a.sTownName || ', ' || b.sProvName " +
            "FROM Town_Info a " +
            "LEFT JOIN Province_Info b " +
            "ON a.sProvIDxx = b.sProvIDxx " +
            "WHERE a.sTownIDxx =:fsTownID")
    LiveData<String> GetTownProvName(String fsTownID);

    @Query("SELECT a.sBrgyName || ', ' || b.sTownName || ', ' || c.sProvName " +
            "FROM Barangay_Info a " +
            "LEFT JOIN Town_Info b " +
            "ON a.sTownIDxx = b.sTownIDxx " +
            "LEFT JOIN Province_Info c " +
            "ON b.sProvIDxx = c.sProvIDxx " +
            "WHERE a.sBrgyIDxx=:fsBrgyID")
    LiveData<String> GetFullAddressName(String fsBrgyID);

    @Query("SELECT " +
            "a.sTownIDxx AS sTownID, " +
            "a.sTownName AS sTownNm, " +
            "b.sProvName AS sProvNm " +
            "FROM Town_Info a " +
            "LEFT JOIN Province_Info b " +
            "ON a.sProvIDxx = b.sProvIDxx")
    LiveData<List<oTownObj>> GetTownList();

    @Query("SELECT sBrgyName FROM Barangay_Info WHERE sBrgyIDxx =:args")
    String GetBarangayName(String args);

    @Query("SELECT a.sTownName || ', ' || b.sProvName FROM Town_Info a LEFT JOIN Province_Info b ON a.sProvIDxx = b.sProvIDxx WHERE sTownIDxx =:args")
    String GetTownProvinceName(String args);

    class oTownObj{
        public String sTownID;
        public String sTownNm;
        public String sProvNm;
    }

    @Query("SELECT COUNT(sBrgyIDxx) FROM Barangay_Info")
    int CheckBarangayInfo();

    @Query("SELECT COUNT(sTownIDxx) FROM Town_Info")
    int CheckTownInfo();

    @Query("SELECT COUNT(sProvIDxx) FROM Province_Info")
    int CheckProvinceInfo();

    @Query("SELECT COUNT(sCntryCde) FROM Country_Info")
    int CheckCountryInfo();
}
