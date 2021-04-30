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
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
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
    private ECreditApplicantInfo poInfo;
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

    public void setGOCasDetailInfo(ECreditApplicantInfo fsDetailInfo){
        try{
            poInfo = fsDetailInfo;
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

    public int getPreviousPage() throws Exception{
        if(Objects.requireNonNull(poMeans.getValue()).getString("employed").equalsIgnoreCase("1")){
            return 3;
        } else {
            return 2;
        }
    }

    public int getNextPage() throws Exception{
        if(poMeans.getValue().getString("financer").equalsIgnoreCase("1")) {
            return 5;
        } else if(poMeans.getValue().getString("pensionx").equalsIgnoreCase("1")) {
            return 6;
        } else if(poInfo.getIsSpouse().equalsIgnoreCase("1")){
            return 7;
        } else {
            return 12;
        }
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
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.BUSINESS_NATURE));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getTypeOfBusiness(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.BUSINESS_TYPE));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSizeOfBusiness(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.BUSINESS_SIZE));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getLenghtOfService(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.LENGTH_OF_STAY));
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
                //poInfo.setDetlInfo(poGoCas.toJSONString());
                poInfo.setBusnInfo(poGoCas.MeansInfo().SelfEmployedInfo().toJSONString());
                poCreditApp.updateGOCasData(poInfo);
                callBack.onSaveSuccessResult(String.valueOf(getNextPage()));
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
        }
    }
}