package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRawDao;

public class RRawData {

    private DRawDao rawDao;

    public RRawData(Application application){
        GGC_GriderDB GGCGriderDB = GGC_GriderDB.getInstance(application);
        rawDao = GGCGriderDB.RawDao();
    }

    public LiveData<DRawDao.McAmortInfo> getMonthlyAmortInfo(String ModelID, int Term){
        return rawDao.getMonthlyAmort(ModelID, Term);
    }

    public LiveData<DRawDao.McDPInfo> getDownpayment(String ModelID){
        return rawDao.getDownpayment(ModelID);
    }
}
