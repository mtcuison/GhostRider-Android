package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GConnect.room.Entities.EProducts;

import java.util.List;

@Dao
public interface DProduct {

    @Insert
    void SaveProductInfo(EProducts foValue);

    @Update
    void UpdateProductInfo(EProducts foVal);

    @Query("SELECT * FROM Product_Inventory LIMIT 10 OFFSET:nIndex")
    LiveData<List<EProducts>> GetProductList(int nIndex);

    @Query("SELECT * FROM Product_Inventory WHERE sListngID=:fsLstID")
    LiveData<EProducts> GetProductInfo(String fsLstID);

    @Query("SELECT dTimeStmp FROM Product_Inventory ORDER BY dTimeStmp DESC LIMIT 1")
    String GetLatestProductStamp();

    @Query("SELECT * FROM Product_Inventory WHERE sListngID =:fsVal")
    EProducts GetProductIfExist(String fsVal);

    @Query("UPDATE Product_Inventory " +
            "SET nTotalQty =:nTotalQty, " +
                "nQtyOnHnd =:nQtyOnHnd, " +
                "nResvOrdr =:nResvOrdr, " +
                "nSoldQtyx =:nSoldQtyx, " +
                "nUnitPrce =:nUnitPrce " +
            "WHERE sListngID=:fsLstID")
    void UpdateProductQtyInfo(String fsLstID, String nTotalQty, String nQtyOnHnd,
                            String nResvOrdr, String nSoldQtyx, String nUnitPrce);

    @Query("UPDATE Product_Inventory SET nTotalQty =:TotalQty, " +
            "nQtyOnHnd =:QtyOnHnd, " +
            "nResvOrdr =:ResvOrdr, " +
            "nSoldQtyx =:SoldQtyx, " +
            "nUnitPrce =:UnitPrce, " +
            "dListStrt =:ListStrt, " +
            "dListEndx =:ListEndx, " +
            "cTranStat =:TranStat, " +
            "dTimeStmp =:TimeStmp " +
            "WHERE sListngID =:ListngID")
    void UpdateProductListing(String ListngID,
                              String TotalQty,
                              String QtyOnHnd,
                              String ResvOrdr,
                              String SoldQtyx,
                              String UnitPrce,
                              String ListStrt,
                              String ListEndx,
                              String TranStat,
                              String TimeStmp);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx, " +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1' " +
            "ORDER BY dListStrt DESC " +
            "LIMIT 10 OFFSET:fnIndex")
    LiveData<List<oProduct>> GetProductsList(int fnIndex);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx, " +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1' " +
            "ORDER BY nUnitPrce ASC " +
            "LIMIT 10 OFFSET:nIndex")
    LiveData<List<oProduct>> GetProductsListPriceSortASC(int nIndex);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx, " +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1'" +
            "ORDER BY nUnitPrce DESC " +
            "LIMIT 10 OFFSET:nIndex ")
    LiveData<List<oProduct>> GetProductsListPriceSortDESC(int nIndex);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx," +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE xCategrNm =:fsCategory " +
            "AND strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1'" +
            "LIMIT 10 OFFSET:nIndex")
    LiveData<List<oProduct>> GetProductsListFilterCategory(int nIndex, String fsCategory);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx," +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE xBrandNme LIKE '%' || :fsName || '%' " +
            "AND strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1'" +
            "LIMIT 10 OFFSET:nIndex")
    LiveData<List<oProduct>> GetProductsListFilterBrandName(int nIndex, String fsName);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx," +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE nUnitPrce BETWEEN :fnFrom AND :fnToxx " +
            "AND strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1'" +
            "ORDER BY nUnitPrce ASC " +
            "LIMIT 10 OFFSET:nIndex")
    LiveData<List<oProduct>> GetProductsListFilterPriceRange(int nIndex, String fnFrom, String fnToxx);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx," +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE xBrandNme LIKE '%' || :fsArgs || '%' " +
            "AND sListngID !=:fsArgs1 " +
            "AND strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1'" +
            "LIMIT 10")
    LiveData<List<oProduct>> GetProductListSameBrandSuggestions(String fsArgs, String fsArgs1);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx," +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE sProdctNm LIKE '%' || :fsVal || '%' " +
            "AND strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1'" +
            "ORDER BY nUnitPrce ASC")
    LiveData<List<oProduct>> SearchProducts(String fsVal);

    @Query("SELECT xBrandNme FROM Product_Inventory GROUP BY xBrandNme")
    LiveData<List<String>> GetBrandNames();

    @Query("SELECT nUnitPrce " +
            "FROM Product_Inventory " +
            "WHERE xBrandNme LIKE '%' || :fsArgs || '%' " +
            "AND strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1'" +
            "GROUP BY nUnitPrce")
    LiveData<List<String>> GetPriceFilterForBrand(String fsArgs);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx," +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold " +
            "FROM Product_Inventory " +
            "WHERE sProdctNm LIKE '%' || :fsArgs || '%' " +
            "AND xBrandNme LIKE '%' || :fsArgs1 || '%' " +
            "AND strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1'" +
            "ORDER BY nUnitPrce ASC")
    LiveData<List<oProduct>> GetProductsOnBrand(String fsArgs, String fsArgs1);

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx, " +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold, " +
            "xBrandNme, " +
            "sModelIDx " +
            "FROM Product_Inventory " +
            "WHERE strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND cAllwCrdt = '1' " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1' " +
            "ORDER BY dListStrt DESC")
    LiveData<List<oProduct>> GetProductsForLoanApplication();

    @Query("SELECT sListngID AS sProdctID, " +
            "xBrandNme|| ' ' ||xModelNme  AS sProdctNm, " +
            "nUnitPrce AS sPricexxx, " +
            "sImagesxx, " +
            "nSoldQtyx AS sUntsSold, " +
            "xBrandNme, " +
            "sModelIDx " +
            "FROM Product_Inventory " +
            "WHERE sProdctNm LIKE '%' || :fsVal || '%' " +
            "AND strftime('%Y-%m-%d %H:%H:%S', datetime('now', 'localtime'))  BETWEEN dListStrt AND dListEndx " +
            "AND cAllwCrdt = '1' " +
            "AND nQtyOnHnd > 0 " +
            "AND cTranStat = '1' " +
            "ORDER BY dListStrt DESC")
    LiveData<List<oProduct>> SearchLoanProducts(String fsVal);

    class oProduct{
        public String sProdctID;
        public String sProdctNm;
        public String sPricexxx;
        public String sUntsSold;
        public String sImagesxx;
        public String xBrandNme;
        public String sModelIDx;
    }
}
