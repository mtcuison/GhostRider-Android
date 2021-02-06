package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EClientInfo;

@Dao
public interface DClientInfo {

    @Insert
    void insert(EClientInfo clientInfo);

    @Update
    void update(EClientInfo clientInfo);

    @Delete
    void delete(EClientInfo clientInfo);

    @Query("SELECT * FROM Client_Info_Master")
    LiveData<EClientInfo> getClientInfo();

    @Query("SELECT sUserIDxx FROM Client_Info_Master")
    String getUserID();
}
