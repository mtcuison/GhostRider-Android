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

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EMcModel;

import java.util.List;

@Dao
public interface DMcModel {

    @Insert
    void insert(EMcModel mcModel);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EMcModel> models);

    @Update
    void update(EMcModel mcModel);

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
}
