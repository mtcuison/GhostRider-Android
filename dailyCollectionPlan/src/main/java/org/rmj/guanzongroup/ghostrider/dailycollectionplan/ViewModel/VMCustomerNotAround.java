package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.AddressUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.MobileUpdate;

import java.util.ArrayList;
import java.util.List;

public class VMCustomerNotAround extends AndroidViewModel {
    private static final String TAG = VMCustomerNotAround.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDcp;
    private final RCollectionUpdate poUpdate;
    private final RTown poTown;
    private final RBarangay poBrgy;

    private final MutableLiveData<String> psTownID = new MutableLiveData<>();

    public VMCustomerNotAround(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDcp = new RDailyCollectionPlan(application);
        this.poUpdate = new RCollectionUpdate(application);
        this.poTown = new RTown(application);
        this.poBrgy = new RBarangay(application);
    }

    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return poTown.getTownProvinceInfo();
    }

    public void setTownID(String fsID){
         this.psTownID.setValue(fsID);
    }

    public LiveData<String[]> getBarangayNameList(){
        return poBrgy.getBarangayNamesFromTown(psTownID.getValue());
    }

    public LiveData<List<EBarangayInfo>> getBarangayInfoList(){
        return poBrgy.getAllBarangayFromTown(psTownID.getValue());
    }

    public LiveData<List<EAddressUpdate>> getAddressRequestList(){
        return poUpdate.getAddressList();
    }

    public LiveData<List<EMobileUpdate>> getMobileRequestList(){
        return poUpdate.getMobileList();
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
                poUpdate.insertUpdateAddress(info);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteAddress(String TransNox){
        poUpdate.deleteAddress(TransNox);
    }

    public void addMobile(MobileUpdate foMobile){
        try{
            if(foMobile.isDataValid()){
                EMobileUpdate info = new EMobileUpdate();
                info.setTransNox("");
                info.setClientID("");
                info.setReqstCDe(foMobile.getcReqstCde());
                info.setMobileNo(foMobile.getsMobileNo());
                info.setPrimaryx(foMobile.getcPrimaryx());
                info.setRemarksx(foMobile.getsRemarksx());
                info.setTranStat("");
                info.setSendStat("0");
                info.setModified(AppConstants.DATE_MODIFIED);
                info.setTimeStmp(AppConstants.DATE_MODIFIED);
                poUpdate.insertUpdateMobile(info);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteMobile(String TransNox){
        poUpdate.deleteMobile(TransNox);
    }
}