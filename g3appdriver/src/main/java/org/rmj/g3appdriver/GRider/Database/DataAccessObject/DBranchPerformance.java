package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;

import java.util.List;

@Dao
public interface DBranchPerformance {

    @Insert
    void insert(EBranchPerformance branchPerformance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EBranchPerformance> branchPerformances);

    @Update
    void update(EBranchPerformance branchPerformance);

    @Delete
    void delete(EBranchPerformance branchPerformance);

    @Query("SELECT * FROM MC_Branch_Performance")
    LiveData<List<EBranchPerformance>> getAllBranchPerformanceInfo();

    @Query("DELETE FROM MC_Branch_Performance")
    void deleteAllBranchPerformanceInfo();
}
