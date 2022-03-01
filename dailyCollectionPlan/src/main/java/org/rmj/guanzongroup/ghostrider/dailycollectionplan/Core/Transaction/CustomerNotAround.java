package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.iDCPTransaction;

public class CustomerNotAround implements iDCPTransaction {

    private final Application instance;
    private final RDailyCollectionPlan poDcp;

    public CustomerNotAround(Application application) {
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(instance);
    }

    @Override
    public LiveData<EDCPCollectionDetail> getAccountInfo(String TransNox, int EntryNo) {
        return poDcp.getCollectionDetail(TransNox, EntryNo);
    }

    @Override
    public void SaveAddress(EAddressUpdate foAddress) {

    }

    @Override
    public void SaveMobileUpdate(EMobileUpdate foAddress) {

    }

    @Override
    public void SaveImageInfo(EImageInfo foImage) {

    }

    @Override
    public void OnSaveTransaction(EDCPCollectionDetail foDetail, TransactionCallback callback) {

    }
}
