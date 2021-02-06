package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface DRawDao {

    @Query("SELECT  " +
            "a.sModelIDx AS ModelIDx, " +
            "a.sModelNme AS ModelNme, " +
            "b.nMinDownx AS MinDownx, " +
            "c.nRebatesx AS Rebatesx, " +
            "c.nMiscChrg AS MiscChrg, " +
            "c.nEndMrtgg AS EndMrtgg, " +
            "b.nSelPrice AS SelPrice, " +
            "b.nLastPrce AS LastPrce " +
            "FROM Mc_Model a, Mc_Model_Price b, MC_Category c " +
            "WHERE a.sModelIDx = b.sModelIDx " +
            "AND b.cRecdStat = '1' " +
            "AND b.sMCCatIDx = c.sMCCatIDx " +
            "AND (a.sModelIDx = :ModelID)")
    LiveData<McDPInfo> getDownpayment(String ModelID);

    class McDPInfo{
        public String ModelIDx;
        public String ModelNme;
        public String Rebatesx;
        public String MiscChrg;
        public String EndMrtgg;
        public String MinDownx;
        public String SelPrice;
        public String LastPrce;
    }

    @Query("SELECT  " +
            "a.nSelPrice AS SelPrice, " +
            "a.nMinDownx AS MinDownx, " +
            "b.nMiscChrg AS MiscChrg, " +
            "b.nRebatesx AS Rebatesx, " +
            "b.nEndMrtgg AS EndMrtgg, " +
            "c.nAcctThru AS AcctThru, " +
            "c.nFactorRt AS FactorRt " +
            "FROM Mc_Model_Price a, MC_Category b, MC_Term_Category c " +
            "WHERE a.sMCCatIDx = b.sMCCatIDx " +
            "AND a.sMCCatIDx = c.sMCCatIDx " +
            "AND a.sModelIDx = :ModelID " +
            "AND c.nAcctThru = :Term")
    LiveData<McAmortInfo> getMonthlyAmort(String ModelID, int Term);

    class McAmortInfo{
        public String SelPrice;
        public String MinDownx;
        public String MiscChrg;
        public String Rebatesx;
        public String EndMrtgg;
        public String AcctThru;
        public String FactorRt;
    }
}
