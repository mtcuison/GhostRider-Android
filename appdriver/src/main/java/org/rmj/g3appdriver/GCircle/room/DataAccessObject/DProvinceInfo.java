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

import org.rmj.g3appdriver.GCircle.room.Entities.EProvinceInfo;

import java.util.List;

@Dao
public interface DProvinceInfo {

    @Insert
    void insert(EProvinceInfo provinceInfo);

    @Insert
    void insertBulkData(List<EProvinceInfo> provinceInfoList);

    @Query("SELECT COUNT(*) FROM Province_Info")
    Integer GetProvinceRecordsCount();

    @Query("SELECT * FROM Province_Info ORDER BY dTimeStmp DESC LIMIT 1")
    EProvinceInfo GetLatestProvince();

    @Query("SELECT * FROM Province_Info WHERE sProvIDxx=:fsVal")
    EProvinceInfo GetProvince(String fsVal);

    @Update
    void update(EProvinceInfo provinceInfo);

    @Query("SELECT * FROM Province_Info")
    LiveData<List<EProvinceInfo>> getAllProvinceInfo();

    @Query("SELECT * FROM Province_Info")
    LiveData<List<EProvinceInfo>> getAllProvinceName();

    @Query("SELECT sProvName FROM Province_Info")
    LiveData<String[]> getAllProvinceNames();

    @Query("SELECT * FROM Province_Info WHERE sProvName LIKE:ProvinceName")
    LiveData<List<EProvinceInfo>> searchProvinceName(String ProvinceName);

    @Query("SELECT sProvName FROM Province_Info WHERE sProvIDxx = :provID")
    LiveData<String> getProvinceNameFromProvID(String provID);

    @Query("SELECT MAX(dTimeStmp) FROM Province_Info")
    String getLatestDataTime();


}
