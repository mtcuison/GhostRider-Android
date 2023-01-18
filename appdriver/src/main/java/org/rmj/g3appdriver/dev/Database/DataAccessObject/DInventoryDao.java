package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.dev.Database.Entities.EInventoryMaster;

import java.util.List;

@Dao
public interface DInventoryDao {

    @Insert
    void SaveMaster(EInventoryMaster foVal);

    @Insert
    void SaveDetail(EInventoryDetail foVal);

    @Update
    void UpdateMaster(EInventoryMaster foVal);

    @Update
    void UpdateDetail(EInventoryDetail foVal);

    @Query("SELECT * FROM Inventory_Count_Master WHERE sTransNox=:TransNox")
    EInventoryMaster GetMaster(String TransNox);

    @Query("SELECT * FROM Inventory_Count_Master WHERE sBranchCd=:Branchd AND dTransact=:Transact")
    EInventoryMaster GetMasterIfExists(String Branchd, String Transact);

    @Query("SELECT * FROM Inventory_Count_Detail WHERE sTransNox =:TransNox AND nEntryNox =:EntryNox AND sBarrCode =:BarrCode")
    LiveData<EInventoryDetail> GetItemDetail(String TransNox, String EntryNox, String BarrCode);

    @Query("SELECT * FROM Inventory_Count_Master WHERE sBranchCd =:fsVal AND dTransact =:Transact")
    LiveData<EInventoryMaster> GetInventoryMaster(String fsVal, String Transact);

    @Query("SELECT * FROM Inventory_Count_Detail WHERE sTransNox =:TransNox")
    LiveData<List<EInventoryDetail>> GetInventoryItems(String TransNox);

    @Query("SELECT * FROM Inventory_Count_Detail WHERE sTransNox =:TransNox AND nEntryNox =:EntryNox AND sBarrCode =:BarrCode")
    EInventoryDetail GetDetail(String TransNox, String EntryNox, String BarrCode);

    @Query("SELECT * FROM Inventory_Count_Detail WHERE sTransNox =:fsVal")
    List<EInventoryDetail> GetInventoryDetails(String fsVal);

    @Query("UPDATE Inventory_Count_Detail SET cTranStat = '2' WHERE sTransNox =:TransNox")
    void UpdateUploadedDetails(String TransNox);

    @Query("UPDATE Inventory_Count_Master SET cTranStat = '2' WHERE sTransNox =:TransNox")
    void UpdateUploadedMaster(String TransNox);

    @Query("SELECT * FROM Inventory_Count_Master WHERE cTranStat != '2'")
    List<EInventoryMaster> GetMasterInventoryForUpload();

//    @Query("SELECT b.* FROM Employee_Log_Selfie a " +
//            "LEFT JOIN Branch_Info b " +
//            "ON a.sBranchCd = b.sBranchCd " +
//            "LEFT JOIN Inventory_Count_Master c ON b.sBranchCd = c.sBranchCd " +
//            "WHERE c.sBranchCd IS NULL OR c.cTranStat = '0'")
    @Query("SELECT a.* FROM Branch_Info a " +
            "LEFT JOIN Inventory_Count_Master b " +
            "ON a.sBranchCd = b.sBranchCd " +
            "WHERE b.dTransact=:args " +
            "AND b.sBranchCd NOT IN (SELECT sBranchCd FROM Inventory_Count_Master WHERE dTransact=:args)")
    List<EBranchInfo> GetBranchesForInventory(String args);

    @Query("SELECT * FROM Branch_Info WHERE sBranchCd =:args")
    LiveData<EBranchInfo> GetBranchInfo(String args);

    @Query("SELECT COUNT(*) FROM Employee_Log_Selfie WHERE dTransact =:args")
    int CheckIfHasSelfieLog(String args);
}
