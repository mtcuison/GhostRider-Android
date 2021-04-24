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
