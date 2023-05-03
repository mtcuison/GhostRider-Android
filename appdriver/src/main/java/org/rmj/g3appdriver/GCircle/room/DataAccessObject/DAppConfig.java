/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 5/21/21 9:52 AM
 * project file last modified : 5/21/21 9:52 AM
 */

package org.rmj.g3appdriver.GCircle.room.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

import org.rmj.g3appdriver.GCircle.room.Entities.EAppConfig;

import java.util.List;

@Dao
public interface DAppConfig {

    @Insert
    void insert(EAppConfig sysConfig);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertSysConfig(List<EAppConfig> sysConfigs);
}
