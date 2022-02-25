package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;

import java.util.List;

@Dao
public interface DCreditOnlineApplicationCI {

    @Query("SELECT * FROM Credit_Online_Application_CI WHERE cTranStat = '0'")
    LiveData<List<ECreditOnlineApplicationCI>> getForEvaluationList();

    @Query("Update Credit_Online_Application_CI SET cTranStat = '1' WHERE sTransNox=:TransNox")
    void UpdateTransaction(String TransNox);


}
