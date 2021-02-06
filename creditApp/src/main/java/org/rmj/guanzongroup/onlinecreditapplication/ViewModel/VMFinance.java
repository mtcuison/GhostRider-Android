package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
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

    public void setGOCasApplication(String sDetailInfo){
        try{
            this.poGoCas.setData(sDetailInfo);
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
                ECreditApplicantInfo applicantInfo = new ECreditApplicantInfo();
                applicantInfo.setTransNox(Objects.requireNonNull(TransNox.getValue()));
                applicantInfo.setClientNm(poGoCas.ApplicantInfo().getClientName());
                applicantInfo.setDetlInfo(poGoCas.toJSONString());
                poCreditApp.updateGOCasData(applicantInfo);
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