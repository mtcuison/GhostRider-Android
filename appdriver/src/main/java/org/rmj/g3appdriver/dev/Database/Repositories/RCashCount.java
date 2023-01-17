/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 4:27 PM
 * project file last modified : 6/9/21 4:27 PM
 */

package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.dev.Database.Entities.ECashCount;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

import java.util.List;

public class RCashCount {
    private final DCashCount ccDao;
    private final Application app;
    public RCashCount(Application application){
        this.app = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        ccDao = GGCGriderDB.CashCountDao();
    }
    public LiveData<List<DCashCount.CashCountLog>> getCashCountLog(){
        return ccDao.getCashCountLog();
    }

    public LiveData<ECashCount> getCashCounDetetail(String TransNox){
        return ccDao.getCashCounDetetail(TransNox);
    }
}
