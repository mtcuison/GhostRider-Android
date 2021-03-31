package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.g3appdriver.utils.AgeCalculator;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;
import java.util.Objects;

public class VMPersonalInfo extends AndroidViewModel {
    private static final String TAG = "Personal Info";
    private ECreditApplicantInfo poInfo;
    private final GOCASApplication poGoCas;
    private final RCreditApplicant RCreditApplicant;
    private final RProvince RProvince;
    private final RTown RTown;
    private final RCountry RCountry;

    private final LiveData<List<EProvinceInfo>> provinceInfoList;

    private final MutableLiveData<PersonalInfoModel> poModel = new MutableLiveData<>();

    private final MutableLiveData<String> TRANSNOX = new MutableLiveData<>();
    private final MutableLiveData<String> lsProvID = new MutableLiveData<>();
    private final MutableLiveData<String> lsBPlace = new MutableLiveData<>();
    private final MutableLiveData<String> lsGender = new MutableLiveData<>();
    private final MutableLiveData<String> lsCvlStats = new MutableLiveData<>();
    private final MutableLiveData<Integer> lnMthrNme = new MutableLiveData<>();
    private final MutableLiveData<String> lsCitizen = new MutableLiveData<>();

    public VMPersonalInfo(@NonNull Application application){
        super(application);
        RCreditApplicant = new RCreditApplicant(application);
        RProvince = new RProvince(application);
        RTown = new RTown(application);
        RCountry = new RCountry(application);
        provinceInfoList = RProvince.getAllProvinceInfo();
        poGoCas = new GOCASApplication();
        this.lnMthrNme.setValue(View.GONE);
        this.poModel.setValue(new PersonalInfoModel());
    }

    public LiveData<PersonalInfoModel> getPersonalInfoModel(){
        return poModel;
    }

    public void setTransNox(String transNox){
        this.TRANSNOX.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicantInfo(){
        return RCreditApplicant.getCreditApplicantInfoLiveData(TRANSNOX.getValue());
    }

    public void setGOCasDetailInfo(ECreditApplicantInfo DetailInfo){
        try {
            poInfo = DetailInfo;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void SavePersonalInfo(PersonalInfoModel infoModel, ViewModelCallBack callBack){
        try {
            infoModel.setBrthPlce(lsBPlace.getValue());
            infoModel.setCitizenx(lsCitizen.getValue());
            infoModel.setGender(lsGender.getValue());
            infoModel.setCvlStats(lsCvlStats.getValue());
            if(infoModel.isPersonalInfoValid()) {
                poModel.setValue(infoModel);
                poGoCas.ApplicantInfo().setLastName(infoModel.getLastName());
                poGoCas.ApplicantInfo().setFirstName(infoModel.getFrstName());
                poGoCas.ApplicantInfo().setMiddleName(infoModel.getMiddName());
                poGoCas.ApplicantInfo().setSuffixName(infoModel.getSuffix());
                poGoCas.ApplicantInfo().setNickName(infoModel.getNickName());
                poGoCas.ApplicantInfo().setBirthdate(infoModel.getBrthDate());
                poGoCas.ApplicantInfo().setBirthPlace(infoModel.getBrthPlce());
                poGoCas.ApplicantInfo().setGender(infoModel.getGender());
                poGoCas.ApplicantInfo().setCivilStatus(infoModel.getCvlStats());
                poGoCas.ApplicantInfo().setCitizenship(infoModel.getCitizenx());
                poGoCas.ApplicantInfo().setMaidenName(infoModel.getMotherNm());
                poGoCas.ApplicantInfo().setMobileNoQty(infoModel.getMobileNoQty());
                for (int x = 0; x < infoModel.getMobileNoQty(); x++) {
                    poGoCas.ApplicantInfo().setMobileNo(x, infoModel.getMobileNo(x));
                    poGoCas.ApplicantInfo().IsMobilePostpaid(x, infoModel.getPostPaid(x));
                    poGoCas.ApplicantInfo().setPostPaidYears(x, infoModel.getPostYear(x));
                }
                poGoCas.ApplicantInfo().setPhoneNoQty(1);
                poGoCas.ApplicantInfo().setPhoneNo(0, infoModel.getPhoneNox());
                poGoCas.ApplicantInfo().setEmailAddQty(1);
                poGoCas.ApplicantInfo().setEmailAddress(0, infoModel.getEmailAdd());
                poGoCas.ApplicantInfo().setFBAccount(infoModel.getFbAccntx());
                poGoCas.ApplicantInfo().setViberAccount(infoModel.getVbrAccnt());
                poInfo.setClientNm(poGoCas.ApplicantInfo().getClientName());
                poInfo.setApplInfo(poGoCas.ApplicantInfo().toJSONString());
                int age = AgeCalculator.getAge(poGoCas.ApplicantInfo().getBirthdate());
                if(age <=17 || age >= 60){
                    poInfo.setIsComakr("1");
                } else {
                    poInfo.setIsComakr("0");
                }

                if(poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("1") ||
                 poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("5")){
                    poInfo.setIsSpouse("1");
                } else {
                    poInfo.setIsSpouse("0");
                }
                RCreditApplicant.updateGOCasData(poInfo);
                callBack.onSaveSuccessResult(TRANSNOX.getValue());
            } else {
                infoModel.clearMobileNo();
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            infoModel.clearMobileNo();
            callBack.onFailedResult(e.getMessage());
        }
    }

    public void setProvID(String ProvID) {
        this.lsProvID.setValue(ProvID);
    }

    public void setTownID(String townID){
        this.lsBPlace.setValue(townID);
    }

    public void setGender(String lsGender) {
        try {
            if (lsGender.equalsIgnoreCase("1") && Objects.requireNonNull(lsCvlStats.getValue()).equalsIgnoreCase("1")) {
                this.lnMthrNme.setValue(View.VISIBLE);
            } else {
                this.lnMthrNme.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.lsGender.setValue(lsGender);
    }

    public void setCvlStats(String lsCvlStats) {
        try {
            if (Objects.requireNonNull(lsGender.getValue()).equalsIgnoreCase("1") && lsCvlStats.equalsIgnoreCase("1")) {
                this.lnMthrNme.setValue(View.VISIBLE);
            } else {
                this.lnMthrNme.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        this.lsCvlStats.setValue(lsCvlStats);
    }

    public void setCitizenship(String lsCitizen) {
        this.lsCitizen.setValue(lsCitizen);
    }

    public LiveData<Integer> setMotherMaidenNameVisibility(){
        return lnMthrNme;
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

    public LiveData<ArrayAdapter<String>> getCivilStatus(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.CIVIL_STATUS);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<String[]> getAllCountryCitizenNames(){
        return RCountry.getAllCountryCitizenName();
    }

    public LiveData<ArrayAdapter<String>> getMobileNoType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.MOBILE_NO_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }
}