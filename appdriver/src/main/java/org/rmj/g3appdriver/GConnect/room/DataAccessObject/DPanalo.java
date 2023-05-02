package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EGuanzonPanalo;
import org.rmj.g3appdriver.GConnect.room.Entities.EPanaloReward;

@Dao
public interface DPanalo {

    @Insert
    void Save(EPanaloReward foVal);

    @Insert
    void Save(EGuanzonPanalo foVal);

    @Update
    void Update(EPanaloReward foVal);

    @Update
    void Update(EGuanzonPanalo foVal);

    @Query("SELECT * FROM Guanzon_Panalo")
    EGuanzonPanalo GetPanaloRedeem();

    @Query("SELECT * FROM Panalo_Reward")
    LiveData<EPanaloReward> GetPanaloRewardNotice();
}
