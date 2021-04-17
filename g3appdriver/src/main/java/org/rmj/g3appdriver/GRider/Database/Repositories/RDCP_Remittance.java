package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

public class RDCP_Remittance {
    private static final String TAG = RDCP_Remittance.class.getSimpleName();

    private final DDCP_Remittance remitDao;

    public RDCP_Remittance(Application application) {
        this.remitDao = GGC_GriderDB.getInstance(application).DCPRemitanceDao();
    }

    public void initializeRemittance(String dTransact){
        new InitializeRemitTask().execute(dTransact);
    }

    public void insert(EDCP_Remittance remittance){
        remitDao.insert(remittance);
    }

    public void updateSendStat(String TransNox){
        remitDao.updateSendStatus(AppConstants.DATE_MODIFIED, TransNox);
    }

    public LiveData<String> getTotalRemittedCollection(String dTransact){
        return remitDao.getTotalRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalCashRemittedCollection(String dTransact){
        return remitDao.getTotalCashRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalCheckRemittedCollection(String dTransact){
        return remitDao.getTotalCheckRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalBranchRemittedCollection(String dTransact){
        return remitDao.getTotalBranchRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalBankRemittedCollection(String dTransact){
        return remitDao.getTotalBankRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalOtherRemittedCollection(String dTransact){
        return remitDao.getTotalOtherRemittedCollection(dTransact);
    }

    public LiveData<String> getCheckOnHand(String dTransact){
        return remitDao.getCheckOnHand(dTransact);
    }

    public LiveData<String> getCashOnHand(String dTransact){
        return remitDao.getCashOnHand(dTransact);
    }

    @SuppressLint("StaticFieldLeak")
    private class InitializeRemitTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String dTransact = strings[0];
            if(remitDao.checkRemittanceExist(dTransact) == null){
                remitDao.initializeCurrentDayRemittanceField(dTransact);
            }
            return null;
        }
    }
}
