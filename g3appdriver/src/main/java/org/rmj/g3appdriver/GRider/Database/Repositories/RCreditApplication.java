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
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
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
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        creditApplicationDao = GGCGriderDB.CreditApplicationDao();
        allCreditApplication = creditApplicationDao.getAllCreditApplication();
    }

    public void insertCreditApplication(ECreditApplication creditApplication){
        creditApplicationDao.insert(creditApplication);
    }

    public void updateCreditApplication(ECreditApplication creditApplication){

    }

    public void deleteCreditApplication(ECreditApplication creditApplication){

    }

    public LiveData<List<ECreditApplication>> getAllCreditOnlineApplication(){
        return allCreditApplication;
    }

    public ECreditApplication getLoanInfoOfTransNox(String TransNox){
        return creditApplicationDao.getLoanInfoOfTransNox(TransNox);
    }

    public void updateSentLoanAppl(String oldTransNox, String TransNox){
        creditApplicationDao.updateSentLoanAppl(oldTransNox, TransNox, new AppConstants().DATE_MODIFIED);
    }

    public void updateApplicationListTransNox(String oldTransNox, String TransNox){
        creditApplicationDao.updateApplicationListTransNox(oldTransNox, TransNox);
    }

    public LiveData<List<DCreditApplication.ApplicationLog>> getApplicationHistory(){
        return creditApplicationDao.getApplicationHistory();
    }

    public List<ECreditApplication> getUnsentLoanApplication() throws Exception{
        return creditApplicationDao.getUnsentLoanApplication();
    }

    public void insertBulkData(List<ECreditApplication> creditApplications){
        creditApplicationDao.insertBulkData(creditApplications);
    }

    public void updateCustomerImageStat(String TransNox){
        creditApplicationDao.updateCustomerImageStat(TransNox);
    }

    //GET ALL CREDIT APP BY BRANCH
    public LiveData<List<DCreditApplication.ApplicationLog>> getApplicationByBranch(String BranchID){
        return creditApplicationDao.getApplicationByBranch(BranchID);
    }
//    public void updateApplicantImageStat( String TransNox){
//        creditApplicationDao.updateApplicantImageStat(TransNox);
//    }
    public void updateApplicantImageStat(String transNox){
        new UpdateByTransNox(creditApplicationDao).execute(transNox);
    }
    private static class UpdateByTransNox extends AsyncTask<String, Void, String> {
        private final DCreditApplication documentsDao;
        public UpdateByTransNox(DCreditApplication documentsDao) {
            this.documentsDao = documentsDao;
        }

        @Override
        protected String doInBackground(String... transNox) {
            if (documentsDao.getDuplicateTransNox(transNox[0]).size()>0){
                documentsDao.updateApplicantImageStat(transNox[0]);
            }
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
