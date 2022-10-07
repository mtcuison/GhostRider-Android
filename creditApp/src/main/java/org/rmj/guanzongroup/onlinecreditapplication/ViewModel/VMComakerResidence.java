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

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.dev.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.dev.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.dev.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.dev.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.dev.Database.Repositories.RCreditApplication;
import org.rmj.g3appdriver.dev.Database.Repositories.RProvince;
import org.rmj.g3appdriver.dev.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Data.UploadCreditApp;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.GOCASHolder;
import org.rmj.guanzongroup.onlinecreditapplication.Model.CoMakerResidenceModel;
import org.rmj.guanzongroup.onlinecreditapplication.Data.GoCasBuilder;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;

public class VMComakerResidence extends AndroidViewModel {
    private static final String TAG = VMComakerResidence.class.getSimpleName();
    private ECreditApplicantInfo poInfo;
    private final GOCASApplication poGoCas;
    private final RCreditApplicant poCreditApp;
    private final RCreditApplication poApplx;
    private final RProvince poProvnce;
    private final RTown poTownRpo;
    private final RBarangay poBarangy;
    private final Application instance;
    private final RBranchLoanApplication poLoan;

    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();

    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();

    public VMComakerResidence(@NonNull Application application) {
        super(application);
        this.poGoCas = new GOCASApplication();
        this.poCreditApp = new RCreditApplicant(application);
        this.poProvnce = new RProvince(application);
        this.poTownRpo = new RTown(application);
        this.poBarangy = new RBarangay(application);
        this.poApplx = new RCreditApplication(application);
        this.instance = application;
        this.poLoan = new RBranchLoanApplication(application);
    }

    public void setTransNox(String transNox){
        this.psTransNox.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicantInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(psTransNox.getValue());
    }

    public void setCreditApplicantInfo(ECreditApplicantInfo applicantInfo){
        poInfo = applicantInfo;
    }

    public LiveData<String[]> getProvinceNameList(){
        return poProvnce.getAllProvinceNames();
    }

    public LiveData<List<EProvinceInfo>> getProvinceInfo(){
        return poProvnce.getAllProvinceInfo();
    }

    public void setProvinceID(String ProvID){
        this.psProvID.setValue(ProvID);
    }

    public LiveData<String[]> getTownNameList(){
        return poTownRpo.getTownNamesFromProvince(psProvID.getValue());
    }

    public LiveData<List<ETownInfo>> getTownInfoList(){
        return poTownRpo.getTownInfoFromProvince(psProvID.getValue());
    }

    public void setTownID(String townID){
        this.psTownID.setValue(townID);
    }

    public LiveData<String[]> getBarangayListName(){
        return poBarangy.getBarangayNamesFromTown(psTownID.getValue());
    }

    public LiveData<List<EBarangayInfo>> getBarangayInfoList(){
        return poBarangy.getAllBarangayFromTown(psTownID.getValue());
    }

    public LiveData<DTownInfo.BrgyTownProvinceInfoWithID> getBrgyTownProvinceInfoWithID(String BrgyID)  {
        return poTownRpo.getBrgyTownProvinceInfoWithID(BrgyID);
    }

    public LiveData<ArrayAdapter<String>> getHouseHolds(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.HOUSEHOLDS));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getHouseType(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.HOUSE_TYPE));
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getLenghtOfStay(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.LENGTH_OF_STAY));
        return liveData;
    }

    public void SaveCoMakerResidence(CoMakerResidenceModel infoModel, ViewModelCallBack callBack){
        if(infoModel.isDataValid()){
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setLandMark(infoModel.getsLandMark());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setHouseNo(infoModel.getsHouseNox());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setAddress1(infoModel.getsAddress1());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setAddress2(infoModel.getsAddress2());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setTownCity(infoModel.getsMuncplID());
            poGoCas.CoMakerInfo().ResidenceInfo().PresentAddress().setBarangay(infoModel.getsBrgyIDxx());
            poGoCas.CoMakerInfo().ResidenceInfo().setOwnership(infoModel.getsHouseOwn());
            poGoCas.CoMakerInfo().ResidenceInfo().setCareTakerRelation(infoModel.getsRelation());
            poGoCas.CoMakerInfo().ResidenceInfo().setOwnedResidenceInfo(infoModel.getsHouseHld());
            poGoCas.CoMakerInfo().ResidenceInfo().setHouseType(infoModel.getsHouseTpe());
            poGoCas.CoMakerInfo().ResidenceInfo().setRentedResidenceInfo(infoModel.getsHouseHld());
            poGoCas.CoMakerInfo().ResidenceInfo().setRentExpenses(infoModel.getsExpenses());
            poGoCas.CoMakerInfo().ResidenceInfo().setRentNoYears(infoModel.getsLenghtSt());
            poGoCas.CoMakerInfo().ResidenceInfo().hasGarage(infoModel.getsHasGarge());
            poInfo.setCmResidx(poGoCas.CoMakerInfo().ResidenceInfo().toJSONString());
            poCreditApp.updateGOCasData(poInfo);
            Log.e(TAG, poGoCas.toJSONString());
            callBack.onSaveSuccessResult("success");
        } else {
            callBack.onFailedResult(infoModel.getMessage());
        }
    }
}