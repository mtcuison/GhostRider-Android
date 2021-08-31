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
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Activity.Activity_CreditApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PensionInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

public class VMPensionInfo extends AndroidViewModel {
    private static final String TAG = VMPersonalInfo.class.getSimpleName();
    private final RCreditApplicant poCreditApp;
    private final GOCASApplication poGoCas;
    private ECreditApplicantInfo poInfo;

    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psPensionSector = new MutableLiveData<>();
    private final MutableLiveData<JSONObject> poJson = new MutableLiveData<>();

    public VMPensionInfo(@NonNull Application application) {
        super(application);
        poCreditApp = new RCreditApplicant(application);
        poGoCas = new GOCASApplication();
    }

    public void setTransNox(String fsTransNox){
        this.TransNox.setValue(fsTransNox);
    }

    public LiveData<ECreditApplicantInfo> getApplicationInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
    }

    public void setDetailInfo(ECreditApplicantInfo fsDetailInfo){
        try{
            poInfo = fsDetailInfo;
            setMeansInfos(poInfo.getAppMeans());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setMeansInfos(String foJson){
        try{
            poJson.setValue(new JSONObject(foJson));
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public LiveData<ArrayAdapter<String>> getPensionSector(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.PENSION_SECTOR));
        return liveData;
    }
    public void setPensionSector(String lngthStay){
        this.psPensionSector.setValue(lngthStay);
    }

    public int getPreviousPage() throws Exception {
        if(poJson.getValue().getString("employed").equalsIgnoreCase("1")) {
            return 3;
        } else if(poJson.getValue().getString("sEmplyed").equalsIgnoreCase("1")) {
            return 4;
        } else if(poJson.getValue().getString("financer").equalsIgnoreCase("1")) {
            return 6;
        } else {
            return 2;
        }
    }

    public int getNextPage() {
        if(poInfo.getIsSpouse().equalsIgnoreCase("1")){
            return 7;
        } else {
            return 12;
        }
    }

    public LiveData<String> getSPensionSector(){
        return this.psPensionSector;
    }
    public void SavePensionInfo(PensionInfoModel infoModel, ViewModelCallBack callBack){
        try{
            if(infoModel.isPensionInfoValid()) {
                poGoCas.MeansInfo().PensionerInfo().setSource(infoModel.getPensionSector());
                poGoCas.MeansInfo().PensionerInfo().setAmount(infoModel.getPensionIncomeRange());
                poGoCas.MeansInfo().PensionerInfo().setYearRetired(infoModel.getRetirementYear());
                poGoCas.MeansInfo().setOtherIncomeNature(infoModel.getNatureOfIncome());
                poGoCas.MeansInfo().setOtherIncomeAmount(infoModel.getRangeOfIncome());
                Activity_CreditApplication.getInstance().setMeansPensioner(poGoCas.MeansInfo().PensionerInfo());
                Activity_CreditApplication.getInstance().setOtherIncomeNature(poGoCas.MeansInfo().getOtherIncomeNature());
                Activity_CreditApplication.getInstance().setOtherIncomeAmount(poGoCas.MeansInfo().getOtherIncomeAmount());
                poInfo.setPensionx(poGoCas.MeansInfo().toJSONString());
                poCreditApp.updateGOCasData(poInfo);
                callBack.onSaveSuccessResult(String.valueOf(getNextPage()));
            } else {
                callBack.onFailedResult(infoModel.getMessage());
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}