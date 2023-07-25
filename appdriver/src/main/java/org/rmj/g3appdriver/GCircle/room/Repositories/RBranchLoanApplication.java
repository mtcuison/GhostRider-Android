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

package org.rmj.g3appdriver.GCircle.room.Repositories;

import android.app.Application;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchLoanApplication;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;

import java.util.List;

public class RBranchLoanApplication {
    private static final String TAG = RBranchLoanApplication.class.getSimpleName();
    private final DBranchLoanApplication docsDao;
    private final Application application;
    private LiveData<List<EBranchLoanApplication>> branchCreditApplication;

    public RBranchLoanApplication(Application application){
        this.application = application;
        GGC_GCircleDB database = GGC_GCircleDB.getInstance(application);
        this.docsDao = GGC_GCircleDB.getInstance(application).CreditAppDocsDao();
    }

    public LiveData<DBranchLoanApplication.CiDetail> getCiDetail(String fsTransNo) {
        return docsDao.getCiDetail(fsTransNo);
    }

    public boolean insertCiApplication(EBranchLoanApplication ciApplication){
        try {
            docsDao.insert(ciApplication);
            return true;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean insertCBulkiApplication(List<EBranchLoanApplication> ciApplication){
        try {
            docsDao.insertBulkData(ciApplication);
            return true;
        } catch (SQLiteConstraintException e) {
            e.printStackTrace();
            return false;
        }
    }

    public LiveData<List<EBranchLoanApplication>> getBranchCreditApplication(){
        return docsDao.getAllBranchCreditApplication();
    }
    public LiveData<List<DBranchLoanApplication.CIEvaluationList>> getAllCICreditApplicationLog(){
        return docsDao.getAllCICreditApplicationLog();
    }
    public LiveData<List<DBranchLoanApplication.CIEvaluationList>> getAllCICreditApplications(){
        return docsDao.getAllCICreditApplications();
    }

    public void insertDetailBulkData(List<EBranchLoanApplication> eBranchLoanApplications){
        new InsertBulkBranchApplicationListAsyncTask(docsDao).execute(eBranchLoanApplications);
    }

    public void insertNewLoanApplication(EBranchLoanApplication loanApplication){
        docsDao.insertNewApplication(loanApplication);

    }

    private static class InsertBulkBranchApplicationListAsyncTask extends AsyncTask<List<EBranchLoanApplication>, Void, Void> {
        private DBranchLoanApplication detailDao;

        private InsertBulkBranchApplicationListAsyncTask(DBranchLoanApplication detailDao){
            this.detailDao = detailDao;
        }

        @Override
        protected Void doInBackground(List<EBranchLoanApplication>... lists) {
            detailDao.insertBulkData(lists[0]);
            return null;
        }
    }
}
