package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;

import java.util.List;

public class RCreditApplication {
    private DCreditApplication creditApplicationDao;
    private LiveData<List<ECreditApplication>> allCreditApplication;

    public RCreditApplication(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        creditApplicationDao = appDatabase.CreditApplicationDao();
        allCreditApplication = creditApplicationDao.getAllCreditApplication();
    }

    public void insertCreditApplication(ECreditApplication creditApplication){

    }

    public void updateCreditApplication(ECreditApplication creditApplication){

    }

    public void deleteCreditApplication(ECreditApplication creditApplication){

    }

    public LiveData<List<ECreditApplication>> getAllCreditOnlineApplication(){
        return allCreditApplication;
    }

    public LiveData<List<DCreditApplication.ApplicationLog>> getApplicationHistory(){
        return creditApplicationDao.getApplicationHistory();
    }

    public void insertBulkData(List<ECreditApplication> creditApplications){
        creditApplicationDao.insertBulkData(creditApplications);
    }
}
