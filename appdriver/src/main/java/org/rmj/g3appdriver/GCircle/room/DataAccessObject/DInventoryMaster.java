package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GCircle.room.Entities.EInventoryMaster;

import java.util.List;

@Dao
public interface DInventoryMaster {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertInventoryMaster(EInventoryMaster foMaster);

    @Query("SELECT * FROM Inventory_Count_Master WHERE dTransact =:Transact AND sBranchCd =:BranchCd")
    LiveData<EInventoryMaster> getInventoryMasterForBranch(String Transact, String BranchCd);

    @Query("SELECT * FROM Inventory_Count_Master WHERE sTransNox=:TransNox")
    EInventoryMaster getInventoryMasterForPosting(String TransNox);

    @Query("UPDATE Inventory_Count_Master SET sRemarksx =:Remarks WHERE sTransNox=:TransNox")
    void UpdateInventoryMasterRemarks(String TransNox, String Remarks);

    @Query("UPDATE Inventory_Count_Master SET cTranStat = '2' WHERE sTransNox=:TransNox")
    void UpdateInventoryMasterPostedStatus(String TransNox);

    @Query("SELECT b.* FROM Employee_Log_Selfie a " +
            "LEFT JOIN Branch_Info b " +
            "ON a.sBranchCd = b.sBranchCd " +
            "LEFT JOIN Inventory_Count_Master c ON b.sBranchCd = c.sBranchCd " +
            "WHERE c.sBranchCd IS NULL OR c.cTranStat = '0'")
    List<EBranchInfo> GetBranchesForInventory();
}
