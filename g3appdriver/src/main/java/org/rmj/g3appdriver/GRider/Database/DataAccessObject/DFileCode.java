package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;

import java.util.List;

@Dao
public interface DFileCode {

    @Query("SELECT * FROM EDocSys_File WHERE sFileCode != '0020' AND sFileCode != '0021'")
    LiveData<List<EFileCode>> selectFileCodeList();

}
