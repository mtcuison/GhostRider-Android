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
}
