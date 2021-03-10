package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMobileRequest;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;

import java.util.List;

public class VMCustomerNotAround_Log extends AndroidViewModel {
    private static final String TAG = VMCustomerNotAround_Log.class.getSimpleName();
    private RCollectionUpdate poUpdate;
    private RImageInfo poImage;

    private final MutableLiveData<String> sClientID = new MutableLiveData<>();

    public VMCustomerNotAround_Log(@NonNull Application application) {
        super(application);
        this.poUpdate = new RCollectionUpdate(application);
        this.poImage = new RImageInfo(application);
    }

    public void setClientID(String sClientID) {
        this.sClientID.setValue(sClientID);
    }

    public LiveData<EImageInfo> getImageLocation(String sDtlSrcNo, String sImageNme) {
        return poImage.getImageLocation(sDtlSrcNo, sImageNme);
    }

    public LiveData<List<DMobileRequest.CNAMobileInfo>> getCNA_MobileDataList() {
        return poUpdate.getCNA_MobileDataList(sClientID.getValue());
    }

    public LiveData<List<DAddressRequest.CNA_AddressInfo>> getCNA_AddressDataList() {
        return poUpdate.getCNA_AddressDataList(sClientID.getValue());
    }
}
