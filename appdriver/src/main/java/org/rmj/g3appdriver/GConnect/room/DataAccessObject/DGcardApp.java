package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EGcardApp;

import java.util.List;

@Dao
public interface DGcardApp {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void Save(EGcardApp gCardApp);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EGcardApp> gCardAppList);

    @Update
    void update(EGcardApp gCardApp);

    @Query("UPDATE Gcard_App_Master SET cActvStat = '0'")
    void DeactivateGCard();

    @Query("UPDATE Gcard_App_Master SET cActvStat = '1' WHERE sCardNmbr =:GCardNmbr")
    void SetActiveGCard(String GCardNmbr);

    @Query("UPDATE Gcard_App_Master " +
            "SET cActvStat = '1' " +
            "WHERE sCardNmbr = " +
            "(SELECT sCardNmbr FROM Gcard_App_Master WHERE sTotPoint IN (SELECT MAX(sTotPoint) FROM Gcard_App_Master))")
    void InitDefaultActiveGCard();

    @Query("SELECT sCardNmbr FROM Gcard_App_Master WHERE cActvStat = '1'")
    String GetGCardNumber();

    @Query("SELECT sGCardNox FROM Gcard_App_Master WHERE cActvStat = '1'")
    String GetCardNox();

    @Query("SELECT * FROM GCard_App_Master WHERE cActvStat = '1'")
    LiveData<EGcardApp> GetActiveGCcardInfo();

    @Query("SELECT * FROM GCard_App_Master")
    LiveData<List<EGcardApp>> GetAllGCardInfo();

    @Query("SELECT * FROM GCard_App_Master")
    List<EGcardApp> getAllGCard();

    @Query("SELECT sAvlPoint FROM Gcard_App_Master WHERE sCardNmbr =:CardNmbr")
    double getGCardTotPoints(String CardNmbr);

    @Query("SELECT SUM(nPointsxx) FROM redeem_item WHERE sGCardNox =:GCardNox AND cTranStat IN ('0', '1')")
    double getOrderPoints(String GCardNox);

    @Query("UPDATE GCard_App_Master SET sAvlPoint = :fsNewPts WHERE sGCardNox = :fsGcardNo")
    void updateAvailablePoints(String fsGcardNo, String fsNewPts);

    @Query("SELECT sGCardNox FROM Gcard_App_Master WHERE cActvStat = '1'")
    LiveData<String> getActiveGcardNo();

    @Query("SELECT sAvlPoint FROM GCard_App_Master WHERE cActvStat ='1'")
    LiveData<String> getActiveGcardAvlPoints();

    @Query("SELECT (SELECT sAvlPoint FROM GCard_App_Master WHERE cActvStat = '1') - " +
            "(SELECT nPointsxx FROM Redeem_Item WHERE sGCardNox = " +
            "(SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1')) AS RemainingPoints")
    double getRemainingActiveCardPoints();

    @Query("SELECT sAvlPoint FROM GCard_App_Master WHERE cActvStat = '1'")
    double getAvailableGcardPoints();

    @Query("SELECT nPointsxx FROM Redeem_Item WHERE sGCardNox = " +
            "(SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1') AND cTranStat IN (0, 1)")
    double getRedeemItemPoints();
}
