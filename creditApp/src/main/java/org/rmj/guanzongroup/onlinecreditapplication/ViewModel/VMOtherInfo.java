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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RCountry;
import org.rmj.g3appdriver.dev.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.dev.Database.Repositories.RProvince;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pojo.OtherInfo;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.GOCASHolder;
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
    private final RProvince poProv;
    private final RTown poTown;
    private final RCountry poCountry;
    private final LiveData<List<EProvinceInfo>> provinceInfoList;

    public VMOtherInfo(@NonNull Application application) {
        super(application);
        this.poApplcnt = new RCreditApplicant(application);
        poProv = new RProvince(application);
        poTown = new RTown(application);
        poCountry = new RCountry(application);
        provinceInfoList = poProv.getAllProvinceInfo();
        poGoCas = new GOCASApplication();
        poReference.setValue(new ArrayList<>());
    }

    public void setTransNox(String transNox){
        this.psTransNox = transNox;
        this.psTranNo.setValue(transNox);
    }

    //    public void setRetrievedReference(PersonalReferenceInfoModel foRefs) {
////        Objects.requireNonNull(poReference.getValue()).add(foRefs);
//        poReference.getValue().add(foRefs);
//    }
    public void setRetrievedReferenceList(List<PersonalReferenceInfoModel> foRefs) {
        Objects.requireNonNull(poReference).getValue().clear();
        Objects.requireNonNull(poReference).setValue(foRefs);
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
        return poTown.getTownInfoFromProvince(lsProvID.getValue());
    }

    public LiveData<List<ECountryInfo>> getCountryInfoList(){
        return poCountry.getAllCountryInfo();
    }

    public LiveData<String[]> getProvinceNameList(){
        return poProv.getAllProvinceNames();
    }

    public LiveData<String[]> getAllTownNames(){
        return poTown.getTownNamesFromProvince(lsProvID.getValue());
    }

    public LiveData<DTownInfo.TownProvinceName> getLiveTownProvinceNames(String TownID) {
        return poTown.getLiveTownProvinceNames(TownID);
    }

    public void setProvID(String ProvID) { this.lsProvID.setValue(ProvID); }
    public LiveData<ArrayAdapter<String>> getUnitUser(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER));
        return liveData;
//        return CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER);
    }

    public LiveData<ArrayAdapter<String>> getOtherUnitUser(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS));
//        ArrayAdapter<String> adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS);
        try {
            if ("1".equalsIgnoreCase(poInfo.getIsSpouse())) {
//                adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS);
                liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS));
            } else {
//                adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS_NO_SPOUSE);
                liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS_NO_SPOUSE));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getUnitPurpose(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PURPOSE));
        return liveData;
    }

    public ArrayAdapter<String> getUnitPayer(){
        return CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER);
    }

    public LiveData<ArrayAdapter<String>> getPayerBuyer(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER));
        ArrayAdapter<String> adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER);
        try {
            if ("1".equalsIgnoreCase(poInfo.getIsSpouse())) {
//                adapter =  CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER);
                liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER));
            } else {
//                adapter =  CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER_NO_SPOUSE);
                liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER_NO_SPOUSE));
            }
        } catch (NullPointerException e){
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
//        return adapter;
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getIntCompanyInfoSource()  {

        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();

        ArrayAdapter<String> adapter;

//        JSONObject loJson = new JSONObject(poInfo.getApplInfo());
        if (poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("1")){
//            adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INTO_US);
            liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INTO_US));
        }else{
//            adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INTO_US_NO_SPOUSE);
            liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INTO_US_NO_SPOUSE));
        }
        return liveData;
    }


//
//    public ArrayAdapter<String> getUnitUser(){
//        return CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER);
//    }
//
//    public ArrayAdapter<String> getOtherUnitUser(){
//        ArrayAdapter<String> adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS);
//        try {
//            if ("1".equalsIgnoreCase(poInfo.getIsSpouse())) {
//                adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS);
//            } else {
//                adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER_OTHERS_NO_SPOUSE);
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return adapter;
//    }
//
//    public ArrayAdapter<String> getUnitPurpose(){
//        return CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PURPOSE);
//    }
//
//    public ArrayAdapter<String> getUnitPayer(){
//        return CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_USER);
//    }
//
//    public ArrayAdapter<String> getPayerBuyer(){
//        ArrayAdapter<String> adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER);
//        try {
//            if ("1".equalsIgnoreCase(poInfo.getIsSpouse())) {
//                adapter =  CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER);
//            } else {
//                adapter =  CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.UNIT_PAYER_NO_SPOUSE);
//            }
//        } catch (NullPointerException e){
//            e.printStackTrace();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
//        return adapter;
//    }
//
//    public ArrayAdapter<String> getIntCompanyInfoSource(){
//        ArrayAdapter<String> adapter;
//        if (poGoCas.ApplicantInfo().getCivilStatus().equalsIgnoreCase("1")){
//            adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INTO_US);
//        }else{
//            adapter = CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.INTO_US_NO_SPOUSE);
//        }
//        return adapter;
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

                    org.json.simple.JSONObject loJson = new org.json.simple.JSONObject();

                    loJson.put("sUnitUser", infoModel.getsUnitUser());
                    loJson.put("sUsr2Buyr", infoModel.getsUsr2Buyr());
                    loJson.put("sUnitPayr", infoModel.getsUnitPayr());
                    loJson.put("sPyr2Buyr", infoModel.getsPyr2Buyr());
                    loJson.put("sPurposex", infoModel.getsPurposex());
                    if (infoModel.getSource().equalsIgnoreCase("Others")){
                        loJson.put("sSrceInfo", infoModel.getCompanyInfoSource());
                    }else{
                        loJson.put("sSrceInfo", infoModel.getSource());
                    }
                    @SuppressLint({"NewApi", "LocalSuppress"})
                    org.json.simple.JSONArray reference = new  org.json.simple.JSONArray();

                    for(int x = 0; x < Objects.requireNonNull(poReference.getValue()).size(); x++){
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("sRefrNmex", poReference.getValue().get(x).getFullname());
                        jsonObject.put("sRefrTown", poReference.getValue().get(x).getTownCity());
                        jsonObject.put("sRefrMPNx", poReference.getValue().get(x).getContactN());
                        jsonObject.put("sRefrAddx", poReference.getValue().get(x).getAddress1());
                        reference.add(x,jsonObject);

                    }
                    loJson.put("personal_reference", reference);
                    poGoCas.OtherInfo().setData(loJson);
                    poInfo.setTransNox(Objects.requireNonNull(psTranNo.getValue()));
                    poInfo.setOthrInfo(poGoCas.OtherInfo().toJSONString());
                    poDcp.updateGOCasData(poInfo);
//                    poDcp.updateOtherInfo(psTransNox, loJson.toString());
                    Log.e(TAG, loJson.toString());
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