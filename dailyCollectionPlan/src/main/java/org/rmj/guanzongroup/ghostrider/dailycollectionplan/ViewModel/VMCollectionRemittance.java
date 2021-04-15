package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;

public class VMCollectionRemittance extends AndroidViewModel {

    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final RDCP_Remittance poRemit;

    public VMCollectionRemittance(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poBranch = new RBranch(application);
        this.poRemit = new RDCP_Remittance(application);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<String> getTotalCollectedCash(){
        return poDcp.getCollectedTotalPayment();
    }

    public LiveData<String> getTotalCollectedCheck(){
        return poDcp.getCollectedTotalCheckPayment();
    }

    public LiveData<String> getTotalCashRemittedCollection(){
        return poRemit.getTotalCashRemittedCollection();
    }

    public LiveData<String> getTotalCheckRemittedCollection(){
        return poRemit.getTotalCheckRemittedCollection();
    }


    public LiveData<String> getTotalBranchRemittedCollection(){
        return poRemit.getTotalBranchRemittedCollection();
    }

    public LiveData<String> getTotalBankRemittedCollection(){
        return poRemit.getTotalBankRemittedCollection();
    }

    public LiveData<String> getTotalOtherRemittedCollection(){
        return poRemit.getTotalOtherRemittedCollection();
    }
}
