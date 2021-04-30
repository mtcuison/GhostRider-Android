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
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.constraintlayout.solver.widgets.analyzer.Dependency;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplication;
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
import org.rmj.guanzongroup.onlinecreditapplication.Model.PersonalReferenceInfoModel;
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
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.SCHOOL_LEVEL));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSchoolType() {
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.SCHOOL_TYPE));
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
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.DEPENDENT_RELATIONSHIP));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSpnEmpSector() {
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.EMPLOYED_YES_NO));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getSpnEmploymentType() {
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.EMPLOYMENT_SECTOR));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getStudentStatus() {
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.STUDENT_YES_NO));
        return liveData;
    }

    public LiveData<List<DependentsInfoModel>> getAllDependent() {
        return dependentInfo;
    }


//    public boolean SubmitDependentInfo(ViewModelCallBack callBack){
//        try {
//            for (int x = 0; x < dependentInfo.getValue().size(); x++) {
//                poGoCasxx.DisbursementInfo().DependentInfo().addDependent();
//                poGoCasxx.DisbursementInfo().DependentInfo().setFullName(x, this.dependentInfo.getValue().get(x).getDpdFullName());
//                poGoCasxx.DisbursementInfo().DependentInfo().setRelation(x, this.dependentInfo.getValue().get(x).getDpdRlationship());
//                poGoCasxx.DisbursementInfo().DependentInfo().setAge(x, Integer.parseInt(this.dependentInfo.getValue().get(x).getDpdAge()));
//                poGoCasxx.DisbursementInfo().DependentInfo().IsStudent(x, this.dependentInfo.getValue().get(x).getIsStudent());
//                poGoCasxx.DisbursementInfo().DependentInfo().IsWorking(x, this.dependentInfo.getValue().get(x).getIsEmployed());
//                poGoCasxx.DisbursementInfo().DependentInfo().IsDependent(x, this.dependentInfo.getValue().get(x).getIsDependent());
//                poGoCasxx.DisbursementInfo().DependentInfo().IsHouseHold(x, this.dependentInfo.getValue().get(x).getIsHouseHold());
//                poGoCasxx.DisbursementInfo().DependentInfo().IsMarried(x, this.dependentInfo.getValue().get(x).getIsMarried());
//                poGoCasxx.DisbursementInfo().DependentInfo().setSchoolName(x, this.dependentInfo.getValue().get(x).getDpdSchoolName());
//                poGoCasxx.DisbursementInfo().DependentInfo().setSchoolAddress(x, this.dependentInfo.getValue().get(x).getDpdSchoolAddress());
//                poGoCasxx.DisbursementInfo().DependentInfo().setSchoolTown(x, this.dependentInfo.getValue().get(x).getDpdSchoolTown());
//                poGoCasxx.DisbursementInfo().DependentInfo().setEducationalLevel(x, this.dependentInfo.getValue().get(x).getDpdEducLevel());
//                poGoCasxx.DisbursementInfo().DependentInfo().IsPrivateSchool(x, this.dependentInfo.getValue().get(x).getDpdSchoolType());
//                poGoCasxx.DisbursementInfo().DependentInfo().IsScholar(x, this.dependentInfo.getValue().get(x).getDpdIsScholar());
//                poGoCasxx.DisbursementInfo().DependentInfo().IsWorking(x, this.dependentInfo.getValue().get(x).getIsEmployed());
//                poGoCasxx.DisbursementInfo().DependentInfo().setWorkType(x, this.dependentInfo.getValue().get(x).getDpdEmployedSector());
//                poGoCasxx.DisbursementInfo().DependentInfo().setCompany(x, this.dependentInfo.getValue().get(x).getDpdCompanyName());
//            }
//            poInfo.setDependnt(poGoCasxx.DisbursementInfo().DependentInfo().toJSONString());
//            //poInfo.setDetlInfo(poGoCasxx.toJSONString());
//            poApplcnt.updateGOCasData(poInfo);
//            Log.e(TAG, "Dependent info has been set." + poGoCasxx.DisbursementInfo().DependentInfo().toJSONString());
//            callBack.onSaveSuccessResult("Success");
//            return true;
//        } catch (Exception e){
//            e.printStackTrace();
//            callBack.onFailedResult(e.getMessage());
//            return false;
//        }
//  }

    public boolean SubmitDependentInfo(ViewModelCallBack callBack) {
        try {
            new UpdateTask(poApplcnt, poInfo, dependentInfo.getValue(), callBack).execute();
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
        private final ECreditApplicantInfo poInfo;
        private final ViewModelCallBack callback;
        private final List<DependentsInfoModel> dependentsInfoModels;
        public UpdateTask(RCreditApplicant poDcp, ECreditApplicantInfo poInfo, List<DependentsInfoModel> dependentsInfoModels, ViewModelCallBack callback) {
            this.poDcp = poDcp;
            this.poInfo = poInfo;
            this.dependentsInfoModels = dependentsInfoModels;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(RCreditApplicant... rApplicant) {
            try{
                for (int x = 0; x < dependentsInfoModels.size(); x++) {
                    poGoCasxx.DisbursementInfo().DependentInfo().addDependent();
                    poGoCasxx.DisbursementInfo().DependentInfo().setFullName(x, this.dependentsInfoModels.get(x).getDpdFullName());
                    poGoCasxx.DisbursementInfo().DependentInfo().setRelation(x, this.dependentsInfoModels.get(x).getDpdRlationship());
                    poGoCasxx.DisbursementInfo().DependentInfo().setAge(x, Integer.parseInt(this.dependentsInfoModels.get(x).getDpdAge()));
                    poGoCasxx.DisbursementInfo().DependentInfo().IsStudent(x, this.dependentsInfoModels.get(x).getIsStudent());
                    poGoCasxx.DisbursementInfo().DependentInfo().IsWorking(x, this.dependentsInfoModels.get(x).getIsEmployed());
                    poGoCasxx.DisbursementInfo().DependentInfo().IsDependent(x, this.dependentsInfoModels.get(x).getIsDependent());
                    poGoCasxx.DisbursementInfo().DependentInfo().IsHouseHold(x, this.dependentsInfoModels.get(x).getIsHouseHold());
                    poGoCasxx.DisbursementInfo().DependentInfo().IsMarried(x, this.dependentsInfoModels.get(x).getIsMarried());
                    poGoCasxx.DisbursementInfo().DependentInfo().setSchoolName(x, this.dependentsInfoModels.get(x).getDpdSchoolName());
                    poGoCasxx.DisbursementInfo().DependentInfo().setSchoolAddress(x, this.dependentsInfoModels.get(x).getDpdSchoolAddress());
                    poGoCasxx.DisbursementInfo().DependentInfo().setSchoolTown(x, this.dependentsInfoModels.get(x).getDpdSchoolTown());
                    poGoCasxx.DisbursementInfo().DependentInfo().setEducationalLevel(x, this.dependentsInfoModels.get(x).getDpdEducLevel());
                    poGoCasxx.DisbursementInfo().DependentInfo().IsPrivateSchool(x, this.dependentsInfoModels.get(x).getDpdSchoolType());
                    poGoCasxx.DisbursementInfo().DependentInfo().IsScholar(x, this.dependentsInfoModels.get(x).getDpdIsScholar());
                    poGoCasxx.DisbursementInfo().DependentInfo().IsWorking(x, this.dependentsInfoModels.get(x).getIsEmployed());
                    poGoCasxx.DisbursementInfo().DependentInfo().setWorkType(x, this.dependentsInfoModels.get(x).getDpdEmployedSector());
                    poGoCasxx.DisbursementInfo().DependentInfo().setCompany(x, this.dependentsInfoModels.get(x).getDpdCompanyName());

                }
                poInfo.setDependnt(poGoCasxx.DisbursementInfo().DependentInfo().toJSONString());
                poDcp.updateGOCasData(poInfo);
                return "success";

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

    public interface AddPersonalInfoListener{
        void OnSuccess();
        void onFailed(String message);
    }

    public boolean AddDependent(DependentsInfoModel foInfo , ExpActionListener listener){
        if(foInfo.isDataValid()) {
            this.dependentInfo.getValue().add(foInfo);
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