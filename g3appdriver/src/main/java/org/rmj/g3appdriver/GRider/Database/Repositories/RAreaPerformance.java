package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;

import java.util.List;

public class RAreaPerformance {
    private final DAreaPerformance areaPerformanceDao;
    private LiveData<List<EAreaPerformance>> allAreaPerformance;

    public RAreaPerformance(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        areaPerformanceDao = appDatabase.AreaPerformanceDao();
        allAreaPerformance = areaPerformanceDao.getAllAreaPerformanceInfo();
    }

    public void insertBulkData(List<EAreaPerformance> areaPerformances){
        areaPerformanceDao.insertBulkData(areaPerformances);
    }

    public LiveData<List<EAreaPerformance>> getAreaPerformanceInfoList(){
        return allAreaPerformance;
    }
}
