package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;

import java.util.List;

@Dao
public interface DTownInfo {

    @Insert
    void insert(ETownInfo townInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<ETownInfo> townInfoList);

    @Update
    void update(ETownInfo townInfo);

    @Delete
    void delete(ETownInfo townInfo);

    @Query("SELECT * FROM Town_Info")
    LiveData<List<ETownInfo>> getAllTownInfo();

    @Query("DELETE FROM Town_Info")
    void deleteAllTownInfo();

    @Query("SELECT * FROM Town_Info WHERE sProvIDxx = :ProvinceID")
    LiveData<List<ETownInfo>> getAllTownInfoFromProvince(String ProvinceID);

    @Query("SELECT sTownName FROM Town_Info WHERE sProvIDxx = :ProvinceID")
    LiveData<String[]> getTownNamesFromProvince(String ProvinceID);

    @Query("SELECT * FROM Town_Info WHERE sTownIDxx = :townID")
    LiveData<ETownInfo> getTownNameAndProvID(String townID);
}
