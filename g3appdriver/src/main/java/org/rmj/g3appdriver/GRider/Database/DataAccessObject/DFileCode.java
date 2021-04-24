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
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;

import java.util.List;

@Dao
public interface DFileCode {

    @Query("SELECT * FROM EDocSys_File WHERE sFileCode != '0020' AND sFileCode != '0021'")
    LiveData<List<EFileCode>> selectFileCodeList();

    @Query("SELECT MAX(dTimeStmp) AS TimeStamp FROM EDocSys_File")
    String getLatestDataTime();
}
