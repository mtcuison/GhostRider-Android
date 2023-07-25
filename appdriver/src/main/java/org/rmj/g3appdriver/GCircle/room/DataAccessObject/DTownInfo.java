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

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GCircle.room.Entities.ETownInfo;

import java.util.List;

@Dao
public interface DTownInfo {

    @Insert
    void insert(ETownInfo townInfo);

    @Insert
    void insertBulkData(List<ETownInfo> townInfoList);

    @Update
    void update(ETownInfo townInfo);

    @Query("SELECT COUNT(*) FROM Town_Info")
    Integer GetTownRecordsCount();

    @Query("SELECT * FROM Town_Info WHERE sTownIDxx =:fsVal")
    ETownInfo GetTown(String fsVal);

    @Query("SELECT * FROM Town_Info ORDER BY dTimeStmp DESC LIMIT 1")
    ETownInfo GetLatestTown();

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

    @Query("SELECT " +
            "a.sTownName, " +
            "b.sProvName, " +
            "c.sBrgyName " +
            "FROM Town_Info a " +
            "INNER JOIN Province_Info b " +
            "INNER JOIN Barangay_Info c " +
            "ON a.sProvIDxx = b.sProvIDxx " +
            "AND a.sTownIDxx = c.sTownIDxx " +
            "AND c.sBrgyIDxx =:BrgyID")
    LiveData<BrgyTownProvinceInfo> getBrgyTownProvinceInfo(String BrgyID);

    @Query("SELECT " +
            "a.sTownName, " +
            "b.sProvName," +
            "'' AS sBrgyName " +
            "FROM Town_Info a " +
            "INNER JOIN Province_Info b " +
            "ON a.sProvIDxx = b.sProvIDxx " +
            "AND a.sTownIDxx  =:townID")
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


    @Query("SELECT a.sTownIDxx, b.sProvIDxx, a.sTownName, b.sProvName " +
            "FROM Town_Info a " +
            "LEFT JOIN Province_Info b " +
            "ON a.sProvIDxx = b.sProvIDxx " +
            "WHERE sTownIDxx =:TownID")
    LiveData<TownProvinceInfo> getTownProvinceByTownID(String TownID);


    @Query("SELECT a.sTownIDxx, b.sProvIDxx, a.sTownName, b.sProvName " +
            "FROM Town_Info a " +
            "LEFT JOIN Province_Info b " +
            "ON a.sProvIDxx = b.sProvIDxx " +
            "WHERE sTownName =:TownNm")
    LiveData<TownProvinceInfo> getTownProvinceByTownName(String TownNm);


    @Query("SELECT a.sBrgyIDxx, " +
            "a.sBrgyName, " +
            "b.sTownIDxx, " +
            "b.sTownName, " +
            "c.sProvIDxx, " +
            "c.sProvName " +
            "FROM Barangay_Info a " +
            "LEFT JOIN Town_Info b " +
            "ON a.sTownIDxx = b.sTownIDxx " +
            "LEFT JOIN Province_Info c " +
            "ON b.sProvIDxx = c.sProvIDxx " +
            "WHERE a.sBrgyIDxx =:BrgyID")
    LiveData<BrgyTownProvinceInfoWithID> getBrgyTownProvinceInfoWithID(String BrgyID);

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

    class BrgyTownProvinceInfoWithID{
        public String sTownIDxx;
        public String sProvIDxx;
        public String sBrgyIDxx;
        public String sTownName;
        public String sProvName;
        public String sBrgyName;
    }
}
