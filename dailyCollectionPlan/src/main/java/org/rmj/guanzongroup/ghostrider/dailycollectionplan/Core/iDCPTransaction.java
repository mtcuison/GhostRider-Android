package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;

public interface iDCPTransaction {
    LiveData<EDCPCollectionDetail> getAccountInfo(String TransNox, int EntryNo);
    void OnSaveTransaction(EDCPCollectionDetail foDetail, TransactionCallback callback);

    public interface TransactionCallback{
        void OnSuccess();
        void OnFailed(String message);
    }
}
