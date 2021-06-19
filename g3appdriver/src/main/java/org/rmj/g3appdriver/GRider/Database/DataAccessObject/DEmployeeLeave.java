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

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;

@Dao
public interface DEmployeeLeave {

    @Insert
    void insertApplication(EEmployeeLeave poLeave);

    @Update
    void updateApplication(EEmployeeLeave poLeave);

    @Query("DELETE FROM Employee_Leave WHERE sTransNox =:TransNox")
    void deleteApplication(String TransNox);

    @Query("UPDATE Employee_Leave SET cSentStat = '1', dSendDate =:DateSent WHERE sTransNox =:TransNox")
    void updateSendStatus(String DateSent, String TransNox);
}
