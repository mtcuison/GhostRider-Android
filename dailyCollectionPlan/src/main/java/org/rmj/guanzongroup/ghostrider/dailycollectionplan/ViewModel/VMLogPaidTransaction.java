package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;

public class VMLogPaidTransaction extends AndroidViewModel {
    private RDailyCollectionPlan poCollect;

    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psAcctNox = new MutableLiveData<>();
    private final MutableLiveData<String> psRemCodex = new MutableLiveData<>();

    public VMLogPaidTransaction(@NonNull Application application) {
        super(application);
        this.poCollect = new RDailyCollectionPlan(application);
    }

    public void setParameters(String TransNox, String Acctnox, String RemCodex) {
        this.psTransNox.setValue(TransNox);
        this.psAcctNox.setValue(Acctnox);
        this.psRemCodex.setValue(RemCodex);
    }

    public LiveData<EDCPCollectionDetail> getPostedCollectionDetail() {
        return poCollect.getPostedCollectionDetail(psTransNox.getValue(), psAcctNox.getValue(), psRemCodex.getValue());
    }

}
