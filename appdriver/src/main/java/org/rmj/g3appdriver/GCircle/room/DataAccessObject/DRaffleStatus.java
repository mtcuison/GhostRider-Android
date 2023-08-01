package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.ERaffleStatus;

@Dao
public interface DRaffleStatus {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void CreateStatus(ERaffleStatus foVal);

    @Update
    void Update(ERaffleStatus foVal);

    @Query("SELECT * FROM Raffle_Status LIMIT 1")
    LiveData<ERaffleStatus> HasRaffle();

    @Query("SELECT * FROM Raffle_Status LIMIT 1")
    ERaffleStatus GetRaffleStatus();
}
