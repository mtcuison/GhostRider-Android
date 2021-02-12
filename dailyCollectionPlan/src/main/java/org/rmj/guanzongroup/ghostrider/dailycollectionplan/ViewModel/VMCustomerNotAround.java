package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.AddressUpdate;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMCustomerNotAround extends AndroidViewModel {
    private static final String TAG = VMCustomerNotAround.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDcp;

    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psEntryNox = new MutableLiveData<>();
    private final MutableLiveData<String> clientID = new MutableLiveData<>();
    private final MutableLiveData<String> requestCode = new MutableLiveData<>();
    private MutableLiveData<List<EAddressUpdate>> plAddress = new MutableLiveData<>();
    private MutableLiveData<List<EMobileUpdate>> plMobile = new MutableLiveData<>();

    public VMCustomerNotAround(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(application);
        this.plAddress.setValue(new ArrayList<>());
        this.plMobile.setValue(new ArrayList<>());
    }

    public void setParameter(String TransNox, String EntryNox){
        this.psTransNox.setValue(TransNox);
        this.psEntryNox.setValue(EntryNox);
    }

    public void setClientID(String clientID) {
        this.clientID.setValue(clientID);
    }

    public void setRequestCode(String requestCode) {
        this.requestCode.setValue(requestCode);
    }

    public LiveData<EDCPCollectionDetail> getCollectionDetail(){
        return poDcp.getCollectionDetail(psTransNox.getValue(), psEntryNox.getValue());
    }

    public void setCurrentCollectionDetail(EDCPCollectionDetail detail){
        this.poDcpDetail.setValue(detail);
    }

    public LiveData<ArrayAdapter<String>> getRequestCodeOptions() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, DCP_Constants.REQUEST_CODE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void addAddress(AddressUpdate foAddress){
        try {
            foAddress.setRequestCode(requestCode.getValue());
            if (foAddress.isDataValid()) {
                EAddressUpdate info = new EAddressUpdate();
                info.setTransNox(Objects.requireNonNull(psTransNox.getValue()));
                info.setClientID(Objects.requireNonNull(clientID.getValue()));
                info.setReqstCDe(foAddress.getRequestCode());
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