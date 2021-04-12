package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;

import java.util.List;

@Dao
public interface DCIEvaluation {

    @Insert
    void insert(ECreditApplication creditApplication);

    @Update
    void update(ECreditApplication creditApplication);

    @Delete
    void delete(ECreditApplication creditApplication);

    @Query("SELECT * FROM Credit_Online_Application")
    LiveData<List<ECreditApplication>> getAllCreditApplication();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<ECreditApplication> creditApplications);

    @Query("UPDATE Credit_Online_Application SET cCaptured = '1', sFileLoct = (SELECT sFileLoct FROM Image_Information WHERE sSourceNo =:TransNox) WHERE sTransNox =:TransNox")
    void updateCustomerImageStat(String TransNox);

    @Query("SELECT * FROM Credit_Online_Application WHERE sTransNox =:TransNox")
    ECreditApplication getLoanInfoOfTransNox(String TransNox);

    @Query("UPDATE Credit_Online_Application SET " +
            "sTransNox =:TransNox, " +
            "cSendStat = '1', " +
            "dModified =:DateTime " +
            "WHERE sTransNox =:oldTransNox")
    void updateSentLoanAppl(String oldTransNox, String TransNox, String DateTime);

    @Query("UPDATE Credit_Online_Application_List SET " +
            "sTransNox =:TransNox " +
            "WHERE sTransNox =:oldTransNox")
    void updateApplicationListTransNox(String oldTransNox, String TransNox);

    @Query("UPDATE Credit_Online_Application_List SET " +
            "sTransNox =:TransNox " +
            "WHERE sTransNox =:oldTransNox")
    void updateApplicationImageTransNox(String oldTransNox, String TransNox);

    @Query("UPDATE Credit_Online_Application_Documents SET " +
            "sTransNox =:TransNox " +
            "WHERE sTransNox =:oldTransNox")
    void updateApplicationDocsTransNox(String oldTransNox, String TransNox);

    @Query("SELECT * FROM Credit_Online_Application WHERE cSendStat <> '1'")
    List<ECreditApplication> getUnsentLoanApplication();


    @Query("SELECT * FROM Credit_Online_Application WHERE sTransNox =:TransNox")
    List<ApplicationLog> getDuplicateTransNox(String TransNox);

    @Query("UPDATE Credit_Online_Application SET sFileLoct = (SELECT sFileLoct FROM Image_Information WHERE sSourceNo =:TransNox) WHERE sTransNox =:TransNox")
    void updateApplicantImageStat(String TransNox);

    @Query("Select a.sGOCASNox, " +
            "a.sTransNox, " +
            "b.sBranchNm, " +
            "a.dCreatedx, " +
            "a.sDetlInfo, " +
            "a.sClientNm, " +
            "a.cWithCIxx, " +
            "a.cSendStat, " +
            "a.cTranStat, " +
            "a.dReceived, " +
            "a.dVerified, " +
            "a.cCaptured, " +
            "c.sFileLoct " +
            "From Credit_Online_Application a " +
            "Left Join Branch_Info b " +
            "Left Join Image_Information c " +
            "ON a.sBranchCd = b.sBranchCd " +
            "OR a.sTransNox = c.sSourceNo " +
            "WHERE cTranStat != 4 " +
            "AND sCreatedx = (SELECT sUserIDxx From User_Info_Master) " +
            "GROUP BY a.sTransNox " +
            "ORDER BY a.dCreatedx DESC")
    LiveData<List<ApplicationLog>> getApplicationHistory();

    class ApplicationLog{
        public String sGOCASNox;
        public String sTransNox;
        public String sBranchNm;
        public String dCreatedx;
        public String sDetlInfo;
        public String sClientNm;
        public String cWithCIxx;
        public String cSendStat;
        public String cTranStat;
        public String dReceived;
        public String dVerified;
        public String cCaptured;
        public String sFileLoct;
    }

    @Query("SELECT a.sGOCASNox," +
            "a.sTransNox," +
            "b.sBranchNm," +
            "a.dCreatedx," +
            "a.sDetlInfo," +
            "a.sClientNm," +
            "a.cWithCIxx," +
            "a.cSendStat," +
            "a.cTranStat," +
            "a.dReceived," +
            "a.dVerified " +
            "FROM Credit_Online_Application a " +
            "LEFT JOIN Branch_Info b " +
            "ON a.sBranchCd = b.sBranchCd " +
            "WHERE cTranStat != 4 " +
            "AND a.sBranchCd =:BranchID " +
            "AND sCreatedx = (SELECT sUserIDxx From User_Info_Master) " +
            "ORDER BY a.dCreatedx DESC" )
    LiveData<List<ApplicationLog>> getApplicationByBranch(String BranchID);
}
