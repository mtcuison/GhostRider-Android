package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EOrderDetail;

import java.util.List;

@Dao
public interface DOrderDetail {

    @Insert
    void SaveDetailOrder(EOrderDetail foVal);

    @Update
    void UpdateDetail(EOrderDetail foVal);

    @Query("SELECT * FROM MarketPlace_Order_Detail WHERE sTransNox =:fsVal AND nEntryNox=:fsVal1")
    EOrderDetail GetOrderDetail(String fsVal, String fsVal1);

    @Query("SELECT * FROM MarketPlace_Order_Detail WHERE sTransNox =:fsTransNo")
    LiveData<List<EOrderDetail>> GetOrderDetail(String fsTransNo);

    @Query("SELECT COUNT(sTransNox) FROM MarketPlace_Order_Detail")
    int CheckIfDetailHasRecord();

    @Query("SELECT dTimeStmp FROM MarketPlace_Order_Detail ORDER BY dTimeStmp DESC LIMIT 1")
    String getDetailLatestTimeStmp();

    @Query("SELECT * FROM MarketPlace_Order_Detail WHERE sTransNox =:fsTransNo")
    LiveData<List<EOrderDetail>> GetDetailInfo(String fsTransNo);

    @Query("UPDATE MarketPlace_Order_Detail SET cReviewed = '1' WHERE sTransNox =:OrderID AND sReferNox =:ListID")
    void UpdateReviewedItem(String OrderID, String ListID);

    @Query("SELECT a.nEntryNox, " +
            "a.nQuantity, " +
            "a.nUnitPrce, " +
            "a.nDiscount, " +
            "b.sBriefDsc, " +
            "b.xBarCodex, " +
            "b.xBrandNme, " +
            "b.xModelNme, " +
            "b.xColorNme, " +
            "b.xCategrNm, " +
            "c.cTranStat " +
            "FROM MarketPlace_Order_Detail a " +
            "LEFT JOIN Product_Inventory b " +
            "ON a.sStockIDx = b.sStockIDx " +
            "LEFT JOIN MarketPlace_Order_Master c " +
            "ON a.sTransNox = c.sTransNox " +
            "WHERE c.sTransNox =:fsVal " +
            "AND c.sAppUsrID = (" +
            "SELECT sUserIDxx " +
            "FROM Client_Profile_Info) " +
            "ORDER BY c.dTransact DESC")
    LiveData<List<OrderHistoryDetail>> GetOrderHistoryDetail(String fsVal);

    @Query("SELECT a.sListngID AS sListIDxx, " +
            "a.xModelNme, " +
            "a.nUnitPrce, " +
            "a.sStockIDx, " +
            "a.sImagesxx, " +
            "b.nQuantity, " +
            "b.cReviewed " +
            "FROM Product_Inventory a " +
            "LEFT JOIN MarketPlace_Order_Detail b " +
            "ON a.sListngID = b.sReferNox " +
            "WHERE b.sTransNox =:fsVal " +
            "AND a.cTranStat == '1'")
    LiveData<List<OrderedItemsInfo>> GetOrderedItems(String fsVal);

    class OrderHistoryDetail{
        public String nEntryNox;
        public String nQuantity;
        public String nUnitPrce;
        public String nDiscount;
        public String sBriefDsc;
        public String xBarCodex;
        public String xBrandNme;
        public String xModelNme;
        public String xColorNme;
        public String xCategrNm;
        public String cTranStat;
    }

    class OrderedItemsInfo{
        public String sListIDxx;
        public String sImagesxx;
        public String sStockIDx;
        public String xModelNme;
        public String nUnitPrce;
        public String nDiscount;
        public String nQuantity;
        public String cReviewed;
    }
}
