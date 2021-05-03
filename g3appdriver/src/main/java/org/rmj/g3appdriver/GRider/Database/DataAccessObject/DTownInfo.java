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

    @Query("SELECT MAX(dTimeStmp) FROM Town_Info")
    String getLatestDataTime();

    @Query("SELECT * FROM Town_Info WHERE sProvIDxx = :ProvinceID")
    LiveData<List<ETownInfo>> getAllTownInfoFromProvince(String ProvinceID);

    @Query("SELECT sTownName FROM Town_Info WHERE sProvIDxx = :ProvinceID")
    LiveData<String[]> getTownNamesFromProvince(String ProvinceID);

    @Query("SELECT * FROM Town_Info WHERE sTownIDxx = :townID")
    LiveData<ETownInfo> getTownNameAndProvID(String townID);

    @Query("SELECT a.sTownIDxx, b.sProvIDxx, a.sTownName, b.sProvName " +
            "FROM Town_Info a " +
            "LEFT JOIN Province_Info b " +
            "ON a.sProvIDxx = b.sProvIDxx")
    LiveData<List<TownProvinceInfo>> getTownProvinceInfo();

    @Query("SELECT a.sTownName, b.sProvName , c.sBrgyName FROM Town_Info a INNER JOIN Province_Info b INNER JOIN Barangay_Info c ON\n" +
            "    a.sProvIDxx = b.sProvIDxx AND a.sTownIDxx = c.sTownIDxx AND c.sBrgyIDxx =:BrgyID")
    LiveData<BrgyTownProvinceInfo> getBrgyTownProvinceInfo(String BrgyID);

    @Query("SELECT a.sTownName, b.sProvName FROM Town_Info a INNER JOIN Province_Info b ON \n" +
            "a.sProvIDxx = b.sProvIDxx AND a.sTownIDxx  =:townID")
    LiveData<BrgyTownProvinceInfo> getTownProvinceInfo(String townID);

    @Query("SELECT a.sTownName, b.sProvName " +
            "FROM Town_Info a " +
            "LEFT JOIN Province_Info b " +
            "ON a.sProvIDxx = b.sProvIDxx " +
            "WHERE sTownIDxx =:TownID")
    TownProvinceName getTownProvinceNames(String TownID);

    @Query("SELECT a.sTownName, b.sProvName " +
            "FROM Town_Info a " +
            "LEFT JOIN Province_Info b " +
            "ON a.sProvIDxx = b.sProvIDxx " +
            "WHERE sTownIDxx =:TownID")
    LiveData<TownProvinceName> getLiveTownProvinceNames(String TownID);

    class TownProvinceInfo{
        public String sTownIDxx;
        public String sProvIDxx;
        public String sTownName;
        public String sProvName;
    }
    class BrgyTownProvinceInfo{
        public String sTownName;
        public String sProvName;
        public String sBrgyName;
    }

    class TownProvinceName{
        public String sTownName;
        public String sProvName;
    }
}
