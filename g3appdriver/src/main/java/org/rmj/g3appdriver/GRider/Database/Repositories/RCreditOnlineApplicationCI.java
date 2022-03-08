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
    public void SaveApplicationInfo(ECreditOnlineApplicationCI foCI) {
        poDao.SaveApplicationInfo(foCI);
    }

    @Override
    public LiveData<List<ECreditOnlineApplicationCI>> getForEvaluationList() {
        return poDao.getForEvaluationList();
    }

    @Override
    public ECreditOnlineApplicationCI getApplication(String TransNox) {
        return poDao.getApplication(TransNox);
    }

    @Override
    public void UpdateTransaction(String TransNox) {
        poDao.UpdateTransaction(TransNox);
    }

    @Override
    public String getAddressForEvaluation(String TransNox) {
        return poDao.getAddressForEvaluation(TransNox);
    }

    @Override
    public String getAssetsForEvaluation(String TransNox) {
        return poDao.getAssetsForEvaluation(TransNox);
    }

    @Override
    public String getIncomeForEvaluation(String TransNox) {
        return poDao.getIncomeForEvaluation(TransNox);
    }

    @Override
    public void updateAddressEvaluation(String TransNox, String Findings) {
        poDao.updateAddressEvaluation(TransNox, Findings);
    }

    @Override
    public void updateAssetEvaluation(String TransNox, String Findings) {
        poDao.updateAssetEvaluation(TransNox, Findings);
    }

    @Override
    public void updateIncomeEvaluation(String TransNox, String Findings) {
        poDao.updateIncomeEvaluation(TransNox, Findings);
    }

    @Override
    public oDataEvaluationInfo getForEvaluateInfo(String TransNox) {
        return poDao.getForEvaluateInfo(TransNox);
    }
}
