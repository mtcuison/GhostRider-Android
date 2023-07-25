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

package org.rmj.g3appdriver.lib.Notifications.Obj;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBranchOpeningMonitor;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchOpenMonitor;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;
import org.rmj.g3appdriver.etc.AppConstants;

import java.util.List;

public class BranchOpeningMonitor {

    private final Application instance;
    private final DBranchOpeningMonitor dao;

    public BranchOpeningMonitor(Application application) {
        this.instance = application;
        this.dao = GGC_GCircleDB.getInstance(instance).openingMonitoryDao();
    }

    public void insert(EBranchOpenMonitor branchOpenMonitor) {
        dao.insert(branchOpenMonitor);
    }

    public LiveData<List<EBranchOpenMonitor>> getBranchOpeningForDashBoard() {
        return dao.getBranchOpeningForDashBoard(AppConstants.CURRENT_DATE());
    }

    public LiveData<List<EBranchOpenMonitor>> getBranchOpeningForDate(String dTransact) {
        return dao.getBranchOpeningForDate(dTransact);
    }

    public LiveData<List<DBranchOpeningMonitor.BranchOpeningInfo>> GetBranchOpeningForDashboard() {
        return dao.GetBranchOpeningInfoForDashBoard(AppConstants.CURRENT_DATE());
    }

    public LiveData<List<DBranchOpeningMonitor.BranchOpeningInfo>> getBranchOpeningInfo(String dTransact) {
        return dao.getBranchOpeningInfo(dTransact);
    }
}
