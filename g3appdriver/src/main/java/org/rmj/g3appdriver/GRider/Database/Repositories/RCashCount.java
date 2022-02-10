/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 6/9/21 4:27 PM
 * project file last modified : 6/9/21 4:27 PM
 */

package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCashCount;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ECashCount;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class RCashCount {
    private final DCashCount ccDao;
    private final Application app;
    public RCashCount(Application application){
        this.app = application;
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        ccDao = GGCGriderDB.CashCountDao();
    }
    public void insertNewCashCount(ECashCount cashCount){
        this.ccDao.insertCashCount(cashCount);
    }
    public void updateCashCount(ECashCount cashCount){
        ccDao.updateCashCount(cashCount);
    }
    public void UpdateByTransNox(String transNox){
        new UpdateByTransNox(ccDao).execute(transNox);
    }
    private static class UpdateByTransNox extends AsyncTask<String, Void, String> {
        private final DCashCount ccDao;
        public UpdateByTransNox(DCashCount ccDao) {
            this.ccDao = ccDao;
        }

        @Override
        protected String doInBackground(String... transNox) {
            if (ccDao.getDuplicateTransNox(transNox[0]).size()>0){
                ccDao.UpdateByTransNox(transNox[0]);
            }
            return null;
        }
    }
    public String getCashCountNextCode(){
        String lsNextCode = "";
        GConnection loConn = DbConnection.doConnect(app);
        try{
            lsNextCode = MiscUtil.getNextCode("Cash_Count_Master", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        loConn = null;
        return lsNextCode;
    }

    public LiveData<List<DCashCount.CashCountLog>> getCashCountLog(){
        return ccDao.getCashCountLog();
    }

    public List<ECashCount> getAllUnsentCashCountEntries(){
        return ccDao.getAllUnsentCashCountEntries();
    }

    public LiveData<ECashCount> getCashCounDetetail(String TransNox){
        return ccDao.getCashCounDetetail(TransNox);
    }
}
