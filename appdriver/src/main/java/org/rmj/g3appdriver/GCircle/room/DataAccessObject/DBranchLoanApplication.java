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

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchLoanApplication;

import java.util.List;

@Dao
public interface DBranchLoanApplication {
    @Insert
    void insert(EBranchLoanApplication branchLoanApplication);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EBranchLoanApplication> branchLoanApplication);

    @Update
    void update(EBranchLoanApplication branchLoanApplication);

    @Query("SELECT * FROM Credit_Online_Application_List " +
            "WHERE sBranchCD = (SELECT sBranchCD FROM User_Info_Master) ")
    LiveData<List<EBranchLoanApplication>> getAllBranchCreditApplication();

//    @Query("SELECT * FROM Credit_Online_Application_List " +
//            "WHERE cTranStat != 4 AND " +
//            "sCredInvx  = (SELECT sEmployID FROM User_Info_Master)")

    @Query("SELECT " +
            "a.sTransNox," +
            "a.dTransact," +
            "a.sCredInvx," +
            "a.sCompnyNm," +
            "a.sSpouseNm," +
            "a.sAddressx," +
            "a.sMobileNo," +
            "a.sQMAppCde," +
            "a.sModelNme," +
            "a.nDownPaym," +
            "a.nAcctTerm," +
            "a.cTranStat," +
            "a.dTimeStmp," +
            "b.cTranStat As ciTranStat " +
            "FROM Credit_Online_Application_List a " +
            "LEFT JOIN Credit_Online_Application_List_CI b " +
            "ON a.sTransNox = b.sTransNox  " +
            "WHERE b.cTranStat <> \"0\" " +
            "AND b.sCredInvx = (SELECT sEmployID FROM User_Info_Master)")
    LiveData<List<CIEvaluationList>> getAllCICreditApplicationLog();

    @Query("SELECT * FROM Credit_Online_Application_List WHERE sTransNox =:TransNox ")
    List<EBranchLoanApplication> getDuplicateTransNox(String TransNox);

    @Query("SELECT " +
            "a.sTransNox," +
            "a.dTransact," +
            "a.sCredInvx," +
            "a.sCompnyNm," +
            "a.sSpouseNm," +
            "a.sAddressx," +
            "a.sMobileNo," +
            "a.sQMAppCde," +
            "a.sModelNme," +
            "a.nDownPaym," +
            "a.nAcctTerm," +
            "a.cTranStat," +
            "a.dTimeStmp," +
            "b.cTranStat AS ciTranStat " +
            "FROM Credit_Online_Application_List a " +
            "LEFT JOIN Credit_Online_Application_List_CI b " +
            "ON a.sTransNox = b.sTransNox " +
            "WHERE a.cTranStat = 1 " +
            "AND COALESCE(b.cTranStat, NULL) IS NULL " +
            "OR ciTranStat = 0 ")
    LiveData<List<CIEvaluationList>> getAllCICreditApplications();
    @Insert
    void insertNewApplication(EBranchLoanApplication loanApplication);

    @Query("SELECT I.sFileLoct, C.sTransNox, C.sCompnyNm, C.sModelNme, C.nDownPaym, C.nAcctTerm " +
            "FROM Credit_Online_Application_List AS C " +
            "LEFT JOIN Image_Information AS I " +
            "ON C.sTransNox = I.sDtlSrcNo " +
            "WHERE C.sTransNox =:fsTransNo")
    LiveData<CiDetail> getCiDetail(String fsTransNo);

    public class CiDetail {
        public String sFileLoct;
        public String sTransNox;
        public String sCompnyNm;
        public String sModelNme;
        public String nDownPaym;
        public String nAcctTerm;
    }

    class CIEvaluationList{
        public String sTransNox;
        public String dTransact;
        public String sCredInvx;
        public String sCompnyNm;
        public String sSpouseNm;
        public String sAddressx;
        public String sMobileNo;
        public String sQMAppCde;
        public String sModelNme;
        public String nDownPaym;
        public String nAcctTerm;
        public String cTranStat;
        public String dTimeStmp;
        public String ciTranStat;
    }
}
