package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.AddressUpdate;

import java.util.ArrayList;
import java.util.List;

public class VMCustomerNotAround extends AndroidViewModel {
    private static final String TAG = VMCustomerNotAround.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDcp;

    private MutableLiveData<List<EAddressUpdate>> plAddress = new MutableLiveData<>();
    private MutableLiveData<List<EMobileUpdate>> plMobile = new MutableLiveData<>();

    public VMCustomerNotAround(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(application);
        this.plAddress.setValue(new ArrayList<>());
        this.plMobile.setValue(new ArrayList<>());
    }

    public void addAddress(AddressUpdate foAddress){
        try {
            if (foAddress.isDataValid()) {
                EAddressUpdate info = new EAddressUpdate();
                info.setTransNox("");
                info.setClientID("");
                info.setReqstCDe("");
                info.setAddrssTp("");
                info.setHouseNox("");
                info.setAddressx("");
                info.setTownIDxx("");
                info.setBrgyIDxx("");
                info.setPrimaryx("");
                info.setLongitud("");
                info.setLatitude("");
                info.setRemarksx("");
                info.setTranStat("");
                info.setSendStat("0");
                info.setModified(AppConstants.DATE_MODIFIED);
                info.setTimeStmp(AppConstants.DATE_MODIFIED);
                plAddress.getValue().add(info);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void addMobile(){

    }
}