package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pojo.DisbursementInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.EmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.Objects;

public class VMDisbursementInfo extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> typeX = new MutableLiveData<>();
    private final MutableLiveData<String> psTranNo = new MutableLiveData<>();
    private final RCreditApplicant poApplcnt;

    private final GOCASApplication poGoCasxx;
    public VMDisbursementInfo(@NonNull Application application)
    {
        super(application);
        this.poApplcnt = new RCreditApplicant(application);
        this.poGoCasxx = new GOCASApplication();
    }

    public void setTransNox(String transNox){
        this.psTranNo.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicationInfo(){
        return poApplcnt.getCreditApplicantInfoLiveData(psTranNo.getValue());
    }

    public void setCreditApplicantInfo(String applicantInfo){
        try{
            poGoCasxx.setData(applicantInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setType(String type){
        this.typeX.setValue(type);
    }

    public LiveData<ArrayAdapter<String>> getAccountType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.ACCOUNT_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }


    public boolean SubmitApplicationInfo(DisbursementInfoModel disbursementInfo,ViewModelCallBack callBack){
        try {

            if (disbursementInfo.isDataValid()){
//                poGoCasxx.DisbursementInfo().Expenses().setElectricBill(disbursementInfo.getElctX());
                poGoCasxx.DisbursementInfo().Expenses().setElectricBill(disbursementInfo.getElctX());
                poGoCasxx.DisbursementInfo().Expenses().setFoodAllowance(disbursementInfo.getFoodX());
                poGoCasxx.DisbursementInfo().Expenses().setWaterBill(disbursementInfo.getWaterX());
                poGoCasxx.DisbursementInfo().Expenses().setLoanAmount((disbursementInfo.getLoans()));
                poGoCasxx.DisbursementInfo().BankAccount().setBankName(Objects.requireNonNull(disbursementInfo.getBankN()));
                poGoCasxx.DisbursementInfo().BankAccount().setAccountType(Objects.requireNonNull(disbursementInfo.getStypeX()));
                poGoCasxx.DisbursementInfo().CreditCard().setBankName(Objects.requireNonNull(disbursementInfo.getCcBnk()));
                poGoCasxx.DisbursementInfo().CreditCard().setCreditLimit(Objects.requireNonNull(disbursementInfo.getLimitCC()));
                poGoCasxx.DisbursementInfo().CreditCard().setMemberSince(Objects.requireNonNull(disbursementInfo.getYearS()));
                ECreditApplicantInfo info = new ECreditApplicantInfo();
                info.setTransNox(Objects.requireNonNull(psTranNo.getValue()));
                info.setDetlInfo(poGoCasxx.toJSONString());
                info.setClientNm(poGoCasxx.ApplicantInfo().getClientName());
                poApplcnt.updateGOCasData(info);
                Log.e("Disbursement Data", String.valueOf(info.getDetlInfo()));
                callBack.onSaveSuccessResult("Success");
                return true;
            } else {
                callBack.onFailedResult(disbursementInfo.getMessage());
                return false;
            }
        } catch (Exception e){
           e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
            return false;
        }
    }




}