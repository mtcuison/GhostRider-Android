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
    private final GOCASApplication poGoCas;
    private final RCreditApplicant poCreditApp;
    private final RCountry poCountry;
    private final RProvince poProvRepo;
    private final RTown poTownRepo;
    private final ROccupation poJobRepo;
    private ECreditApplicantInfo poInfo;

    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();
    private final MutableLiveData<String> psSector = new MutableLiveData<>();
    private final MutableLiveData<String> psUniform = new MutableLiveData<>();
    private final MutableLiveData<String> psMilitary = new MutableLiveData<>();
    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();
    private final MutableLiveData<String> psJobTitle = new MutableLiveData<>();
    private final MutableLiveData<String> psEmpStat = new MutableLiveData<>();
    private final MutableLiveData<String> psCountry = new MutableLiveData<>();

    private final MutableLiveData<String> psCmpLvl = new MutableLiveData<>();
    private final MutableLiveData<String> psEmpLvl = new MutableLiveData<>();
    private final MutableLiveData<String> psBsnssLvl = new MutableLiveData<>();
    private final MutableLiveData<String> psService = new MutableLiveData<>();

    private final MutableLiveData<Integer> pnGovInfo = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnCountry = new MutableLiveData<>();

    public VMSpouseEmploymentInfo(@NonNull Application application) {
        super(application);
        this.poGoCas = new GOCASApplication();
        this.poCreditApp = new RCreditApplicant(application);
        this.poCountry = new RCountry(application);
        this.poProvRepo = new RProvince(application);
        this.poTownRepo = new RTown(application);
        this.poJobRepo = new ROccupation(application);

        this.psCountry.setValue("");
        this.psSector.setValue("1");
        this.pnGovInfo.setValue(View.GONE);
        this.pnCountry.setValue(View.GONE);
    }

    public void setTransNox(String transNox){
        this.psTransNo.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getActiveGOCasApplication(){
        return poCreditApp.getCreditApplicantInfoLiveData(psTransNo.getValue());
    }

    //  Set Detail info to GoCas
    public void setDetailInfo(ECreditApplicantInfo fsDetailInfo){
        try{
            poInfo = fsDetailInfo;
            poGoCas.setData(poInfo.getDetlInfo());
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
        this.psSector.setValue(sector);
    }

    public void setPsCmpLvl(String compLvl){
        this.psCmpLvl.setValue(compLvl);
    }

    public LiveData<String> getPsCmpLvl(){
        return this.psCmpLvl;
    }

    public void setPsEmpLvl(String empLvl){
        this.psEmpLvl.setValue(empLvl);
    }
    public LiveData<String> getPsEmpLvl(){
        return this.psEmpLvl;
    }
    public void setPsBsnssLvl(String bsnssLvl){
        this.psBsnssLvl.setValue(bsnssLvl);
    }
    public LiveData<String> getPsBsnssLvl(){
        return this.psBsnssLvl;
    }
    public void setPsService(String service){
        this.psService.setValue(service);
    }
    public LiveData<String> getPsService(){
        return this.psService;
    }

    public void setUniformPersonnel(String isUniform){
        this.psUniform.setValue(isUniform);
    }

    public void setMilitaryPersonnel(String isMilitary){
        this.psMilitary.setValue(isMilitary);
    }

    public LiveData<ArrayAdapter<String>> getCompanyLevelList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.COMPANY_LEVEL);
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

    public LiveData<ArrayAdapter<String>> getEmployeeLevelList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.EMPLOYEE_LEVEL);
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

    public LiveData<ArrayAdapter<String>> getRegionList(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.OFW_REGION);
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
        return poProvRepo.getAllProvinceNames();
    }

    public LiveData<List<EProvinceInfo>> getProvinceInfo(){
        return poProvRepo.getAllProvinceInfo();
    }

    public void setProvinceID(String ID){
        this.psProvID.setValue(ID);
    }

    public LiveData<String[]> getTownNameList(){
        return poTownRepo.getTownNamesFromProvince(psProvID.getValue());
    }

    public LiveData<List<ETownInfo>> getTownInfoList(){
        return poTownRepo.getTownInfoFromProvince(psProvID.getValue());
    }

    public void setTownID(String ID){
        this.psTownID.setValue(ID);
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
        this.psJobTitle.setValue(ID);
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

    public void Save(SpouseEmploymentInfoModel infoModel, ViewModelCallBack callBack){
        try{
            infoModel.setSector(psSector.getValue());
            infoModel.setUniformedPersonnel(psUniform.getValue());
            infoModel.setMilitaryPersonnel(psMilitary.getValue());
            infoModel.setCountry(psCountry.getValue());
            infoModel.setCompProvince(psProvID.getValue());
            infoModel.setCompTown(psTownID.getValue());
            infoModel.setJobTitle(psJobTitle.getValue());
            infoModel.setEmploymentStat(psEmpStat.getValue());

            infoModel.setCompanyLvl(psCmpLvl.getValue());
            infoModel.setEmployeeLvl(psEmpLvl.getValue());
            infoModel.setBizIndustry(psBsnssLvl.getValue());
            if(infoModel.isSpouseEmploymentInfoValid()){
                poGoCas.SpouseMeansInfo().EmployedInfo().setEmploymentSector(infoModel.getSector());
                poGoCas.SpouseMeansInfo().EmployedInfo().IsUniformedPersonel(infoModel.getUniformedPersonnel());
                poGoCas.SpouseMeansInfo().EmployedInfo().IsMilitaryPersonel(infoModel.getMilitaryPersonnel());
                poGoCas.SpouseMeansInfo().EmployedInfo().setGovernmentLevel(infoModel.getGovermentLevel());
                poGoCas.SpouseMeansInfo().EmployedInfo().setOFWRegion(infoModel.getOFWRegion());
                poGoCas.SpouseMeansInfo().EmployedInfo().setCompanyLevel(infoModel.getCompanyLevel());
                poGoCas.SpouseMeansInfo().EmployedInfo().setEmployeeLevel(infoModel.getEmployeeLvl());
                poGoCas.SpouseMeansInfo().EmployedInfo().setOFWCategory(infoModel.getWorkCategory());
                poGoCas.SpouseMeansInfo().EmployedInfo().setOFWNation(infoModel.getCountry());
                poGoCas.SpouseMeansInfo().EmployedInfo().setNatureofBusiness(infoModel.getBizIndustry());
                poGoCas.SpouseMeansInfo().EmployedInfo().setCompanyName(infoModel.getCompanyName());
                poGoCas.SpouseMeansInfo().EmployedInfo().setCompanyAddress(infoModel.getCompAddress());
                poGoCas.SpouseMeansInfo().EmployedInfo().setCompanyTown(infoModel.getCompTown());
                poGoCas.SpouseMeansInfo().EmployedInfo().setPosition(infoModel.getJobTitle());
                poGoCas.SpouseMeansInfo().EmployedInfo().setJobDescription(infoModel.getJobSpecific());
                poGoCas.SpouseMeansInfo().EmployedInfo().setEmployeeStatus(infoModel.getEmploymentStat());
                poGoCas.SpouseMeansInfo().EmployedInfo().setLengthOfService(infoModel.getLengthOfService());
                poGoCas.SpouseMeansInfo().EmployedInfo().setSalary(infoModel.getGrossMonthly());
                poGoCas.SpouseMeansInfo().EmployedInfo().setCompanyNo(infoModel.getCompTelNox());

                poInfo.setTransNox(Objects.requireNonNull(psTransNo.getValue()));
                poInfo.setSpsEmplx(poGoCas.SpouseMeansInfo().EmployedInfo().toJSONString());
                poCreditApp.updateGOCasData(poInfo);

                Log.e(TAG, poGoCas.SpouseMeansInfo().EmployedInfo().toJSONString());
                Log.e(TAG, "GOCAS Full JSON String : " + poGoCas.toJSONString());
                callBack.onSaveSuccessResult("Success");
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
        }
    }

}