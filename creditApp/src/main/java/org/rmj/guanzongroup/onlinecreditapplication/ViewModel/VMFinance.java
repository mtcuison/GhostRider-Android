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
import org.rmj.g3appdriver.GRider.Database.Entities.ECountryInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCountry;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Model.FinanceInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;

import java.util.List;
import java.util.Objects;

public class VMFinance extends AndroidViewModel {
    private static final String TAG = VMFinance.class.getSimpleName();
    private final RCreditApplicant poCreditApp;
    private final RCountry poCountry;
    private final GOCASApplication poGoCas;
    private ECreditApplicantInfo poInfo;

    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<String> psFinanceSource = new MutableLiveData<>();
    private JSONObject poJson;

    public VMFinance(@NonNull Application application) {
        super(application);
        this.poCreditApp = new RCreditApplicant(application);
        this.poCountry = new RCountry(application);
        this.poGoCas = new GOCASApplication();
    }

    public void setTransNox(String TransNox){
        this.TransNox.setValue(TransNox);
    }

    public LiveData<ECreditApplicantInfo> getApplicationInfo(){
        return poCreditApp.getCreditApplicantInfoLiveData(TransNox.getValue());
    }

    public void setGOCasApplication(ECreditApplicantInfo sDetailInfo){
        try{
            poInfo = sDetailInfo;
            setMeansInfos(poInfo.getAppMeans());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<String[]> getCountryNameList(){
        return poCountry.getAllCountryNames();
    }

    public LiveData<List<ECountryInfo>> getCountryInfoList(){
        return poCountry.getAllCountryInfo();
    }

    public LiveData<String> getFinanceSourceSpn(){
        return this.psFinanceSource;
    }
    public void setFinanceSourceSpn(String source){
        this.psFinanceSource.setValue(source);
    }

    public LiveData<ArrayAdapter<String>> getFinanceSource(){
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(CreditAppConstants.getAdapter(getApplication(), CreditAppConstants.FINANCE_SOURCE));
        return liveData;
    }

    public void setMeansInfos(String foJson){
        try{
            poJson = new JSONObject(foJson);
        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public int getPreviousPage() throws Exception{
        if(poJson.getString("sEmplyed").equalsIgnoreCase("1")){
            return 4;
        } else if(poJson.getString("employed").equalsIgnoreCase("1")){
            return 3;
        } else {
            return 2;
        }
    }

    public int getNextPage() throws Exception{
        if(poJson.getString("pensionx").equalsIgnoreCase("1")) {
            return 6;
        } else if(poInfo.getIsSpouse().equalsIgnoreCase("1")){
            return 7;
        } else {
            return 12;
        }
    }
    public void SaveFinancierInfo(FinanceInfoModel infoModel, ViewModelCallBack callBack){
        try{
            if(infoModel.isFinancierInfoValid()){
                poGoCas.MeansInfo().FinancerInfo().setSource(infoModel.getFinancierRelation());
                poGoCas.MeansInfo().FinancerInfo().setFinancerName(infoModel.getFinancierName());
                poGoCas.MeansInfo().FinancerInfo().setAmount(infoModel.getRangeOfIncome());
                poGoCas.MeansInfo().FinancerInfo().setCountry(infoModel.getCountryName());
                poGoCas.MeansInfo().FinancerInfo().setMobileNo(infoModel.getMobileNo());
                poGoCas.MeansInfo().FinancerInfo().setFBAccount(infoModel.getFacebook());
                poGoCas.MeansInfo().FinancerInfo().setEmailAddress(infoModel.getEmail());
                poInfo.setFinancex(poGoCas.MeansInfo().FinancerInfo().toJSONString());
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