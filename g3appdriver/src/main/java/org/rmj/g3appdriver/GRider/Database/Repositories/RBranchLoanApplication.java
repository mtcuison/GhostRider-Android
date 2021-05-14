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
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;

import java.util.ArrayList;
import java.util.List;

public class RBranchLoanApplication {
    private static final String TAG = RBranchLoanApplication.class.getSimpleName();
    private final DBranchLoanApplication docsDao;
    private final Application application;
    private LiveData<List<EBranchLoanApplication>> branchCreditApplication;

    public RBranchLoanApplication(Application application){
        this.application = application;
        GGC_GriderDB database = GGC_GriderDB.getInstance(application);
        this.docsDao = GGC_GriderDB.getInstance(application).CreditAppDocsDao();
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

    public boolean insertBranchApplicationInfos(JSONArray faJson) throws Exception {
        try {
            List<EBranchLoanApplication> loanApplications = new ArrayList<>();
            for (int x = 0; x < faJson.length(); x++) {
                JSONObject loJson = faJson.getJSONObject(x);
                EBranchLoanApplication loanInfo = new EBranchLoanApplication();
                loanInfo.setTransNox(loJson.getString("sTransNox"));
                loanInfo.setBranchCD(loJson.getString("sBranchCd"));
                loanInfo.setTransact(loJson.getString("dTransact"));
                loanInfo.setCredInvx(loJson.getString("sCredInvx"));
                loanInfo.setCompnyNm(loJson.getString("sCompnyNm"));
                loanInfo.setSpouseNm(loJson.getString("sSpouseNm"));
                loanInfo.setAddressx(loJson.getString("sAddressx"));
                loanInfo.setMobileNo(loJson.getString("sMobileNo"));
                loanInfo.setQMAppCde(loJson.getString("sQMAppCde"));
                loanInfo.setModelNme(loJson.getString("sModelNme"));
                loanInfo.setDownPaym(loJson.getString("nDownPaym"));
                loanInfo.setAcctTerm(loJson.getString("nAcctTerm"));
                loanInfo.setCreatedX(loJson.getString("sCreatedx"));
                loanInfo.setTranStat(loJson.getString("cTranStat"));
                loanInfo.setTimeStmp(loJson.getString("dTimeStmp"));
                loanInfo.setCiTransTat(loJson.getString("ciTransTat"));

                loanApplications.add(loanInfo);
            }
            docsDao.insertBulkData(loanApplications);
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public LiveData<List<EBranchLoanApplication>> getBranchCreditApplication(){
        return docsDao.getAllBranchCreditApplication();
    }
    public LiveData<List<EBranchLoanApplication>> getCICreditApplication(){
        return docsDao.getAllCICreditApplication();
    }
    public LiveData<List<EBranchLoanApplication>> getAllCICreditApplicationLog(){
        return docsDao.getAllCICreditApplicationLog();
    }
    public LiveData<ECIEvaluation> getCITransTat(String TransNox){
        return docsDao.getCITransTat(TransNox);
    }
    public void updateCiTransTat(String transNox){
        new updateCiTranStat(docsDao).execute(transNox);
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

    public String getGOCasNextCode(){
        String lsTransNox = "";
        GConnection loConn = DbConnection.doConnect(application);
        try{
            lsTransNox = MiscUtil.getNextCode("Credit_Online_Application_List", "sTransNox", true, loConn.getConnection(), "", 12, false);

        } catch (Exception e){
            e.printStackTrace();
        }
        loConn = null;
        return lsTransNox;
    }
    private static class updateCiTranStat extends AsyncTask<String, Void, String>{
        private final DBranchLoanApplication ciDao;
        public updateCiTranStat(DBranchLoanApplication documentsDao) {
            this.ciDao = documentsDao;
        }

        @Override
        protected String doInBackground(String... transNox) {
            if (ciDao.getDuplicateTransNox(transNox[0]).size()>0){
                ciDao.updateTranStatByTransNox(transNox[0]);
            }
            return null;
        }
    }
}
