package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

public class VMIncompleteTransaction extends AndroidViewModel {
    private static final String TAG = VMIncompleteTransaction.class.getSimpleName();

    private final RDailyCollectionPlan poDcp;
    private final RImageInfo poImage;

    private final MutableLiveData<String> sTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> nEntryNox = new MutableLiveData<>();

    public VMIncompleteTransaction(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poImage = new RImageInfo(application);
    }

    public void setParameter(String fsTransNox, String nEntryNox){
        this.sTransNox.setValue(fsTransNox);
        this.nEntryNox.setValue(nEntryNox);
    }

    public LiveData<EDCPCollectionDetail> getCollectionDetail(){
        return poDcp.getCollectionDetail(sTransNox.getValue(), nEntryNox.getValue());
    }

    public void saveImageInfo(EImageInfo foImageInfo){
        foImageInfo.setTransNox(poDcp.getImageNextCode());
        poImage.insertImageInfo(foImageInfo);
    }
}