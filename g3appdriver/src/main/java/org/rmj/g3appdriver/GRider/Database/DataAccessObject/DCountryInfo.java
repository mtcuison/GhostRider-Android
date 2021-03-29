package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;

import java.util.List;

@Dao
public interface DCountryInfo {

    @Insert
    void insert(ECountryInfo countryInfo);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertBulkData(List<ECountryInfo> countryInfos);

    @Update
    void update(ECountryInfo countryInfo);

    @Delete
    void delete(ECountryInfo countryInfo);

    @Query("SELECT * FROM Country_Info")
    LiveData<List<ECountryInfo>> getAllCountryInfo();

    @Query("SELECT sCntryNme FROM Country_Info")
    LiveData<String[]> getAllCountryNames();

    @Query("SELECT sNational FROM Country_Info")
    LiveData<String[]> getAllCountryCitizenNames();

    @Query("SELECT MAX(dTimeStmp) FROM Country_Info")
    String getLatestDataTime();

    @Query("SELECT * FROM Country_Info WHERE sCntryCde=:ID")
    ECountryInfo getCountryInfo(String ID);
}
