package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;

import java.util.List;

@Dao
public interface DInventoryDetail {

    @Insert
    void insertInventoryDetail(List<EInventoryDetail> foDetail);

    @Query("SELECT * FROM Inventory_Count_Detail " +
            "WHERE sTransNox = (SELECT sTransNox FROM Inventory_Count_Master WHERE sBranchCd =:BranchCd)")
    LiveData<List<EInventoryDetail>> getInventoryDetailForBranch(String BranchCd);
}
