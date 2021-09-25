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

import org.rmj.g3appdriver.GRider.Database.Entities.EOccupationInfo;

import java.util.List;

@Dao
public interface DOccupationInfo {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<EOccupationInfo> occupationInfos);

    @Update
    void update(EOccupationInfo occupationInfo);

    @Delete
    void delete(EOccupationInfo occupationInfo);

    @Query("SELECT * FROM Occupation_Info")
    LiveData<List<EOccupationInfo>> getAllOccupationInfo();

    @Query("SELECT * FROM Occupation_Info WHERE sOccptnNm = :OccupationName")
    LiveData<EOccupationInfo> getOccupationInfo(String OccupationName);

    @Query("SELECT sOccptnNm FROM Occupation_Info")
    LiveData<String[]> getOccupationNameList();

    @Query("SELECT MAX(dTimeStmp) FROM Occupation_Info")
    String getLatestDataTime();

    @Query("SELECT sOccptnNm FROM Occupation_Info WHERE sOccptnID=:ID")
    String getOccupationName(String ID);

    @Query("SELECT sOccptnNm FROM Occupation_Info WHERE sOccptnID=:ID")
    LiveData<String> getLiveOccupationName(String ID);
}
