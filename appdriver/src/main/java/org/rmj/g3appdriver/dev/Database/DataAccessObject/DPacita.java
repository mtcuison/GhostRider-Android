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

    @Query("UPDATE Pacita_Evaluation SET cTranStat = 1, sTransNox =:NextCde WHERE sTransNox=:transNo")
    void UpdatePosted(String transNo, String NextCde);

    @Query("SELECT * FROM Pacita_Rule")
    LiveData<List<EPacitaRule>> GetPacitaRules();

    @Query("SELECT dTimeStmp FROM Pacita_Rule ORDER BY dTimeStmp DESC LIMIT 1")
    String GetLatestRecordTimeStamp();

    @Query("SELECT * FROM Pacita_Evaluation WHERE sTransNox=:TransNox")
    LiveData<EPacitaEvaluation> GetPacitaEvaluation(String TransNox);

    @Query("DELETE FROM Pacita_Evaluation WHERE sTransNox =:TransNox")
    void ResetPacitaRewardForBranch(String TransNox);

    @Query("SELECT * FROM Pacita_Rule WHERE nEntryNox =:entryNox")
    EPacitaRule GetPacitaRule(int entryNox);

    @Query("SELECT * FROM Pacita_Evaluation WHERE sTransNox=:args")
    EPacitaEvaluation GetEvaluationForPosting(String args);

    @Query("SELECT * FROM Pacita_Evaluation WHERE sBranchCD=:BranchCd ORDER BY dTransact DESC LIMIT 1")
    EPacitaEvaluation GetEvaluationForInitialization(String BranchCd);

    @Query("SELECT * FROM Branch_Info")
    LiveData<List<EBranchInfo>> GetBranchList();

    @Query("SELECT COUNT(*) FROM Pacita_Evaluation")
    int GetRowsCountForID();

    @Query("SELECT nEntryNox FROM Pacita_Rule")
    List<Integer> GetPacitaRulesEntryNo();

    @Query("SELECT sUserIDxx FROM User_Info_Master")
    String GetUserID();

    @Query("SELECT " +
            "a.sTransNox, " +
            "a.dTransact, " +
            "a.nRatingxx " +
            "FROM Pacita_Evaluation a " +
            "WHERE a.sBranchCD =:BranchCD " +
            "AND a.cTranStat == '1' " +
            "ORDER BY a.dTransact DESC")
    LiveData<List<BranchRecords>> GetBranchRecords(String BranchCD);

    class BranchRecords{
        public String sTransNox;
        public String dTransact;
        public String nRatingxx;
    }
}
