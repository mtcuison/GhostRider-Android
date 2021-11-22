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
            "WHERE sTransNox =:TransNox")
    LiveData<List<EInventoryDetail>> getInventoryDetailForBranch(String TransNox);

    @Query("SELECT * FROM Inventory_Count_Detail WHERE sTransNox=:TransNox AND sPartsIDx=:PartID AND sBarrCode=:BarCode")
    LiveData<EInventoryDetail> getInventoryItemDetail(String TransNox, String PartID, String BarCode);

    @Query("SELECT (SELECT COUNT(*) FROM Inventory_Count_Detail " +
            "WHERE sTransNox =:TransNox AND sRemarksx <> '' AND nActCtr01 <> '0')" +
            " || '/' || " +
            "(SELECT COUNT(*) FROM Inventory_Count_Detail " +
            "WHERE sTransNox =:TransNox) AS Current_Inventory")
    LiveData<String> getInventoryCountForBranch(String TransNox);

    @Query("UPDATE Inventory_Count_Detail SET nActCtr01 =:ActualQty, sRemarksx=:Remarks " +
            "WHERE sTransNox=:TransNox AND " +
            "sBarrCode=:BarCode AND " +
            "sPartsIDx=:PartID")
    void UpdateInventoryItem(String TransNox, String BarCode, String PartID,String ActualQty, String Remarks);
}
