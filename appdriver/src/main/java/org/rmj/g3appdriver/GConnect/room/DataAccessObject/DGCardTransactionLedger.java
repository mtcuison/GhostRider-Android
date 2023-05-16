package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EGCardLedger;

import java.util.List;

@Dao
public interface DGCardTransactionLedger {
    
    @Insert
    void Save(EGCardLedger egCardTransactionLedger);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EGCardLedger> egCardTransactionLedgerList);

    @Update
    void update(EGCardLedger egCardTransactionLedger);

    @Query("DELETE FROM G_Card_Transaction_Ledger")
    void deleteGCardTrans();

    @Query("SELECT sGCardNox FROM Gcard_App_Master WHERE cActvStat = '1'")
    String getCardNox();

    @Query("SELECT * FROM G_Card_Transaction_Ledger " +
            "WHERE sGCardNox = (SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1') " +
            "AND sSourceDs = 'REDEMPTION'" +
            "OR sSourceDs = 'PREORDER'")
    LiveData<List<EGCardLedger>> GetRedemptionTransactionsList();

    @Query("SELECT * FROM G_Card_Transaction_Ledger " +
            "WHERE sGCardNox = (SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1') " +
            "ORDER BY dTransact DESC")
    LiveData<List<EGCardLedger>> GetAllTransactionsList();

    @Query("SELECT * FROM G_Card_Transaction_Ledger " +
            "WHERE sGCardNox = (SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1') " +
            "AND sSourceDs = 'ONLINE' " +
            "OR sSourceDs = 'OFFLINE'")
    LiveData<List<EGCardLedger>> GetPointsEntryTransactionsList();

    @Query("SELECT * FROM G_Card_Transaction_Ledger WHERE "  +
                "sGCardNox =:gcardNo AND " +
                "sSourceDs =:srcedsc AND " +
                "sReferNox =:referno AND " +
                "sTranDesc =:trandsc AND " +
                "sSourceNo =:srcenox AND " +
                "nPointsxx =:pointsx ")
    EGCardLedger isTransactionValid(String gcardNo, String srcedsc, String referno, String trandsc, String srcenox, String pointsx);
}
