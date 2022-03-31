/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 8/13/21 10:32 AM
 * project file last modified : 8/13/21 10:32 AM
 */

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeBusinessTrip;

import java.util.List;

@Dao
public interface DEmployeeBusinessTrip {

    @Insert
    void insert(EEmployeeBusinessTrip obLeave);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(EEmployeeBusinessTrip obLeave);

    @Delete
    void delete(EEmployeeBusinessTrip obLeave);

    @Insert
    void insertNewOBLeave(EEmployeeBusinessTrip obLeave);

    @Query("SELECT * FROM Employee_Business_Trip WHERE sTransNox =:TransNox")
    List<EEmployeeBusinessTrip> getOBIfExist(String TransNox);

    @Query("SELECT * FROM Employee_Business_Trip WHERE sTransNox =:TransNox")
    LiveData<EEmployeeBusinessTrip> getBusinessTripInfo(String TransNox);

    @Query("UPDATE Employee_Business_Trip SET cSendStat = '1' WHERE sTransNox =:TransNox")
    void updateOBSentStatus(String TransNox);

    @Query("UPDATE Employee_Business_Trip SET " +
            "cTranStat =:TranStat, " +
            "dApproved =:DateSent, " +
            "sApproved = (SELECT sUserIDxx FROM User_Info_Master) " +
            "WHERE sTransNox =:TransNox")
    void updateOBApproval(String TranStat, String TransNox, String DateSent);

    @Query("SELECT * FROM Employee_Business_Trip WHERE sApproved IS NULL AND dApproved IS NULL")
    LiveData<List<EEmployeeBusinessTrip>> getOBListForApproval();

    @Query("SELECT * FROM Employee_Business_Trip " +
            "WHERE xEmployee = (SELECT sEmployID FROM User_Info_Master)")
    LiveData<List<EEmployeeBusinessTrip>> getOBList();

    @Query("SELECT * FROM Employee_Business_Trip WHERE cSendStat <> '1'")
    List<EEmployeeBusinessTrip> getUnsentEmployeeOB();

    @Query("SELECT * FROM Employee_Business_Trip WHERE sTransNox =:TransNox")
    EEmployeeBusinessTrip getBusinessTripForPosting(String TransNox);

    @Query("SELECT * FROM Employee_Business_Trip WHERE cSendStat IS NULL")
    List<EEmployeeBusinessTrip> getOBListForUpload();
}
