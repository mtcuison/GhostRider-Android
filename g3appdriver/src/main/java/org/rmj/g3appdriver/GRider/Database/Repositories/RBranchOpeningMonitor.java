/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 2:17 PM
 * project file last modified : 6/9/21 2:17 PM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class RBranchOpeningMonitor implements DBranchOpeningMonitor {

    private final Application instance;
    private final DBranchOpeningMonitor dao;

    public RBranchOpeningMonitor(Application application) {
        this.instance = application;
        this.dao = GGC_GriderDB.getInstance(instance).openingMonitoryDao();
    }

    @Override
    public void insert(EBranchOpenMonitor branchOpenMonitor) {
        dao.insert(branchOpenMonitor);
    }

    @Override
    public LiveData<List<EBranchOpenMonitor>> getBranchOpeningForDashBoard(String dTransact) {
        return dao.getBranchOpeningForDashBoard(dTransact);
    }

    @Override
    public LiveData<List<EBranchOpenMonitor>> getBranchOpeningForDate(String dTransact) {
        return dao.getBranchOpeningForDate(dTransact);
    }
}
