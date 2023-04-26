package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EAddressUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EImageInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EMobileUpdate;
import org.rmj.g3appdriver.dev.Database.GCircle.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.iDCPTransaction;

public class OthTransaction implements iDCPTransaction {

    private final Application instance;
    private final RDailyCollectionPlan poDcp;

    public OthTransaction(Application application) {
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(instance);
    }

    @Override
    public LiveData<EDCPCollectionDetail> getAccountInfo(String TransNox, int EntryNo) {
        return poDcp.getCollectionDetail(TransNox, EntryNo);
    }

    @Override
    public void SaveAddress(EAddressUpdate foAddress) {
        throw new NullPointerException();
    }

    @Override
    public void SaveMobileUpdate(EMobileUpdate foAddress) {
        throw new NullPointerException();
    }

    @Override
    public void SaveImageInfo(EImageInfo foImage) {

    }

    @Override
    public void OnSaveTransaction(EDCPCollectionDetail foDetail, TransactionCallback callback) {
        poDcp.updateCollectionDetail(foDetail.getEntryNox(), foDetail.getRemCodex(), foDetail.getRemarksx());
    }
}
