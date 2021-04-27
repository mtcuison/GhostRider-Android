/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/26/21 4:18 PM
 * project file last modified : 4/26/21 4:18 PM
 */

package org.rmj.g3appdriver.GRider.Database.DataAccessObject;

import androidx.room.Dao;
import androidx.room.Insert;

import org.rmj.g3appdriver.GRider.Database.Entities.ESysConfig;

import java.util.List;

@Dao
public interface DSysConfig {

    @Insert
    void insert(ESysConfig sysConfig);

    @Insert
    void insertSysConfig(List<ESysConfig> sysConfigs);
}
