/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
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

    @Query("SELECT * FROM MC_Branch_Performance ORDER BY ROUND(nMCActual * 100.0 / 100, 1) DESC LIMIT 5")
    LiveData<List<EBranchPerformance>> getBranchPerformanceForDashBoard();
}
