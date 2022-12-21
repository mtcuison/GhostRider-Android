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
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ESelfieLog;

import java.util.List;

@Dao
public interface DSelfieLog {

    @Insert
    void SaveSelfieLog(ESelfieLog log_selfie);

    @Query("SELECT COUNT (*) FROM Employee_Log_Selfie")
    int GetRowsCountForID();

    @Query("SELECT * FROM Employee_Log_Selfie WHERE sTransNox =:TransNo")
    ESelfieLog GetSelfieLog(String TransNo);

    @Query("SELECT * FROM Employee_Log_Selfie WHERE dLogTimex LIKE:DateLog AND cSendStat = \"1\" ")
    LiveData<List<ESelfieLog>> getCurrentTimeLogIfExist(String DateLog);

    @Query("UPDATE Employee_Log_Selfie " +
            "SET sTransNox =:TransNox, " +
            "cSendStat = '1', " +
            "dSendDate =:DateSent " +
            "WHERE sTransNox =:OldTransNox")
    void updateEmployeeLogStat(String TransNox, String OldTransNox, String DateSent);

    @Query("SELECT * FROM Employee_Log_Selfie WHERE cSendStat <> '1'")
    List<ESelfieLog> GetSelfieLogsForUpload();

    @Query("SELECT * FROM Employee_Log_Selfie " +
            "WHERE sEmployID = (SELECT sEmployID FROM User_Info_Master) " +
            "AND dTransact =:fsVal " +
            "ORDER BY dLogTimex DESC")
    LiveData<List<ESelfieLog>> getAllEmployeeTimeLog(String fsVal);

    @Query("SELECT COUNT(*) FROM Employee_Log_Selfie WHERE sBranchCd=:BranchCd AND dTransact=:Transact")
    int checkBranchCodeIfExist(String BranchCd, String Transact);

    @Query("SELECT * FROM Employee_Log_Selfie WHERE sBranchCd=:BranchCd AND dTransact=:Transact")
    ESelfieLog CheckSelfieLogIfExist(String BranchCd, String Transact);

    @Query("SELECT * FROM Branch_Info WHERE sBranchCd =:args")
    EBranchInfo GetSelfieLogBranch(String args);


}
