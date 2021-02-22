package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.FinanceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;
import java.util.Objects;

public class VMFinance extends AndroidViewModel {
    private static final String TAG = VMFinance.class.getSimpleName();
    private final RCreditApplicant poCreditApp;
    private final RCountry poCountry;
    private final GOCASApplication poGoCas;

    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<ECreditApplicantInfo> poInfo = new MutableLiveData<>();
    private final MutableLiveData<String> psFinanceSource = new MutableLiveData<>();
    private final MutableLiveData<JSONObject> poJson = new MutableLiveData<>();

    public VMFinance(@NonNull Application application) {
        super(application);
        this.poCreditApp = new RCreditApplicant(application);
        this.poCountry = new RCountry(application);
        this.poGoCas = new GOCASApplication();
    }

    public void setTransNox(String TransNox){
        this.TransNox.setValue(TransNox);
    }

    public LiveData<ECreditApplicantInfo> getApplicationInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
    }

    public void setGOCasApplication(ECreditApplicantInfo sDetailInfo){
        try{
            poInfo.setValue(sDetailInfo);
            setMeansInfos(poInfo.getValue().getAppMeans());
            this.poGoCas.setData(sDetailInfo.getDetlInfo());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<String[]> getCountryNameList(){
        return poCountry.getAllCountryNames();
    }

    public LiveData<List<ECountryInfo>> getCountryInfoList(){
        return poCountry.getAllCountryInfo();
    }

    public LiveData<String> getFinanceSourceSpn(){
        return this.psFinanceSource;
    }
    public void setFinanceSourceSpn(String source){
        this.psFinanceSource.setValue(source);
    }

    public LiveData<ArrayAdapter<String>> getFinanceSource(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.FINANCE_SOURCE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void setMeansInfos(String foJson){
        try{
            poJson.setValue(new JSONObject(foJson));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }
    public LiveData<Integer> getNextPage(){
        MutableLiveData<Integer> loPage = new MutableLiveData<>();
        try {
            if(poJson.getValue().getString("pensionx").equalsIgnoreCase("1")) {
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
    public void SaveFinancierInfo(FinanceInfoModel infoModel, ViewModelCallBack callBack){
        try{
            if(infoModel.isFinancierInfoValid()){
                poGoCas.MeansInfo().FinancerInfo().setSource(infoModel.getFinancierRelation());
                poGoCas.MeansInfo().FinancerInfo().setFinancerName(infoModel.getFinancierName());
                poGoCas.MeansInfo().FinancerInfo().setAmount(infoModel.getRangeOfIncome());
                poGoCas.MeansInfo().FinancerInfo().setCountry(infoModel.getCountryName());
                poGoCas.MeansInfo().FinancerInfo().setMobileNo(infoModel.getMobileNo());
                poGoCas.MeansInfo().FinancerInfo().setFBAccount(infoModel.getFacebook());
                poGoCas.MeansInfo().FinancerInfo().setEmailAddress(infoModel.getEmail());
                ECreditApplicantInfo applicantInfo = poInfo.getValue();
                applicantInfo.setTransNox(Objects.requireNonNull(TransNox.getValue()));
                applicantInfo.setClientNm(poGoCas.ApplicantInfo().getClientName());
                applicantInfo.setDetlInfo(poGoCas.toJSONString());
                poCreditApp.updateGOCasData(applicantInfo);
                CreditAppConstants.finance_done = true;
                callBack.onSaveSuccessResult(TransNox.getValue());
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
        }
    }
}