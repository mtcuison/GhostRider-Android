package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.ROccupation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DependentsInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.DisbursementInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.OtherInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMDependent extends AndroidViewModel {

    private static final String TAG = VMDependent.class.getSimpleName();
    public final MutableLiveData<String> psTranNo = new MutableLiveData<>();
    private final MutableLiveData<String> spnSchoolType = new MutableLiveData<>();
    private final MutableLiveData<String> spnSchoolLvl = new MutableLiveData<>();
    private final MutableLiveData<String> spnRelationx = new MutableLiveData<>();
    private final MutableLiveData<String> spnEmpSector = new MutableLiveData<>();
    private final MutableLiveData<String> spnStudentStatus = new MutableLiveData<>();
    private final MutableLiveData<String> spnEmpTypeX = new MutableLiveData<>();


    private final MutableLiveData<Integer> linearEmployed = new MutableLiveData<>();
    private final MutableLiveData<Integer> linearStudent = new MutableLiveData<>();

    private final List<DependentsInfoModel> infoModels;
    private final MutableLiveData<List<DependentsInfoModel>> dependentInfo = new MutableLiveData<>();

    private final RCreditApplicant poApplcnt;
    private final RProvince poProvnce;
    private final RTown poTownRpo;
    private final GOCASApplication poGoCasxx;
    private ECreditApplicantInfo poInfo;

    public VMDependent(@NonNull Application application) {
        super(application);
        this.poProvnce = new RProvince(application);
        this.poTownRpo = new RTown(application);
        this.poApplcnt = new RCreditApplicant(application);
        this.poGoCasxx = new GOCASApplication();
        infoModels = new ArrayList<>();
        dependentInfo.setValue(new ArrayList<>());
        this.linearEmployed.setValue(View.GONE);
        this.linearStudent.setValue(View.GONE);
    }

    public void setTransNox(String transNox) {
        this.psTranNo.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicationInfo() {
        return poApplcnt.getCreditApplicantInfoLiveData(psTranNo.getValue());
    }

    public void setCreditApplicantInfo(ECreditApplicantInfo applicantInfo) {
        try {
            this.poInfo = applicantInfo;
            poGoCasxx.setData(poInfo.getDetlInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LiveData<Integer> setLinearStudent() {
        return linearStudent;
    }

    public LiveData<Integer> setLinearEmployed() {
        return linearEmployed;
    }

    public void setEducLevel(String educLevel) {
        this.spnSchoolLvl.setValue(educLevel);
    }

    public void setSpnSchoolType(String type) {
        this.spnSchoolType.setValue(type);
    }

    public void setSpnRelationx(String spnRelationx) {
//        if (spnRelationx.trim().isEmpty() || spnRelationx == null){
//            this.spnRelationx.setValue("0");
//        }else {
//            this.spnRelationx.setValue(spnRelationx);
//        }
        this.spnRelationx.setValue(spnRelationx);

    }

    public LiveData<String> getRelationX() {
        return this.spnRelationx;
    }

    public LiveData<String> getSchoolTypeX() {
        return this.spnSchoolType;
    }

    public LiveData<String> getSchoolLvlX() {
        return this.spnSchoolLvl;
    }

    public void setSpnEmpSector(String spnEmpSector) {
        try {
            if (spnEmpSector.equalsIgnoreCase("1")) {
                this.linearEmployed.setValue(View.VISIBLE);
            } else {
                this.linearEmployed.setValue(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.spnEmpSector.setValue(spnEmpSector);
    }

    public void setSpnStudentStatus(String studentStatus) {
        try {
            if (studentStatus.equalsIgnoreCase("1")) {
                this.linearStudent.setValue(View.VISIBLE);
            } else {
                this.linearStudent.setValue(View.GONE);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.spnStudentStatus.setValue(studentStatus);
    }


    public LiveData<ArrayAdapter<String>> getEducLevel() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.SCHOOL_LEVEL);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSchoolType() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.SCHOOL_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<List<EProvinceInfo>> getProvinceInfoList(){
        return poProvnce.getAllProvinceInfo();
    }

    public LiveData<String[]> getAllProvinceName(){
        return poProvnce.getAllProvinceNames();
    }

    public LiveData<List<ETownInfo>> getAllTownInfoList(String ProvID){
        return poTownRpo.getTownInfoFromProvince(ProvID);
    }

    public LiveData<String[]> getAllTownNames(String ProvID){
        return poTownRpo.getTownNamesFromProvince(ProvID);
    }

    public LiveData<ArrayAdapter<String>> getSpnRelationx() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.DEPENDENT_RELATIONSHIP);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSpnEmpSector() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.EMPLOYED_YES_NO);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSpnEmploymentType() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.EMPLOYMENT_SECTOR);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getStudentStatus() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.STUDENT_YES_NO);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<List<DependentsInfoModel>> getAllDependent() {
        return dependentInfo;
    }


    public boolean SubmitDependentInfo(ViewModelCallBack callBack){
        try {
            for (int x = 0; x < dependentInfo.getValue().size(); x++) {
                poGoCasxx.DisbursementInfo().DependentInfo().addDependent();
                poGoCasxx.DisbursementInfo().DependentInfo().setFullName(x, this.dependentInfo.getValue().get(x).getDpdFullName());
                poGoCasxx.DisbursementInfo().DependentInfo().setRelation(x, this.dependentInfo.getValue().get(x).getDpdRlationship());
                poGoCasxx.DisbursementInfo().DependentInfo().setAge(x, Integer.parseInt(this.dependentInfo.getValue().get(x).getDpdAge()));
                poGoCasxx.DisbursementInfo().DependentInfo().IsStudent(x, this.dependentInfo.getValue().get(x).getIsStudent());
                poGoCasxx.DisbursementInfo().DependentInfo().IsWorking(x, this.dependentInfo.getValue().get(x).getIsEmployed());
                poGoCasxx.DisbursementInfo().DependentInfo().IsDependent(x, this.dependentInfo.getValue().get(x).getIsDependent());
                poGoCasxx.DisbursementInfo().DependentInfo().IsHouseHold(x, this.dependentInfo.getValue().get(x).getIsHouseHold());
                poGoCasxx.DisbursementInfo().DependentInfo().IsMarried(x, this.dependentInfo.getValue().get(x).getIsMarried());
                poGoCasxx.DisbursementInfo().DependentInfo().setSchoolName(x, this.dependentInfo.getValue().get(x).getDpdSchoolName());
                poGoCasxx.DisbursementInfo().DependentInfo().setSchoolAddress(x, this.dependentInfo.getValue().get(x).getDpdSchoolAddress());
                poGoCasxx.DisbursementInfo().DependentInfo().setSchoolTown(x, this.dependentInfo.getValue().get(x).getDpdSchoolTown());
                poGoCasxx.DisbursementInfo().DependentInfo().setEducationalLevel(x, this.dependentInfo.getValue().get(x).getDpdEducLevel());
                poGoCasxx.DisbursementInfo().DependentInfo().IsPrivateSchool(x, this.dependentInfo.getValue().get(x).getDpdSchoolType());
                poGoCasxx.DisbursementInfo().DependentInfo().IsScholar(x, this.dependentInfo.getValue().get(x).getDpdIsScholar());
                poGoCasxx.DisbursementInfo().DependentInfo().IsWorking(x, this.dependentInfo.getValue().get(x).getIsEmployed());
                poGoCasxx.DisbursementInfo().DependentInfo().setWorkType(x, this.dependentInfo.getValue().get(x).getDpdEmployedSector());
                poGoCasxx.DisbursementInfo().DependentInfo().setCompany(x, this.dependentInfo.getValue().get(x).getDpdCompanyName());
            }
            poInfo.setDetlInfo(poGoCasxx.toJSONString());
            poApplcnt.updateGOCasData(poInfo);
            Log.e(TAG, "Dependent info has been set." + poGoCasxx.DisbursementInfo().DependentInfo().toJSONString());
            callBack.onSaveSuccessResult("Success");

            return true;
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult(e.getMessage());
            return false;
        }
  }

//    public boolean AddDependent(DependentsInfoModel dependentsInfoModel , ExpActionListener listener){
//        if(dependentsInfoModel.isDataValid()) {
//            this.dependentInfo.getValue().add(dependentsInfoModel);
//            listener.onSuccess("Success");
//
//            return true;
//        } else {
//            listener.onFailed(dependentsInfoModel.getMessage());
//            return false;
//        }
//    }

    public boolean AddDependent(DependentsInfoModel foInfo , ExpActionListener listener){
        if(foInfo.isDataValid()) {
            Objects.requireNonNull(this.dependentInfo.getValue().add(foInfo));
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