package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

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
import org.rmj.guanzongroup.onlinecreditapplication.Model.SpouseEmploymentInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;
import java.util.Objects;

public class VMSpouseEmploymentInfo extends AndroidViewModel {
    public static final String TAG = VMSpouseEmploymentInfo.class.getSimpleName();
    private final RProvince poProvnce;
    private final RTown poTownRpo;
    private final RCountry poCountry;
    private final ROccupation poJobRepo;
    private final RCreditApplicant poCredtAp;
    private final GOCASApplication poGoCasxx;

    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();
    private final MutableLiveData<String> psSectorx = new MutableLiveData<>();
    private final MutableLiveData<String> psUniform = new MutableLiveData<>();
    private final MutableLiveData<String> psMiltary = new MutableLiveData<>();
    private final MutableLiveData<String> psProvIDx = new MutableLiveData<>();
    private final MutableLiveData<String> psTownIDx = new MutableLiveData<>();
    private final MutableLiveData<String> psJobTtle = new MutableLiveData<>();
    private final MutableLiveData<String> psEmpStat = new MutableLiveData<>();
    private final MutableLiveData<String> psCountry = new MutableLiveData<>();

    private final MutableLiveData<Integer> pnGovInfo = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnCountry = new MutableLiveData<>();

    public VMSpouseEmploymentInfo(@NonNull Application application) {
        super(application);
        this.poProvnce = new RProvince(application);
        this.poTownRpo = new RTown(application);
        this.poCountry = new RCountry(application);
        this.poJobRepo = new ROccupation(application);
        this.poCredtAp = new RCreditApplicant(application);
        this.poGoCasxx = new GOCASApplication();
        this.psCountry.setValue("");
        this.psSectorx.setValue("1");
        this.pnGovInfo.setValue(View.GONE);
        this.pnCountry.setValue(View.GONE);
    }

    public void setTransNox(String transNox){
        this.psTransNo.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicationInfo(){
        return poCredtAp.getCreditApplicantInfoLiveData(psTransNo.getValue());
    }

    public void setCreditApplicantInfo(String applicantInfo){
        try{
            poGoCasxx.setData(applicantInfo);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setEmploymentSector(String sector){
        if(sector.equalsIgnoreCase("0")){
            this.pnCountry.setValue(View.GONE);
            this.pnGovInfo.setValue(View.VISIBLE);
        }
        if(sector.equalsIgnoreCase("1")){
            this.pnCountry.setValue(View.GONE);
            this.pnGovInfo.setValue(View.GONE);
        }
        if(sector.equalsIgnoreCase("2")){
            this.pnCountry.setValue(View.VISIBLE);
            this.pnGovInfo.setValue(View.GONE);
        }
        this.psSectorx.setValue(sector);
    }

    public void setUniformPersonnel(String isUniform){
        this.psUniform.setValue(isUniform);
    }

    public void setMilitaryPersonnel(String isMilitary){
        this.psMiltary.setValue(isMilitary);
    }

    public LiveData<ArrayAdapter<String>> getCompanyLevelList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.COMPANY_LEVEL);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getEmployeeLevelList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.EMPLOYEE_LEVEL);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getGovernmentLevelList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.GOVERMENT_LEVEL);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getRegionList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.OFW_REGION);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getWorkCategoryList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.OFW_CATEGORY);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getBusinessNature(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.BUSINESS_NATURE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
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

    public void setTownID(String ID){
        this.psTownIDx.setValue(ID);
    }

    public LiveData<String[]> getCountryNameList(){
        return poCountry.getAllCountryNames();
    }

    public LiveData<List<ECountryInfo>> getCountryInfoList(){
        return poCountry.getAllCountryInfo();
    }

    public void setCountry(String ID){
        this.psCountry.setValue(ID);
    }

    public LiveData<String[]> getJobTitleNameList(){
        return poJobRepo.getAllOccupationNameList();
    }

    public LiveData<List<EOccupationInfo>> getJobTitleInfoList(){
        return poJobRepo.getAllOccupationInfo();
    }

    public void setJobTitle(String ID){
        this.psJobTtle.setValue(ID);
    }

    public LiveData<ArrayAdapter<String>> getEmploymentStatus(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.EMPLOYMENT_STATUS);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void setEmploymentStatus(String status){
        this.psEmpStat.setValue(status);
    }

    public LiveData<ArrayAdapter<String>> getLengthOfService(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.LENGTH_OF_STAY);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void SaveEmploymentInfo(SpouseEmploymentInfoModel infoModel, ViewModelCallBack callBack){
//        try{
//            infoModel.setEmploymentSector(psSectorx.getValue());
//            infoModel.setUniformPersonal(psUniform.getValue());
//            infoModel.setMilitaryPersonal(psMiltary.getValue());
//            infoModel.setCountry(psCountry.getValue());
//            infoModel.setProvinceID(psProvIDx.getValue());
//            infoModel.setTownID(psTownIDx.getValue());
//            infoModel.setJobTitle(psJobTtle.getValue());
//            infoModel.setEmployeeStatus(psEmpStat.getValue());
//            if(infoModel.isDataValid()){
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setEmploymentSector(infoModel.getEmploymentSector());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().IsUniformedPersonel(infoModel.getUniformPersonal());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().IsMilitaryPersonel(infoModel.getMilitaryPersonal());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setGovernmentLevel(infoModel.getGovermentLevel());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setOFWRegion(infoModel.getOfwRegion());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setCompanyLevel(infoModel.getCompanyLevel());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setEmployeeLevel(infoModel.getEmploymentSector());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setOFWCategory(infoModel.getOfwWorkCategory());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setOFWNation(infoModel.getCountry());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setNatureofBusiness(infoModel.getBusinessNature());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setCompanyName(infoModel.getCompanyName());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setCompanyAddress(infoModel.getCompanyAddress());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setPosition(infoModel.getJobTitle());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setJobDescription(infoModel.getSpecificJob());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setEmployeeStatus(infoModel.getEmployeeStatus());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setLengthOfService(infoModel.getLengthOfService());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setSalary(infoModel.getMonthlyIncome());
//                poGoCasxx.SpouseMeansInfo().EmployedInfo().setCompanyNo(infoModel.getContact());
//
//                ECreditApplicantInfo info = new ECreditApplicantInfo();
//                info.setTransNox(Objects.requireNonNull(psTransNo.getValue()));
//                info.setDetlInfo(poGoCasxx.toJSONString());
//                info.setClientNm(poGoCasxx.ApplicantInfo().getClientName());
//                poCredtAp.updateGOCasData(info);
//
//                Log.e(TAG, poGoCasxx.SpouseMeansInfo().EmployedInfo().toJSONString());
//                callBack.onSaveSuccessResult("Success");
//            } else {
//                callBack.onFailedResult(infoModel.getMessage());
//            }
//        } catch (Exception e){
//            e.printStackTrace();
//            callBack.onFailedResult(e.getMessage());
//        }
    }
}