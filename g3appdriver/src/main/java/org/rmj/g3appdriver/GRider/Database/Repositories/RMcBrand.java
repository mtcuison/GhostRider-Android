package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcBrand;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcBrand;

import java.util.List;

public class RMcBrand {
    private final DMcBrand mcBrandDao;
    private final LiveData<List<EMcBrand>> allMcBrand;
    private final LiveData<String[]> allBrandNames;

    public RMcBrand(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mcBrandDao = appDatabase.McBrandDao();
        allBrandNames = mcBrandDao.getAllBrandName();
        allMcBrand = mcBrandDao.getAllMcBrand();
    }

    public void insertBulkData(List<EMcBrand> brandList){
        mcBrandDao.insertBulkData(brandList);
    }

    public LiveData<List<EMcBrand>> getAllBrandInfo(){
        return allMcBrand;
    }

    public LiveData<String[]> getAllBrandNames(){
        return allBrandNames;
    }
}
