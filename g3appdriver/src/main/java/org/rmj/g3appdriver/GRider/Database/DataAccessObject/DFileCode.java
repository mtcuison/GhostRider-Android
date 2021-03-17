package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;

import java.util.List;

@Dao
public interface DFileCode {

    @Query("SELECT * FROM EDocSys_File")
    LiveData<List<EFileCode>> selectFileCodeList();

    @Query("SELECT MAX(dTimeStmp) AS TimeStamp FROM EDocSys_File")
    String getLatestDataTime();
}
