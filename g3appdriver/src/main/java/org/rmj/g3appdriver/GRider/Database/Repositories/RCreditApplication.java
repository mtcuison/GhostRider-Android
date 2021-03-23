package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplication;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;

import java.util.List;

public class RCreditApplication {
    private final DCreditApplication creditApplicationDao;
    private final LiveData<List<ECreditApplication>> allCreditApplication;

    private final Application app;

    public RCreditApplication(Application application){
        this.app = application;
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        creditApplicationDao = appDatabase.CreditApplicationDao();
        allCreditApplication = creditApplicationDao.getAllCreditApplication();
    }

    public void insertCreditApplication(ECreditApplication creditApplication){
        new InsertDataTask(creditApplicationDao).execute(creditApplication);
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

    //GET ALL CREDIT APP BY BRANCH
    public LiveData<List<DCreditApplication.ApplicationLog>> getApplicationByBranch(String BranchID){
        return creditApplicationDao.getApplicationByBranch(BranchID);
    }

    private static class InsertDataTask extends AsyncTask<ECreditApplication, Void, String>{
        private final DCreditApplication creditApplicationDao;

        public InsertDataTask(DCreditApplication creditApplicationDao) {
            this.creditApplicationDao = creditApplicationDao;
        }

        @Override
        protected String doInBackground(ECreditApplication... eCreditApplications) {
            creditApplicationDao.insert(eCreditApplications[0]);
            return null;
        }
    }

    public String getGOCasNextCode(){
        String lsTransNox = "";
        GConnection loConn = DbConnection.doConnect(app);
        try{
            lsTransNox = MiscUtil.getNextCode("Credit_Online_Application", "sTransNox", true, loConn.getConnection(), "", 12, false);

        } catch (Exception e){
            e.printStackTrace();
        }
        loConn = null;
        return lsTransNox;
    }
}
