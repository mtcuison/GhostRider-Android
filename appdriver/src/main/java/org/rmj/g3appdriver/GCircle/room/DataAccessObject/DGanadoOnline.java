package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.EGanadoOnline;
import org.rmj.g3appdriver.GCircle.room.Entities.EMCColor;
import org.rmj.g3appdriver.GCircle.room.Entities.EMcBrand;
import org.rmj.g3appdriver.GCircle.room.Entities.EMcModel;

import java.util.List;

@Dao
public interface DGanadoOnline {

    @Insert
    void Save(EGanadoOnline foVal);

    @Update
    void Update(EGanadoOnline foVal);

    @Query("SELECT * FROM Ganado_Online WHERE sTransNox =:TransNox")
    EGanadoOnline GetInquiry(String TransNox);

    @Query("SELECT COUNT(sTransNox) FROM GANADO_ONLINE")
    int GetRowsCountForID();

    @Query("UPDATE Ganado_Online SET cSendStat = '1', sTransNox =:NewTransNo WHERE sTransNox=:TransNox")
    void UpdateSentInquiry(String TransNox, String NewTransNo);

    @Query("SELECT * FROM MC_Brand WHERE sBrandNme IN ('HONDA', 'SUZUKI', 'KAWASAKI', 'YAMAHA')")
    LiveData<List<EMcBrand>> getAllMcBrand();

    @Query("SELECT * FROM Mc_Model WHERE sBrandIDx = :BrandID")
    LiveData<List<EMcModel>> getAllModeFromBrand(String BrandID);

    @Query("SELECT * FROM MC_Model_Color WHERE sModelIDx =:ModelID")
    LiveData<List<EMCColor>> GetModelColors(String ModelID);

    @Query("SELECT  " +
            "a.nSelPrice, " +
            "a.nMinDownx, " +
            "b.nMiscChrg, " +
            "b.nRebatesx, " +
            "b.nEndMrtgg, " +
            "c.nAcctThru, " +
            "c.nFactorRt " +
            "FROM Mc_Model_Price a, MC_Category b, MC_Term_Category c, Mc_Model d " +
            "WHERE a.sMCCatIDx = b.sMCCatIDx " +
            "AND a.sMCCatIDx = c.sMCCatIDx " +
            "AND a.sModelIDx = d.sModelIDx " +
            "AND a.sModelIDx = :ModelID " +
            "AND c.nAcctThru = :Term")
    McAmortization GetMonthlyPayment(String ModelID, int Term);

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
    McDownpayment getDownpayment(String ModelID);

    class McDownpayment{
        public String ModelIDx;
        public String ModelNme;
        public String Rebatesx;
        public String MiscChrg;
        public String EndMrtgg;
        public String MinDownx;
        public String SelPrice;
        public String LastPrce;
    }

    class McAmortization{
        public String nSelPrice;
        public String nMinDownx;
        public String nMiscChrg;
        public String nRebatesx;
        public String nEndMrtgg;
        public String nAcctThru;
        public String nFactorRt;
    }
}
