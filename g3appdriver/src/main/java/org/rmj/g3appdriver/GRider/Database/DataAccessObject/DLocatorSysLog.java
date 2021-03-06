/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/28/21 8:54 AM
 * project file last modified : 4/28/21 8:53 AM
 */

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import org.rmj.g3appdriver.GRider.Database.Entities.EGLocatorSysLog;

@Dao
public interface DLocatorSysLog {

    @Insert
    void insertLocation(EGLocatorSysLog locatorSysLog);

    @Query("UPDATE GLocator_Sys_log SET cSendStat = '1', dTimeStmp =:dTimeStmp WHERE dTransact =:dTransact")
    void updateSysLogStatus(String dTimeStmp, String dTransact);
}
