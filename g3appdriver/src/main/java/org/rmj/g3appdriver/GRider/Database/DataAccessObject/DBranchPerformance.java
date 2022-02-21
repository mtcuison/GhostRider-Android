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

    @Query("SELECT a.sAreaCode FROM Branch_Info a " +
            "LEFT JOIN Branch_Info b " +
            "ON a.sBranchCd = b.sBranchCd " +
            "WHERE b.sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    String getUserAreaCode();

    @Query("SELECT * FROM MC_Branch_Performance GROUP BY sBranchCd ORDER BY nSPActual DESC, nMCActual DESC LIMIT 3")
    LiveData<List<EBranchPerformance>> getBranchPerformanceForDashBoard();

    @Query("SELECT * FROM MC_Branch_Performance ORDER BY ROUND(nMCActual * 100.0 / 100, 1) ASC")
    LiveData<List<EBranchPerformance>> getMCSalesBranchPerformanceASC();

    @Query("SELECT * FROM MC_Branch_Performance ORDER BY ROUND(nMCActual * 100.0 / 100, 1) DESC")
    LiveData<List<EBranchPerformance>> getMCSalesBranchPerformanceDESC();

    @Query("SELECT * FROM MC_Branch_Performance ORDER BY ROUND(nSPActual * 100.0 / 100, 1) ASC")
    LiveData<List<EBranchPerformance>> getSPSalesBranchPerformanceASC();

    @Query("SELECT * FROM MC_Branch_Performance ORDER BY ROUND(nSPActual * 100.0 / 100, 1) DESC")
    LiveData<List<EBranchPerformance>> getSPSalesBranchPerformanceDESC();

    @Query("SELECT * FROM MC_Branch_Performance ORDER BY ROUND(nJOGoalxx * 100.0 / 100, 1) ASC")
    LiveData<List<EBranchPerformance>> getJOBranchPerformanceASC();

    @Query("SELECT * FROM MC_Branch_Performance ORDER BY ROUND(nJOGoalxx * 100.0 / 100, 1) DESC")
    LiveData<List<EBranchPerformance>> getJOBranchPerformanceDESC();

    @Query("SELECT * FROM MC_Branch_Performance WHERE sBranchCd =:BranchCd ORDER BY sPeriodxx ASC")
    LiveData<List<EBranchPerformance>>  getAllBranchPerformanceInfoByBranch(String BranchCd);

    // For Area Monitoring
    @Query("SELECT * FROM MC_Branch_Performance WHERE sPeriodxx= :fsPeriodx ORDER BY sBranchNm ASC")
    LiveData<List<EBranchPerformance>> getAreaBranchesSalesPerformance(String fsPeriodx);

    @Query("SELECT nMCActual AS Actual, ROUND (nMCActual * 100.0 / nMCGoalxx) AS Percentage, nMCGoalxx AS Goal FROM MC_Branch_Performance WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    LiveData<ActualGoal> getMCBranchPerformance();

    @Query("SELECT nSPActual AS Actual, ROUND (nSPActual * 100.0 / nSPGoalxx) AS Percentage, nSPGoalxx AS Goal FROM MC_Branch_Performance WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    LiveData<ActualGoal> getSPBranchPerformance();

    @Query("SELECT * FROM MC_Branch_Performance ORDER BY sPeriodxx DESC LIMIT 1")
    LiveData<EBranchPerformance> getCurrentPeriodPerformance();

    @Query("SELECT sPeriodxx AS Period, " +
            "nMCActual AS Actual, " +
            "nMCGoalxx AS Goal " +
            "FROM MC_Branch_Performance " +
            "WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    LiveData<List<PeriodicalPerformance>> getMCBranchPeriodicalPerformance();

    @Query("SELECT sPeriodxx AS Period, " +
            "nSPActual AS Actual, " +
            "nSPGoalxx AS Goal " +
            "FROM MC_Branch_Performance " +
            "WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    LiveData<List<PeriodicalPerformance>> getSPBranchPeriodicalPerformance();

    @Query("SELECT MIN(sPeriodxx) AS Start, MAX(sPeriodxx) AS Current FROM MC_Branch_Performance;")
    LiveData<PeriodRange> getPeriodRange();

    @Query("SELECT" +
            " SUM(nMCGoalxx) as mcGoal," +
            " SUM(nMCActual) as mcActual," +
            " SUM(nSPGoalxx) as spGoal," +
            " SUM(nSPActual) as spActual" +
            " FROM MC_Branch_Performance" +
            " WHERE sPeriodxx = :fsPeriodx")
    LiveData<MonthlyPieChart> getMonthlyPieChartData(String fsPeriodx);

    class ActualGoal{
        public String Actual;
        public String Percentage;
        public String Goal;
    }

    class PeriodicalPerformance{
        public String Period;
        public String Actual;
        public String Goal;
    }

    class PeriodRange{
        public String Start;
        public String Current;
    }

    class MonthlyPieChart {
        public int mcGoal;
        public int mcActual;
        public int spGoal;
        public int spActual;
    }
}
