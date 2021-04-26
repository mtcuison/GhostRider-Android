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

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;

import java.util.List;

public class RAreaPerformance {
    private final DAreaPerformance areaPerformanceDao;
    private LiveData<List<EAreaPerformance>> allAreaPerformance;

    public RAreaPerformance(Application application) {
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        areaPerformanceDao = GGCGriderDB.AreaPerformanceDao();
        allAreaPerformance = areaPerformanceDao.getAllAreaPerformanceInfo();
    }

    public void insertBulkData(List<EAreaPerformance> areaPerformances){
        areaPerformanceDao.insertBulkData(areaPerformances);
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceInfoList(){
        return allAreaPerformance;
    }
}
