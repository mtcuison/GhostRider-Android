package org.rmj.g3appdriver.GRider.Database.Repositories;

import android.app.Application;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.GGC_GriderDB;

public class RDCPCollection_Detail {
    private static final String TAG = RDCPCollection_Detail.class.getSimpleName();

    private final DDCPCollectionDetail poDao;

    public RDCPCollection_Detail(Application instance) {
        this.poDao = GGC_GriderDB.getInstance(instance).DcpDetailDao();
    }
}
