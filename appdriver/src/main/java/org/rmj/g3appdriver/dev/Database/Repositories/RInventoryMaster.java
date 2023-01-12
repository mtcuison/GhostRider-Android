package org.rmj.g3appdriver.dev.Database.Repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DInventoryMaster;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EInventoryMaster;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

import java.util.List;

public class RInventoryMaster implements DInventoryMaster {
    private static final String TAG = RInventoryMaster.class.getSimpleName();

    private final Application instance;
    private final DInventoryMaster poDao;

    public RInventoryMaster(Application application) {
        this.instance = application;
        this.poDao = GGC_GriderDB.getInstance(instance).inventoryMasterDao();
    }

    @Override
    public void insertInventoryMaster(EInventoryMaster foMaster) {
        this.poDao.insertInventoryMaster(foMaster);
    }

    @Override
    public LiveData<EInventoryMaster> getInventoryMasterForBranch(String Transact, String BranchCd) {
        return poDao.getInventoryMasterForBranch(Transact, BranchCd);
    }

    @Override
    public EInventoryMaster getInventoryMasterForPosting(String TransNox) {
        return poDao.getInventoryMasterForPosting(TransNox);
    }

    @Override
    public void UpdateInventoryMasterRemarks(String TransNox, String Remarks) {
        poDao.UpdateInventoryMasterRemarks(TransNox, Remarks);
    }

    @Override
    public void UpdateInventoryMasterPostedStatus(String TransNox) {
        poDao.UpdateInventoryMasterPostedStatus(TransNox);
    }

    @Override
    public List<EBranchInfo> GetBranchesForInventory() {
        return null;
    }


}

