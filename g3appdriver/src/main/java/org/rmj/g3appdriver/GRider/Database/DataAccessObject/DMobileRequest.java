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

    @Query("DELETE FROM Mobile_Update_Request WHERE sTransNox = :sTransNox")
    void deleteMobileInfo(String sTransNox);

    @Query("SELECT * FROM Mobile_Update_Request")
    LiveData<List<EMobileUpdate>> getMobileRequestList();
}
