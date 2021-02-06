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

    @Delete
    void delete(EMcModel mcModel);

    @Query("SELECT * FROM Mc_Model WHERE sBrandIDx = :BrandID")
    LiveData<List<EMcModel>> getAllModeFromBrand(String BrandID);

    @Query("SELECT (sModelNme || \" \" || sModelCde) AS ModelInfo FROM Mc_Model WHERE sBrandIDx = :BrandID")
    LiveData<String[]> getAllModelName(String BrandID);
}
