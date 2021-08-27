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

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EOccupationInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.ROccupation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.EmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;
import java.util.Objects;

public class VMEmploymentInfo extends AndroidViewModel {
    public static final String TAG = VMEmploymentInfo.class.getSimpleName();
    private final RProvince poProvnce;
    private final RTown poTownRpo;
    private final RCountry poCountry;
    private final ROccupation poJobRepo;
    private final RCreditApplicant poCredtAp;
    private final GOCASApplication poGoCasxx;
    private ECreditApplicantInfo poInfo;

    private final MutableLiveData<JSONObject> poJson = new MutableLiveData<>();
    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();
    private final MutableLiveData<String> psSectorx = new MutableLiveData<>();
    private final MutableLiveData<String> psUniform = new MutableLiveData<>();
    private final MutableLiveData<String> psMiltary = new MutableLiveData<>();
    private final MutableLiveData<String> psProvIDx = new MutableLiveData<>();
    private final MutableLiveData<String> psEmpStat = new MutableLiveData<>();

    public VMEmploymentInfo(@NonNull Application application) {
        super(application);
        this.poProvnce = new RProvince(application);
        this.poTownRpo = new RTown(application);
        this.poCountry = new RCountry(application);
        this.poJobRepo = new ROccupation(application);
        this.poCredtAp = new RCreditApplicant(application);
        this.poGoCasxx = new GOCASApplication();
        this.psSectorx.setValue("1");
    }

    public void setTransNox(String transNox){
        this.psTransNo.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicationInfo(){
        return poCredtAp.getCreditApplicantInfoLiveData(psTransNo.getValue());
    }

    public void setCreditApplicantInfo(ECreditApplicantInfo foInfo){
        try{
            poInfo = foInfo;
            setMeansInfos(foInfo.getAppMeans());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setEmploymentSector(String sector){
        this.psSectorx.setValue(sector);
    }
    public void setMeansInfos(String foJson){
        try{
            poJson.setValue(new JSONObject(foJson));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public void setUniformPersonnel(String isUniform){
        this.psUniform.setValue(isUniform);
    }

    public void setMilitaryPersonnel(String isMilitary){
        this.psMiltary.setValue(isMilitary);
    }

    public LiveData<ArrayAdapter<String>> getCompanyLevelList(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.COMPANY_LEVEL));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getEmployeeLevelList(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.EMPLOYEE_LEVEL));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getGovernmentLevelList(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.GOVERMENT_LEVEL));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getRegionList(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.OFW_REGION));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getWorkCategoryList(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.OFW_CATEGORY));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getBusinessNature(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.BUSINESS_NATURE));
        return liveData;
    }

    public LiveData<String[]> getProvinceName(){
        return poProvnce.getAllProvinceNames();
    }

    public LiveData<List<EProvinceInfo>> getProvinceInfo(){
        return poProvnce.getAllProvinceInfo();
    }

    public void setProvinceID(String ID){
        this.psProvIDx.setValue(ID);
    }

    public LiveData<String[]> getTownNameList(){
        return poTownRpo.getTownNamesFromProvince(psProvIDx.getValue());
    }

    public LiveData<List<ETownInfo>> getTownInfoList(){
        return poTownRpo.getTownInfoFromProvince(psProvIDx.getValue());
    }

    public LiveData<String[]> getCountryNameList(){
        return poCountry.getAllCountryNames();
    }

    public LiveData<List<ECountryInfo>> getCountryInfoList(){
        return poCountry.getAllCountryInfo();
    }

    public LiveData<String[]> getJobTitleNameList(){
        return poJobRepo.getAllOccupationNameList();
    }

    public LiveData<List<EOccupationInfo>> getJobTitleInfoList(){
        return poJobRepo.getAllOccupationInfo();
    }

    public LiveData<ArrayAdapter<String>> getEmploymentStatus(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.EMPLOYMENT_STATUS));
        return liveData;
    }

    public void setEmploymentStatus(String status){
        this.psEmpStat.setValue(status);
    }

    public LiveData<ArrayAdapter<String>> getLengthOfService(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.LENGTH_OF_STAY));
        return liveData;
    }

    public void SaveEmploymentInfo(EmploymentInfoModel infoModel, ViewModelCallBack callBack){
        try{
            infoModel.setEmploymentSector(psSectorx.getValue());
            infoModel.setUniformPersonal(psUniform.getValue());
            infoModel.setMilitaryPersonal(psMiltary.getValue());
            infoModel.setProvinceID(psProvIDx.getValue());
            infoModel.setEmployeeStatus(psEmpStat.getValue());
            if(infoModel.isDataValid()){
                poGoCasxx.MeansInfo().EmployedInfo().setEmploymentSector(infoModel.getEmploymentSector());
                poGoCasxx.MeansInfo().EmployedInfo().IsUniformedPersonel(infoModel.getUniformPersonal());
                poGoCasxx.MeansInfo().EmployedInfo().IsMilitaryPersonel(infoModel.getMilitaryPersonal());
                poGoCasxx.MeansInfo().EmployedInfo().setGovernmentLevel(infoModel.getGovermentLevel());
                poGoCasxx.MeansInfo().EmployedInfo().setOFWRegion(infoModel.getOfwRegion());
                poGoCasxx.MeansInfo().EmployedInfo().setCompanyLevel(infoModel.getCompanyLevel());
                poGoCasxx.MeansInfo().EmployedInfo().setEmployeeLevel(infoModel.getEmployeeLevel());
                poGoCasxx.MeansInfo().EmployedInfo().setOFWCategory(infoModel.getOfwWorkCategory());
                poGoCasxx.MeansInfo().EmployedInfo().setOFWNation(infoModel.getCountry());
                poGoCasxx.MeansInfo().EmployedInfo().setNatureofBusiness(infoModel.getBusinessNature());
                poGoCasxx.MeansInfo().EmployedInfo().setCompanyName(infoModel.getCompanyName());
                poGoCasxx.MeansInfo().EmployedInfo().setCompanyAddress(infoModel.getCompanyAddress());
                poGoCasxx.MeansInfo().EmployedInfo().setCompanyTown(infoModel.getTownID());
                poGoCasxx.MeansInfo().EmployedInfo().setPosition(infoModel.getJobTitle());
                poGoCasxx.MeansInfo().EmployedInfo().setJobDescription(infoModel.getSpecificJob());
                poGoCasxx.MeansInfo().EmployedInfo().setEmployeeStatus(infoModel.getEmployeeStatus());
                poGoCasxx.MeansInfo().EmployedInfo().setLengthOfService(infoModel.getLengthOfService());
                poGoCasxx.MeansInfo().EmployedInfo().setSalary(infoModel.getMonthlyIncome());
                poGoCasxx.MeansInfo().EmployedInfo().setCompanyNo(infoModel.getContact());

                poInfo.setEmplymnt(poGoCasxx.MeansInfo().EmployedInfo().toJSONString());
                poCredtAp.updateGOCasData(poInfo);
                Log.e(TAG, poGoCasxx.toJSONString());
                callBack.onSaveSuccessResult(String.valueOf(getNextPage()));
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
        }
    }

    public int getNextPage() throws Exception{
        if(Objects.requireNonNull(poJson.getValue()).getString("sEmplyed").equalsIgnoreCase("1")) {
            return 4;
        } else if(poJson.getValue().getString("financer").equalsIgnoreCase("1")) {
            return 5;
        } else if(poJson.getValue().getString("pensionx").equalsIgnoreCase("1")) {
            return 6;
        } else if("1".equalsIgnoreCase(poInfo.getIsSpouse())){
            return 7;
        } else {
            return 12;
        }
    }
}