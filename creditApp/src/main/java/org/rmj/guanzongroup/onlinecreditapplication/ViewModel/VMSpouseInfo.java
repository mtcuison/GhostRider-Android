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
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;

public class VMSpouseInfo extends AndroidViewModel {
    private static final String TAG = VMSpouseInfo.class.getSimpleName();
    private final GOCASApplication poGoCas; //Java
    private final RCreditApplicant poCreditApp; //Insert Update
    private final RProvince poProvRepo; //Province Repository
    private final RTown poTownRepo; //Town Repository
    private final RCountry RCountry;

    private ECreditApplicantInfo poInfo;

    // Declare variable what you need to observe
    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();
    private final MutableLiveData<String> lsCitizen = new MutableLiveData<>();


    private final MutableLiveData<String> lsMobile1 = new MutableLiveData<>();
    private final MutableLiveData<String> lsMobile2 = new MutableLiveData<>();
    private final MutableLiveData<String> lsMobile3 = new MutableLiveData<>();

    private final MutableLiveData<Integer> mobileNo1Year = new MutableLiveData<>();
    private final MutableLiveData<Integer> mobileNo2Year = new MutableLiveData<>();
    private final MutableLiveData<Integer> mobileNo3Year = new MutableLiveData<>();
    public VMSpouseInfo(@NonNull Application application) { // Application is context
        super(application);
        poGoCas = new GOCASApplication();
        poCreditApp = new RCreditApplicant(application);
        poProvRepo = new RProvince(application);
        poTownRepo = new RTown(application);
        RCountry = new RCountry(application);
        this.mobileNo1Year.setValue(View.GONE);
        this.mobileNo2Year.setValue(View.GONE);
        this.mobileNo3Year.setValue(View.GONE);
    }

    // Set TransNox to be saved in applicant info entity
    public boolean setTransNox(String fsTransNox){
        this.TransNox.setValue(fsTransNox);
        if(!this.TransNox.getValue().equalsIgnoreCase(fsTransNox)) {
            return false;
        }
        return true;
    }

    // Get single and current input record based from transNox
    public LiveData<ECreditApplicantInfo> getActiveGOCasApplication(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
    }

    //  Set Detail info to GoCas
    public boolean setDetailInfo(ECreditApplicantInfo fsDetailInfo){
        try{
            poInfo = fsDetailInfo;
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    // Get all Province Names in a form of LiveData<String[]> ~> For suggest/dropdown in fragment field
    public LiveData<String[]> getProvinceNames() {
        return poProvRepo.getAllProvinceNames();
    }

    // Get all of the Province table fields ~> For province ID  saving and town selection reference
    public LiveData<List<EProvinceInfo>> getProvinceInfos() {
        return poProvRepo.getAllProvinceInfo();
    }

    // Get all Town Names in a form of LiveData<String[]> depending in the provided Province ID ~> For suggest/dropdown in fragment field
    public LiveData<String[]> getAllTownNames() {
        return poTownRepo.getTownNamesFromProvince(psProvID.getValue());
    }

    // Get all of the Town table fields ~> For Town ID saving and town selection reference
    public LiveData<List<ETownInfo>> getAllTownInfo() {
        return poTownRepo.getTownInfoFromProvince(psProvID.getValue());
    }

    // Get all of the Citizen names in the form of string
    public LiveData<String[]> getAllCountryCitizenNames(){
        return RCountry.getAllCountryCitizenName();
    }


    public LiveData<List<ECountryInfo>> getCountryInfoList(){
        return RCountry.getAllCountryInfo();
    }

    public LiveData<ArrayAdapter<String>> getMobileNoType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.MOBILE_NO_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    //Province and Town ID Setters inputs in the ViewModel.
    public boolean setProvinceID(String fsID){
        this.psProvID.setValue(fsID);
        if(!this.psProvID.getValue().equalsIgnoreCase(fsID)) {
            return false;
        }
        return true;
    }
    public boolean setTownID(String fsID){
        this.psTownID.setValue(fsID);
        if(!this.psTownID.getValue().equalsIgnoreCase(fsID)) {
            return false;
        }
        return true;
    }
    public boolean setCitizenship(String lsCitizen) {
        this.lsCitizen.setValue(lsCitizen);
        if(!this.lsCitizen.getValue().equalsIgnoreCase(lsCitizen)) {
            return false;
        }
        return true;
    }

    public LiveData<Integer> getMobileNo3Year(){
        return this.mobileNo3Year;
    }
    public LiveData<Integer> getMobileNo2Year(){
        return this.mobileNo2Year;
    }
    public LiveData<Integer> getMobileNo1Year(){
        return this.mobileNo1Year;
    }
    public LiveData<String> getMobileNo3(){
        return this.lsMobile3;
    }
    public LiveData<String> getMobileNo2(){
        return this.lsMobile2;
    }
    public LiveData<String> getMobileNo1(){
        return this.lsMobile1;
    }
    public void setLsMobile1(String mobile1){
        try {
            if(mobile1.equalsIgnoreCase("1")){
                this.mobileNo1Year.setValue(View.VISIBLE);
            } else {
                this.mobileNo1Year.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.lsMobile1.setValue(mobile1);
    }
    public void setLsMobile2(String mobile2){
        try {
            if(mobile2.equalsIgnoreCase("1")){
                this.mobileNo2Year.setValue(View.VISIBLE);
            } else {
                this.mobileNo2Year.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.lsMobile2.setValue(mobile2);
    }
    public void setLsMobile3(String mobile3){
        try {
            if(mobile3.equalsIgnoreCase("1")){
                this.mobileNo3Year.setValue(View.VISIBLE);
            } else {
                this.mobileNo3Year.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.lsMobile3.setValue(mobile3);
    }
    public boolean Save(SpouseInfoModel infoModel, ViewModelCallBack callBack) {
        try{
            infoModel.setBirthPlace(psTownID.getValue());
            if(infoModel.isSpouseInfoValid()) {
                // Get all the set data from fragment_spouse
                poGoCas.SpouseInfo().PersonalInfo().setLastName(infoModel.getLastName());
                poGoCas.SpouseInfo().PersonalInfo().setFirstName(infoModel.getFrstName());
                poGoCas.SpouseInfo().PersonalInfo().setMiddleName(infoModel.getMiddName());
                poGoCas.SpouseInfo().PersonalInfo().setSuffixName(infoModel.getSuffix());
                poGoCas.SpouseInfo().PersonalInfo().setNickName(infoModel.getNickName());
                poGoCas.SpouseInfo().PersonalInfo().setBirthdate(infoModel.getBirthDate());
                poGoCas.SpouseInfo().PersonalInfo().setBirthPlace(psTownID.getValue());
                poGoCas.SpouseInfo().PersonalInfo().setCitizenship(lsCitizen.getValue());

                poGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(infoModel.getMobileNoQty());
                for (int x = 0; x < infoModel.getMobileNoQty(); x++) {
                    // Debugging purposes
                    Log.e("NUMERO " + x , infoModel.getMobileNo(x));
                    poGoCas.SpouseInfo().PersonalInfo().setMobileNo(x, infoModel.getMobileNo(x));
                    poGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(x, infoModel.getPostPaid(x));
                    poGoCas.SpouseInfo().PersonalInfo().setPostPaidYears(x, infoModel.getPostYear(x));
                }

                poGoCas.SpouseInfo().PersonalInfo().setPhoneNoQty(1);
                poGoCas.SpouseInfo().PersonalInfo().setEmailAddQty(1);
                poGoCas.SpouseInfo().PersonalInfo().setPhoneNo(0, infoModel.getPhoneNo());
                poGoCas.SpouseInfo().PersonalInfo().setEmailAddress(0, infoModel.getEmailAdd());
                poGoCas.SpouseInfo().PersonalInfo().setFBAccount(infoModel.getFBacct());
                poGoCas.SpouseInfo().PersonalInfo().setViberAccount(infoModel.getVbrAcct());
                poInfo.setTransNox(TransNox.getValue());
                poInfo.setSpousexx(poGoCas.SpouseInfo().PersonalInfo().toJSONString());
                poCreditApp.updateGOCasData(poInfo);

                Log.e(TAG, poGoCas.SpouseInfo().PersonalInfo().toJSONString());
                Log.e(TAG, "GOCAS Full JSON String : " + poGoCas.toJSONString());
                callBack.onSaveSuccessResult(TransNox.getValue());
                return true;
            } else {
                infoModel.clearMobileNo();
                callBack.onFailedResult(infoModel.getMessage());
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            infoModel.clearMobileNo();
            callBack.onFailedResult(e.getMessage());
            return false;
        }
    }
}