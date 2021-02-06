package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;

import java.util.List;

@Dao
public interface DBranchInfo {

    @Insert
    void insert(EBranchInfo branchInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EBranchInfo> branchInfoList);

    @Update
    void update(EBranchInfo branchInfo);

    @Delete
    void delete(EBranchInfo branchInfo);

    @Query("DELETE FROM Branch_Info")
    void deleteAllBranches();

    @Query("SELECT * FROM Branch_Info")
    LiveData<EBranchInfo> getBranchInfo();

    @Query("SELECT * FROM Branch_Info WHERE cDivision = 1")
    LiveData<List<EBranchInfo>> getAllMcBranchInfo();

    @Query("SELECT * FROM Branch_Info WHERE cDivision = 0")
    LiveData<List<EBranchInfo>> getAllMpBranchInfo();

    @Query("SELECT sBranchNm FROM Branch_Info WHERE cDivision = 1")
    LiveData<String[]> getMCBranchNames();

    @Query("SELECT sBranchNm FROM Branch_Info WHERE cRecdStat = 1")
    LiveData<String[]> getAllBranchNames();

    @Query("SELECT * FROM Branch_Info WHERE cRecdStat = 1")
    LiveData<List<EBranchInfo>> getAllBranchInfo();

    @Query("SELECT cPromoDiv FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    LiveData<String> getPromoDivision();

    @Query("SELECT * FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    LiveData<EBranchInfo> getUserBranchInfo();
}
