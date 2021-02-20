package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMCoMaker extends AndroidViewModel {
    private static final String TAG = VMOtherInfo.class.getSimpleName();
    private final MutableLiveData<String> psTranNo = new MutableLiveData<>();

    private final MutableLiveData<String> spnCMakerRelation = new MutableLiveData<>();
    private MutableLiveData<String> spnCMakeIncomeSource = new MutableLiveData<>();
    private MutableLiveData<String> spnCMakeNetworkStats = new MutableLiveData<>();
    private MutableLiveData<String> primaryContact = new MutableLiveData<>();
    private MutableLiveData<String> secondaryContact = new MutableLiveData<>();
    private MutableLiveData<String> tertiaryContact = new MutableLiveData<>();

    private MutableLiveData<Integer> cmrTertiaryCntctPlan = new MutableLiveData<>();
    private MutableLiveData<Integer> cmrSecondaryCntctPlan = new MutableLiveData<>();
    private MutableLiveData<Integer> cmrPrimaryCntctPlan = new MutableLiveData<>();

    private MutableLiveData<String> lsProvID = new MutableLiveData<>();
    private MutableLiveData<String> lsBPlace = new MutableLiveData<>();

    private final GOCASApplication poGoCas;
    private final RCreditApplicant poApplcnt;
    private final RProvince RProvince;
    private final RTown RTown;
    private final RCountry RCountry;
    private final LiveData<List<EProvinceInfo>> provinceInfoList;
    public VMCoMaker(@NonNull Application application) {
        super(application);
        this.poApplcnt = new RCreditApplicant(application);
        RProvince = new RProvince(application);
        RTown = new RTown(application);
        RCountry = new RCountry(application);
        provinceInfoList = RProvince.getAllProvinceInfo();
        poGoCas = new GOCASApplication();
        this.cmrPrimaryCntctPlan.setValue(View.GONE);
        this.cmrSecondaryCntctPlan.setValue(View.GONE);
        this.cmrTertiaryCntctPlan.setValue(View.GONE);
    }
    // TODO: Implement the ViewModel
    public void setTransNox(String transNox){
        this.psTranNo.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicationInfo(){
        return poApplcnt.getCreditApplicantInfoLiveData(psTranNo.getValue());
    }

    public void setCreditApplicantInfo(String applicantInfo){
        try{
            poGoCas.setData(applicantInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
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

    //Contact Number Setter
    public void setPrimaryContact(String primaryContact){
        try {
            if(primaryContact.equalsIgnoreCase("1")){
                this.cmrPrimaryCntctPlan.setValue(View.VISIBLE);
            } else {
                this.cmrPrimaryCntctPlan.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.primaryContact.setValue(primaryContact);
    }
    public void setSecondaryContact(String secondaryContact){
        try {
            if(secondaryContact.equalsIgnoreCase("1")){
                this.cmrSecondaryCntctPlan.setValue(View.VISIBLE);
            } else {
                this.cmrSecondaryCntctPlan.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.secondaryContact.setValue(secondaryContact);
    }
    public void setTertiaryContact(String tertiaryContact){
        try {
            if(tertiaryContact.equalsIgnoreCase("1")){
                this.cmrTertiaryCntctPlan.setValue(View.VISIBLE);
            } else {
                this.cmrTertiaryCntctPlan.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.tertiaryContact.setValue(tertiaryContact);
    }


    //Spinner Setter
    public void setSpnCMakerRelation(String type)
    {
        this.spnCMakerRelation.setValue(type);
    }
    public void setSpnCMakeIncomeSource(String type)
    {
        this.spnCMakeIncomeSource.setValue(type);
    }

    public void setSpnCMakeNetworkStats(String type)
    {
        this.spnCMakeNetworkStats.setValue(type);
    }


    public LiveData<String> getCMakerRelation(){
        return this.spnCMakerRelation;
    }
    public LiveData<String> getCMakeIncomeSource(){
        return this.spnCMakeIncomeSource;
    }


    //Spinner Getter
    public LiveData<ArrayAdapter<String>> getSpnCMakerRelation(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.CO_MAKER_RELATIONSHIP);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }
    public LiveData<ArrayAdapter<String>> getSpnCMakerIncomeSource(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.CO_MAKER_INCOME_SOURCE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }
    public LiveData<ArrayAdapter<String>> getPrmryMobileNoType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.MOBILE_NO_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }
    public LiveData<ArrayAdapter<String>> getScndMobileNoType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.MOBILE_NO_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }
    public LiveData<ArrayAdapter<String>> getTrtMobileNoType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.MOBILE_NO_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getMobileNoType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.MOBILE_NO_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<Integer> getCmrTertiaryCntctPlan(){
        return cmrTertiaryCntctPlan;
    }
    public LiveData<Integer> getCmrSecondaryCntctPlan(){
        return cmrSecondaryCntctPlan;
    }
    public LiveData<Integer> getCmrPrimaryCntctPlan(){
        return cmrPrimaryCntctPlan;
    }

    public LiveData<String> getPrimaryContact(){
        return this.primaryContact;
    }
    public LiveData<String> getSecondaryContact(){
        return this.secondaryContact;
    }
    public LiveData<String> getTertiaryContact(){
        return this.tertiaryContact;}
    public boolean SubmitComaker(CoMakerModel infoModel, ViewModelCallBack callBack){
        try {

            if(infoModel.isCoMakerInfoValid()) {
                poGoCas.CoMakerInfo().setLastName(infoModel.getCoLastName());
                poGoCas.CoMakerInfo().setFirstName(infoModel.getCoFrstName());
                poGoCas.CoMakerInfo().setMiddleName(infoModel.getCoMiddName());
                poGoCas.CoMakerInfo().setSuffixName(infoModel.getCoSuffix());
                poGoCas.CoMakerInfo().setNickName(infoModel.getCoNickName());
                poGoCas.CoMakerInfo().setBirthdate(infoModel.getCoBrthDate());
                poGoCas.CoMakerInfo().setBirthPlace(infoModel.getCoBrthPlce());
                poGoCas.CoMakerInfo().setIncomeSource(infoModel.getCoIncomeSource());
                poGoCas.CoMakerInfo().setRelation(infoModel.getCoBorrowerRel());
                poGoCas.CoMakerInfo().setMobileNoQty(infoModel.getCoMobileNoQty());
                for (int x = 0; x < infoModel.getCoMobileNoQty(); x++) {
                    poGoCas.CoMakerInfo().setMobileNo(x, infoModel.getCoMobileNo(x));
                    poGoCas.CoMakerInfo().IsMobilePostpaid(x, infoModel.getCoPostPaid(x));
                    poGoCas.CoMakerInfo().setPostPaidYears(x, infoModel.getCoPostYear(x));
                }
                poGoCas.CoMakerInfo().setFBAccount(infoModel.getCoFbAccntx());
                ECreditApplicantInfo applicantInfo = new ECreditApplicantInfo();
                applicantInfo.setTransNox(Objects.requireNonNull(psTranNo.getValue()));
                applicantInfo.setClientNm(poGoCas.ApplicantInfo().getClientName());
                applicantInfo.setDetlInfo(poGoCas.toJSONString());
                poApplcnt.updateGOCasData(applicantInfo);
                Log.e(TAG, "Co-Maker info has been set." + poGoCas.toJSONString());
                callBack.onSaveSuccessResult(psTranNo.getValue());
                return true;
            } else {
                callBack.onFailedResult(infoModel.getMessage());
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
            return false;
        }
    }
}