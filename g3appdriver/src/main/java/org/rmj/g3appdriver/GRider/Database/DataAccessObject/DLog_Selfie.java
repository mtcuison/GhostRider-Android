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

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;

import java.util.List;

@Dao
public interface DLog_Selfie {


    @Insert
    void insertLoginSelfie(ELog_Selfie log_selfie);

    @Query("SELECT * FROM Employee_Log_Selfie WHERE dLogTimex LIKE:DateLog AND cSendStat = \"1\" ")
    LiveData<List<ELog_Selfie>> getCurrentTimeLogIfExist(String DateLog);

    @Query("SELECT * FROM Employee_Log_Selfie WHERE dLogTimex LIKE:DateLog AND cSendStat = \"1\" OR cSendStat = \"0\"")
    LiveData<List<ELog_Selfie>> getCurrentTimeLog(String DateLog);

    @Query("UPDATE Employee_Log_Selfie " +
            "SET sTransNox =:TransNox, " +
            "cSendStat = '1', " +
            "dSendDate =:DateSent " +
            "WHERE sTransNox =:OldTransNox")
    void updateEmployeeLogStat(String TransNox, String OldTransNox, String DateSent);

    @Query("SELECT * FROM Employee_Log_Selfie WHERE cSendStat <> '1'")
    List<ELog_Selfie> getUnsentSelfieLogin();

    @Query("SELECT * FROM Employee_Log_Selfie WHERE sEmployID = (SELECT sEmployID FROM User_Info_Master)")
    LiveData<List<ELog_Selfie>> getAllEmployeeTimeLog();
}
