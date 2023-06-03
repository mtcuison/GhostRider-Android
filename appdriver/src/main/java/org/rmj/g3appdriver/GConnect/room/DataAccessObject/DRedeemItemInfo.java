package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.GConnect.room.Entities.ERedeemItemInfo;

import java.util.List;

@Dao
public interface DRedeemItemInfo {

    @Insert()
    void insert(ERedeemItemInfo redeemItemInfo);

    @Insert()
    void insertBulkData(List<ERedeemItemInfo> redeemItemInfoList);

    @Update
    void update(ERedeemItemInfo redeemItemInfo);

    @Query("SELECT COUNT(*) FROM Redeem_Item")
    int GetRowsCountForID();

    @Query("SELECT sGCardNox FROM Gcard_App_Master WHERE cActvStat = '1'")
    String getCardNox();

    @Query("SELECT sCardNmbr FROM Gcard_App_Master WHERE cActvStat = '1'")
    String GetGCardNumber();

    @Query("SELECT sUserIDxx FROM Client_Profile_Info")
    String GetUserID();

    @Query("SELECT (SELECT sAvlPoint FROM GCard_App_Master WHERE cActvStat = '1') - " +
            "(SELECT nPointsxx FROM Redeem_Item WHERE sGCardNox = " +
            "(SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1')) AS RemainingPoints")
    double GetRemainingGcardPoints();

    @Query("SELECT sAvlPoint FROM Gcard_App_Master WHERE sCardNmbr =:CardNmbr")
    double getGCardTotPoints(String CardNmbr);

    @Query("SELECT SUM(nPointsxx) FROM redeem_item WHERE sGCardNox =:GCardNox AND cTranStat IN ('0', '1')")
    double getOrderPoints(String GCardNox);

    @Query("SELECT * FROM Redeem_Item WHERE sGCardNox = (SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1')")
    LiveData<List<ERedeemItemInfo>> getCartItems();

    @Query("SELECT COUNT(*) FROM Redeem_Item WHERE sGCardNox =:GCardNox AND cTranStat = '0' AND cPlcOrder = '0'")
    LiveData<Integer> getCartItemCount(String GCardNox);

    @Query("SELECT COUNT(*) FROM Redeem_Item WHERE sGCardNox =:GCardNox AND cTranStat = '1' AND cPlcOrder = '1'")
    LiveData<Integer> getCartOrderCount(String GCardNox);

    @Query("UPDATE Redeem_Item SET nItemQtyx = :fnNewCnt, nPointsxx = :fnNewPts WHERE sPromoIDx = :fsPromoId AND sGCardNox = :fsGcardNo")
    void updateItemDetails(String fsGcardNo, String fsPromoId, int fnNewCnt, double fnNewPts);

    @Query("SELECT nItemQtyx AS quantity, nPointsxx AS points FROM Redeem_Item WHERE sPromoIDx = :fsPromoId")
    LiveData<List<ItemDetail>> getExistingItemDetail(String fsPromoId);

    @Query("SELECT C.sPromoIDx, " +
            "C.sBatchNox, " +
            "C.sTransNox, " +
            "C.sGCardNox, " +
            "C.nItemQtyx, " +
            "C.nPointsxx, " +
            "C.sBranchCd, " +
            "C.sReferNox, " +
            "C.dOrderedx, " +
            "C.dPlacOrdr, " +
            "C.dPickupxx, " +
            "C.cTranStat, " +
            "C.cPlcOrder, " +
            "C.cNotified, " +
            "R.sPromoDsc, " +
            "R.nPointsxx AS origPoints, " +
            "R.sImageUrl, " +
            "G.sAvlPoint " +
            "FROM redeem_item as C " +
            "LEFT JOIN redeemables as R " +
            "ON C.sPromoIDx = R.sTransNox " +
            "LEFT JOIN gcard_app_master as G " +
            "ON C.sGCardNox = G.sGCardNox " +
            "WHERE C.cTranStat = '0' " +
            "AND C.sGCardNox = :fsGcardNo")
    LiveData<List<CartItemsDetail>> getCartItemsDetail(String fsGcardNo);

    @Query("SELECT SUM(nPointsxx) FROM redeem_item WHERE cTranStat = '0' " +
            "AND sGCardNox = :fsGcardNo")
    LiveData<Double> getTotalCartPoints(String fsGcardNo);

    @Query("DELETE FROM redeem_item WHERE sTransNox = :fsPromoId")
    void removeItemFromCart(String fsPromoId);

    @Query("UPDATE Redeem_Item SET sBranchCd = :fsBranch, cTranStat = '1', cPlcOrder = '1' WHERE sGCardNox = :fsGcardNo")
    void placeOrder(String fsGcardNo ,String fsBranch);

    @Query("SELECT * FROM Redeem_Item WHERE sTransNox =:TransNox AND sPromoIDx=:PromoIDx")
    List<ERedeemItemInfo> getRedeemableIfExist(String TransNox, String PromoIDx);

    @Query("UPDATE Redeem_Item SET " +
            "nItemQtyx =:ItemQty " +
            "WHERE sTransNox=:TransNox AND sTransNox =:PromoIDx")
    void UpdateExistingItemOnCart(String TransNox, String PromoIDx, int ItemQty);

    @Query("SELECT a.sTransNox, " +
            "b.sPromoDsc, " +
            "a.nPointsxx," +
            "a.nItemQtyx, " +
            "b.sImageUrl " +
            "FROM Redeem_Item a " +
            "LEFT JOIN Redeemables b " +
            "ON a.sPromoIDx = b.sPromoCde " +
            "WHERE a.sGCardNox = (SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1')")
    LiveData<List<GCardCartItem>> GetGCardCartItemList();

    @Query("SELECT * FROM BranchInfo WHERE sBranchCd LIKE '%M%'")
    List<EBranchInfo> GetMCBranchesForRedemption();

    @Query("SELECT COUNT(*) FROM Redeem_Item WHERE sGCardNox = (SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1')")
    LiveData<Integer> GetGcardCartItemCount();

    @Query("SELECT SUM(nPointsxx * nItemQtyx) FROM Redeem_Item WHERE sGCardNox = (SELECT sGCardNox FROM GCard_App_Master WHERE cActvStat = '1')")
    LiveData<Double> GetGCardCartItenTotalPoints();

    class GCardCartItem{
        public String sTransNox;
        public String sPromoDsc;
        public String nPointsxx;
        public String nItemQtyx;
        public String sImageUrl;
    }

    class ItemDetail {
        public int quantity;
        public double points;
    }

    class CartItemsDetail {
        public String sPromoIDx;
        public String sBatchNox;
        public String sTransNox;
        public String sGCardNox;
        public int nItemQtyx;
        public double nPointsxx;
        public String sBranchCd;
        public String sReferNox;
        public String dOrderedx;
        public String dPlacOrdr;
        public String dPickupxx;
        public String cTranStat;
        public String cPlcOrder;
        public String cNotified;
        public String sPromoDsc;
        public String cForCheck;
        public double origPoints;
        public String sImageUrl;
        public String sAvlPoint;
    }
}
