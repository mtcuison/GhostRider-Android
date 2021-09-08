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
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.GOCASHolder;
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseSelfEmployedInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;
import java.util.Objects;

public class VMSpouseSelfEmployedInfo extends AndroidViewModel {
    public static final String TAG = VMSpouseSelfEmployedInfo.class.getSimpleName();
    private final GOCASApplication poGoCas;
    private final RCreditApplicant poCreditApp;
    private final RProvince poProvRepo;
    private final RTown poTownRepo;
    private ECreditApplicantInfo poInfo;

    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();
    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();
    private final MutableLiveData<String> psBizIndustry = new MutableLiveData<>();
    private final MutableLiveData<String> psBizType = new MutableLiveData<>();
    private final MutableLiveData<String> psBizSize = new MutableLiveData<>();
    private final MutableLiveData<String> psMosOrYr = new MutableLiveData<>();

    public VMSpouseSelfEmployedInfo(@NonNull Application application) {
        super(application);
        this.poGoCas = GOCASHolder.getInstance().getGOCAS();
        this.poCreditApp = new RCreditApplicant(application);
        this.poProvRepo = new RProvince(application);
        this.poTownRepo = new RTown(application);
    }

    public boolean setTransNox(String transNox){
        this.psTransNo.setValue(transNox);
        if(!this.psTransNo.getValue().equalsIgnoreCase(transNox)) {
            return false;
        }
        return true;
    }

    public boolean setBizIndustry(String bizIndustryIndex) {
        this.psBizIndustry.setValue(bizIndustryIndex);
        if(!psBizIndustry.getValue().equalsIgnoreCase(bizIndustryIndex)) {
            return false;
        }
        return true;
    }

    public boolean setBizType(String bizTypeIndex) {
        this.psBizType.setValue(bizTypeIndex);
        if(!this.psBizType.getValue().equalsIgnoreCase(bizTypeIndex)) {
            return false;
        }
        return true;
    }

    public boolean setBizSize(String bizSizeIndex) {
        this.psBizSize.setValue(bizSizeIndex);
        if(!this.psBizSize.getValue().equalsIgnoreCase(bizSizeIndex)) {
            return false;
        }
        return true;
    }

    public boolean setMosOrYr(String mosOrYrIndex) {
        this.psMosOrYr.setValue(mosOrYrIndex);
        if(!this.psMosOrYr.getValue().equalsIgnoreCase(mosOrYrIndex)) {
            return false;
        }
        return true;
    }

    public LiveData<ECreditApplicantInfo> getActiveGOCasApplication(){
        return poCreditApp.getCreditApplicantInfoLiveData(psTransNo.getValue());
    }

    public void setDetailInfo(ECreditApplicantInfo fsDetailInfo){
        try{
            poInfo = fsDetailInfo;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<String[]> getProvinceName(){
        return poProvRepo.getAllProvinceNames();
    }

    public LiveData<List<EProvinceInfo>> getProvinceInfo(){
        return poProvRepo.getAllProvinceInfo();
    }

    public boolean setProvinceID(String ID){
        this.psProvID.setValue(ID);
        if(!this.psProvID.getValue().equalsIgnoreCase(ID)) {
            return false;
        }
        return true;
    }

    public LiveData<String[]> getTownNameList(){
        return poTownRepo.getTownNamesFromProvince(psProvID.getValue());
    }

    public LiveData<List<ETownInfo>> getTownInfoList(){
        return poTownRepo.getTownInfoFromProvince(psProvID.getValue());
    }

    public boolean setTownID(String ID){
        this.psTownID.setValue(ID);
        if(!this.psTownID.getValue().equalsIgnoreCase(ID)) {
            return false;
        }
        return true;
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

    public LiveData<ArrayAdapter<String>> getLengthOfService(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.LENGTH_OF_STAY));
        return liveData;
    }

    public boolean Save(SpouseSelfEmployedInfoModel infoModel, ViewModelCallBack callBack) {
        try {
            infoModel.setsBizIndustry(psBizIndustry.getValue());
            infoModel.setsProvId(psProvID.getValue());
            infoModel.setsTownId(psTownID.getValue());
            infoModel.setsBizType(psBizType.getValue());
            infoModel.setsBizSize(psBizSize.getValue());
            infoModel.setsMonthOrYear(psMosOrYr.getValue());
            if(infoModel.isSpouseInfoValid()) {
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setNatureOfBusiness(infoModel.getsBizIndustry());
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setNameOfBusiness(infoModel.getsBizName());
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setBusinessAddress(infoModel.getsBizAddress());
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setCompanyTown(infoModel.getsTownId());
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setBusinessType(infoModel.getsBizType());
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setOwnershipSize(infoModel.getsBizSize());
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setBusinessLength(infoModel.getsBizYrs());
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setIncome(infoModel.getsGrossMonthly());
                poGoCas.SpouseMeansInfo().SelfEmployedInfo().setMonthlyExpense(infoModel.getsMonthlyExps());

                poInfo.setTransNox(Objects.requireNonNull(psTransNo.getValue()));
                poInfo.setSpsBusnx(poGoCas.SpouseMeansInfo().SelfEmployedInfo().toJSONString());
                poCreditApp.updateGOCasData(poInfo);
                callBack.onSaveSuccessResult("Success");
                return true;
            }
            else {
                callBack.onFailedResult(infoModel.getsMsg());
                return false;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
            return false;
        }
    }

}