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

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchPerformance;

import java.util.List;

@Dao
public interface DBranchPerformance {

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(EBranchPerformance branchPerformance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EBranchPerformance> branchPerformances);

    @Update
    void update(EBranchPerformance branchPerformance);

    @Query("SELECT sEmpLevID FROM User_Info_Master")
    int GetUserLevel();

    @Query("SELECT sDeptIDxx FROM User_Info_Master")
    String GetUserDepartment();

    @Query("SELECT * FROM MC_Branch_Performance WHERE sPeriodxx =:Period AND sBranchCd =:BranchCd")
    EBranchPerformance GetBranchPerformance(String Period, String BranchCd);

    @Query("SELECT sAreaCode FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    String GetAreaCode();

    @Query("SELECT sBranchCd FROM Branch_Info WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master)")
    String GetBranchCode();

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
    @Query("SELECT * FROM MC_Branch_Performance WHERE sPeriodxx= :fsPeriodx ORDER BY nMCActual DESC")
    LiveData<List<EBranchPerformance>> getAreaBranchesMCSalesPerformance(String fsPeriodx);

    @Query("SELECT * FROM MC_Branch_Performance WHERE sPeriodxx= :fsPeriodx ORDER BY nSPActual DESC")
    LiveData<List<EBranchPerformance>> getAreaBranchesSPSalesPerformance(String fsPeriodx);

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
            "WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master) " +
            "ORDER BY sPeriodxx ASC")
    LiveData<List<PeriodicalPerformance>> getMCBranchPeriodicalPerformance();

    @Query("SELECT sPeriodxx AS Period, " +
            "nSPActual AS Actual, " +
            "nSPGoalxx AS Goal " +
            "FROM MC_Branch_Performance " +
            "WHERE sBranchCd = (SELECT sBranchCd FROM User_Info_Master) " +
            "ORDER BY sPeriodxx ASC")
    LiveData<List<PeriodicalPerformance>> getSPBranchPeriodicalPerformance();

    @Query("SELECT sPeriodxx AS Period, " +
            "nMCActual AS Actual, " +
            "nMCGoalxx AS Goal " +
            "FROM MC_Branch_Performance " +
            "WHERE sBranchCd =:BranchCd " +
            "ORDER BY sPeriodxx ASC")
    LiveData<List<PeriodicalPerformance>> GetMCSalesPeriodicPerformance(String BranchCd);

    @Query("SELECT sPeriodxx AS Period, " +
            "nSPActual AS Actual, " +
            "nSPGoalxx AS Goal " +
            "FROM MC_Branch_Performance " +
            "WHERE sBranchCd =:BranchCd " +
            "ORDER BY sPeriodxx ASC")
    LiveData<List<PeriodicalPerformance>> GetSPSalesPeriodicPerformance(String BranchCd);

    @Query("SELECT sPeriodxx AS Period, " +
            "nJOActual AS Actual, " +
            "nJOGoalxx AS Goal " +
            "FROM MC_Branch_Performance " +
            "WHERE sBranchCd =:BranchCd " +
            "ORDER BY sPeriodxx ASC")
    LiveData<List<PeriodicalPerformance>> GetJobOrderPeriodicPerformance(String BranchCd);

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

    @Query("SELECT" +
            " SUM(nMCGoalxx) as mcGoal," +
            " SUM(nMCActual) as mcActual," +
            " SUM(nSPGoalxx) as spGoal," +
            " SUM(nSPActual) as spActual" +
            " FROM MC_Branch_Performance" +
            " WHERE sPeriodxx" +
            " BETWEEN :fsValue1 AND :fsValue2")
    LiveData<MonthlyPieChart> get12MonthPieChartData(String fsValue1, String fsValue2);

    @Query("SELECT" +
            " SUM(nMCGoalxx) as mcGoal," +
            " SUM(nMCActual) as mcActual," +
            " SUM(nSPGoalxx) as spGoal," +
            " SUM(nSPActual) as spActual" +
            " FROM MC_Branch_Performance" +
            " WHERE sBranchCd = :sBranchCd" +
            " AND sPeriodxx BETWEEN" +
            " :fsValue1 AND :fsValue2")
    LiveData<MonthlyPieChart> get12MonthBranchPieChartData(String sBranchCd, String fsValue1, String fsValue2);

    @Query("SELECT nMCActual || '/' || nMCGoalxx AS Performance FROM MC_Branch_Performance WHERE sBranchCd =:branchCd ORDER BY sPeriodxx DESC LIMIT 1")
    LiveData<String> GetMCSalesPerformance(String branchCd);

    @Query("SELECT nSPActual || '/' || nSPGoalxx AS Performance FROM MC_Branch_Performance WHERE sBranchCd =:branchCd ORDER BY sPeriodxx DESC LIMIT 1")
    LiveData<String> GetSPSalesPerformance(String branchCd);

    @Query("SELECT nJOActual || '/' || nJOGoalxx AS Performance FROM MC_Branch_Performance WHERE sBranchCd =:branchCd ORDER BY sPeriodxx DESC LIMIT 1")
    LiveData<String> GetJobOrderPerformance(String branchCd);

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
        public float mcGoal;
        public float mcActual;
        public float spGoal;
        public float spActual;
    }
}
