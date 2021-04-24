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
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DOccupationInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EOccupationInfo;

import java.util.List;

public class ROccupation {
    private DOccupationInfo OccupationDao;
    private final LiveData<String[]> allOccupationNameList;
    private final LiveData<List<EOccupationInfo>> allOccupationInfo;

    public ROccupation(Application application){
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        OccupationDao = GGCGriderDB.OccupationDao();
        allOccupationNameList = OccupationDao.getOccupationNameList();
        allOccupationInfo = OccupationDao.getAllOccupationInfo();
    }

    public LiveData<String[]> getAllOccupationNameList() {
        return allOccupationNameList;
    }

    public LiveData<List<EOccupationInfo>> getAllOccupationInfo() {
        return allOccupationInfo;
    }

    public void insertBulkData(List<EOccupationInfo> occupationInfos){
        OccupationDao.insertBulkData(occupationInfos);
    }

    public String getOccupationName(String ID){
        return OccupationDao.getOccupationName(ID);
    }

    public String getLatestDataTime(){
        return OccupationDao.getLatestDataTime();
    }
}
