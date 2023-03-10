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

package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.dev.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchPerformance;

import java.util.List;

@Dao
public interface DAreaPerformance {

    @Insert
    void insert(EAreaPerformance areaPerformance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EAreaPerformance> areaPerformances);

    @Update
    void update(EAreaPerformance areaPerformance);

    @Query("SELECT sEmpLevID FROM User_Info_Master")
    int GetUserLevel();

    @Query("SELECT sDeptIDxx FROM User_Info_Master")
    String GetUserDepartment();

    @Query("SELECT sAreaCode FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    String GetAreaCode();

    @Query("SELECT * FROM MC_Area_Performance WHERE sPeriodxx =:Period AND sAreaCode=:AreaCode")
    EAreaPerformance GetAreaPerformance(String Period, String AreaCode);

    @Query("SELECT * FROM MC_Area_Performance ORDER BY sPeriodxx ASC")
    LiveData<List<EAreaPerformance>> getAllAreaPerformanceInfo();

    @Query("SELECT * FROM MC_Area_Performance ORDER BY ROUND(nMCActual * 100.0 / nMCGoalxx, 1) DESC LIMIT 5")
    LiveData<List<EAreaPerformance>> getAreaPerformanceDashboard();

    @Query("SELECT sAreaDesc FROM MC_Area_Performance WHERE sAreaCode = (SELECT sAreaCode FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master))")
    LiveData<String> getAreaDescription();

    @Query("SELECT nMCActual || '/' || nMCGoalxx AS Performance FROM MC_Area_Performance ORDER BY sPeriodxx DESC LIMIT 1")
    LiveData<String> GetMCSalesPerformance();

    @Query("SELECT nSPActual || '/' || nSPGoalxx AS Performance FROM MC_Area_Performance ORDER BY sPeriodxx DESC LIMIT 1")
    LiveData<String> GetSPSalesPerformance();

    @Query("SELECT nJOActual || '/' || nJOGoalxx AS Performance FROM MC_Area_Performance ORDER BY sPeriodxx DESC LIMIT 1")
    LiveData<String> GetJobOrderPerformance();

    @Query("SELECT a.* FROM MC_Branch_Performance a LEFT JOIN Branch_Info b ON a.sBranchCd = b.sBranchCd " +
            "WHERE b.sAreaCode = (SELECT sAreaCode FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)) " +
            "AND a.sPeriodxx = (SELECT sPeriodxx FROM MC_Branch_Performance ORDER BY sPeriodxx DESC LIMIT 1)" +
            "ORDER BY a.nMCActual DESC LIMIT 5")
    LiveData<List<EBranchPerformance>> GetTopBranchPerformerForMCSales();

    @Query("SELECT a.* FROM MC_Branch_Performance a LEFT JOIN Branch_Info b ON a.sBranchCd = b.sBranchCd " +
            "WHERE b.sAreaCode = (SELECT sAreaCode FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)) " +
            "AND a.sPeriodxx = (SELECT sPeriodxx FROM MC_Branch_Performance ORDER BY sPeriodxx DESC LIMIT 1)" +
            "ORDER BY a.nSPActual DESC LIMIT 5")
    LiveData<List<EBranchPerformance>> GetTopBranchPerformerForSPSales();

    @Query("SELECT a.* FROM MC_Branch_Performance a LEFT JOIN Branch_Info b ON a.sBranchCd = b.sBranchCd " +
            "WHERE b.sAreaCode = (SELECT sAreaCode FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)) " +
            "AND a.sPeriodxx = (SELECT sPeriodxx FROM MC_Branch_Performance ORDER BY sPeriodxx DESC LIMIT 1)" +
            "ORDER BY a.nJOActual DESC LIMIT 5")
    LiveData<List<EBranchPerformance>> GetTopBranchPerformerForJobOrder();
}
