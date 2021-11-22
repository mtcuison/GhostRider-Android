package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryMaster;

import java.util.List;

@Dao
public interface DInventoryMaster {

    @Insert
    void insertInventoryMaster(EInventoryMaster foMaster);

    @Query("SELECT * FROM Inventory_Count_Master WHERE dTransact =:Transact AND sBranchCd =:BranchCd")
    LiveData<EInventoryMaster> getInventoryMasterForBranch(String Transact, String BranchCd);
}
