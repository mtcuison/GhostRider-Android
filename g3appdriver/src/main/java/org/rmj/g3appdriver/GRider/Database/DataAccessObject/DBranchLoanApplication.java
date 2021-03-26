package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;

import java.util.List;

@Dao
public interface DBranchLoanApplication {
    @Insert
    void insert(EBranchLoanApplication branchLoanApplication);

    @Insert
    void insertBulkData(List<EBranchLoanApplication> branchLoanApplication);

    @Update
    void update(EBranchLoanApplication branchLoanApplication);

    @Delete
    void delete(EBranchLoanApplication branchLoanApplication);

    @Query("SELECT * FROM Credit_Online_Application_List " +
            "WHERE sBranchCD = (SELECT sBranchCD FROM User_Info_Master) ")
    LiveData<List<EBranchLoanApplication>> getAllBranchCreditApplication();

    @Query("SELECT * FROM Credit_Online_Application_List " +
            "WHERE sBranchCD = (SELECT sBranchCD FROM User_Info_Master)")
    LiveData<List<EBranchLoanApplication>> getAllCICreditApplication();

//    @Query("SELECT * FROM Credit_Online_Application_List " +
//            "WHERE cTranStat != 4 AND " +
//            "sCreatedx  = (SELECT sEmployID FROM User_Info_Master) ")

    @Query("SELECT * FROM Credit_Online_Application_List " +
            "WHERE cTranStat != 4 AND " +
            "sCreatedx  = 'GAP020200310' ")
    LiveData<List<EBranchLoanApplication>> getAllCICreditApplicationLog();


//    @Query("Select a.sTransNox, " +
//            "a.sBranchCd, " +
//            "a.dCreatedx, " +
//            "a.sCreatedx, " +
//            "a.sClientNm, " +
//            "a.sDetlInfo, " +
//            "a.cSendStat, " +
//            "a.cTranStat, " +
//            "a.dReceived, " +
//            "a.dVerified " +
//            "From Credit_Online_Application a " +
//            "Left Join Branch_Info b " +
//            "ON a.sBranchCd = b.sBranchCd " +
//            "WHERE cTranStat != 4 " +
//            "AND sCreatedx = (SELECT sUserIDxx From User_Info_Master) " +
//            "ORDER BY a.dCreatedx DESC")
//    LiveData<List<BranchCreditApplication>> getAllBranchCreditApplicationLocal();
//
//    class BranchCreditApplication{
//        public String sTransNox;
//        public String sBranchCD;
//        public String dTransact;
//        public String CredInvx;
//        public String sCompnyNm;
//        public String sSpouseNm;
//        public String sAddressx;
//        public String sMobileNo;
//        public String sQMAppCde;
//        public String sModelNme;
//        public String nDownPaym;
//        public String nAcctTerm;
//        public String sCreatedx;
//        public String cTranStat;
//        public String dTimeStmp;
//    }

}
