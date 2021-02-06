package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.media.tv.TvTrackInfo;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplication;
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

    // Declare variable what you need to observe
    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();
    private MutableLiveData<String> lsCitizen = new MutableLiveData<>();


    public VMSpouseInfo(@NonNull Application application) { // Application is context
        super(application);
        poGoCas = new GOCASApplication();
        poCreditApp = new RCreditApplicant(application);
        poProvRepo = new RProvince(application);
        poTownRepo = new RTown(application);
        RCountry = new RCountry(application);
    }

    // Set TransNox to be saved in applicant info entity
    public void setTransNox(String fsTransNox){
        this.TransNox.setValue(fsTransNox);
    }

    // Get single and current input record based from transNox
    public LiveData<ECreditApplicantInfo> getActiveGOCasApplication(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
    }

   //  Set Detail info to GoCas
    public void setDetailInfo(String fsDetailInfo){
        try{
             poGoCas.setData(fsDetailInfo);
        } catch (Exception e){
            e.printStackTrace();
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
    public void setProvinceID(String fsID){
        this.psProvID.setValue(fsID);
    }
    public void setTownID(String fsID){
        this.psTownID.setValue(fsID);
    }
    public void setCitizenship(String lsCitizen) {
        this.lsCitizen.setValue(lsCitizen);
    }


    public void Save(SpouseInfoModel infoModel, ViewModelCallBack callBack){
        try{
            infoModel.setBirthPlace(psTownID.getValue());
            if(infoModel.isSpouseInfoValid()){
                // Get all the set data from fragment_spouse
                poGoCas.SpouseInfo().PersonalInfo().setLastName(infoModel.getLastName());
                poGoCas.SpouseInfo().PersonalInfo().setFirstName(infoModel.getFrstName());
                poGoCas.SpouseInfo().PersonalInfo().setMiddleName(infoModel.getMiddName());
                poGoCas.SpouseInfo().PersonalInfo().setSuffixName(infoModel.getSuffix());
                poGoCas.SpouseInfo().PersonalInfo().setNickName(infoModel.getNickName());
                poGoCas.SpouseInfo().PersonalInfo().setBirthdate(infoModel.getBirthDate());
                poGoCas.SpouseInfo().PersonalInfo().setBirthPlace(psTownID.getValue());
                poGoCas.SpouseInfo().PersonalInfo().setCitizenship(lsCitizen.getValue());

                // Contact?
                poGoCas.SpouseInfo().PersonalInfo().setMobileNoQty(infoModel.getMobileNoQty());
                for (int x = 0; x < infoModel.getMobileNoQty(); x++) {
                    // Debugging purposes
                    Log.e("NUMERO " + x , infoModel.getMobileNo(x));
                    poGoCas.SpouseInfo().PersonalInfo().setMobileNo(x, infoModel.getMobileNo(x));
                    poGoCas.SpouseInfo().PersonalInfo().IsMobilePostpaid(x, infoModel.getPostPaid(x));
                    poGoCas.SpouseInfo().PersonalInfo().setPostPaidYears(x, infoModel.getPostYear(x));
                }
                // Contact?

                poGoCas.SpouseInfo().PersonalInfo().setPhoneNoQty(1);
                poGoCas.SpouseInfo().PersonalInfo().setEmailAddQty(1);
                poGoCas.SpouseInfo().PersonalInfo().setPhoneNo(0, infoModel.getPhoneNo());
                poGoCas.SpouseInfo().PersonalInfo().setEmailAddress(0, infoModel.getEmailAdd());
                poGoCas.SpouseInfo().PersonalInfo().setFBAccount(infoModel.getFBacct());
                poGoCas.SpouseInfo().PersonalInfo().setViberAccount(infoModel.getVbrAcct());


                ECreditApplicantInfo applicantInfo = new ECreditApplicantInfo();
                applicantInfo.setTransNox(TransNox.getValue());
                applicantInfo.setDetlInfo(poGoCas.toJSONString());
                applicantInfo.setClientNm(poGoCas.ApplicantInfo().getClientName());
                poCreditApp.updateGOCasData(applicantInfo);

                //Added by sir mike
                Log.e(TAG, poGoCas.SpouseInfo().PersonalInfo().toJSONString());
                Log.e(TAG, "GOCAS Full JSON String : " + poGoCas.toJSONString());
                callBack.onSaveSuccessResult(TransNox.getValue());

//                Log.e(TAG, "GOCAS Full JSON String : " + poGoCas.toJSONString());
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
        }
    }
}