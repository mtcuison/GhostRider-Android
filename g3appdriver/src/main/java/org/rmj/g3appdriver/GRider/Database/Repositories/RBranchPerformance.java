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

import org.rmj.g3appdriver.GRider.Database.Entities.EAreaPerformance;
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

    public LiveData<List<EBranchPerformance>> getBranchPerformanceForDashBoard(){
        return branchPerformanceDao.getBranchPerformanceForDashBoard();
    }

    public LiveData<List<EBranchPerformance>> getMCSalesBranchPerformanceASC(){
        return branchPerformanceDao.getMCSalesBranchPerformanceASC();
    }

    public LiveData<List<EBranchPerformance>> getMCSalesBranchPerformanceDESC(){
        return branchPerformanceDao.getMCSalesBranchPerformanceDESC();
    }

    public LiveData<List<EBranchPerformance>> getSPSalesBranchPerformanceASC(){
        return branchPerformanceDao.getSPSalesBranchPerformanceASC();
    }

    public LiveData<List<EBranchPerformance>> getSPSalesBranchPerformanceDESC(){
        return branchPerformanceDao.getSPSalesBranchPerformanceDESC();
    }

    public LiveData<List<EBranchPerformance>> getJOBranchPerformanceASC(){
        return branchPerformanceDao.getJOBranchPerformanceASC();
    }

    public LiveData<List<EBranchPerformance>> getJOBranchPerformanceDESC(){
        return branchPerformanceDao.getJOBranchPerformanceDESC();
    }

    public String getUserAreaCode(){
        return branchPerformanceDao.getUserAreaCode();
    }

    public LiveData<List<EBranchPerformance>> getAllBranchPerformanceInfoByBranch(String branchCD){
        return branchPerformanceDao.getAllBranchPerformanceInfoByBranch(branchCD);
    }

    // For Area Monitoring
    public LiveData<List<EBranchPerformance>> getAreaBranchesSalesPerformance(String fsPeriodx) {
        return branchPerformanceDao.getAreaBranchesSalesPerformance(fsPeriodx);
    }


    public LiveData<DBranchPerformance.ActualGoal> getMCBranchPerformance(){
        return branchPerformanceDao.getMCBranchPerformance();
    }

    public LiveData<DBranchPerformance.ActualGoal> getSPBranchPerformance(){
        return branchPerformanceDao.getSPBranchPerformance();
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> getMCBranchPeriodicalPerformance(){
        return branchPerformanceDao.getMCBranchPeriodicalPerformance();
    }

    public LiveData<List<DBranchPerformance.PeriodicalPerformance>> getSPBranchPeriodicalPerformance(){
        return branchPerformanceDao.getSPBranchPeriodicalPerformance();
    }
}
