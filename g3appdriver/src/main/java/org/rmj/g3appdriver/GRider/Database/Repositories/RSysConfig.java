/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/26/21 4:19 PM
 * project file last modified : 4/26/21 4:19 PM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DSysConfig;
import org.rmj.g3appdriver.GRider.Database.Entities.ESysConfig;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class RSysConfig {
    private static final String TAG = RSysConfig.class.getSimpleName();
    private final DSysConfig sysConfigDao;

    public RSysConfig(Application instance) {
        this.sysConfigDao = GGC_GriderDB.getInstance(instance).sysConfigDao();
    }

    public void insertSysConfig(List<ESysConfig> sysConfig){
        sysConfigDao.insertSysConfig(sysConfig);
    }
}
