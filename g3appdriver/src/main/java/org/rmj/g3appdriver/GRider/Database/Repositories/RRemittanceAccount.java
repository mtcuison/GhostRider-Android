package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRemittanceAccounts;
import org.rmj.g3appdriver.GRider.Database.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class RRemittanceAccount {

    private final DRemittanceAccounts remittanceDao;

    public RRemittanceAccount(Application application) {
        remittanceDao = GGC_GriderDB.getInstance(application).RemitanceAccDao();
    }

    public void insertBulkData(List<ERemittanceAccounts> remittanceAccounts){
        remittanceDao.insertBulkData(remittanceAccounts);
    }

    public LiveData<List<ERemittanceAccounts>> getRemittanceBankAccount(){
        return remittanceDao.getRemittanceBankAccountsList();
    }

    public LiveData<List<ERemittanceAccounts>> getRemittanceOtherAccount(){
        return remittanceDao.getRemittanceOtherAccountsList();
    }

    public LiveData<ERemittanceAccounts> getDefaultRemittanceAccount(){
        return remittanceDao.getDefaultRemittanceAccount();
    }

    public List<ERemittanceAccounts> getRemittanceAccountIfExist(){
        return remittanceDao.getRemittanceAccountsIfExist();
    }
}
