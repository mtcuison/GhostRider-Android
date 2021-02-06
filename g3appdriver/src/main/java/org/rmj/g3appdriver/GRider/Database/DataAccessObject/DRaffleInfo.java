package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ERaffleBasis;
import org.rmj.g3appdriver.GRider.Database.Entities.ERaffleInfo;

import java.util.List;

@Dao
public interface DRaffleInfo {

    @Insert
    void insert(ERaffleInfo raffleEntry);

    @Update
    void update(ERaffleInfo raffleEntry);

    @Delete
    void delete(ERaffleInfo raffleEntry);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<ERaffleBasis> raffleEntries);

    @Query("SELECT * FROM FB_Raffle_Transaction_Basis " +
            "WHERE cRecdStat = '1' " +
            "AND sDivision = (SELECT cPromoDiv FROM Branch_Info " +
            "WHERE sBranchCd = (" +
            "SELECT sBranchCd FROM User_Info_Master)) " +
            "GROUP BY sReferNme " +
            "ORDER BY sReferNme")
    LiveData<List<ERaffleBasis>> getRaffleBasis();

    @Query("SELECT sReferNme FROM FB_Raffle_Transaction_Basis " +
            "WHERE cRecdStat = '1' " +
            "AND sDivision = (" +
            "SELECT cPromoDiv FROM Branch_Info " +
            "WHERE sBranchCd = (" +
            "SELECT sBranchCd FROM User_Info_Master))" +
            "GROUP BY sReferNme " +
            "ORDER BY sReferNme")
    LiveData<String[]> getRaffleBasisDesc();

    @Query("SELECT * FROM PromoLocal_Detail")
    LiveData<ERaffleInfo> getRaffleInfo();
}
