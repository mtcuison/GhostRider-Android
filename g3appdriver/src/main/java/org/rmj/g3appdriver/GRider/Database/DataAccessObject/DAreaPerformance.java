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

import java.util.List;

@Dao
public interface DAreaPerformance {

    @Insert
    void insert(EAreaPerformance areaPerformance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EAreaPerformance> areaPerformances);

    @Update
    void update(EAreaPerformance areaPerformance);

    @Delete
    void delete(EAreaPerformance areaPerformance);

    @Query("DELETE FROM MC_Area_Performance")
    void deleteAllAreaPerformanceInfo();

    @Query("SELECT * FROM MC_Area_Performance ORDER BY sPeriodxx ASC")
    LiveData<List<EAreaPerformance>> getAllAreaPerformanceInfo();

    @Query("SELECT * FROM MC_Area_Performance ORDER BY ROUND(nMCActual * 100.0 / nMCGoalxx, 1) DESC LIMIT 5")
    LiveData<List<EAreaPerformance>> getAreaPerformanceDashboard();

    @Query("SELECT sAreaDesc FROM MC_Area_Performance WHERE sAreaCode = (SELECT sAreaCode FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master))")
    LiveData<String> getAreaDescription();

//    @Query("SELECT sAreaDesc FROM MC_Area_Performance WHERE sAreaCode = :fsAreaCde")
//    LiveData<String> getAreaNameFromCode(String fsAreaCde);

    @Query("SELECT sAreaDesc FROM MC_Area_Performance WHERE sAreaCode = (SELECT sAreaCode FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master))")
    LiveData<String> getAreaNameFromCode();
}
