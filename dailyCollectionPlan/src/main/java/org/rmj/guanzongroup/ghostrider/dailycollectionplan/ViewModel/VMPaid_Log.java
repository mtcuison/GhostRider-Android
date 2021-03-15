package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;

public class VMPaid_Log extends AndroidViewModel {
    private RDailyCollectionPlan poCollect;

    private MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private MutableLiveData<String> psEntryNox = new MutableLiveData<>();
    private MutableLiveData<String> psAcctNox = new MutableLiveData<>();

    public VMPaid_Log(@NonNull Application application) {
        super(application);
        this.poCollect = new RDailyCollectionPlan(application);
    }

    public void setParameters(String TransNox, String EntryNox, String Acctnox) {
        this.psTransNox.setValue(TransNox);
        this.psEntryNox.setValue(EntryNox);
        this.psAcctNox.setValue(Acctnox);
    }

    public LiveData<EDCPCollectionDetail> getPaidCollectionDetail() {
        return poCollect.getPaidCollectionDetail(psTransNox.getValue(), psAcctNox.getValue());
    }

}
