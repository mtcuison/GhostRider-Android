package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pojo.DisbursementInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.TextFormatter;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.EmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.Objects;

public class VMDisbursementInfo extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private final MutableLiveData<String> typeX = new MutableLiveData<>();
    private final MutableLiveData<String> psTranNo = new MutableLiveData<>();
    private final RCreditApplicant poApplcnt;
    private ECreditApplicantInfo poInfo;

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
    public String getTransNox(){
        return this.psTranNo.getValue();
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicationInfo(){
        return poApplcnt.getCreditApplicantInfoLiveData(psTranNo.getValue());
    }

    public void setCreditApplicantInfo(ECreditApplicantInfo applicantInfo){
        try{
            poInfo = applicantInfo;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public int getPreviousPage() throws Exception{
        JSONObject loJson = new JSONObject(poInfo.getAppMeans());
        if(poInfo.getIsSpouse().equalsIgnoreCase("1")){
            return 11;
        } else if(loJson.getString("pensionx").equalsIgnoreCase("1")){
            return 6;
        } else if(loJson.getString("financer").equalsIgnoreCase("1")){
            return 5;
        } else if(loJson.getString("sEmplyed").equalsIgnoreCase("1")){
            return 4;
        } else if(loJson.getString("employed").equalsIgnoreCase("1")){
            return 3;
        } else {
            return 2;
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

    public boolean SubmitApplicationInfo(DisbursementInfoModel disbursementInfo,ViewModelCallBack callBack) {
        try {
            new UpdateTask(poApplcnt, disbursementInfo, psTranNo.getValue(), callBack).execute();
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
//            callback.OnFailedResult(e.getMessage());
            callBack.onFailedResult("NullPointerException error");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFailedResult("Exception error");
            return false;
        }
    }

    private  class UpdateTask extends AsyncTask<RCreditApplicant, Void, String> {
        private final RCreditApplicant poDcp;
        private final DisbursementInfoModel infoModel;
        private final ViewModelCallBack callback;
        private final String transNox;
        public UpdateTask(RCreditApplicant poDcp, DisbursementInfoModel infoModel, String transNox, ViewModelCallBack callback) {
            this.poDcp = poDcp;
            this.infoModel = infoModel;
            this.transNox = transNox;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(RCreditApplicant... rApplicant) {
            try{
                if (infoModel.isDataValid()){
                    poGoCasxx.DisbursementInfo().Expenses().setElectricBill(infoModel.getElctX());
                    poGoCasxx.DisbursementInfo().Expenses().setFoodAllowance(infoModel.getFoodX());
                    poGoCasxx.DisbursementInfo().Expenses().setWaterBill(infoModel.getWaterX());
                    poGoCasxx.DisbursementInfo().Expenses().setLoanAmount((infoModel.getLoans()));
                    poGoCasxx.DisbursementInfo().BankAccount().setBankName(Objects.requireNonNull(infoModel.getBankN()));
                    poGoCasxx.DisbursementInfo().BankAccount().setAccountType(Objects.requireNonNull(infoModel.getStypeX()));
                    poGoCasxx.DisbursementInfo().CreditCard().setBankName(Objects.requireNonNull(infoModel.getCcBnk()));
                    poGoCasxx.DisbursementInfo().CreditCard().setCreditLimit(infoModel.getLimitCC());
                    poGoCasxx.DisbursementInfo().CreditCard().setMemberSince(infoModel.getYearS());
                    poInfo.setTransNox(Objects.requireNonNull(transNox));
                    poInfo.setDisbrsmt(poGoCasxx.DisbursementInfo().toJSONString());
                    poDcp.updateGOCasData(poInfo);
                    return "success";
                }else {
                    return infoModel.getMessage();
                }
            } catch (NullPointerException e){
                e.printStackTrace();
                return e.getMessage();
            }catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.onSaveSuccessResult("Success");
            } else {
                callback.onFailedResult(s);
            }
        }
    }
}