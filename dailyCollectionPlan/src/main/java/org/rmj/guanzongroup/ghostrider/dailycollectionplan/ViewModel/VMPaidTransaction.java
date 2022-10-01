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
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.apprdiver.util.LRUtil;
import org.rmj.g3appdriver.GRider.Database.Entities.EBankInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBankInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.lib.integsys.Dcp.PaidDCP;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class VMPaidTransaction extends AndroidViewModel {
    private static final String TAG = VMPaidTransaction.class.getSimpleName();

    private final RBranch poBranch;
    private final RBankInfo poBank;
    private final ConnectionUtil poConn;
    private final AppConfigPreference poConfig;

    private final LRDcp poSys;

    private EDCPCollectionDetail loDetail;

    private double pnAmount = 0;

    private final MutableLiveData<Double> pnTotalx = new MutableLiveData<>();
    private final MutableLiveData<Double> pnRebate = new MutableLiveData<>();
    private final MutableLiveData<Double> pnPenlty = new MutableLiveData<>();
    private final MutableLiveData<String> psMssage = new MutableLiveData<>();

    private boolean isDuePass = true;

    public VMPaidTransaction(@NonNull Application application) {
        super(application);
        EmployeeMaster poUser = new EmployeeMaster(application);
        this.poSys = new LRDcp(application);
        this.poBranch = new RBranch(application);
        this.poBank = new RBankInfo(application);
        this.poConfig = AppConfigPreference.getInstance(application);
        this.poConn = new ConnectionUtil(application);
        this.pnRebate.setValue((double) 0);
        this.pnPenlty.setValue((double) 0);
    }

    public LiveData<EDCPCollectionDetail> GetAccountDetail(String TransNo, int EntryNo, String Accountno){
        return poSys.GetAccountDetailForTransaction(TransNo, Accountno, String.valueOf(EntryNo));
    }

    public LiveData<EBranchInfo> GetEmployeeBranch(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<ArrayAdapter<String>> GetPaymentType(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplication(), android.R.layout.simple_spinner_dropdown_item, DCP_Constants.PAYMENT_TYPE);
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(adapter);
        return liveData;
    }

    public LiveData<String> GetPrNumber(){
        MutableLiveData<String> loPrNox = new MutableLiveData<>();
        loPrNox.setValue(poConfig.getDCP_PRNox());
        return loPrNox;
    }

    public void InitPurchaseInfo(EDCPCollectionDetail foVal){
        this.loDetail = foVal;
    }

    public void setAmount(Double fnAmount){
        this.pnAmount = fnAmount;
        if(!isDuePass) {
            calculateRebate();
        } else {
            calculatePenalty();
        }

        double lnRebate = pnRebate.getValue();
        double lnPnalty = pnPenlty.getValue();
        double lnTotal = pnAmount + lnPnalty - lnRebate;
        pnTotalx.setValue(lnTotal);
    }

    public void setRebate(Double fnDiscount){
        try {
            if (!isDuePass) {
                psMssage.setValue("Rebate is no longer available for client.");
            } else {
                this.pnRebate.setValue(Objects.requireNonNull(fnDiscount));
                if (pnRebate.getValue() < Objects.requireNonNull(fnDiscount)) {
                    psMssage.setValue("Rebate given is greater than the supposed rebate.");
                } else {
                    psMssage.setValue("");
                }
            }

            double lnRebate = pnRebate.getValue();
            double lnPnalty = pnPenlty.getValue();
            double lnTotal = pnAmount + lnPnalty - lnRebate;
            pnTotalx.setValue(lnTotal);
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setPenalty(Double fnOthers){
        double lnRebate = pnRebate.getValue();
        double lnTotal = pnAmount + fnOthers - lnRebate;
        pnTotalx.setValue(lnTotal);
    }

    public void setIsDuePass(boolean isDuePass){
        this.isDuePass = isDuePass;
        if(!isDuePass){
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

    private void calculateRebate(){
        try {
            double reb = Double.parseDouble(poConfig.getDCP_CustomerRebate());

            double lnAmortx = Double.parseDouble(loDetail.getMonAmort());
            double lnAmtDue = Double.parseDouble(loDetail.getAmtDuexx());

            double lnRebate = LRUtil.getRebate(pnAmount, lnAmortx, lnAmtDue, reb);
            pnRebate.setValue(lnRebate);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void calculatePenalty(){
        try {
            DateFormat loFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date ldPurchase = loFormat.parse(loDetail.getPurchase());
            Date ldDueDatex = loFormat.parse(loDetail.getDueDatex());
            double lnMonAmort = Double.parseDouble(loDetail.getMonAmort());
            double lnABalance = Double.parseDouble(loDetail.getABalance());
            double lnPenalty = LRUtil.getPenalty(ldPurchase, ldDueDatex, lnMonAmort, lnABalance, pnAmount);
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

    public void SavePaymentInfo(PaidDCP foVal, ViewModelCallback callback){
        new SavePaymentTask(callback).execute(foVal);
    }

    private class SavePaymentTask extends AsyncTask<PaidDCP, Void, Boolean>{
        private final ViewModelCallback callback;

        private String message;

        public SavePaymentTask(ViewModelCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @Override
        protected Boolean doInBackground(PaidDCP... paidDCPS) {
            paidDCPS[0].setPrNoxxx(poConfig.getDCP_PRNox());
            String lsResult = poSys.SavePaidTransaction(paidDCPS[0]);
            if(lsResult == null){
                message = poSys.getMessage();
                return false;
            }

            if(!poConn.isDeviceConnected()){
                message = "Payment info has been save to local device.";
                return true;
            }

            if(!poSys.UploadPaidTransaction(lsResult)){
                message = poSys.getMessage();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnFailedResult(message);
            } else {
                callback.OnSuccessResult();
            }
        }
    }
}