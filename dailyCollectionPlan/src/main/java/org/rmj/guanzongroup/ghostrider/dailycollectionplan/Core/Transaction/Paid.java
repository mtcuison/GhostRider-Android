package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.Transaction;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.iDCPTransaction;

public class Paid implements iDCPTransaction {

    private final Application instance;
    private final RDailyCollectionPlan poDcp;

    public Paid(Application application) {
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(instance);
    }

    @Override
    public LiveData<EDCPCollectionDetail> getAccountInfo(String TransNox, int EntryNo) {
        return poDcp.getCollectionDetail(TransNox, EntryNo);
    }

    @Override
    public void OnSaveTransaction(EDCPCollectionDetail foDetail, TransactionCallback callback) {
        try{
//            String lsResponse = WebClient.sendRequest()
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailed("OnSaveTransaction" + e.getMessage());
        }
    }
}
