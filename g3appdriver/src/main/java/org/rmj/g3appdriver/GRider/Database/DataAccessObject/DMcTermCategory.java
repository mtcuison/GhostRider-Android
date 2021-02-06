package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EMcTermCategory;

import java.util.List;

@Dao
public interface DMcTermCategory {

    @Insert
    void insert(EMcTermCategory mcTermCategory);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EMcTermCategory> categories);

    @Update
    void update(EMcTermCategory mcTermCategory);

    @Delete
    void delete(EMcTermCategory mcTermCategory);

    @Query("SELECT * FROM MC_Term_Category")
    LiveData<List<EMcTermCategory>> getAllMcTermCategory();
}
