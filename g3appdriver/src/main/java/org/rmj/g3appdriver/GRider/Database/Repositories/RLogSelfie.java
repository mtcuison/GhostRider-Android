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
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DLog_Selfie;
import org.rmj.g3appdriver.GRider.Database.DbConnection;
import org.rmj.g3appdriver.GRider.Database.Entities.ELog_Selfie;

import java.util.List;

public class RLogSelfie {
    private static final String TAG = RLogSelfie.class.getSimpleName();
    private final Application instance;
    private final DLog_Selfie selfieDao;

    public RLogSelfie(Application application){
        this.instance = application;
        this.selfieDao = GGC_GriderDB.getInstance(application).SelfieDao();
    }

    public LiveData<List<ELog_Selfie>> getCurrentLogTimeIfExist(String fsDate){
        String DateLog = "%"+fsDate+"%";
        return selfieDao.getCurrentTimeLogIfExist(DateLog);
    }
    public LiveData<List<ELog_Selfie>> getCurrentTimeLog(String fsDate){
        String DateLog = "%"+fsDate+"%";
        return selfieDao.getCurrentTimeLog(DateLog);
    }

    public List<ELog_Selfie> getUnsentSelfieLogin() throws Exception{
        return selfieDao.getUnsentSelfieLogin();
    }

    public LiveData<List<ELog_Selfie>> getAllEmployeeTimeLog(){
        return selfieDao.getAllEmployeeTimeLog();
    }

    public void insertSelfieLog(ELog_Selfie selfieLog){
        new InsertSelfieTask(selfieDao).execute(selfieLog);
    }

    public LiveData<List<String>> getLastLogDate(){
        return selfieDao.getLastLogDate();
    }

    public void updateEmployeeLogStatus(String sTransNox, String OldTransNox){
        selfieDao.updateEmployeeLogStat(sTransNox, OldTransNox, new AppConstants().DATE_MODIFIED);
    }

    public LiveData<ELog_Selfie> getLastSelfieLog(){
        return selfieDao.getLastSelfieLog();
    }

    public void UpdateCashCountRequireStatus(){
        selfieDao.UpdateCashCountRequireStatus();
    }
    public LiveData<String> getSelfieBranchCode(){
        return selfieDao.getSelfieBranchCode();
    }

    public String getLogNextCode(){
        String lsNextCode = "";
        try{
            GConnection loConn = DbConnection.doConnect(instance);
            lsNextCode = MiscUtil.getNextCode("Employee_Log_Selfie", "sTransNox", true, loConn.getConnection(), "", 12, false);
        } catch (Exception e){
            e.printStackTrace();
        }
        return lsNextCode;
    }

    private static class InsertSelfieTask extends AsyncTask<ELog_Selfie, Void, String>{
        private final DLog_Selfie selfieDao;

        public InsertSelfieTask(DLog_Selfie selfieDao) {
            this.selfieDao = selfieDao;
        }

        @Override
        protected String doInBackground(ELog_Selfie... eLog_selfies) {
            selfieDao.insertLoginSelfie(eLog_selfies[0]);
            return null;
        }
    }
}
