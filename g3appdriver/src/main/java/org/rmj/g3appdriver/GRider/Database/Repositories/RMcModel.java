package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcModel;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModel;

import java.util.List;

public class RMcModel {
    private DMcModel mcModelDao;
    private LiveData<List<EMcModel>> allMcModelInfo;
    private LiveData<String[]> allMcModelName;

    public RMcModel(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mcModelDao = appDatabase.McModelDao();
    }

    public LiveData<List<EMcModel>> getMcModelFromBrand(String BrandID){
        allMcModelInfo = mcModelDao.getAllModeFromBrand(BrandID);
        return allMcModelInfo;
    }

    public LiveData<String[]> getAllMcModelName(String BrandID){
        allMcModelName = mcModelDao.getAllModelName(BrandID);
        return allMcModelName;
    }

    public void insertBulkData(List<EMcModel> modelList){
        mcModelDao.insertBulkData(modelList);
    }
}
