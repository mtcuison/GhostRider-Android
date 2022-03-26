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

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.utils.LiveDataTestUtil;

import java.util.List;

@Dao
public interface DEmployeeLeave {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertApplication(EEmployeeLeave poLeave);

    @Update
    void updateApplication(EEmployeeLeave poLeave);

    @Query("SELECT * FROM Employee_Leave WHERE sTransNox =:TransNox AND cTranStat =:TranStat")
    List<EEmployeeLeave> getTransnoxIfExist(String TransNox, String TranStat);

    @Query("DELETE FROM Employee_Leave WHERE sTransNox =:TransNox")
    void deleteApplication(String TransNox);

    @Query("SELECT * FROM Employee_Leave WHERE sTransNox =:TransNox")
    LiveData<EEmployeeLeave> getEmployeeLeaveInfo(String TransNox);

    @Query("UPDATE Employee_Leave SET sTransNox =:newTransNox, cSentStat = '1', dSendDate =:DateSent WHERE sTransNox =:TransNox")
    void updateSendStatus(String DateSent, String TransNox, String newTransNox);

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

    @Query("SELECT * FROM Employee_Leave WHERE sTransNox =:TransNox AND cSentStat <> '1'")
    EEmployeeLeave getLeaveForPosting(String TransNox);

    @Query("SELECT * FROM Employee_Leave WHERE sTransNox =:TransNox AND sApproved IS NULL AND dApproved IS NULL")
    EEmployeeLeave getLeaveForApproval(String TransNox);

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
