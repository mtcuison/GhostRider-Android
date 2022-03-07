package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;

import java.util.List;

@Dao
public interface DCreditOnlineApplicationCI {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void SaveApplicationInfo(ECreditOnlineApplicationCI foCI);

    @Query("SELECT * FROM Credit_Online_Application_CI WHERE cTranStat = '0'")
    LiveData<List<ECreditOnlineApplicationCI>> getForEvaluationList();

    @Query("SELECT * FROM Credit_Online_Application_CI WHERE sTransNox=:TransNox")
    ECreditOnlineApplicationCI getApplication(String TransNox);

    @Query("Update Credit_Online_Application_CI SET cTranStat = '1' WHERE sTransNox =:TransNox")
    void UpdateTransaction(String TransNox);

    @Query("SELECT sAddrFndg FROM Credit_Online_Application_CI WHERE sTransNox =:TransNox")
    String getAddressForEvaluation(String TransNox);

    @Query("SELECT sAsstFndg FROM Credit_Online_Application_CI WHERE sTransNox =:TransNox")
    String getAssetsForEvaluation(String TransNox);

    @Query("SELECT sIncmFndg FROM Credit_Online_Application_CI WHERE sTransNox =:TransNox")
    String getIncomeForEvaluation(String TransNox);

    @Query("UPDATE Credit_Online_Application_CI SET sAddrFndg=:Findings WHERE sTransNox =:TransNox")
    void updateAddressEvaluation(String TransNox, String Findings);

    @Query("UPDATE Credit_Online_Application_CI SET sAsstFndg=:Findings WHERE sTransNox =:TransNox")
    void updateAssetEvaluation(String TransNox, String Findings);

    @Query("UPDATE Credit_Online_Application_CI SET sIncmFndg=:Findings WHERE sTransNox =:TransNox")
    void updateIncomeEvaluation(String TransNox, String Findings);

//    @Query("SELECT sTransNox")

    public class oDataEvaluationInfo {
        public String sTransNox;
        public String sCredInvx;
        public String sAddressx;
        public String sAddrFndg;
        public String sAssetsxx;
        public String sAsstFndg;
        public String sIncomexx;
        public String sIncmFndg;
        public String cHasRecrd;
        public String sRecrdRem;
        public String sClientNm;
        public String dTransact;
        public String sBranchNm;
        public String sMobileNo;
        public String nDownPaym;
        public String sModelIDx;
        public String nAcctTerm;
    }
}
