package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;

import java.util.List;

@Dao
public interface DMobileRequest {

    @Insert
    void insert(EMobileUpdate mobileUpdate);

    @Query("UPDATE Address_Update_Request " +
            "SET sTransNox =:TransNox " +
            ",cSendStat = '1', " +
            "dSendDate =:DateEntry, " +
            "dModified=:DateEntry, " +
            "dTimeStmp=:DateEntry " +
            "WHERE sTransNox=:oldTransNox")
    void updateMobileStatus(String TransNox, String oldTransNox, String DateEntry);

    @Query("DELETE FROM Mobile_Update_Request WHERE sTransNox = :sTransNox")
    void deleteMobileInfo(String sTransNox);

    @Query("SELECT * FROM Mobile_Update_Request")
    LiveData<List<EMobileUpdate>> getMobileRequestList();
}
