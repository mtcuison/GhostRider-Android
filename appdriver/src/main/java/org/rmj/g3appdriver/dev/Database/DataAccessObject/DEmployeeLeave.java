/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/19/21 10:49 AM
 * project file last modified : 6/19/21 10:49 AM
 */

package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EEmployeeLeave;

import java.util.List;

@Dao
public interface DEmployeeLeave {

    @Insert
    void insertApplication(EEmployeeLeave poLeave);

    @Update
    void updateApplication(EEmployeeLeave poLeave);

    @Query("SELECT COUNT (*) FROM Employee_Leave")
    int GetRowsCountForID();

    @Query("SELECT * FROM User_Info_Master")
    EEmployeeInfo GetEmployeeInfo();

    @Query("SELECT * FROM Employee_Leave WHERE sTransNox =:TransNox")
    EEmployeeLeave GetEmployeeLeave(String TransNox);

    @Query("SELECT * FROM Employee_Leave WHERE sTransNox =:TransNox AND cTranStat =:TranStat")
    List<EEmployeeLeave> getTransnoxIfExist(String TransNox, String TranStat);

    @Query("SELECT * FROM Employee_Leave WHERE sTransNox =:TransNox")
    LiveData<EEmployeeLeave> getEmployeeLeaveInfo(String TransNox);

    @Query("UPDATE Employee_Leave SET sTransNox =:newTransNox, cSentStat = '1', dSendDate =:DateSent WHERE sTransNox =:TransNox")
    void updateSendStatus(String DateSent, String TransNox, String newTransNox);

    @Query("UPDATE Employee_Leave SET cSentStat = '1' WHERE sTransNox =:TransNox")
    void updatePostedApproval(String TransNox);

    @Query("UPDATE Employee_Leave SET " +
            "cTranStat =:TranStat, " +
            "dModified =:DateSent, " +
            "dApproved =:DateSent, " +
            "sApproved = (SELECT sUserIDxx FROM User_Info_Master) " +
            "WHERE sTransNox =:TransNox")
    void updateLeaveApproval(String TranStat, String TransNox, String DateSent);

    @Query("SELECT sTransNox, dTransact, sEmployID,  dDateFrom, dDateThru, sApproved, dApproved, dAppldFrx, dAppldTox, sPurposex FROM Employee_Leave UNION " +
            "SELECT sTransNox, dTransact, xEmployee, dDateFrom, dDateThru, sApproved, dApproved, dAppldFrx, dAppldTox, sRemarksx FROM Employee_Business_Trip " +
            "ORDER BY dTransact")
    LiveData<List<LeaveOBApplication>> getAllLeaveOBApplication();

    @Query("SELECT * FROM Employee_Leave WHERE sApproved IS NULL AND dApproved IS NULL ORDER BY dTransact DESC")
    LiveData<List<EEmployeeLeave>> getEmployeeLeaveForApprovalList();

    @Query("SELECT * FROM Employee_Leave " +
            "WHERE sEntryByx = (SELECT sEmployID FROM User_Info_Master)")
    LiveData<List<EEmployeeLeave>> getEmployeeLeaveList();

    @Query("SELECT * FROM Employee_Leave WHERE cTranStat <> '0'")
    LiveData<List<EEmployeeLeave>> getApproveLeaveList();

    class LeaveOBApplication {
        public String sTransNox;
        public String dTransact;
        public String EmployName;
        public String dDateFrom;
        public String dDateThru;
    }

    @Query("SELECT * FROM Employee_Leave WHERE cSentStat <> '1'")
    List<EEmployeeLeave> getUnsentEmployeeLeave();
}
