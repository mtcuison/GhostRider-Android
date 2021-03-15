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

    @Query("SELECT * FROM EMPLOYEE_LOG_SELFIE WHERE dLogTimex LIKE:DateLog")
    LiveData<List<ELog_Selfie>> getCurrentTimeLogIfExist(String DateLog);
}
