package org.rmj.g3appdriver.GCircle.room.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DInventoryDetail;
import org.rmj.g3appdriver.GCircle.room.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GCircle.room.GGC_GCircleDB;

import java.util.List;

public class RInventoryDetail implements DInventoryDetail {
    private static final String TAG = RInventoryDetail.class.getSimpleName();

    private final Application instance;
    private final DInventoryDetail poDao;

    public RInventoryDetail(Application application) {
        this.instance = application;
        this.poDao = GGC_GCircleDB.getInstance(instance).inventoryDetailDao();
    }

    @Override
    public void insertInventoryDetail(List<EInventoryDetail> foDetail) {
        poDao.insertInventoryDetail(foDetail);
    }

    @Override
    public LiveData<List<EInventoryDetail>> getInventoryDetailForBranch(String TransNox) {
        return poDao.getInventoryDetailForBranch(TransNox);
    }

    @Override
    public LiveData<EInventoryDetail> getInventoryItemDetail(String TransNox, String PartID, String BarCode) {
        return poDao.getInventoryItemDetail(TransNox, PartID, BarCode);
    }

    @Override
    public LiveData<String> getInventoryCountForBranch(String TransNox) {
        return poDao.getInventoryCountForBranch(TransNox);
    }

    @Override
    public void UpdateInventoryItem(String TransNox, String BarCode, String PartID, String ActualQty, String Remarks) {
        poDao.UpdateInventoryItem(TransNox, BarCode, PartID, ActualQty, Remarks);
    }

    @Override
    public Integer getUncountedInventoryItems(String TransNox) {
        return poDao.getUncountedInventoryItems(TransNox);
    }

    @Override
    public List<EInventoryDetail> getInventoryDetailForPosting(String TransNox) {
        return poDao.getInventoryDetailForPosting(TransNox);
    }

    @Override
    public void UpdateInventoryItemPostedStatus(String TransNox, String PartID) {
        poDao.UpdateInventoryItemPostedStatus(TransNox, PartID);
    }

    @Override
    public Integer checkForUnpostedInventoryDetail(String TransNox) {
        return poDao.checkForUnpostedInventoryDetail(TransNox);
    }

    @Override
    public String GetLatestBranchCode() {
        return poDao.GetLatestBranchCode();
    }

}
