/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pojo.OtherInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalReferenceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMOtherInfo extends AndroidViewModel {

    private static final String TAG = VMOtherInfo.class.getSimpleName();

    private String psTransNox;
    private final MutableLiveData<String> lsProvID = new MutableLiveData<>();

    private final MutableLiveData<List<PersonalReferenceInfoModel>> poReference = new MutableLiveData<>();
    public final MutableLiveData<String> psTranNo = new MutableLiveData<>();
    private ECreditApplicantInfo poInfo;

    private final GOCASApplication poGoCas;
    private final RCreditApplicant poApplcnt;
    private final RProvince RProvince;
    private final RTown RTown;
    private final RCountry RCountry;
    private final LiveData<List<EProvinceInfo>> provinceInfoList;

    public VMOtherInfo(@NonNull Application application) {
        super(application);
        this.poApplcnt = new RCreditApplicant(application);
        RProvince = new RProvince(application);
        RTown = new RTown(application);
        RCountry = new RCountry(application);
        provinceInfoList = RProvince.getAllProvinceInfo();
        poGoCas = new GOCASApplication();
        poReference.setValue(new ArrayList<>());
    }

    public void setTransNox(String transNox){
        this.psTransNox = transNox;
        this.psTranNo.setValue(transNox);
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

    public LiveData<List<PersonalReferenceInfoModel>> getReferenceList(){
        return poReference;
    }

    public boolean addReference(PersonalReferenceInfoModel poInfo, AddPersonalInfoListener listener){
        try{
            if(poInfo.isDataValid()){
                Objects.requireNonNull(poReference.getValue()).add(poInfo);
                listener.OnSuccess();
                return true;
            } else {
                listener.onFailed(poInfo.getMessage());
                return false;
            }
        } catch (Exception e){
            e.printStackTrace();
            listener.onFailed(e.getMessage());
            return false;
        }
    }

    public boolean removeReference(int position) {
        try {
            poReference.getValue().remove(position);
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
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

    public LiveData<DTownInfo.TownProvinceName> getLiveTownProvinceNames(String TownID) {
        return RTown.getLiveTownProvinceNames(TownID);
    }

    public void setProvID(String ProvID) { this.lsProvID.setValue(ProvID); }

    public ArrayAdapter<String> getUnitUser(){
        return CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER);
    }

    public ArrayAdapter<String> getOtherUnitUser(){
        ArrayAdapter<String> adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS);
        try {
            if (poInfo.getIsSpouse().equalsIgnoreCase("1")) {
                adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS);
            } else {
                adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS_NO_SPOUSE);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return adapter;
    }

    public ArrayAdapter<String> getUnitPurpose(){
        return CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PURPOSE);
    }

    public ArrayAdapter<String> getUnitPayer(){
        return CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER);
    }

    public ArrayAdapter<String> getPayerBuyer(){
        ArrayAdapter<String> adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER);
        try {
            if (poInfo.getIsSpouse().equalsIgnoreCase("1")) {
                adapter =  CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER);
            } else {
                adapter =  CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER_NO_SPOUSE);
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
        return adapter;
    }

    public ArrayAdapter<String> getIntCompanyInfoSource(){
        ArrayAdapter<String> adapter;
        if (poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("1")){
            adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INTO_US);
        }else{
            adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INTO_US_NO_SPOUSE);
        }
        return adapter;
    }
//
//    public boolean SubmitOtherInfo(OtherInfoModel otherInfo, ViewModelCallBack callBack){
//        try {
//            otherInfo.setPersonalReferences(poReference.getValue());
//            if(otherInfo.isDataValid()){
//                poGoCas.OtherInfo().setUnitUser(otherInfo.getUnitUser());
//                poGoCas.OtherInfo().setPurpose(String.valueOf(otherInfo.getUnitPrps()));
//                if (Integer.parseInt(otherInfo.getUnitPayr()) != 1){
//                    poGoCas.OtherInfo().setUnitPayor(String.valueOf(otherInfo.getUnitPayr()));
//                }else{
//                    poGoCas.OtherInfo().setPayorRelation(String.valueOf(otherInfo.getPayrRltn()));
//                }
//                if (otherInfo.getSource().equalsIgnoreCase("Others")){
//                    poGoCas.OtherInfo().setSourceInfo(otherInfo.getCompanyInfoSource());
//                }else{
//                    poGoCas.OtherInfo().setSourceInfo(otherInfo.getSource());
//                }
//                for(int x = 0; x < Objects.requireNonNull(poReference.getValue()).size(); x++){
//                    PersonalReferenceInfoModel loRef = poReference.getValue().get(x);
//                    poGoCas.OtherInfo().addReference();
//                    poGoCas.OtherInfo().setPRName(x, loRef.getFullname());
//                    poGoCas.OtherInfo().setPRTownCity(x, loRef.getTownCity());
//                    poGoCas.OtherInfo().setPRMobileNo(x, loRef.getContactN());
//                    poGoCas.OtherInfo().setPRAddress(x, loRef.getAddress1());
//                }
//                poInfo.setTransNox(Objects.requireNonNull(psTranNo.getValue()));
//                poInfo.setOthrInfo(poGoCas.OtherInfo().toJSONString());
//                poApplcnt.updateGOCasData(poInfo);
//                callBack.onSaveSuccessResult("Success");
//                Log.e(TAG, "Other information result : " + poGoCas.OtherInfo().toJSONString());
//
//                return true;
//                } else {
//                    callBack.onFailedResult("Else " + otherInfo.getMessage());
//                    return false;
//                }
//        }catch (NullPointerException e){
//            e.printStackTrace();
//            callBack.onFailedResult("NullPointerException " + e.getMessage());
//            return false;
//        } catch (Exception e){
//            e.printStackTrace();
//            callBack.onFailedResult("Exception " + e.getMessage());
//            return false;
//        }
//    }
    public boolean SubmitOtherInfo(OtherInfoModel otherInfo, ViewModelCallBack callBack) {
        try {
            new UpdateTask(poApplcnt, poInfo, otherInfo, callBack).execute();
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            callBack.onFailedResult("NullPointerException error");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            callBack.onFailedResult("Exception error");
            return false;
        }
    }
    private class UpdateTask extends AsyncTask<RCreditApplicant, Void, String> {
        private final RCreditApplicant poDcp;
        private final OtherInfoModel infoModel;
        private final ViewModelCallBack callback;
        private final ECreditApplicantInfo poInfo;
        public UpdateTask(RCreditApplicant poDcp,ECreditApplicantInfo poInfo, OtherInfoModel infoModel, ViewModelCallBack callback) {
            this.poDcp = poDcp;
            this.infoModel = infoModel;
            this.poInfo = poInfo;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(RCreditApplicant... rApplicant) {
            try{
                infoModel.setPersonalReferences(poReference.getValue());
                if(infoModel.isDataValid()){
                    poGoCas.OtherInfo().setUnitUser(infoModel.getUnitUser());
                    poGoCas.OtherInfo().setPurpose(String.valueOf(infoModel.getUnitPrps()));
                    if (Integer.parseInt(infoModel.getUnitPayr()) != 1){
                        poGoCas.OtherInfo().setUnitPayor(String.valueOf(infoModel.getUnitPayr()));
                    }else{
                        poGoCas.OtherInfo().setPayorRelation(String.valueOf(infoModel.getPayrRltn()));
                    }
                    if (infoModel.getSource().equalsIgnoreCase("Others")){
                        poGoCas.OtherInfo().setSourceInfo(infoModel.getCompanyInfoSource());
                    }else{
                        poGoCas.OtherInfo().setSourceInfo(infoModel.getSource());
                    }
                    for(int x = 0; x < Objects.requireNonNull(poReference.getValue()).size(); x++){
                        PersonalReferenceInfoModel loRef = poReference.getValue().get(x);
                        poGoCas.OtherInfo().addReference();
                        poGoCas.OtherInfo().setPRName(x, loRef.getFullname());
                        poGoCas.OtherInfo().setPRTownCity(x, loRef.getTownCity());
                        poGoCas.OtherInfo().setPRMobileNo(x, loRef.getContactN());
                        poGoCas.OtherInfo().setPRAddress(x, loRef.getAddress1());
                    }
                    poInfo.setTransNox(Objects.requireNonNull(psTranNo.getValue()));
                    poInfo.setOthrInfo(poGoCas.OtherInfo().toJSONString());
                    poDcp.updateGOCasData(poInfo);
                    return "success";
                } else {
                    return infoModel.getMessage();
                }
            }catch (NullPointerException e){
                e.printStackTrace();
                return e.getMessage();
            }
            catch (Exception e){
                e.printStackTrace();
                return e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s.equalsIgnoreCase("success")){
                callback.onSaveSuccessResult(psTranNo.getValue());
            } else {
                callback.onFailedResult(s);
            }
        }
    }
    public interface ExpActionListener{
        void onSuccess(String message);
        void onFailed(String message);
    }

    public interface AddPersonalInfoListener{
        void OnSuccess();
        void onFailed(String message);
    }
}