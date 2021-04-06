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
