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
