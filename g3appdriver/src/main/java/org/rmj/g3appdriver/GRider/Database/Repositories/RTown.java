package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;

import java.util.List;

public class RTown {
    private final DTownInfo townDao;
    private LiveData<List<ETownInfo>> allTownInfo;
    private LiveData<List<ETownInfo>> townInfoFromProvince;

    public RTown(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        townDao = appDatabase.TownDao();
        allTownInfo = townDao.getAllTownInfo();
    }

    public LiveData<List<ETownInfo>> getTownInfoFromProvince(String ProvinceID){
        return townDao.getAllTownInfoFromProvince(ProvinceID);
    }

    public LiveData<String[]> getTownNamesFromProvince(String ProvinceID){
        return townDao.getTownNamesFromProvince(ProvinceID);
    }

    public void insertBulkData(List<ETownInfo> townInfoList){
        townDao.insertBulkData(townInfoList);
    }

    public LiveData<ETownInfo> getTownNameAndProvID(String townID) {
        return townDao.getTownNameAndProvID(townID);
    }


}
