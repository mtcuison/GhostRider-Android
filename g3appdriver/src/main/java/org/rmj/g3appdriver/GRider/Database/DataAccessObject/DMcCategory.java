package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EMcCategory;

import java.util.List;

@Dao
public interface DMcCategory {

    @Insert
    void insert(EMcCategory mcCategory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insetBulkData(List<EMcCategory> categories);

    @Update
    void update(EMcCategory mcCategory);

    @Delete
    void delete(EMcCategory mcCategory);

    @Query("SELECT * FROM MC_Category")
    LiveData<List<EMcCategory>> getAllMcCategory();
}
