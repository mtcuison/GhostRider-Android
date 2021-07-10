/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/28/21 8:56 AM
 * project file last modified : 4/28/21 8:56 AM
 */
package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EGLocatorSysLog;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

public class RLocationSysLog {
    private static final String TAG = "LocationSysLog";
    private final Application instance;
    private final DLocatorSysLog sysLogDao;

    public RLocationSysLog(Application instance) {
        this.instance = instance;
        sysLogDao = GGC_GriderDB.getInstance(instance).locatorSysLogDao();
    }

    public void saveCurrentLocation(EGLocatorSysLog sysLog){
        sysLogDao.insertLocation(sysLog);
    }

    public void updateSysLogStatus(String dTransact){
        sysLogDao.updateSysLogStatus(new AppConstants().DATE_MODIFIED, dTransact);
    }
}
