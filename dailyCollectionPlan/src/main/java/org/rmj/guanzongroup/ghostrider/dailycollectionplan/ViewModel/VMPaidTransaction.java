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
import android.util.Log;
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

    private double pnAmortx = 0, pnAmtDue = 0, pnAmount = 0;
    private String psPrchse = "",
                    pnDueDte = "",
                    MonAmort = "",
                    Balancex = "";

    private final MutableLiveData<Double> pnDsCntx = new MutableLiveData<>();
    private final MutableLiveData<Double> pnOthers = new MutableLiveData<>();
    private final MutableLiveData<Double> pnTotalx = new MutableLiveData<>();
    private final MutableLiveData<Double> pnRebate = new MutableLiveData<>();
    private final MutableLiveData<Double> pnPenlty = new MutableLiveData<>();
    private final MutableLiveData<String> psMssage = new MutableLiveData<>();

    private boolean pbRebate = true;

    public VMPaidTransaction(@NonNull Application application) {
        super(application);
        EmployeeMaster poUser = new EmployeeMaster(application);
        this.poSys = new LRDcp(application);
        this.poBranch = new RBranch(application);
        this.poBank = new RBankInfo(application);
        this.poConfig = AppConfigPreference.getInstance(application);
        this.poConn = new ConnectionUtil(application);
        this.pnDsCntx.setValue((double) 0);
        this.pnOthers.setValue((double) 0);
        this.pnOthers.setValue((double) 0);
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

    public void setAmount(Double fnAmount){
        this.pnAmount = fnAmount;
//        calculatePenalty();
        calculateTotal();
    }

    public void setDiscount(Double fnDiscount){
        try {
            this.pnDsCntx.setValue(Objects.requireNonNull(fnDiscount));
            calculateTotal();
            if (pnRebate.getValue() < Objects.requireNonNull(fnDiscount)) {
                if (pbRebate) {
                    psMssage.setValue("Rebate given is greater than the supposed rebate.");
                } else {
                    psMssage.setValue("Rebate disabled");
                }
            } else {
                psMssage.setValue("");
            }
        } catch(NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void setOthers(Double fnOthers){
        this.pnOthers.setValue(fnOthers);
        calculateTotal();
    }

    public void setMonthlyAmort(Double fnAmortx){
        this.pnAmortx = fnAmortx;
    }

    public void setAmountDue(Double fnAmtDue){
        this.pnAmtDue = fnAmtDue;
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
                double lnOthers = pnOthers.getValue();
                double lnAmount = pnAmount;

                double reb = Double.parseDouble(poConfig.getDCP_CustomerRebate());

                double lnRebate = LRUtil.getRebate(lnAmount, pnAmortx, pnAmtDue, reb);

                lnTotal = lnAmount + lnOthers - lnRebate;
                pnRebate.setValue(lnRebate);
            } else {
                double lnOthers = pnOthers.getValue();
                double lnAmount = pnAmount;
                lnTotal = lnAmount + lnOthers;
                pnRebate.setValue(0.00);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        pnTotalx.setValue(lnTotal);
    }

    private void calculatePenalty(String dPurcahse, String dDueDate, String MonAmort, String Balancex){
        try {
            DateFormat loFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date ldPurchase = loFormat.parse(dPurcahse);
            Date ldDueDatex = loFormat.parse(dDueDate);
            Log.d(TAG, "Date Purchase: "+ ldPurchase);
            Log.d(TAG, "Due Date: "+ ldDueDatex);
            double lnMonAmort = Double.parseDouble(MonAmort);
            double lnABalance = Double.parseDouble(Balancex);
            double lnAmtPaidx = pnAmtDue;
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
                callback.OnSuccessResult(new String[]{"Transaction has been posted successfully."});
            }
        }
    }
}