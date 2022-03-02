package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;

public interface iDCPTransaction {
    LiveData<EDCPCollectionDetail> getAccountInfo(String TransNox, int EntryNo);
    void SaveAddress(EAddressUpdate foAddress);
    void SaveMobileUpdate(EMobileUpdate foAddress);
    void SaveImageInfo(EImageInfo foImage);
    void OnSaveTransaction(EDCPCollectionDetail foDetail, TransactionCallback callback);

    public interface TransactionCallback{
        void OnSuccess();
        void OnFailed(String message);
    }
}
