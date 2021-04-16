package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;

public class VMCollectionRemittance extends AndroidViewModel {

    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final RDCP_Remittance poRemit;

    private String dTransact;

    public VMCollectionRemittance(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poBranch = new RBranch(application);
        this.poRemit = new RDCP_Remittance(application);
    }

    public void setTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<String> getTotalCollectedCash(){
        return poDcp.getCollectedTotalPayment(dTransact);
    }

    public LiveData<String> getTotalCollectedCheck(){
        return poDcp.getCollectedTotalCheckPayment(dTransact);
    }

    public LiveData<String> getTotalCashRemittedCollection(){
        return poRemit.getTotalCashRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalCheckRemittedCollection(){
        return poRemit.getTotalCheckRemittedCollection(dTransact);
    }


    public LiveData<String> getTotalBranchRemittedCollection(){
        return poRemit.getTotalBranchRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalBankRemittedCollection(){
        return poRemit.getTotalBankRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalOtherRemittedCollection(){
        return poRemit.getTotalOtherRemittedCollection(dTransact);
    }
}
