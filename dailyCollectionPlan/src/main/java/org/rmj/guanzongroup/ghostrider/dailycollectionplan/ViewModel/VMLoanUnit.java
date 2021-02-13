package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.LoanUnitModel;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMLoanUnit extends AndroidViewModel {
    private static final String TAG = VMLoanUnit.class.getSimpleName();
    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psEntryNox = new MutableLiveData<>();

    private MutableLiveData<String> lsBPlace = new MutableLiveData<>();
    private MutableLiveData<String> lsProvID = new MutableLiveData<>();

    private final MutableLiveData<String> spnCivilStats = new MutableLiveData<>();
    private final MutableLiveData<String> luGender = new MutableLiveData<>();
    private final MutableLiveData<String> luCivilStats = new MutableLiveData<>();

    private final RProvince RProvince;
    private final RTown RTown;
    private final RCountry RCountry;
    private final LiveData<List<EProvinceInfo>> provinceInfoList;
    public VMLoanUnit(@NonNull Application application) {
        super(application);
        this.poBranch = new RBranch(application);
        this.poDcp = new RDailyCollectionPlan(application);
        RProvince = new RProvince(application);
        RTown = new RTown(application);
        RCountry = new RCountry(application);
        provinceInfoList = RProvince.getAllProvinceInfo();
    }
    // TODO: Implement the ViewModel
    public void setParameter(String TransNox, String EntryNox){
        this.psTransNox.setValue(TransNox);
        this.psEntryNox.setValue(EntryNox);
    }

    public LiveData<EDCPCollectionMaster> getCollectionMaster(){
        return poDcp.getCollectionMaster();
    }
    public LiveData<EDCPCollectionDetail> getCollectionDetail(){
        return poDcp.getCollectionDetail(psTransNox.getValue(), psEntryNox.getValue());
    }
    public void setCurrentCollectionDetail(EDCPCollectionDetail detail){
        this.poDcpDetail.setValue(detail);
    }

    public LiveData<EBranchInfo> getUserBranchEmployee(){
        return poBranch.getUserBranchInfo();
    }
    public LiveData<List<EProvinceInfo>> getProvinceInfoList(){
        return provinceInfoList;
    }

    public LiveData<List<ETownInfo>> getTownInfoList(){
        return RTown.getTownInfoFromProvince(lsProvID.getValue());
    }

    public LiveData<List<ECountryInfo>> getCountryInfoList(){
        return RCountry.getAllCountryInfo();
    }

    public LiveData<String[]> getProvinceNameList(){
        return RProvince.getAllProvinceNames();
    }

    public LiveData<String[]> getAllTownNames(){
        return RTown.getTownNamesFromProvince(lsProvID.getValue());
    }

    public void setProvID(String ProvID) { this.lsProvID.setValue(ProvID); }
    public void setTownID(String townID){
        this.lsBPlace.setValue(townID);
    }
    //Setter Civil Status
    public void setSpnCivilStats(String type) { this.spnCivilStats.setValue(type); }
    public void setGender(String lsGender) { this.luGender.setValue(lsGender); }
    //Spinner Getter
    public LiveData<ArrayAdapter<String>> getSpnCivilStats(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, DCP_Constants.CIVIL_STATUS);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void saveLuInfo(LoanUnitModel infoModel, ViewModelCallback callback){
        try{
            infoModel.setLuGender(luGender.getValue());
            infoModel.setLuCivilStats(luCivilStats.getValue());
            if(!infoModel.isValidData()){
                callback.OnFailedResult(infoModel.getMessage());
            } else {
                String fullName = infoModel.getLuLastName() + ", " +
                        infoModel.getLuFirstName() + " " +
                        infoModel.getLuMiddleName() + " " +
                        infoModel.getLuSuffix();
                EDCPCollectionDetail detail = poDcpDetail.getValue();
                detail.setFullName(fullName);
                detail.setBrgyName(infoModel.getLuBrgy());
                detail.setHouseNox(infoModel.getLuHouseNo());
                detail.setAddressx(infoModel.getLuStreet());
                detail.setTownName(infoModel.getLuTown());
                detail.setMobileNo(infoModel.getLuMobile());
                detail.setSendStat("0");
                detail.setModified(AppConstants.DATE_MODIFIED);
                poDcp.updateCollectionDetailInfo(detail);
                //Log.e(TAG, "Promise to Pay info has been set." + poDcp.getCollectionDetail(psTransNox.getValue(),psEntryNox.getValue()).getValue().toString());
                callback.OnSuccessResult(new String[]{"Dcp Save!"});
            }
        } catch (Exception e){
            e.printStackTrace();
            callback.OnFailedResult(e.getMessage());
        }
    }
}