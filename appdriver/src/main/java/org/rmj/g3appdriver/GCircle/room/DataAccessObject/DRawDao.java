/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import org.rmj.g3appdriver.GCircle.room.Entities.ETokenInfo;

@Dao
public interface DRawDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTokenInfo(ETokenInfo tokenInfo);

    @Query("DELETE FROM App_Token_Info")
    void clearTokenInfo();

    @Query("SELECT sTokenInf FROM App_Token_Info")
    String getTokenInfo();

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

    @Query("SELECT (SELECT COUNT(*) FROM Branch_Info) AS Branch_Data," +
            "(SELECT COUNT(*) FROM Barangay_Info) AS Barangay_Data," +
            "(SELECT COUNT(*) FROM Town_Info) AS Town_Data," +
            "(SELECT COUNT(*) FROM Province_Info) AS Province_Data," +
            "(SELECT COUNT(*) FROM Country_Info) AS Country_Data," +
            "(SELECT COUNT(*) FROM MC_Brand) AS Mc_Brand," +
            "(SELECT COUNT(*) FROM Mc_Model) AS Mc_Model," +
            "(SELECT COUNT(*) FROM MC_Category) AS Mc_Category," +
            "(SELECT COUNT(*) FROM Mc_Model_Price) AS Mc_Model_Price," +
            "(SELECT COUNT(*) FROM MC_Term_Category) AS Mc_Term_Category," +
            "(SELECT COUNT(*) FROM Occupation_Info) AS Occupation_Data," +
            "(SELECT COUNT(*) FROM FB_Raffle_Transaction_Basis) AS Raffle_Basis," +
            "(SELECT COUNT(*) FROM EDocSys_File) AS File_Code," +
            "(SELECT COUNT(*) FROM Bank_Info) AS Bank_Data," +
            "(SELECT COUNT(*) FROM Collection_Account_Remittance) AS Remittance_Data," +
            "(SELECT COUNT(*) FROM Relation) AS Relation_Data, " +
            "(SELECT COUNT(*) FROM XXXSCA_REQUEST) AS Approval_Code")
    LiveData<AppLocalData> getAppLocalData();

    class McAmortInfo{
        public String SelPrice;
        public String MinDownx;
        public String MiscChrg;
        public String Rebatesx;
        public String EndMrtgg;
        public String AcctThru;
        public String FactorRt;
    }

    class AppLocalData{
        public int Branch_Data;
        public int Barangay_Data;
        public int Town_Data;
        public int Province_Data;
        public int Country_Data;
        public int Mc_Brand;
        public int Mc_Model;
        public int Mc_Category;
        public int Mc_Model_Price;
        public int Mc_Term_Category;
        public int Occupation_Data;
        public int Raffle_Basis;
        public int File_Code;
        public int Bank_Data;
        public int Remittance_Data;
        public int Relation_Data;
        public int Approval_Code;
    }
}
