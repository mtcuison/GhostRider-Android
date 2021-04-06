package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchPerformance;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchPerformance;

import java.util.List;

public class RBranchPerformance {
    private DBranchPerformance branchPerformanceDao;
    private LiveData<List<EBranchPerformance>> allBranchPerformanceInfo;

    public RBranchPerformance(Application application) {
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        branchPerformanceDao = GGCGriderDB.BranchPerformanceDao();
        allBranchPerformanceInfo = branchPerformanceDao.getAllBranchPerformanceInfo();
    }

    public void insertBulkData(List<EBranchPerformance> list) {
        branchPerformanceDao.insertBulkData(list);
    }
}
