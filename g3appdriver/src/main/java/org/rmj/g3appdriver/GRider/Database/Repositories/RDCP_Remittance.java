package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

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

    public void insert(EDCP_Remittance remittance){
        remitDao.insert(remittance);
    }

    public void updateSendStat(String TransNox){
        remitDao.updateSendStatus(AppConstants.DATE_MODIFIED, TransNox);
    }

    public LiveData<String> getTotalRemittedCollection(){
        return remitDao.getTotalRemittedCollection(AppConstants.CURRENT_DATE);
    }

    public LiveData<String> getTotalCashRemittedCollection(){
        return remitDao.getTotalCashRemittedCollection(AppConstants.CURRENT_DATE);
    }

    public LiveData<String> getTotalCheckRemittedCollection(){
        return remitDao.getTotalCheckRemittedCollection(AppConstants.CURRENT_DATE);
    }

    public LiveData<String> getTotalBranchRemittedCollection(){
        return remitDao.getTotalBranchRemittedCollection(AppConstants.CURRENT_DATE);
    }

    public LiveData<String> getTotalBankRemittedCollection(){
        return remitDao.getTotalBankRemittedCollection(AppConstants.CURRENT_DATE);
    }

    public LiveData<String> getTotalOtherRemittedCollection(){
        return remitDao.getTotalOtherRemittedCollection(AppConstants.CURRENT_DATE);
    }
}
