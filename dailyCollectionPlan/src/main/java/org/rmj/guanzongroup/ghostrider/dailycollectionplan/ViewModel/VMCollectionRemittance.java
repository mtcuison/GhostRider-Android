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

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Entities.ERemittanceAccounts;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDCP_Remittance;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RRemittanceAccount;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;

import java.util.ArrayList;
import java.util.List;

public class VMCollectionRemittance extends AndroidViewModel {

    private final RBranch poBranch;
    private final RRemittanceAccount poRemittance;
    private final RDailyCollectionPlan poDcp;
    private final RDCP_Remittance poRemit;
    private final ConnectionUtil poConn;
    private final HttpHeaders poHeaders;

    private String dTransact;
    private double nTotRCash = 0;
    private double nTotRChck = 0;
    private double nTotCashx = 0;
    private double nTotCheck = 0;

    private final MutableLiveData<String> psCashOHnd = new MutableLiveData<>();
    private final MutableLiveData<String> psCheckOHx = new MutableLiveData<>();

    public interface OnRemitCollectionCallback{
        void OnRemit();
        void OnSuccess();
        void OnFailed(String message);
    }

    public interface OnInitializeBranchAccountCallback{
        void OnDownload();
        void OnSuccessDownload();
        void OnFailedDownload(String message);
    }

    public VMCollectionRemittance(@NonNull Application application) {
        super(application);
        this.poDcp = new RDailyCollectionPlan(application);
        this.poBranch = new RBranch(application);
        this.poRemit = new RDCP_Remittance(application);
        this.poConn = new ConnectionUtil(application);
        this.poHeaders = HttpHeaders.getInstance(application);
        this.poRemittance = new RRemittanceAccount(application);
    }

    public void setTotalRemittedCash(double nTotRCash) {
        this.nTotRCash = nTotRCash;
        calcCashOnHand();
    }

    public void setTotalRemittedCheck(double nTotRChck) {
        this.nTotRChck = nTotRChck;
        calcCheckOnHand();
    }

    public void setTotalCash(double nTotCashx) {
        this.nTotCashx = nTotCashx;
        calcCashOnHand();
    }

    public void setTotalCheck(double nTotCheck) {
        this.nTotCheck = nTotCheck;
        calcCheckOnHand();
    }

    public void setTransact(String dTransact) {
        this.dTransact = dTransact;
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<ERemittanceAccounts> getDefaultRemittanceAccount(){
        return poRemittance.getDefaultRemittanceAccount();
    }

    public LiveData<String[]> getBranchRemittanceList(){
        return poBranch.getAllBranchNames();
    }

    public LiveData<List<ERemittanceAccounts>> getRemittanceBankAccountsList(){
        return poRemittance.getRemittanceBankAccount();
    }

    public LiveData<List<ERemittanceAccounts>> getRemittanceOtherAccountList(){
        return poRemittance.getRemittanceOtherAccount();
    }

    public LiveData<String> getCashOnHand(){
        return psCashOHnd;
    }

    public LiveData<String> getCheckOnHand(){
        return psCheckOHx;
    }

    private void calcCashOnHand(){
        double total = nTotCashx - nTotRCash;
        psCashOHnd.setValue(String.valueOf(total));
    }

    private void calcCheckOnHand(){
        double total = nTotCheck - nTotRChck;
        psCheckOHx.setValue(String.valueOf(total));
    }

    public LiveData<String> getTotalCollectedCash(){
        return poDcp.getCollectedTotalPayment(dTransact);
    }

    public LiveData<String> getTotalCollectedCheck(){
        return poDcp.getCollectedTotalCheckPayment(dTransact);
    }

    public LiveData<String> getTotalRemittedCash(){
        return poRemit.getTotalCashRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalRemittedCheck(){
        return poRemit.getTotalCheckRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalBranchRemittedCollection(){
        return poRemit.getTotalBranchRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalBankRemittedCollection(){
        return poRemit.getTotalBankRemittedCollection(dTransact);
    }

    public LiveData<String> getTotalOtherRemittedCollection(){
        return poRemit.getTotalOtherRemittedCollection(dTransact);
    }

    public void Calculate_COH_Remitted(RDCP_Remittance.OnCalculateCallback callback){
        poRemit.Calculate_COH_Remitted(dTransact, callback);
    }

    public void Calculate_Check_Remitted(RDCP_Remittance.OnCalculateCallback callback){
        poRemit.Calculate_Check_Remitted(dTransact, callback);
    }

    public void InitializeBranchAccountNox(OnInitializeBranchAccountCallback callback){
        new InitializeBranchAccount(callback).execute();
    }

    public void RemitCollection(EDCP_Remittance foRemittance, OnRemitCollectionCallback callback){
        new RemitCollectionTask(callback).execute(foRemittance);
    }

    @SuppressLint("StaticFieldLeak")
    private class RemitCollectionTask extends AsyncTask<EDCP_Remittance, Void, String>{

        OnRemitCollectionCallback callback;

        public RemitCollectionTask(OnRemitCollectionCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnRemit();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(EDCP_Remittance... edcp_remittances) {
            String lsResult = "";
            EDCP_Remittance loRemit = edcp_remittances[0];
            try{

                String lsTransNox = poRemit.getTransnoxMaster(loRemit.getTransact());
                String lsEntryNox = poRemit.getRemittanceEntry(loRemit.getTransact());
                loRemit.setTransNox(lsTransNox);
                loRemit.setEntryNox(lsEntryNox);
                poRemit.insert(loRemit);
                if(!poConn.isDeviceConnected()){
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Cash Remittance has been save.");
                } else {
                    JSONObject param = new JSONObject();
                    param.put("sTransNox", loRemit.getTransNox());
                    param.put("nEntryNox", loRemit.getEntryNox());
                    param.put("dTransact", loRemit.getTransact());
                    param.put("cRemitTyp", loRemit.getRemitTyp());
                    param.put("sCompnyNm", loRemit.getCompnyNm());
                    param.put("sBankAcct", loRemit.getBankAcct());
                    param.put("sReferNox", loRemit.getReferNox());
                    param.put("cPaymType", loRemit.getPaymForm());
                    param.put("nAmountxx", Double.parseDouble(loRemit.getAmountxx()));

                    String lsResponse = WebClient.httpsPostJSon(WebApi.URL_DCP_REMITTANCE, param.toString(), poHeaders.getHeaders());
                    JSONObject loJson = new JSONObject(lsResponse);
                    String result = loJson.getString("result");
                    if(result.equalsIgnoreCase("success")){
                        poRemit.updateSendStat(loRemit.getTransNox(), loRemit.getEntryNox());
                    }
                    lsResult = lsResponse;
                }
            } catch (Exception e){
                e.printStackTrace();
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccess();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailed(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @SuppressLint("StaticFieldLeak")
    private class InitializeBranchAccount extends AsyncTask<Void, Void, String>{
        OnInitializeBranchAccountCallback callback;

        public InitializeBranchAccount(OnInitializeBranchAccountCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload();
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(Void... voids) {
            String lsResult;
            try{
                List<ERemittanceAccounts> loAccounts = poRemittance.getRemittanceAccountIfExist();
                if(loAccounts.size() == 0) {
                    if (poConn.isDeviceConnected()) {
                        JSONObject param = new JSONObject();
                        param.put("bsearch", true);
                        param.put("descript", "all");
                        String lsResponse = WebClient.httpsPostJSon(WebApi.URL_BRANCH_REMITTANCE_ACC, param.toString(), poHeaders.getHeaders());
                        JSONObject loResult = new JSONObject(lsResponse);
                        if(loResult.getString("result").equalsIgnoreCase("success")){
                            JSONArray loDetail = loResult.getJSONArray("detail");
                            loAccounts.clear();
                            loAccounts = new ArrayList<>();
                            for(int x = 0; x < loDetail.length(); x++){
                                JSONObject loJson = loDetail.getJSONObject(x);
                                ERemittanceAccounts loAcc = new ERemittanceAccounts();
                                loAcc.setBranchCd(loJson.getString("sBranchCd"));
                                loAcc.setBranchNm(loJson.getString("sBranchNm"));
                                loAcc.setActNamex(loJson.getString("sActNamex"));
                                loAcc.setActNumbr(loJson.getString("sActNumbr"));
                                loAcc.setBnkActID(loJson.getString("sBnkActID"));
                                loAccounts.add(loAcc);
                            }
                            poRemittance.insertBulkData(loAccounts);
                        }
                        lsResult = lsResponse;
                    } else {
                        lsResult = AppConstants.NO_INTERNET();
                    }
                } else {
                    lsResult = AppConstants.LOCAL_EXCEPTION_ERROR("Bank accounts has been initialized.");
                }
            } catch (Exception e){
                e.printStackTrace();
                lsResult = AppConstants.LOCAL_EXCEPTION_ERROR(e.getMessage());
            }
            return lsResult;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try{
                JSONObject loJson = new JSONObject(s);
                String lsResult = loJson.getString("result");
                if(lsResult.equalsIgnoreCase("success")){
                    callback.OnSuccessDownload();
                } else {
                    JSONObject loError = loJson.getJSONObject("error");
                    String message = loError.getString("message");
                    callback.OnFailedDownload(message);
                }
            } catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
