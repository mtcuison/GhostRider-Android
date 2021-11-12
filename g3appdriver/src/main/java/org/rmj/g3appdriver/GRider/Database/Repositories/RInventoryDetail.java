package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

import java.util.List;

public class RInventoryDetail implements DInventoryDetail {
    private static final String TAG = RInventoryDetail.class.getSimpleName();

    private final Application instance;
    private final DInventoryDetail poDao;

    public RInventoryDetail(Application application) {
        this.instance = application;
        this.poDao = GGC_GriderDB.getInstance(instance).inventoryDetailDao();
    }

    @Override
    public void insertInventoryDetail(List<EInventoryDetail> foDetail) {
        poDao.insertInventoryDetail(foDetail);
    }
}
