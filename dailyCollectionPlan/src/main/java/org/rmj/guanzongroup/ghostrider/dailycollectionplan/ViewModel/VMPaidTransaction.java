/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.apprdiver.util.LRUtil;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBankInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PaidTransactionModel;

import java.util.Date;
import java.util.List;

public class VMPaidTransaction extends AndroidViewModel {
    private static final String TAG = VMPaidTransaction.class.getSimpleName();
    private final Application instance;
    private final RBranch poBranch;
    private final RDailyCollectionPlan poDcp;
    private final RBankInfo poBank;
    private final AppConfigPreference poConfig;

    private final MutableLiveData<EDCPCollectionDetail> poDcpDetail = new MutableLiveData<>();

    private final MutableLiveData<String> psTransNox = new MutableLiveData<>();
    private final MutableLiveData<Integer> psEntryNox = new MutableLiveData<>();
    private final MutableLiveData<Double> pnAmount = new MutableLiveData<>();
    private final MutableLiveData<Double> pnDsCntx = new MutableLiveData<>();
    private final MutableLiveData<Double> pnOthers = new MutableLiveData<>();
    private final MutableLiveData<Double> pnAmortx = new MutableLiveData<>();
    private final MutableLiveData<Double> pnAmtDue = new MutableLiveData<>();
    private final MutableLiveData<Double> pnTotalx = new MutableLiveData<>();
    private final MutableLiveData<Double> pnRebate = new MutableLiveData<>();
    private final MutableLiveData<Double> pnPenlty = new MutableLiveData<>();
    private final MutableLiveData<String> psMssage = new MutableLiveData<>();

    private boolean pbRebate = true;

    public VMPaidTransaction(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poBranch = new RBranch(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.pnDsCntx.setValue((double) 0);
        this.pnOthers.setValue((double) 0);
        this.poBank = new RBankInfo(application);
        this.poConfig = AppConfigPreference.getInstance(application);
    }

    public void setParameter(String TransNox, int EntryNox){
        this.psTransNox.setValue(TransNox);
        this.psEntryNox.setValue(EntryNox);
    }

    public LiveData<EDCPCollectionDetail> getCollectionDetail(){
        return poDcp.getCollectionDetail(psTransNox.getValue(), psEntryNox.getValue());
    }

    public void setCurrentCollectionDetail(EDCPCollectionDetail detail){
        this.poDcpDetail.setValue(detail);
    }

    public LiveData<EBranchInfo> getUserBranchEmployee(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<ArrayAdapter<String>> getPaymentType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, DCP_Constants.PAYMENT_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<String> getPrNox(){
        MutableLiveData<String> loPrNox = new MutableLiveData<>();
        loPrNox.setValue(poConfig.getDCP_PRNox());
        return loPrNox;
    }

    public void setAmount(Double fnAmount){
        this.pnAmount.setValue(fnAmount);
        calculatePenalty();
        calculateTotal();
    }

    public void setDiscount(Double fnDiscount){
        this.pnDsCntx.setValue(fnDiscount);
        calculateTotal();
        if(pnRebate.getValue() < fnDiscount){
            if(pbRebate) {
                psMssage.setValue("Rebate given is greater than the supposed rebate.");
            } else {
                psMssage.setValue("Rebate disabled");
            }
        } else {
            psMssage.setValue("");
        }
    }

    public void setOthers(Double fnOthers){
        this.pnOthers.setValue(fnOthers);
        calculateTotal();
    }

    public void setMonthlyAmort(Double fnAmortx){
        this.pnAmortx.setValue(fnAmortx);
    }

    public void setAmountDue(Double fnAmtDue){
        this.pnAmtDue.setValue(fnAmtDue);
    }

    public void setIsRebated(boolean isRebated){
        this.pbRebate = isRebated;
        calculateTotal();
        if(!isRebated){
            psMssage.setValue("");
        }
    }

    public LiveData<Double> getTotalAmount(){
        return pnTotalx;
    }

    public LiveData<String[]> getBankNameList(){
        return poBank.getBankNameList();
    }

    public LiveData<List<EBankInfo>> getBankInfoList(){
        return poBank.getBankInfoList();
    }

    public LiveData<String> getRebateNotice(){
        return psMssage;
    }

    private void calculateTotal(){
        double lnTotal = 0.00;
        try {
            if(pbRebate) {
                double lnAmount = pnAmount.getValue();
                double lnOthers = pnOthers.getValue();
                double lnAmortx = pnAmortx.getValue();
                double lnAmtDue = pnAmtDue.getValue();

                double reb = Double.parseDouble(poConfig.getDCP_CustomerRebate());

                double lnRebate = LRUtil.getRebate(lnAmount, lnAmortx, lnAmtDue, reb);

                lnTotal = lnAmount + lnOthers - lnRebate;
                pnRebate.setValue(lnRebate);
            } else {
                double lnAmount = pnAmount.getValue();
                double lnOthers = pnOthers.getValue();
                lnTotal = lnAmount + lnOthers;
                pnRebate.setValue(0.00);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        pnTotalx.setValue(lnTotal);
    }

    private void calculatePenalty(){
        try {
            EDCPCollectionDetail loDetail = poDcpDetail.getValue();
            Date ldPurchase = new Date(loDetail.getPurchase());
            Date ldDueDatex = new Date(loDetail.getDueDatex());
            double lnMonAmort = Double.parseDouble(loDetail.getMonAmort());
            double lnABalance = Double.parseDouble(loDetail.getABalance());
            double lnAmtPaidx = pnAmtDue.getValue();
            double lnPenalty = LRUtil.getPenalty(ldPurchase, ldDueDatex, lnMonAmort, lnABalance, lnAmtPaidx);
            pnPenlty.setValue(lnPenalty);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public LiveData<Double> getRebate(){
        return pnRebate;
    }

    public LiveData<Double> getPenalty(){
        return pnPenlty;
    }

    public void savePaidInfo(PaidTransactionModel infoModel, ViewModelCallback callback){
        new PostPaidTransactionTask(poDcpDetail.getValue(), infoModel, instance, callback).execute();
    }

    private static class PostPaidTransactionTask extends AsyncTask<String, Void, String>{
        private final EDCPCollectionDetail poDcpDetail;
        private final PaidTransactionModel infoModel;
        private final ViewModelCallback callback;
        private final RDailyCollectionPlan poDcp;

        private final HttpHeaders poHeaders;
        private final ConnectionUtil poConn;
        private final Telephony poDevID;
        private final SessionManager poUser;
        private final AppConfigPreference poConfig;

        public PostPaidTransactionTask(EDCPCollectionDetail dcpDetail, PaidTransactionModel infoModel, Application instance, ViewModelCallback callback) {
            this.poDcpDetail = dcpDetail;
            this.infoModel = infoModel;
            this.callback = callback;
            this.poDcp = new RDailyCollectionPlan(instance);
            this.poHeaders = HttpHeaders.getInstance(instance);
            this.poConn = new ConnectionUtil(instance);
            this.poDevID = new Telephony(instance);
            this.poUser = new SessionManager(instance);
            this.poConfig = AppConfigPreference.getInstance(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            String lsResponse;
            try{
                EDCPCollectionDetail detail = poDcpDetail;
                if(!infoModel.isDataValid()){
                    lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(infoModel.getMessage());
                } else {
                    if(infoModel.getBankNme() != null){
                        detail.setBankIDxx(infoModel.getBankNme());
                        detail.setCheckDte(infoModel.getCheckDt());
                        detail.setCheckNox(infoModel.getCheckNo());
                        detail.setCheckAct(infoModel.getAccntNo());
                    }
                    detail.setRemCodex(infoModel.getRemarksCode());
                    detail.setTranType(infoModel.getPayment());
                    detail.setPRNoxxxx(infoModel.getPrNoxxx());
                    detail.setTranAmtx(infoModel.getAmountx().replace(",", ""));
                    detail.setDiscount(infoModel.getDscount().replace(",", ""));
                    detail.setOthersxx(infoModel.getOthersx().replace(",", ""));
                    detail.setTranTotl(infoModel.getTotAmnt().replace(",", ""));
                    detail.setRemarksx(infoModel.getRemarks());
                    detail.setTranStat("2");
                    detail.setModified(new AppConstants().DATE_MODIFIED);
                    poDcp.updateCollectionDetailInfo(detail);
                    poConfig.setDCP_PRNox(detail.getPRNoxxxx());

                    //StartSending to Server
                    if(!poConn.isDeviceConnected()) {
                        lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR("Collection info has been save.");
                    } else {
                        JSONObject loData = new JSONObject();
                        loData.put("sPRNoxxxx", detail.getPRNoxxxx());
                        loData.put("nTranAmtx", detail.getTranAmtx());
                        loData.put("nDiscount", detail.getDiscount());
                        loData.put("nOthersxx", detail.getOthersxx());
                        loData.put("cTranType", detail.getTranType());
                        loData.put("nTranTotl", detail.getTranTotl());
                        if(detail.getBankIDxx() == null) {
                            loData.put("sBankIDxx", "");
                            loData.put("sCheckDte", "");
                            loData.put("sCheckNox", "");
                            loData.put("sCheckAct", "");
                        } else {
                            loData.put("sBankIDxx", detail.getBankIDxx());
                            loData.put("sCheckDte", detail.getCheckDte());
                            loData.put("sCheckNox", detail.getCheckNox());
                            loData.put("sCheckAct", detail.getCheckAct());
                        }
                        JSONObject loJson = new JSONObject();
                        loJson.put("sTransNox", detail.getTransNox());
                        loJson.put("nEntryNox", detail.getEntryNox());
                        loJson.put("sAcctNmbr", detail.getAcctNmbr());
                        loJson.put("sRemCodex", detail.getRemCodex());
                        loJson.put("sJsonData", loData);
                        loJson.put("dReceived", "");
                        loJson.put("sUserIDxx", poUser.getUserID());
                        loJson.put("sDeviceID", poDevID.getDeviceID());
                        Log.e(TAG, loJson.toString());
                        lsResponse = WebClient.httpsPostJSon(WebApi.URL_DCP_SUBMIT, loJson.toString(), poHeaders.getHeaders());

                        if(lsResponse == null){
                            lsResponse = AppConstants.SERVER_NO_RESPONSE();
                        } else {
                            JSONObject loResponse = new JSONObject(lsResponse);
                            if(loResponse.getString("result").equalsIgnoreCase("success")){
                                detail.setSendStat("1");
                                detail.setSendDate(new AppConstants().DATE_MODIFIED);
                                detail.setModified(new AppConstants().DATE_MODIFIED);
                                poDcp.updateCollectionDetailInfo(detail);
                            }
                        }
                    }
                }

            } catch (Exception e){
                e.printStackTrace();
                lsResponse = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject loJson = new JSONObject(s);
                Log.e(TAG, loJson.getString("result"));
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccessResult(new String[]{"Transaction has been posted successfully."});
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailedResult(message);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                callback.OnFailedResult(e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                callback.OnFailedResult(e.getMessage());
            }
        }
    }
}