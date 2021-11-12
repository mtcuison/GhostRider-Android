package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;

import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryMaster;

@Dao
public interface DInventoryMaster {

    @Insert
    void insertInventoryMaster(EInventoryMaster foMaster);
}
