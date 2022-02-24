package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditOnlineApplicationCI;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class RCreditOnlineApplicationCI implements DCreditOnlineApplicationCI {

    private final Application instance;

    private final DCreditOnlineApplicationCI poDao;

    public RCreditOnlineApplicationCI(Application application) {
        this.instance = application;
        this.poDao = GGC_GriderDB.getInstance(instance).creditEvaluationDao();
    }

    @Override
    public LiveData<List<ECreditOnlineApplicationCI>> getForEvaluationList() {
        return poDao.getForEvaluationList();
    }

    @Override
    public void UpdateTransaction(String TransNox) {
        poDao.UpdateTransaction(TransNox);
    }
}
