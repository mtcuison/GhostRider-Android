package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EItemCart;

import java.util.List;

@Dao
public interface DItemCart {

    @Insert
    void SaveItemInfo(EItemCart foVal);

    @Update
    void UpdateItemInfo(EItemCart foVal);

    @Query("SELECT * FROM MarketPlace_Cart WHERE sListIDxx =:ListngID")
    EItemCart GetItemCart(String ListngID);

    @Query("SELECT COUNT(*) FROM MarketPlace_Cart WHERE sUserIDxx = (SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<Integer> GetMartketplaceCartItemCount();

    @Query("SELECT ((SELECT COUNT(*) FROM MarketPlace_Cart) + (SELECT COUNT(*) FROM Redeem_Item WHERE sBatchNox IS NULL)) AS CartItemCount")
    LiveData<Integer> GetCartItemCount();

    @Query("SELECT COUNT(*) FROM MarketPlace_Cart")
    int CheckIfCartHasRecord();

    @Query("SELECT dTimeStmp FROM MarketPlace_Cart ORDER BY dTimeStmp DESC LIMIT 1")
    String GetLatestCartTimeStamp();

    @Query("SELECT * FROM MarketPlace_Cart WHERE cBuyNowxx = '1' AND cCheckOut = '1'")
    LiveData<List<oMarketplaceCartItem>> CheckCartIfHasForPlaceOrder();

    @Query("SELECT COUNT(*) FROM MarketPlace_Cart " +
            "WHERE sListIDxx =:fsListID " +
            "AND sUserIDxx = (SELECT sUserIDxx FROM Client_Profile_Info) " +
            "AND cBuyNowxx = '1' " +
            "AND cCheckOut = '1'")
    int CheckIfItemForBuyNowExist(String fsListID);

    @Query("SELECT * FROM MarketPlace_Cart WHERE sListIDxx=:fsListID")
    EItemCart CheckIFItemExist(String fsListID);

    @Query("UPDATE MarketPlace_Cart SET nQuantity = :fnQty WHERE sListIDxx =:fsListID")
    void UpdateItem(String fsListID, int fnQty);

    @Query("DELETE FROM MarketPlace_Cart WHERE sListIDxx=:fsListID")
    void DeleteCartItem(String fsListID);

    @Query("DELETE FROM MarketPlace_Cart WHERE cBuyNowxx = '1' AND cCheckOut = '1'")
    void CancelBuyNowItem();

    @Query("UPDATE MarketPlace_Cart SET cCheckOut = '1' WHERE sListIDxx =:fsListID")
    void UpdateForCheckOut(String fsListID);

    @Query("UPDATE MarketPlace_Cart SET cCheckOut = '1'")
    void UpdateSelectAllCheckOut();

    @Query("UPDATE MarketPlace_Cart SET cCheckOut = '0'")
    void UpdateUnselectAllCheckOut();

    @Query("SELECT * FROM MarketPlace_Cart")
    List<EItemCart> GetAllItemCart();

    @Query("UPDATE MarketPlace_Cart SET cCheckOut = '0' WHERE sListIDxx =:fsListID")
    void RemoveForCheckOut(String fsListID);

    @Query("SELECT COUNT(*) FROM MarketPlace_Cart WHERE cCheckOut ='1'")
    int CheckCartItemsForOrder();

    @Query("SELECT IFNULL(SUM(b.nUnitPrce * a.nQuantity), 0) " +
            "AS CART_TOTAL " +
            "FROM MarketPlace_Cart a " +
            "LEFT JOIN Product_Inventory b " +
            "ON a.sListIDxx = b.sListngID  " +
            "WHERE a.sUserIDxx = (" +
            "SELECT sUserIDxx FROM Client_Profile_Info) " +
            "AND cCheckOut = '1'")
    LiveData<Double> GetSelectedItemCartTotalPrice();

    @Query("UPDATE Product_Inventory " +
            "SET nTotalQty =:nTotalQty, " +
            "nQtyOnHnd =:nQtyOnHnd, " +
            "nResvOrdr =:nResvOrdr, " +
            "nSoldQtyx =:nSoldQtyx, " +
            "nUnitPrce =:nUnitPrce " +
            "WHERE sListngID=:fsLstID")
    void UpdateProdcutQuantity(String fsLstID, String nTotalQty, String nQtyOnHnd,
                               String nResvOrdr, String nSoldQtyx, String nUnitPrce);

    @Query("SELECT IFNULL(SUM(b.nUnitPrce * a.nQuantity), 0) " +
            "AS CART_TOTAL " +
            "FROM MarketPlace_Cart a " +
            "LEFT JOIN Product_Inventory b " +
            "ON a.sListIDxx = b.sListngID  " +
            "WHERE a.sUserIDxx = (" +
            "SELECT sUserIDxx FROM Client_Profile_Info) " +
            "AND cCheckOut = '1'")
    Double GetPlacedOrderTotalPrice();

    @Query("SELECT IFNULL(COUNT (a.sListIDxx), 0) FROM MARKETPLACE_CART a " +
            "LEFT JOIN Product_Inventory b " +
            "ON a.sListIDxx = b.sListngID " +
            "WHERE a.sUserIDxx = (" +
            "SELECT sUserIDxx FROM Client_Profile_Info) " +
            "AND a.cCheckOut = '1'")
    LiveData<Integer> GetSelectedItemCartTotalCount();

    @Query("SELECT a.sListIDxx AS sListIDxx, " +
            "a.nQuantity AS nQuantity, " +
            "a.cCheckOut AS cCheckOut, " +
            "b.xModelNme AS xModelNme, " +
            "b.xDescript AS xDescript," +
            "b.sImagesxx," +
            "b.nUnitPrce AS nUnitPrce " +
            "FROM MarketPlace_Cart a " +
            "LEFT JOIN Product_Inventory b " +
            "ON a.sListIDxx = b.sListngID " +
            "WHERE a.cBuyNowxx = '0' " +
            "AND a.sUserIDxx = (" +
            "SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<List<oMarketplaceCartItem>> GetCartItemsList();

    @Query("SELECT a.sListIDxx AS sListIDxx, " +
            "a.nQuantity AS nQuantity, " +
            "b.xModelNme AS xModelNme, " +
            "b.xDescript AS xDescript," +
            "b.sImagesxx," +
            "b.nUnitPrce AS nUnitPrce " +
            "FROM MarketPlace_Cart a " +
            "LEFT JOIN Product_Inventory b " +
            "ON a.sListIDxx = b.sListngID  " +
            "WHERE a.cBuyNowxx = '1' AND cCheckOut = '1' " +
            "AND a.sUserIDxx = (" +
            "SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<List<oMarketplaceCartItem>> GetBuyNowItem();

    @Query("SELECT a.sListIDxx AS sListIDxx, " +
            "a.nQuantity AS nQuantity, " +
            "a.cCheckOut AS cCheckOut, " +
            "b.xModelNme AS xModelNme, " +
            "b.xDescript AS xDescript," +
            "b.sImagesxx," +
            "b.nUnitPrce AS nUnitPrce " +
            "FROM MarketPlace_Cart a " +
            "LEFT JOIN Product_Inventory b " +
            "ON a.sListIDxx = b.sListngID  " +
            "WHERE a.cBuyNowxx = '0' AND cCheckOut = '1' " +
            "AND a.sUserIDxx = (" +
            "SELECT sUserIDxx FROM Client_Profile_Info)")
    LiveData<List<oMarketplaceCartItem>> GetItemsForCheckOut();

    class oMarketplaceCartItem{
        public String sListIDxx;
        public String nQuantity;
        public String xModelNme;
        public String xDescript;
        public String sImagesxx;
        public String nUnitPrce;
        public String cCheckOut;
    }
}
