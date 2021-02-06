package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;

import java.util.List;

@Dao
public interface DBarangayInfo {

    @Insert
    void insert(EBarangayInfo barangayInfo);

    @Update
    void update(EBarangayInfo barangayInfo);

    @Delete
    void delete(EBarangayInfo barangayInfo);

    @Query("SELECT * FROM Barangay_Info")
    LiveData<List<EBarangayInfo>> getAllBarangayInfo();

    @Query("DELETE FROM Barangay_Info")
    void deleteAllBarangayInfo();

    @Query("SELECT * FROM Barangay_Info WHERE sTownIDxx = :TownID")
    LiveData<List<EBarangayInfo>> getAllBarangayInfoFromTown(String TownID);

    @Query("SELECT sBrgyName FROM Barangay_Info WHERE sTownIDxx = :TownID")
    LiveData<String[]> getAllBarangayNameFromTown(String TownID);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkBarangayData(List<EBarangayInfo> barangayInfoList);

    @Query("SELECT sBrgyName FROM Barangay_Info WHERE sBrgyIDxx = :fsID")
    LiveData<String> getBarangayInfoFromID(String fsID);
}
