package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaRule;

import java.util.List;

@Dao
public interface DPacita {

    @Insert
    void Save(EPacitaRule foVal);

    @Update
    void Update(EPacitaRule foVal);

    @Insert
    void Save(EPacitaEvaluation foVal);

    @Update
    void Update(EPacitaEvaluation foVal);

//    @Query("SELECT * FROM Pacita_Evaluation WHERE sBranchCD =:Branch AND cTranStat <> '1'")
//    void ResetPacitaRewardForBranch(String Branch);

    @Query("SELECT * FROM Pacita_Rule WHERE nEntryNox =:entryNox")
    EPacitaRule GetPacitaRule(int entryNox);

    @Query("SELECT * FROM Pacita_Evaluation WHERE sTransNox=:args")
    EPacitaEvaluation GetEvaluation(String args);

    @Query("SELECT * FROM Pacita_Evaluation WHERE sTransNox=:BranchCd ORDER BY dTransact DESC LIMIT 1")
    EPacitaEvaluation GetEvaluationForInitialization(String BranchCd);

    @Query("SELECT * FROM Branch_Info")
    LiveData<List<EBranchInfo>> GetBranchList();

    @Query("SELECT COUNT(*) FROM Pacita_Evaluation")
    int GetRowsCountForID();

    @Query("SELECT nEntryNox FROM Pacita_Rule")
    List<Integer> GetPacitaRules();

    @Query("SELECT sUserIDxx FROM User_Info_Master")
    String GetUserID();

    @Query("SELECT " +
            "a.dTransact, " +
            "a.nRatingxx " +
            "FROM Pacita_Evaluation a " +
            "WHERE a.sBranchCD =:BranchCD " +
            "AND a.cTranStat == '1' " +
            "ORDER BY a.dTransact DESC")
    LiveData<List<BranchRecords>> GetBranchRecords(String BranchCD);

    class BranchRecords{
        public String dTransact;
        public String nRatingxx;
    }
}
