package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcCategory;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcCategory;

import java.util.List;

public class RMcCategory {
    private DMcCategory mcCategoryDao;
    private LiveData<List<EMcCategory>> allMcCategory;

    public RMcCategory(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mcCategoryDao = appDatabase.McCategoryDao();
        allMcCategory = mcCategoryDao.getAllMcCategory();
    }

    public void insertBulkData(List<EMcCategory> categories){
        mcCategoryDao.insetBulkData(categories);
    }
}
