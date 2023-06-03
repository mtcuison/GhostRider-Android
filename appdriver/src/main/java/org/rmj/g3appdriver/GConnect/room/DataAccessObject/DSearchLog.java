package org.rmj.g3appdriver.GConnect.room.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GConnect.room.Entities.ESearchLog;

import java.util.List;

@Dao
public interface DSearchLog {

    @Insert
    void SaveNewSearch(ESearchLog foVal);

    @Query("UPDATE Mkt_Search_log SET dTimeStmp =:DateTime WHERE nEntryNox =:fnArgs")
    void UpdateSearch(int fnArgs, String DateTime);

    @Query("SELECT * FROM Mkt_Search_log WHERE sSearchxx=:fsArgs")
    ESearchLog CheckIfExist(String fsArgs);

    @Query("SELECT COUNT(*) + 1 AS nEntryNox FROM Mkt_Search_log")
    int CreateNewEntryNox();

    @Query("SELECT * FROM Mkt_Search_log ORDER BY dTimeStmp DESC LIMIT 10")
    LiveData<List<ESearchLog>> GetSearchLog();
}
