package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.EMcModelPrice;

import java.util.List;

@Dao
public interface DMcModelPrice {

    @Insert
    void insert(EMcModelPrice mcModelPrice);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkdData(List<EMcModelPrice> mcModelPrices);

    @Update
    void update(EMcModelPrice mcModelPrice);

    @Delete
    void delete(EMcModelPrice mcModelPrice);

    @Query("SELECT * FROM Mc_Model_Price WHERE sModelIDx = :BrandID")
    LiveData<List<EMcModelPrice>> getAllModelPrice(String BrandID);

    @Query("SELECT MAX(dTimeStmp) FROM Mc_Model_Price")
    String getLatestDataTime();
}
