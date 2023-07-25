package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.dev.Database.Entities.EPanaloReward;

@Dao
public interface DPanalo {

    @Insert
    void Insert(EPanaloReward args);

    @Update
    void Update(EPanaloReward args);

    @Query("DELETE FROM Panalo_Reward WHERE sPanaloCD =:args")
    void Delete(String args);
}
