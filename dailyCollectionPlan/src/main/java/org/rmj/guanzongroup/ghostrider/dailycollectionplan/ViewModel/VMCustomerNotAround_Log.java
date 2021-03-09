package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressRequest;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMobileRequest;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;

import java.util.List;

public class VMCustomerNotAround_Log extends AndroidViewModel {
    private static final String TAG = VMCustomerNotAround_Log.class.getSimpleName();
    private RCollectionUpdate poUpdate;

    private final MutableLiveData<String> sClientID = new MutableLiveData<>();

    public VMCustomerNotAround_Log(@NonNull Application application) {
        super(application);
        this.poUpdate = new RCollectionUpdate(application);
    }

    public void setClientID(String sClientID) {
        this.sClientID.setValue(sClientID);
    }

    public LiveData<List<DMobileRequest.CNAMobileInfo>> getCNA_MobileDataList() {
        return poUpdate.getCNA_MobileDataList(sClientID.getValue());
    }

    public LiveData<List<DAddressRequest.CNA_AddressInfo>> getCNA_AddressDataList() {
        return poUpdate.getCNA_AddressDataList(sClientID.getValue());
    }
}
