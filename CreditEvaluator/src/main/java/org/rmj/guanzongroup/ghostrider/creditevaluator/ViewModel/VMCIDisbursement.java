/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.SessionManager;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Etc.ViewModelCallBack;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIDisbursementInfoModel;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.CIResidenceInfoModel;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMCIDisbursement extends AndroidViewModel {
    private static final String TAG = VMCIResidenceInfo.class.getSimpleName();
    private final Application instance;
    private final RCIEvaluation poCI;

    private final MutableLiveData<ECIEvaluation> poCIDetail = new MutableLiveData<>();
    private final MutableLiveData<String> sTransNox = new MutableLiveData<>();

    private final MutableLiveData<Double> nWater = new MutableLiveData<>();
    private final MutableLiveData<Double> nElctx = new MutableLiveData<>();
    private final MutableLiveData<Double> nFoodx = new MutableLiveData<>();
    private final MutableLiveData<Double> nLoans = new MutableLiveData<>();
    private final MutableLiveData<Double> nEducation = new MutableLiveData<>();
    private final MutableLiveData<Double> pnOthers = new MutableLiveData<>();
    private final MutableLiveData<Double> pnTotalx = new MutableLiveData<>();
    public VMCIDisbursement(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poCI = new RCIEvaluation(application);
        this.nWater.setValue((double) 0);
        this.nElctx.setValue((double) 0);
        this.nFoodx.setValue((double) 0);
        this.nLoans.setValue((double) 0);
        this.nEducation.setValue((double) 0);
        this.pnOthers.setValue((double) 0);
        this.pnTotalx.setValue((double) 0);
    }


    public void setCurrentCIDetail(ECIEvaluation detail) {
        this.poCIDetail.setValue(detail);
    }

    public void setsTransNox(String transNox) {
        this.sTransNox.setValue(transNox);
    }

    //Added by Jonathan 2021/04/13
    //Computation for all monthly expenses
    private void calculateTotal(){
        double waterBill = nWater.getValue();
        double electBill = nElctx.getValue();
        double foodAllow = nFoodx.getValue();
        double loans = nLoans.getValue();
        double educAllow = nEducation.getValue();
        double otherExp = pnOthers.getValue();
        double lnTotal = waterBill + electBill + foodAllow + loans + educAllow + otherExp;
        pnTotalx.setValue(lnTotal);
    }

    //Added by Jonathan 2021/04/13
    //set  monthly expenses value
    public void setnWater(Double fnAmount){
        this.nWater.setValue(fnAmount);
        calculateTotal();
    }
    public void setnElctx(Double fnAmount){
        this.nElctx.setValue(fnAmount);
        calculateTotal();
    }
    public void setnFoodx(Double fnAmount){
        this.nFoodx.setValue(fnAmount);
        calculateTotal();
    }
    public void setnLoans(Double fnAmount){
        this.nLoans.setValue(fnAmount);
        calculateTotal();
    }
    public void setnEducation(Double fnAmount){
        this.nEducation.setValue(fnAmount);
        calculateTotal();
    }
    public void setOthers(Double fnAmount){
        this.pnOthers.setValue(fnAmount);
        calculateTotal();
    }
    public LiveData<Double> getTotalAmount(){
        return pnTotalx;
    }
    public LiveData<ECIEvaluation> getCIByTransNox() {
        return poCI.getAllCIApplication(sTransNox.getValue());
    }

    public boolean saveCIDisbursement(CIDisbursementInfoModel infoModel, ViewModelCallBack callback) {
        try {

            new UpdateTask(poCI, infoModel, callback).execute(poCIDetail.getValue());
            return true;
        } catch (NullPointerException e) {
            e.printStackTrace();
            callback.onFailedResult("NullPointerException error");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            callback.onFailedResult("Exception error");
            return false;
        }
    }

    //Added by Jonathan 2021/04/13
    //Need AsyncTask for background threading..
    //RoomDatabase requires background task in order to manipulate Tables...
    private class UpdateTask extends AsyncTask<ECIEvaluation, Void, String> {
        private final RCIEvaluation poCIEvaluation;
        private final CIDisbursementInfoModel infoModel;
        private final ViewModelCallBack callback;

        public UpdateTask(RCIEvaluation poCIEvaluation, CIDisbursementInfoModel infoModel, ViewModelCallBack callback) {
            this.poCIEvaluation = poCIEvaluation;
            this.infoModel = infoModel;
            this.callback = callback;
        }

        @Override
        protected String doInBackground(ECIEvaluation... detail) {
            try {
                ECIEvaluation loDetail = detail[0];
                if(infoModel.getCiDbmWater() == null || infoModel.getCiDbmWater().isEmpty()){
                    loDetail.setWaterBil("0.00");
                }else{
                    loDetail.setWaterBil(infoModel.getCiDbmWater());
                }

                if(infoModel.getCiDbmElectricity() == null || infoModel.getCiDbmElectricity().isEmpty()){
                    loDetail.setElctrcBl("0.00");
                }else{
                    loDetail.setElctrcBl(infoModel.getCiDbmElectricity());
                }

                if(infoModel.getCiDbmFood() == null || infoModel.getCiDbmFood().isEmpty()){
                    loDetail.setFoodAllw("0.00");
                }else{
                    loDetail.setFoodAllw(infoModel.getCiDbmFood());
                }


                if(infoModel.getCiDbmLoans() == null || infoModel.getCiDbmLoans().isEmpty()){
                    loDetail.setLoanAmtx("0.00");
                }else{
                    loDetail.setLoanAmtx(infoModel.getCiDbmLoans());
                }

                if(infoModel.getCiDbmEducation() == null || infoModel.getCiDbmEducation().isEmpty()){
                    loDetail.setEducExpn("0.00");
                }else{
                    loDetail.setEducExpn(infoModel.getCiDbmEducation());
                }
                if(infoModel.getCiDbmOthers() == null || infoModel.getCiDbmOthers().isEmpty()){
                    loDetail.setOthrExpn("0.00");
                }else{
                    loDetail.setOthrExpn(infoModel.getCiDbmOthers());
                }

                poCIEvaluation.updateCiDisbursement(loDetail);
                Log.e(TAG, "Disbursement info has been updated!");
                return "success";

            } catch (Exception e) {
                e.printStackTrace();
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equalsIgnoreCase("success")) {
                callback.onSaveSuccessResult("Disbursement info has been save.");
            } else {
                callback.onFailedResult(s);
            }
        }
    }


}