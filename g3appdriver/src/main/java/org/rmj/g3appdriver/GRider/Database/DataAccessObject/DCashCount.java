/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 4:27 PM
 * project file last modified : 6/9/21 4:27 PM
 */

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECashCount;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;

import java.util.List;

@Dao
public interface DCashCount {
    @Insert
    void insertCashCount(ECashCount cashCount);

    @Update
    void updateCashCount(ECashCount cashCount);

//    @Update
//    void update(ECashCount cashCount);

    @Delete
    void delete(ECashCount cashCount);

    @Query("SELECT * FROM Cash_Count_Master WHERE sTransNox =:TransNox")
    List<ECashCount> getDuplicateTransNox(String TransNox);

    @Query("SELECT * FROM Cash_Count_Master")
    LiveData<List<ECashCount>> getAllCashCountLog();

    @Query("UPDATE Cash_Count_Master SET " +
            "sSendStat = 1 " +
            "WHERE sTransNox =:TransNox ")
    void UpdateByTransNox(String TransNox);

}
