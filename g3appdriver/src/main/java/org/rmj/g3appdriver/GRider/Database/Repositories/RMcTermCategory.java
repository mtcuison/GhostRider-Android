package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcTermCategory;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcTermCategory;

import java.util.List;

public class RMcTermCategory {
    private DMcTermCategory mcTermCategoryDao;
    private LiveData<List<EMcTermCategory>> allMcTermCategory;

    public RMcTermCategory(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mcTermCategoryDao = appDatabase.McTermCategoryDao();
        allMcTermCategory = mcTermCategoryDao.getAllMcTermCategory();
    }

    public void insertBulkData(List<EMcTermCategory> categories){
        mcTermCategoryDao.insertBulkData(categories);
    }
}
