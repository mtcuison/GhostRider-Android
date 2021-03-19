package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pojo.OtherInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMOtherInfo extends AndroidViewModel {

    private static final String TAG = VMOtherInfo.class.getSimpleName();
    private final MutableLiveData<String> psTranNo = new MutableLiveData<>();
    private final MutableLiveData<String> unitUser = new MutableLiveData<>();
    private final MutableLiveData<String> uniPurpose = new MutableLiveData<>();
    private final MutableLiveData<String> unitPayer = new MutableLiveData<>();
    private final MutableLiveData<String> sUserBuyer = new MutableLiveData<>();
    private final MutableLiveData<String> sPayerBuyer = new MutableLiveData<>();
    private final MutableLiveData<String> source = new MutableLiveData<>();

    private final MutableLiveData<Integer> userBuyer = new MutableLiveData<>();
    private final MutableLiveData<Integer> payerBuyer = new MutableLiveData<>();
    private final MutableLiveData<Integer> compInfoSource = new MutableLiveData<>();

    private MutableLiveData<String> lsProvID = new MutableLiveData<>();
    private MutableLiveData<String> lsBPlace = new MutableLiveData<>();
    private String message;

    private ECreditApplicantInfo poInfo;

    private final GOCASApplication poGoCas;
    private SavedStateHandle poStatexx;
    private final RCreditApplicant poApplcnt;
    private final RProvince RProvince;
    private final RTown RTown;
    private final RCountry RCountry;
    private final LiveData<List<EProvinceInfo>> provinceInfoList;

    private final List<OtherInfoModel> otherInfo;
    private final MutableLiveData<List<OtherInfoModel>> referenceInfo = new MutableLiveData<>();
    private Context mContext;
    public VMOtherInfo(@NonNull Application application) {
        super(application);
        this.poApplcnt = new RCreditApplicant(application);
        RProvince = new RProvince(application);
        RTown = new RTown(application);
        RCountry = new RCountry(application);
        provinceInfoList = RProvince.getAllProvinceInfo();
        otherInfo = new ArrayList<>();
        mContext = application.getApplicationContext();
        poGoCas = new GOCASApplication();
        referenceInfo.setValue(new ArrayList<>());
        this.userBuyer.setValue(View.GONE);
        this.payerBuyer.setValue(View.GONE);
        this.compInfoSource.setValue(View.GONE);
    }
    // TODO: Implement the ViewModel
    public LiveData<List<OtherInfoModel>> getPersonalReference() {
        return referenceInfo;
    }
    public void setTransNox(String transNox){
        this.psTranNo.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicationInfo(){
        return poApplcnt.getCreditApplicantInfoLiveData(psTranNo.getValue());
    }

    public void setCreditApplicantInfo(ECreditApplicantInfo applicantInfo){
        try{
            poInfo = applicantInfo;
            poGoCas.setData(poInfo.getDetlInfo());
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

    public LiveData<Integer> setUserBuyer(){
        return userBuyer;
    }
    public LiveData<Integer> setPayerBuyer(){
        return payerBuyer;
    }

    public LiveData<ArrayAdapter<String>> getUnitUser(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.UNIT_USER);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getUserBuyer(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.UNIT_USER_OTHERS);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getUnitPurpose(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.UNIT_PURPOSE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<Integer> setCompanySource(){
        return compInfoSource;
    }

    public void setUnitPayer(String type){
        try {
            if(type.equalsIgnoreCase("1")){
                this.payerBuyer.setValue(View.VISIBLE);
            } else {
                this.payerBuyer.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.unitPayer.setValue(type);
    }

    public void setUnitUser(String type)
    {
        try {
            if(type.equalsIgnoreCase("1")){
                this.userBuyer.setValue(View.VISIBLE);
            } else {
                this.userBuyer.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.unitUser.setValue(type);
    }
    public LiveData<ArrayAdapter<String>> getUnitPayer(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.UNIT_USER);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getPayerBuyer(){
        ArrayAdapter<String> adapter;
        if (poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("1")){
            adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.UNIT_PAYER);
        }else{
            adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.UNIT_PAYER_NO_SPOUSE);
        }
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void setIntCompanyInfoSource(String type)
    {
        try {
            if(type.equalsIgnoreCase("5")){
                this.compInfoSource.setValue(View.VISIBLE);
            } else {
                this.compInfoSource.setValue(View.GONE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        this.source.setValue(type);
    }




    public LiveData<String> getSUnitUser() {
        return this.unitUser;
    }

//    user buyer
    public LiveData<String> getSUserBuyer() {
        return this.sUserBuyer;
    }
    public void setSUserBuyer(String userBuyer) {
         this.sUserBuyer.setValue(userBuyer);
    }
//    Unit Purpose
    public LiveData<String> getSUnitPurpose() {
        return this.uniPurpose;
    }
    public void setSUnitPurpose(String purpose) {
        this.uniPurpose.setValue(purpose);
    }


    public LiveData<String> getSUnitPayer() {
        return this.unitPayer;
    }

//    Payer Buyer
    public LiveData<String> getSPayerBuyer() {
        return this.sPayerBuyer;
    }
    public void setSPayerBuyer(String payerBuyer) {
        this.sPayerBuyer.setValue(payerBuyer);
    }

    public LiveData<String> getSCompanyInfoSource() {
        return this.source;
    }
    public LiveData<ArrayAdapter<String>> getIntCompanyInfoSource(){
        ArrayAdapter<String> adapter;
        if (poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("1")){
            adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.INTO_US);
        }else{
            adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.INTO_US_NO_SPOUSE);
        }

        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public String getMessage() {
        return message;
    }
    public boolean isValidSpnSource(int index){
        if(index==0){
            message = "Please select Company Information Source!";
            return false;
        }
        return true;
    }
    public boolean isValidUnitPayer(int index){
        if(index==0){
            message = "Please select unit payer!";
            return false;
        }
        return true;
    }
    public boolean isReferenceValid(){
        return referenceInfo.getValue().size() >= 3;
    }

    public boolean SubmitOtherInfo(OtherInfoModel reference, ViewModelCallBack callBack){
        try {
            if(reference.isValidSpinner()){
                if (isReferenceValid()){
                    if (Integer.parseInt(reference.getMonthlyPayerModel()) != 1){
                        poGoCas.OtherInfo().setUnitPayor(String.valueOf(reference.getMonthlyPayerModel()));
                    }else{
                        poGoCas.OtherInfo().setPayorRelation(String.valueOf(reference.getPayer2BuyerModel()));
                    }
                    poGoCas.OtherInfo().setUnitUser(reference.getUnitUserModel());
                    poGoCas.OtherInfo().setPurpose(String.valueOf(reference.getUserUnitPurposeModel()));
                    if (reference.getSourceModel().equalsIgnoreCase("Others")){
                        poGoCas.OtherInfo().setSourceInfo(reference.getCompanyInfoSourceModel());

                    }else{
                        poGoCas.OtherInfo().setSourceInfo(reference.getSourceModel());
                    }
                    for(int x = 0; x < otherInfo.size(); x++){
                        poGoCas.OtherInfo().addReference();
                        poGoCas.OtherInfo().setPRName(x, otherInfo.get(x).getFullname());
                        poGoCas.OtherInfo().setPRTownCity(x, otherInfo.get(x).getTownCity());
                        poGoCas.OtherInfo().setPRMobileNo(x, otherInfo.get(x).getContactN());
                        poGoCas.OtherInfo().setPRAddress(x, otherInfo.get(x).getAddress1());
                    }
                    poInfo.setTransNox(Objects.requireNonNull(psTranNo.getValue()));
                    poInfo.setDetlInfo(poGoCas.toJSONString());
                    poInfo.setClientNm(poGoCas.ApplicantInfo().getClientName());
                    poApplcnt.updateGOCasData(poInfo);
                    callBack.onSaveSuccessResult("Success");
                    Log.e(TAG, "Other information result : " + poGoCas.OtherInfo().toJSONString());

                    return true;
                } else {
                    callBack.onFailedResult("Please provide atleast 3 personal reference!");
                    return false;

                }
            }else {
                callBack.onFailedResult(reference.getMessage());
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
            return false;
        }

    }
    public boolean addReference(OtherInfoModel foInfo, ExpActionListener listener){
        if(foInfo.isValidReferences()) {
            Objects.requireNonNull(this.referenceInfo.getValue().add(foInfo));
            listener.onSuccess("Success");
            return true;

        } else {
            listener.onFailed(foInfo.getMessage());
            return false;
        }
    }
    public interface ExpActionListener{
        void onSuccess(String message);
        void onFailed(String message);
    }


}