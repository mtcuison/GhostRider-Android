package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;

import java.util.List;

public class RBranchPerformance {
    private DBranchPerformance branchPerformanceDao;
    private LiveData<List<EBranchPerformance>> allBranchPerformanceInfo;

    public RBranchPerformance(Application application) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        branchPerformanceDao = appDatabase.BranchPerformanceDao();
        allBranchPerformanceInfo = branchPerformanceDao.getAllBranchPerformanceInfo();
    }

    public void insertBulkData(List<EBranchPerformance> list) {
        branchPerformanceDao.insertBulkData(list);
    }
}
