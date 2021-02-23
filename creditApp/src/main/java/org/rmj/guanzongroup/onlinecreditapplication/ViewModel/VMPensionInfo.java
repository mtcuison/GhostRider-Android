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
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PensionInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.Objects;

public class VMPensionInfo extends AndroidViewModel {
    private static final String TAG = VMPersonalInfo.class.getSimpleName();
    private final RCreditApplicant poCreditApp;
    private final GOCASApplication poGoCas;

    private final MutableLiveData<ECreditApplicantInfo> poInfo = new MutableLiveData<>();
    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psPensionSector = new MutableLiveData<>();
    private final MutableLiveData<JSONObject> poJson = new MutableLiveData<>();

    public VMPensionInfo(@NonNull Application application) {
        super(application);
        poCreditApp = new RCreditApplicant(application);
        poGoCas = new GOCASApplication();
    }

    public void setTransNox(String fsTransNox){
        this.TransNox.setValue(fsTransNox);
    }

    public LiveData<ECreditApplicantInfo> getApplicationInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
    }

    public void setDetailInfo(ECreditApplicantInfo fsDetailInfo){
        try{
            poInfo.setValue(fsDetailInfo);
            setMeansInfos(poInfo.getValue().getAppMeans());
            poGoCas.setData(fsDetailInfo.getDetlInfo());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMeansInfos(String foJson){
        try{
            poJson.setValue(new JSONObject(foJson));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public LiveData<ArrayAdapter<String>> getPensionSector(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.PENSION_SECTOR);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }
    public void setPensionSector(String lngthStay){
        this.psPensionSector.setValue(lngthStay);
    }

    public LiveData<Integer> getNextPage(){
        MutableLiveData<Integer> loPage = new MutableLiveData<>();
        try {
            if(poJson.getValue().getString("employed").equalsIgnoreCase("1") &&  CreditAppConstants.employment_done == false) {
                loPage.setValue(3);
            } else if(poJson.getValue().getString("sEmplyed").equalsIgnoreCase("1")  &&  CreditAppConstants.self_employment_done == false) {
                loPage.setValue(4);
            } else if(poJson.getValue().getString("financer").equalsIgnoreCase("1")  &&  CreditAppConstants.finance_done == false) {
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
    public LiveData<String> getSPensionSector(){
        return this.psPensionSector;
    }
    public void SavePensionInfo(PensionInfoModel infoModel, ViewModelCallBack callBack){
        try{
            if(infoModel.isPensionInfoValid()) {
                poGoCas.MeansInfo().PensionerInfo().setSource(infoModel.getPensionSector());
                poGoCas.MeansInfo().PensionerInfo().setAmount(infoModel.getPensionIncomeRange());
                poGoCas.MeansInfo().PensionerInfo().setYearRetired(infoModel.getRetirementYear());
                poGoCas.MeansInfo().setOtherIncomeNature(infoModel.getNatureOfIncome());
                poGoCas.MeansInfo().setOtherIncomeAmount(infoModel.getRangeOfIncome());
                ECreditApplicantInfo applicantInfo = poInfo.getValue();
                applicantInfo.setTransNox(TransNox.getValue());
                applicantInfo.setClientNm(poGoCas.ApplicantInfo().getClientName());
                applicantInfo.setDetlInfo(poGoCas.toJSONString());
                poCreditApp.updateGOCasData(applicantInfo);
                CreditAppConstants.pension_done = true;
                callBack.onSaveSuccessResult("Success");
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}