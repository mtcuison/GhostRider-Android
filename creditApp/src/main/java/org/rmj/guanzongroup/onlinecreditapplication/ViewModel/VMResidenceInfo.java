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

import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EProvinceInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ETownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RProvince;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ResidenceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;

public class VMResidenceInfo extends AndroidViewModel {
    private ECreditApplicantInfo poInfo;
    private final GOCASApplication poGoCas;
    private final RCreditApplicant RCreditApplicant;
    private final RProvince poProvnce;
    private final RTown poTownRpo;
    private final RBarangay poBarangy;

    private final MutableLiveData<String> TRANSNOX = new MutableLiveData<>();
    private final MutableLiveData<String> psProvID = new MutableLiveData<>();
    private final MutableLiveData<String> psTownID = new MutableLiveData<>();
    private final MutableLiveData<String> psBrgyID = new MutableLiveData<>();
    private final MutableLiveData<String> psPProvD = new MutableLiveData<>();
    private final MutableLiveData<String> psPTownD = new MutableLiveData<>();
    private final MutableLiveData<String> psPBrgyD = new MutableLiveData<>();

    public VMResidenceInfo(@NonNull Application application) {
        super(application);
        this.RCreditApplicant = new RCreditApplicant(application);
        this.poProvnce = new RProvince(application);
        this.poTownRpo = new RTown(application);
        this.poBarangy = new RBarangay(application);
        this.poGoCas = new GOCASApplication();
    }

    public void setProvinceID(String ID){
        this.psProvID.setValue(ID);
    }

    public void setTownID(String ID){
        this.psTownID.setValue(ID);
    }

    public void setBarangayID(String ID){
        this.psBrgyID.setValue(ID);
    }

    public void setPermanentProvinceID(String ID){
        this.psPProvD.setValue(ID);
    }

    public void setPermanentTownID(String ID){
        this.psPTownD.setValue(ID);
    }

    public void setPermanentBarangayID(String ID){
        this.psPBrgyD.setValue(ID);
    }

    public void setTransNox(String transNox){
        this.TRANSNOX.setValue(transNox);
    }

    public LiveData<ECreditApplicantInfo> getCreditApplicationInfo(){
        return RCreditApplicant.getCreditApplicantInfoLiveData(TRANSNOX.getValue());
    }

    public void setGOCasDetailInfo(ECreditApplicantInfo DetailInfo){
        try{
            poInfo = DetailInfo;
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<String[]> getProvinceNameList(){
        return poProvnce.getAllProvinceNames();
    }

    public LiveData<List<EProvinceInfo>> getProvinceInfoList(){
        return poProvnce.getAllProvinceInfo();
    }

    public LiveData<String[]> getTownNameList(){
        return poTownRpo.getTownNamesFromProvince(psProvID.getValue());
    }

    public LiveData<List<ETownInfo>> getTownInfoList(){
        return poTownRpo.getTownInfoFromProvince(psProvID.getValue());
    }

    public LiveData<String[]> getBarangayNameList(){
        return poBarangy.getBarangayNamesFromTown(psTownID.getValue());
    }

    public LiveData<List<EBarangayInfo>> getBarangayInfoList(){
        return poBarangy.getAllBarangayFromTown(psTownID.getValue());
    }

    public LiveData<String[]> getPermanentTownNameList(){
        return poTownRpo.getTownNamesFromProvince(psPProvD.getValue());
    }

    public LiveData<List<ETownInfo>> getPermanentTownInfoList(){
        return poTownRpo.getTownInfoFromProvince(psPProvD.getValue());
    }

    public LiveData<String[]> getPermanentBarangayNameList(){
        return poBarangy.getBarangayNamesFromTown(psPTownD.getValue());
    }

    public LiveData<List<EBarangayInfo>> getPermanentBarangayInfoList(){
        return poBarangy.getAllBarangayFromTown(psPTownD.getValue());
    }

    public LiveData<ArrayAdapter<String>> getHouseHolds(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.HOUSEHOLDS){
            @SuppressLint("ResourceAsColor")
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                //change the color to which ever you want
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    ((CheckedTextView) view).setTextColor(Color.WHITE);
                }else{
                    ((CheckedTextView) view).setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getHouseType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.HOUSE_TYPE){
            @SuppressLint("ResourceAsColor")
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                //change the color to which ever you want
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    ((CheckedTextView) view).setTextColor(Color.WHITE);
                }else{
                    ((CheckedTextView) view).setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getLenghtOfStay(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.LENGTH_OF_STAY){
            @SuppressLint("ResourceAsColor")
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                //change the color to which ever you want
                if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                    ((CheckedTextView) view).setTextColor(Color.WHITE);
                }else{
                    ((CheckedTextView) view).setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public void SaveResidenceInfo(ResidenceInfoModel infoModel, ViewModelCallBack callBack){
        try {
            infoModel.setProvinceID(psProvID.getValue());
            infoModel.setMunicipalID(psTownID.getValue());
            infoModel.setBarangayID(psBrgyID.getValue());
            infoModel.setPermanentProvinceID(psPProvD.getValue());
            infoModel.setPermanentMunicipalID(psPTownD.getValue());
            infoModel.setPermanentBarangayID(psPBrgyD.getValue());
            if(infoModel.isDataValid()) {
                poGoCas.ResidenceInfo().PresentAddress().setLandMark(infoModel.getLandMark());
                poGoCas.ResidenceInfo().PresentAddress().setHouseNo(infoModel.getHouseNox());
                poGoCas.ResidenceInfo().PresentAddress().setAddress1(infoModel.getAddress1());
                poGoCas.ResidenceInfo().PresentAddress().setAddress2(infoModel.getAddress2());
                poGoCas.ResidenceInfo().PresentAddress().setTownCity(infoModel.getMunicipalID());
                poGoCas.ResidenceInfo().PresentAddress().setBarangay(infoModel.getBarangayID());
                poGoCas.ResidenceInfo().setOwnership(infoModel.getHouseOwn());
                poGoCas.ResidenceInfo().setCareTakerRelation(infoModel.getOwnerRelation());
                poGoCas.ResidenceInfo().setOwnedResidenceInfo(infoModel.getHouseHold());
                poGoCas.ResidenceInfo().setHouseType(infoModel.getHouseType());
                poGoCas.ResidenceInfo().setRentedResidenceInfo(infoModel.getHouseHold());
                poGoCas.ResidenceInfo().setRentExpenses(infoModel.getMonthlyExpenses());
                poGoCas.ResidenceInfo().setRentNoYears(infoModel.getLenghtofStay());
                poGoCas.ResidenceInfo().hasGarage(infoModel.getHasGarage());
                poGoCas.ResidenceInfo().PermanentAddress().setLandMark(infoModel.getPermanentLandMark());
                poGoCas.ResidenceInfo().PermanentAddress().setHouseNo(infoModel.getPermanentHouseNo());
                poGoCas.ResidenceInfo().PermanentAddress().setAddress1(infoModel.getPermanentAddress1());
                poGoCas.ResidenceInfo().PermanentAddress().setAddress2(infoModel.getPermanentAddress2());
                poGoCas.ResidenceInfo().PermanentAddress().setTownCity(infoModel.getPermanentMunicipalID());
                poGoCas.ResidenceInfo().PermanentAddress().setBarangay(infoModel.getPermanentBarangayID());
                poInfo.setResidnce(poGoCas.ResidenceInfo().toJSONString());
                RCreditApplicant.updateGOCasData(poInfo);
                callBack.onSaveSuccessResult("Success");
            } else {
                 callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            callBack.onFailedResult(e.getMessage());
            e.printStackTrace();
        }
    }
}