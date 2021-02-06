package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EMcBrand;

import java.util.List;

@Dao
public interface DMcBrand {

    @Insert
    void insert(EMcBrand mcBrand);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EMcBrand> brands);

    @Update
    void update(EMcBrand mcBrand);

    @Delete
    void delete(EMcBrand mcBrand);

    @Query("SELECT * FROM MC_Brand")
    LiveData<List<EMcBrand>> getAllMcBrand();

    @Query("SELECT sBrandNme FROM MC_Brand")
    LiveData<String[]> getAllBrandName();
}
