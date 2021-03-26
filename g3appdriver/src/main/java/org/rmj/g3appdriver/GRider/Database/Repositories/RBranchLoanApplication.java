package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.apprdiver.util.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchLoanApplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RBranchLoanApplication {
    private static final String TAG = RBranchLoanApplication.class.getSimpleName();
    private final DBranchLoanApplication docsDao;

    private final Application application;
    private LiveData<List<EBranchLoanApplication>> branchCreditApplication;

    public RBranchLoanApplication(Application application){
        this.application = application;
        AppDatabase database = AppDatabase.getInstance(application);
        this.docsDao = AppDatabase.getInstance(application).CreditAppDocsDao();
    }

    public boolean insertBranchApplicationInfos(JSONArray faJson) throws Exception {
        try {
            List<EBranchLoanApplication> loanApplications = new ArrayList<>();
            for (int x = 0; x < faJson.length(); x++) {
                JSONObject loJson = faJson.getJSONObject(x);
                EBranchLoanApplication loanInfo = new EBranchLoanApplication();
                loanInfo.setTransNox(loJson.getString("sTransNox"));
                loanInfo.setTransNox(loJson.getString("sBranchCd"));
                loanInfo.setTransNox(loJson.getString("dTransact"));
                loanInfo.setTransNox(loJson.getString("sCredInvx"));
                loanInfo.setTransNox(loJson.getString("sCompnyNm"));
                loanInfo.setTransNox(loJson.getString("sSpouseNm"));
                loanInfo.setTransNox(loJson.getString("sAddressx"));
                loanInfo.setTransNox(loJson.getString("sMobileNo"));
                loanInfo.setTransNox(loJson.getString("sQMAppCde"));
                loanInfo.setTransNox(loJson.getString("sModelNme"));
                loanInfo.setTransNox(loJson.getString("nDownPaym"));
                loanInfo.setTransNox(loJson.getString("nAcctTerm"));
                loanInfo.setTransNox(loJson.getString("sCreatedx"));
                loanInfo.setTransNox(loJson.getString("cTranStat"));
                loanInfo.setTransNox(loJson.getString("dTimeStmp"));
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
}
