/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 8/16/21 8:50 AM
 * project file last modified : 8/16/21 8:21 AM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeLeave;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeLeave;

import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class VMLeaveApproval extends AndroidViewModel {

    public static final String TAG = VMLeaveApproval.class.getSimpleName();
    private final Application instance;
    private final RBranch pobranch;
    private final REmployee poUser;
    private final REmployeeLeave poLeave;

    private final MutableLiveData<String> TransNox = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnCredit = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnWithPay = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnWOPay = new MutableLiveData<>();
    private final MutableLiveData<Integer> pnNoDays = new MutableLiveData<>();

    public VMLeaveApproval(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.pobranch = new RBranch(instance);
        this.poUser = new REmployee(instance);
        this.poLeave = new REmployeeLeave(instance);
        this.TransNox.setValue("");
    }

    public interface OnDownloadLeaveAppInfo {
        void OnDownload();
        void OnSuccessDownload(String TransNox);
        void OnFailedDownload(String message);
    }

    public interface OnConfirmLeaveAppCallback {
        void onConfirm();
        void onSuccess(String message);
        void onFailed(String message);
    }

    public void setTransNox(String transNox){
        this.TransNox.setValue(transNox);
    }

    public LiveData<String> getTransNox(){
        return TransNox;
    }

    public LiveData<EEmployeeLeave> getEmployeeLeaveInfo(String TransNox){
        return poLeave.getEmployeeLeaveInfo(TransNox);
    }

    public LiveData<EEmployeeInfo> getUserInfo(){
        return poUser.getEmployeeInfo();
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return pobranch.getUserBranchInfo();
    }

    public void downloadLeaveApplication(String transNox, OnDownloadLeaveAppInfo callBack){
        new DownloadLeaveAppTask(instance, callBack).execute(transNox);
    }

    private static class DownloadLeaveAppTask extends AsyncTask<String, Void, Boolean> {
        private final ConnectionUtil conn;
        private final REmployeeLeave poLeave;
        private final OnDownloadLeaveAppInfo callback;

        private String transno, message;

        public DownloadLeaveAppTask(Application instance, OnDownloadLeaveAppInfo callback) {
            this.conn = new ConnectionUtil(instance);
            this.poLeave = new REmployeeLeave(instance);
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try{
                if(!conn.isDeviceConnected()) {
                    message = conn.getMessage();
                    return false;
                }

                if(!poLeave.DownloadLeaveApplication(strings[0])){
                    message = poLeave.getMessage();
                    return false;
                }

                transno = strings[0];
                return true;
            } catch (Exception e){
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccessDownload(transno);
            } else {
                callback.OnFailedDownload(message);
            }
        }
    }

    public void confirmLeaveApplication(REmployeeLeave.LeaveApprovalInfo infoModel, OnConfirmLeaveAppCallback callBack){
        new ConfirmLeaveTask(infoModel, instance, callBack).execute();
    }

    private static class ConfirmLeaveTask extends AsyncTask<REmployeeLeave.LeaveApprovalInfo, Void, Boolean> {
        private final OnConfirmLeaveAppCallback callback;

        private final ConnectionUtil poConn;
        private final REmployeeLeave poLeave;
        private final AppConfigPreference loConfig;

        private String message;

        public ConfirmLeaveTask(REmployeeLeave.LeaveApprovalInfo infoModel, Application instance, OnConfirmLeaveAppCallback callback) {
            this.callback = callback;
            this.poConn = new ConnectionUtil(instance);
            this.poLeave = new REmployeeLeave(instance);
            this.loConfig = AppConfigPreference.getInstance(instance);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.onConfirm();
        }

        @Override
        protected Boolean doInBackground(REmployeeLeave.LeaveApprovalInfo... infos) {
            try{
                if(!infos[0].isDataValid()){
                    message = infos[0].getMessage();
                    return false;
                }

                String lsTransNox = poLeave.SaveLeaveApproval(infos[0]);
                if(lsTransNox == null){
                    message = poLeave.getMessage();
                    return false;
                }

                if(!poConn.isDeviceConnected()){
                    message = poConn.getMessage() + " Your approval will be automatically send if device is reconnected to internet.";
                    return false;
                }
                if(!poLeave.PostLeaveApproval(lsTransNox)){
                    message = poLeave.getMessage();
                    return false;
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            try {
                if (isSuccess){
                    callback.onSuccess(message);
                } else {
                    callback.onFailed(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                callback.onFailed(e.getMessage());
            }
        }
    }

    public void setCredits(int value){
        this.pnCredit.setValue(value);
    }

    public void calculateWithOPay(int fnWithOPy){
        int lnNoDays = pnNoDays.getValue();
        int lnCredt = pnCredit.getValue();
        if(lnCredt > 0) {
            int lnWOPayx;
            if (fnWithOPy <= lnNoDays) {
                lnWOPayx = Math.abs(fnWithOPy - lnNoDays);
                pnWOPay.setValue(lnWOPayx);
            } else {
                pnWithPay.setValue(lnNoDays);
                calculateWithOPay(lnNoDays);
            }
        } else {
            pnWOPay.setValue(lnNoDays);
            pnWithPay.setValue(0);
        }
    }

    public void calculateWithPay(int fnWithPay){
        int lnNoDays = pnNoDays.getValue();
        int lnCredt = pnCredit.getValue();
        if(lnCredt > 0) {
            if (fnWithPay <= lnNoDays) {
                int lnWithPay;
                lnWithPay = Math.abs(lnNoDays - fnWithPay);
                pnWithPay.setValue(lnWithPay);
            } else {
                pnWOPay.setValue(lnNoDays);
                calculateWithPay(lnNoDays);
            }
        } else {
            pnWOPay.setValue(lnNoDays);
            pnWithPay.setValue(0);
        }
    }

    public LiveData<Integer> getWithPay(){
        return pnWithPay;
    }

    public LiveData<Integer> getWOPay(){
        return pnWOPay;
    }

    public void calculateLeavePay(String fsDateFrm, String fsDateTo) throws ParseException {
        int lnCredt = pnCredit.getValue();
        @SuppressLint("SimpleDateFormat") final SimpleDateFormat loDate = new SimpleDateFormat("yyyy-MM-dd");
        Date dateFrom = loDate.parse(Objects.requireNonNull(fsDateFrm));
        Date dateTo = loDate.parse(Objects.requireNonNull(fsDateTo));
        long diff = Objects.requireNonNull(dateTo).getTime() - Objects.requireNonNull(dateFrom).getTime();
        long noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS) + 1;
        pnNoDays.setValue((int) noOfDays);
        if(noOfDays > lnCredt && lnCredt != 0){
            int lnDiff = (int) (noOfDays - lnCredt);
            pnWOPay.setValue(lnDiff);
            pnWithPay.setValue(lnCredt);
        } else if(lnCredt == 0){
            pnWOPay.setValue((int) noOfDays);
            pnWithPay.setValue(0);
        } else {
            pnWithPay.setValue((int) noOfDays);
            pnWOPay.setValue(0);
        }
    }
}