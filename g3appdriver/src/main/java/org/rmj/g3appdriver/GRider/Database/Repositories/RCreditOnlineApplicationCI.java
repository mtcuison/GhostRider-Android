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
    public LiveData<List<DCreditOnlineApplicationCI.oDataEvaluationInfo>> getForEvaluationListData() {
        return poDao.getForEvaluationListData();
    }

    @Override
    public LiveData<List<oDataEvaluationInfo>> getForEvaluationListDataPreview() {
        return poDao.getForEvaluationListDataPreview();
    }

    @Override
    public ECreditOnlineApplicationCI getApplication(String TransNox) {
        return poDao.getApplication(TransNox);
    }

    @Override
    public LiveData<ECreditOnlineApplicationCI> getApplications(String TransNox) {
        return poDao.getApplications(TransNox);
    }

    @Override
    public LiveData<ECreditOnlineApplicationCI> RetrieveApplicationData(String TransNox) {
        return poDao.RetrieveApplicationData(TransNox);
    }
    @Override
    public void UpdateTransactionSendStat(String TransNox) {
        poDao.UpdateTransactionSendStat(TransNox);
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
    public void UpdateRecordInfo(String TransNox, String val) {
           poDao.UpdateRecordInfo(TransNox,val);
    }

    @Override
    public void UpdateRecordRemarks(String TransNox, String val) {
        poDao.UpdateRecordRemarks(TransNox,val);
    }

    @Override
    public void UpdatePresentBarangay(String TransNox, String val) {
        poDao.UpdatePresentBarangay(TransNox,val);
    }

    @Override
    public void UpdatePosition(String TransNox, String val) {
        poDao.UpdatePosition(TransNox,val);
    }

    @Override
    public void UpdateContact(String TransNox, String val) {
        poDao.UpdateContact(TransNox,val);
    }

    @Override
    public void UpdateNeighbor1(String TransNox, String val) {
        poDao.UpdateNeighbor1(TransNox,val);
    }

    @Override
    public void UpdateNeighbor2(String TransNox, String val) {
        poDao.UpdateNeighbor2(TransNox,val);
    }

    @Override
    public void UpdateNeighbor3(String TransNox, String val) {
        poDao.UpdateNeighbor3(TransNox,val);
    }

    @Override
    public void SaveCIApproval(String TransNox, String fsResult, String fsRemarks, String DateApp) {
        poDao.SaveCIApproval(TransNox, fsResult, fsRemarks, DateApp);
    }

    @Override
    public void SaveBHApproval(String TransNox, String fsResult, String fsRemarks) {
        poDao.SaveBHApproval(TransNox, fsResult, fsRemarks);
    }

    @Override
    public LiveData<oDataEvaluationInfo> getForEvaluateInfo(String TransNox) {
        return poDao.getForEvaluateInfo(TransNox);
    }
}
