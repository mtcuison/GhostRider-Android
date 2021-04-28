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

import org.json.simple.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicantInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcBrand;
import org.rmj.g3appdriver.GRider.Database.Entities.EMcModel;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCreditApplicant;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcBrand;
import org.rmj.g3appdriver.GRider.Database.Repositories.RMcModel;
import org.rmj.g3appdriver.GRider.Database.Repositories.RRawData;
import org.rmj.gocas.base.GOCASApplication;
import org.rmj.gocas.pricelist.PriceFactory;
import org.rmj.gocas.pricelist.Pricelist;
import org.rmj.guanzongroup.onlinecreditapplication.Etc.CreditAppConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Data.GoCasBuilder;
import org.rmj.guanzongroup.onlinecreditapplication.Model.PurchaseInfoModel;
import org.rmj.guanzongroup.onlinecreditapplication.Model.ViewModelCallBack;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class VMIntroductoryQuestion extends AndroidViewModel {
    public static final String TAG = VMIntroductoryQuestion.class.getSimpleName();
    private final DecimalFormat currency_total = new DecimalFormat("###,###,###.###");
    private final RBranch RBranch;
    private final RMcBrand oBrandRepo;
    private final RMcModel oModelRepo;
    private final RCreditApplicant oCredtRepo;
    private final RRawData RRawData;
    private final Pricelist poPrice;

    private final MutableLiveData<String> psAppType = new MutableLiveData<>();
    private final MutableLiveData<String> psCusType = new MutableLiveData<>();
    private final MutableLiveData<String> psBrnchCd = new MutableLiveData<>();
    private final MutableLiveData<String> psBrandID = new MutableLiveData<>();
    private final MutableLiveData<String> psModelCd = new MutableLiveData<>();
    private final MutableLiveData<JSONObject> poDpInfo = new MutableLiveData<>();
    private final MutableLiveData<JSONObject> poMAInfo = new MutableLiveData<>();
    private final MutableLiveData<Integer> psIntTerm = new MutableLiveData<>();
    private final MutableLiveData<String> psDwnPymt = new MutableLiveData<>();
    private final MutableLiveData<String> psMonthly = new MutableLiveData<>();

    private final LiveData<String[]> paBranchNm;
    private final LiveData<String[]> paBrandNme;
    public VMIntroductoryQuestion(@NonNull Application application) {
        super(application);
        RBranch = new RBranch(application);
        oBrandRepo = new RMcBrand(application);
        oModelRepo = new RMcModel(application);
        oCredtRepo = new RCreditApplicant(application);
        RRawData = new RRawData(application);
        paBranchNm = RBranch.getAllMcBranchNames();
        paBrandNme = oBrandRepo.getAllBrandNames();
       // oCredtRepo.deleteAllCredit();
        poPrice = PriceFactory.make(PriceFactory.ProductType.MOTORCYCLE);
        psIntTerm.setValue(36);
    }

    public void setCustomerType(String type){
        this.psCusType.setValue(type);
    }

    public void setApplicationType(String type){
        this.psAppType.setValue(type);
    }

    public void setBanchCde(String psBrnchCd) {
        this.psBrnchCd.setValue(psBrnchCd);
    }

    public LiveData<String> getBrandID(){
        return psBrandID;
    }

    public void setLsBrandID(String lsBrandID) {
        this.psBrandID.setValue(lsBrandID);
    }

    public LiveData<String> getModelID(){
        return psModelCd;
    }

    public void setLsModelCd(String lsModelCd) {
        this.psModelCd.setValue(lsModelCd);
    }

    public void setLsIntTerm(int lsIntTerm) {
        this.psIntTerm.setValue(lsIntTerm);
    }

    public LiveData<Integer> getSelectedInstallmentTerm(){
        return psIntTerm;
    }

    public void setLoDpInfo(JSONObject loJson){
        this.poDpInfo.setValue(loJson);
        try{
            if(poPrice.setModelInfo(poDpInfo.getValue())){
                psDwnPymt.setValue(String.valueOf(poPrice.getDownPayment()));
                poPrice.setPaymentTerm(36);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setLoMonthlyInfo(JSONObject loJson){
        this.poMAInfo.setValue(loJson);
        try{
            poPrice.setPaymentTerm(36);
            poPrice.setDownPayment(poPrice.getDownPayment());
            BigDecimal price = new BigDecimal(String.valueOf(poPrice.getMonthlyAmort(poMAInfo.getValue())));
            psMonthly.setValue(String.valueOf(currency_total.format(price)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<List<EBranchInfo>> getAllBranchInfo(){
        return RBranch.getAllMcBranchInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return RBranch.getUserBranchInfo();
    }

    public LiveData<String[]> getAllBranchNames(){
        return paBranchNm;
    }

    public LiveData<List<EMcBrand>> getAllMcBrand(){
        return oBrandRepo.getAllBrandInfo();
    }

    public LiveData<String[]> getAllBrandNames(){
        return paBrandNme;
    }


    public LiveData<List<EMcModel>> getAllBrandModelInfo(){
        return oModelRepo.getMcModelFromBrand(psBrandID.getValue());
    }

    public LiveData<String[]> getAllBrandModelName(String lsBrandID){
        return oModelRepo.getAllMcModelName(lsBrandID);
    }

    public LiveData<ArrayAdapter<String>> getApplicationType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.APPLICATION_TYPE){
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

        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<ArrayAdapter<String>> getCustomerType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.CUSTOMER_TYPE){
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

    public LiveData<ArrayAdapter<String>> getInstallmentTerm(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplication(), android.R.layout.simple_spinner_dropdown_item, CreditAppConstants.INSTALLMENT_TERM){
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

    public LiveData<String> getDownpayment(){
        return psDwnPymt;
    }

    public LiveData<DRawDao.McDPInfo> getDpInfo(String ModelCd){
        return RRawData.getDownpayment(ModelCd);
    }

    public LiveData<DRawDao.McAmortInfo> getMonthlyAmortInfo(String ModelCd){
        return RRawData.getMonthlyAmortInfo(ModelCd, 36);
    }

    public LiveData<String> getMonthlyAmort(){
        return psMonthly;
    }

    public void calculateMonthlyPayment(){
        try {
            if(poMAInfo.getValue() != null) {
            poPrice.setPaymentTerm(psIntTerm.getValue());
            poPrice.setDownPayment(poPrice.getDownPayment());
                BigDecimal price = new BigDecimal(String.valueOf(poPrice.getMonthlyAmort(poMAInfo.getValue())));
                psMonthly.setValue(String.valueOf(currency_total.format(price)));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void calculateMonthlyPayment(String Downpayment){
        try {
            poPrice.setPaymentTerm(psIntTerm.getValue());
            poPrice.setDownPayment(Double.parseDouble(Downpayment));
            BigDecimal price = new BigDecimal(String.valueOf(poPrice.getMonthlyAmort(poMAInfo.getValue())));
            psMonthly.setValue(String.valueOf(currency_total.format(price)));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void CreateNewApplication(PurchaseInfoModel model,ViewModelCallBack callBack){
        try {
            String transnox = oCredtRepo.getGOCasNextCode();

            Log.e("TransNox", transnox);
            model.setsBranchCde(psBrnchCd.getValue());
            if(model.isPurchaseInfoValid()) {
                GOCASApplication loGoCas = new GOCASApplication();
                loGoCas.PurchaseInfo().setAppliedFor("0");
                loGoCas.PurchaseInfo().setTargetPurchase(model.getdTargetDte());
                loGoCas.PurchaseInfo().setCustomerType(model.getsCustTypex());
                loGoCas.PurchaseInfo().setPreferedBranch(model.getsBranchCde());
                loGoCas.PurchaseInfo().setBrandName(model.getsBrandIDxx());
                loGoCas.PurchaseInfo().setModelID(model.getsModelIDxx());
                loGoCas.PurchaseInfo().setDownPayment(model.getsDownPaymt());
                loGoCas.PurchaseInfo().setAccountTerm(model.getsAccTermxx());
                loGoCas.PurchaseInfo().setDateApplied(model.getDateApplied());
                loGoCas.PurchaseInfo().setMonthlyAmortization(model.getsMonthlyAm());

                ECreditApplicantInfo creditApp = new ECreditApplicantInfo();
                creditApp.setPurchase(loGoCas.PurchaseInfo().toJSONString());
                creditApp.setDownPaym(loGoCas.PurchaseInfo().getDownPayment());
                creditApp.setAppliedx(loGoCas.PurchaseInfo().getDateApplied());
                creditApp.setCreatedx(AppConstants.DATE_MODIFIED);
                creditApp.setTransact(AppConstants.CURRENT_DATE);
                creditApp.setBranchCd(loGoCas.PurchaseInfo().getPreferedBranch());
                creditApp.setTranStat("0");
                creditApp.setTransNox(transnox);
                oCredtRepo.insertGOCasData(creditApp);
                callBack.onSaveSuccessResult(transnox);
            } else {
                callBack.onFailedResult(model.getMessage());
            }
        } catch (NullPointerException e){
            e.printStackTrace();
            callBack.onFailedResult("Something went wrong. Required information might not provided by user.");
        } catch (Exception e){
            e.printStackTrace();
            callBack.onFailedResult("Something went wrong. Required information might not provided by user.");
        }
    }
}