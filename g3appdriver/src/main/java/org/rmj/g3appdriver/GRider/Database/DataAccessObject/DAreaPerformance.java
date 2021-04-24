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

import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;

import java.util.List;

@Dao
public interface DAreaPerformance {

    @Insert
    void insert(EAreaPerformance areaPerformance);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EAreaPerformance> areaPerformances);

    @Update
    void update(EAreaPerformance areaPerformance);

    @Delete
    void delete(EAreaPerformance areaPerformance);

    @Query("DELETE FROM MC_Area_Performance")
    void deleteAllAreaPerformanceInfo();

    @Query("SELECT * FROM MC_Area_Performance")
    LiveData<List<EAreaPerformance>> getAllAreaPerformanceInfo();
}
