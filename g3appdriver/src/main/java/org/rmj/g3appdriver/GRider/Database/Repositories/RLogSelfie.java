package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.appdriver.base.GConnection;
import org.rmj.apprdiver.util.MiscUtil;
import org.rmj.g3appdriver.GRider.Database.AppDatabase;
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
        this.selfieDao = AppDatabase.getInstance(application).SelfieDao();
    }

    public LiveData<List<ELog_Selfie>> getCurrentLogTimeIfExist(String fsDate){
        String DateLog = "%"+fsDate+"%";
        return selfieDao.getCurrentTimeLogIfExist(DateLog);
    }

    public void insertSelfieLog(ELog_Selfie selfieLog){
        new InsertSelfieTask(selfieDao).execute(selfieLog);
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
