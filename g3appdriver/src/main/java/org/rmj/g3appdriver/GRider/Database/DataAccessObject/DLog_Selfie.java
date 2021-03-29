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

    @Query("SELECT * FROM Employee_Log_Selfie WHERE dLogTimex LIKE:DateLog")
    LiveData<List<ELog_Selfie>> getCurrentTimeLogIfExist(String DateLog);

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
