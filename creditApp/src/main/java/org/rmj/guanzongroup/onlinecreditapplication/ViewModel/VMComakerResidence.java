package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerResidenceModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;

public class VMComakerResidence extends AndroidViewModel {
    private static final String TAG = VMComakerResidence.class.getSimpleName();
    private ECreditApplicantInfo poInfo;
    private final GOCASApplication poGoCas;
    private final RCreditApplicant poCreditApp;
    private final RProvince poProvnce;
    private final RTown poTownRpo;
    private final RBarangay poBarangy;

    private MutableLiveData<String> psTransNox = new MutableLiveData<>();

    private MutableLiveData<String> psProvID = new MutableLiveData<>();
    private MutableLiveData<String> psTownID = new MutableLiveData<>();
    private MutableLiveData<String> psBrgyID = new MutableLiveData<>();

    public VMComakerResidence(@NonNull Application application) {
        super(application);
        this.poGoCas = new GOCASApplication();
        this.poCreditApp = new RCreditApplicant(application);
        this.poProvnce = new RProvince(application);
        this.poTownRpo = new RTown(application);
        this.poBarangy = new RBarangay(application);
    }

    public void setTransNox(String transNox){
        this.psTransNox.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicantInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(psTransNox.getValue());
    }

    public void setCreditApplicantInfo(ECreditApplicantInfo applicantInfo){
        poInfo = applicantInfo;
        poGoCas.setData(poInfo.getDetlInfo());
    }

    public LiveData<String[]> getProvinceNameList(){
        return poProvnce.getAllProvinceNames();
    }

    public LiveData<List<EProvinceInfo>> getProvinceInfo(){
        return poProvnce.getAllProvinceInfo();
    }

    public void setProvinceID(String ProvID){
        this.psProvID.setValue(ProvID);
    }

    public LiveData<String[]> getTownNameList(){
        return poTownRpo.getTownNamesFromProvince(psProvID.getValue());
    }

    public LiveData<List<ETownInfo>> getTownInfoList(){
        return poTownRpo.getTownInfoFromProvince(psProvID.getValue());
    }

    public void setTownID(String townID){
        this.psTownID.setValue(townID);
    }

    public LiveData<String[]> getBarangayListName(){
        return poBarangy.getBarangayNamesFromTown(psTownID.getValue());
    }

    public LiveData<List<EBarangayInfo>> getBarangayInfoList(){
        return poBarangy.getAllBarangayFromTown(psTownID.getValue());
    }

    public LiveData<ArrayAdapter<String>> getHouseHolds(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.HOUSEHOLDS);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getHouseType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.HOUSE_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getLenghtOfStay(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.LENGTH_OF_STAY);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void SaveCoMakerResidence(CoMakerResidenceModel infoModel, ViewModelCallBack callBack){
        if(infoModel.isDataValid()){
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setLandMark(infoModel.getsLandMark());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setHouseNo(infoModel.getsHouseNox());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setAddress1(infoModel.getsAddress1());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setAddress2(infoModel.getsAddress2());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setTownCity(infoModel.getsMuncplID());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setBarangay(infoModel.getsBrgyIDxx());
            poGoCas.CoMakerInfo().ResidenceInfo().setOwnership(infoModel.getsHouseOwn());
            poGoCas.CoMakerInfo().ResidenceInfo().setCareTakerRelation(infoModel.getsRelation());
            poGoCas.CoMakerInfo().ResidenceInfo().setOwnedResidenceInfo(infoModel.getsHouseHld());
            poGoCas.CoMakerInfo().ResidenceInfo().setHouseType(infoModel.getsHouseTpe());
            poGoCas.CoMakerInfo().ResidenceInfo().setRentedResidenceInfo(infoModel.getsHouseHld());
            poGoCas.CoMakerInfo().ResidenceInfo().setRentExpenses(infoModel.getsExpenses());
            poGoCas.CoMakerInfo().ResidenceInfo().setRentNoYears(infoModel.getsLenghtSt());
            poGoCas.CoMakerInfo().ResidenceInfo().hasGarage(infoModel.getsHasGarge());
            poInfo.setDetlInfo(poGoCas.toJSONString());
            poCreditApp.updateGOCasData(poInfo);
        } else {
            callBack.onFailedResult(infoModel.getMessage());
        }
    }

}