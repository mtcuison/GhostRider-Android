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
