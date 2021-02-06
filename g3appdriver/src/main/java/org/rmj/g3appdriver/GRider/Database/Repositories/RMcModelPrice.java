package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMcModelPrice;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModelPrice;

import java.util.List;

public class RMcModelPrice {
    private DMcModelPrice mcModelPriceDao;
    private LiveData<List<EMcModelPrice>> allMcModelPrice;

    public RMcModelPrice(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        mcModelPriceDao = appDatabase.McModelPriceDao();
    }

    public void insertBulkData(List<EMcModelPrice> modelPrices){
        mcModelPriceDao.insertBulkdData(modelPrices);
    }
}
