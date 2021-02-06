package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import org.rmj.g3appdriver.GRider.Database.AppDatabase;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;

import java.util.List;

public class RDailyCollectionPlan {
    private final DDCPCollectionDetail detailDao;
    private final DDCPCollectionMaster masterDao;

    public RDailyCollectionPlan(Application application){
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        detailDao = appDatabase.DcpDetailDao();
        masterDao = appDatabase.DcpMasterDao();
    }

    public void insertDetailBulkData(List<EDCPCollectionDetail> collectionDetails){
        detailDao.insertBulkData(collectionDetails);
    }

    public void insertMasterData(EDCPCollectionMaster collectionMaster){
        masterDao.insert(collectionMaster);
    }
}
