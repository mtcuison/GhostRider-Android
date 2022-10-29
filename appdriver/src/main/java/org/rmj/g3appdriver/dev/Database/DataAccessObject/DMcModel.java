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

package org.rmj.g3appdriver.dev.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.dev.Database.Entities.EMcModel;

import java.util.List;

@Dao
public interface DMcModel {

    @Insert
    void insert(EMcModel mcModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EMcModel> models);

    @Update
    void update(EMcModel mcModel);

    @Query("SELECT * FROM MC_Model WHERE sModelIDx =:fsVal")
    EMcModel GetMCModel(String fsVal);

    @Query("SELECT * FROM MC_Model ORDER BY dTimeStmp DESC LIMIT 1")
    EMcModel GetLatestMCModel();

    @Query("SELECT * FROM Mc_Model WHERE sBrandIDx = :BrandID")
    LiveData<List<EMcModel>> getAllModeFromBrand(String BrandID);

    @Query("SELECT (sModelNme || \" \" || sModelCde) AS ModelInfo FROM Mc_Model WHERE sBrandIDx = :BrandID")
    LiveData<String[]> getAllModelName(String BrandID);


    @Query("SELECT sModelNme FROM Mc_Model WHERE sModelIDx = :ModelIDx")
    String getModelName(String ModelIDx);

    @Query("SELECT MAX(dTimeStmp) FROM Mc_Model")
    String getLatestDataTime();

    @Query("SELECT * FROM Mc_Model WHERE sModelIDx =:ModelID")
    EMcModel getModelInfo(String ModelID);

    @Query("SELECT  " +
            "a.nSelPrice, " +
            "a.nMinDownx, " +
            "b.nMiscChrg, " +
            "b.nRebatesx, " +
            "b.nEndMrtgg, " +
            "c.nAcctThru, " +
            "c.nFactorRt " +
            "FROM Mc_Model_Price a, MC_Category b, MC_Term_Category c " +
            "WHERE a.sMCCatIDx = b.sMCCatIDx " +
            "AND a.sMCCatIDx = c.sMCCatIDx " +
            "AND a.sModelIDx = :ModelID " +
            "AND c.nAcctThru = :Term")
    LiveData<McAmortInfo> GetMonthlyPayment(String ModelID, int Term);

    class McAmortInfo{
        public String nSelPrice;
        public String nMinDownx;
        public String nMiscChrg;
        public String nRebatesx;
        public String nEndMrtgg;
        public String nAcctThru;
        public String nFactorRt;
    }
}
