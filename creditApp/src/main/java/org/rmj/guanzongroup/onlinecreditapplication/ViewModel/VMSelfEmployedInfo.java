package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;
import java.util.Objects;

public class VMSelfEmployedInfo extends AndroidViewModel {
    private static final String TAG = VMSelfEmployedInfo.class.getSimpleName();
    private final GOCASApplication poGoCas;
    private final RCreditApplicant poCreditApp;
    private final RProvince poProvRepo;
    private final RTown poTownRepo;

    private final MutableLiveData<JSONObject> poMeans = new MutableLiveData<>();
    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();


    private final MutableLiveData<String> psBsnssNature = new MutableLiveData<>();
    private final MutableLiveData<String> psBsnssType = new MutableLiveData<>();
    private final MutableLiveData<String> psBsnssSize = new MutableLiveData<>();
    private final MutableLiveData<String> pslngthStay = new MutableLiveData<>();

    public VMSelfEmployedInfo(@NonNull Application application) {
        super(application);
        poCreditApp = new RCreditApplicant(application);
        poProvRepo = new RProvince(application);
        poTownRepo = new RTown(application);
        poGoCas = new GOCASApplication();
    }

    public void setTransNox(String fsTransnox){
        this.TransNox.setValue(fsTransnox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicantInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
    }

    public void setGOCasDetailInfo(String fsDetailInfo){
        try{
            poGoCas.setData(fsDetailInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMeansInfos(String fsJson){
        try{
            JSONObject loJSon = new JSONObject(fsJson);
            poMeans.setValue(loJSon);
        } catch (JSONException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<Integer> getPreviousPage(){
        MutableLiveData<Integer> loPage = new MutableLiveData<>();
        try {
            if(Objects.requireNonNull(poMeans.getValue()).getString("employed").equalsIgnoreCase("1")){
                loPage.setValue(3);
            } else {
                loPage.setValue(2);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        loPage.setValue(4);
        return loPage;
    }

    public LiveData<Integer> getNextPage(){
        MutableLiveData<Integer> loPage = new MutableLiveData<>();
        try {
            if(poMeans.getValue().getString("employed").equalsIgnoreCase("1") &&  CreditAppConstants.employment_done == false) {
                loPage.setValue(3);
            } else if(poMeans.getValue().getString("financer").equalsIgnoreCase("1")  &&  CreditAppConstants.finance_done == false) {
                loPage.setValue(5);
            } else if(poMeans.getValue().getString("pensionx").equalsIgnoreCase("1")  &&  CreditAppConstants.pension_done == false) {
                loPage.setValue(6);
            } else if(poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("1") ||
                    poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("5")){
                loPage.setValue(7);
            } else {
                loPage.setValue(12);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return loPage;
    }
    public LiveData<String[]> getAllProvinceNames(){
        return poProvRepo.getAllProvinceNames();
    }

    public LiveData<List<EProvinceInfo>> getAllProvinceInfo(){
        return poProvRepo.getAllProvinceInfo();
    }

    public void setProvinceID(String fsID){
        psProvID.setValue(fsID);
    }

    public LiveData<String[]> getAllTownNames(){
        return poTownRepo.getTownNamesFromProvince(psProvID.getValue());
    }

    public LiveData<List<ETownInfo>> getAllTownInfo(){
        return poTownRepo.getTownInfoFromProvince(psProvID.getValue());
    }

    public void setTownID(String fsID){
        psTownID.setValue(fsID);
    }

    public LiveData<ArrayAdapter<String>> getNatureOfBusiness(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.BUSINESS_NATURE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getTypeOfBusiness(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.BUSINESS_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSizeOfBusiness(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.BUSINESS_SIZE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getLenghtOfService(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.LENGTH_OF_STAY);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void setPsBsnssNature(String bsnssNature){
        this.psBsnssNature.setValue(bsnssNature);
    }
    public void setPsBsnssType(String bsnssType){
        this.psBsnssType.setValue(bsnssType);
    }

    public void setPsBsnssSize(String bsnssSize){
        this.psBsnssSize.setValue(bsnssSize);
    }
    public void setLngthStay(String lngthStay){
        this.pslngthStay.setValue(lngthStay);
    }


    public LiveData<String> getPsBsnssNature(){
        return this.psBsnssNature;
    }
    public  LiveData<String> getPsBsnssType(){
        return this.psBsnssType;
    }

    public  LiveData<String> getPsBsnssSize(){
        return this.psBsnssSize;
    }
    public  LiveData<String> getLngthStay(){
        return this.pslngthStay;
    }

    public void SaveSelfEmployedInfo(SelfEmployedInfoModel infoModel, ViewModelCallBack callBack){
        try{
            infoModel.setTown(psTownID.getValue());
            if(infoModel.isSelfEmployedValid()){
                poGoCas.MeansInfo().SelfEmployedInfo().setNatureOfBusiness(infoModel.getNatureOfBusiness());
                poGoCas.MeansInfo().SelfEmployedInfo().setNameOfBusiness(infoModel.getNameOfBusiness());
                poGoCas.MeansInfo().SelfEmployedInfo().setBusinessAddress(infoModel.getBusinessAddress());
                poGoCas.MeansInfo().SelfEmployedInfo().setCompanyTown(infoModel.getTown());
                poGoCas.MeansInfo().SelfEmployedInfo().setBusinessType(infoModel.getTypeOfBusiness());
                poGoCas.MeansInfo().SelfEmployedInfo().setOwnershipSize(infoModel.getSizeOfBusiness());
                poGoCas.MeansInfo().SelfEmployedInfo().setBusinessLength(infoModel.getLenghtOfService());
                poGoCas.MeansInfo().SelfEmployedInfo().setMonthlyExpense(infoModel.getMonthlyExpense());
                poGoCas.MeansInfo().SelfEmployedInfo().setIncome(infoModel.getMonthlyIncome());
                ECreditApplicantInfo applicantInfo = new ECreditApplicantInfo();
                applicantInfo.setTransNox(TransNox.getValue());
                applicantInfo.setClientNm(poGoCas.ApplicantInfo().getClientName());
                applicantInfo.setDetlInfo(poGoCas.toJSONString());
                poCreditApp.updateGOCasData(applicantInfo);
                CreditAppConstants.self_employment_done = true;
                callBack.onSaveSuccessResult("");
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
        }
    }
}