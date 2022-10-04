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

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBarangay;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;
import org.rmj.g3appdriver.GRider.Etc.LocationRetriever;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.AddressUpdate;
import org.rmj.g3appdriver.lib.integsys.Dcp.CustomerNotAround;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.lib.integsys.Dcp.MobileUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.imgcapture.ImageFileCreator;

import java.util.List;

public class VMCustomerNotAround extends AndroidViewModel {
    private static final String TAG = VMCustomerNotAround.class.getSimpleName();
    private static final String ZERO = "0";
    private final Application instance;

    private final LRDcp poSys;
    private final EmployeeMaster poUser;

    private final RTown poTown;
    private final RBarangay poBrgy;

    public VMCustomerNotAround(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poSys = new LRDcp(application);
        this.poUser = new EmployeeMaster(application);
        this.poTown = new RTown(application);
        this.poBrgy = new RBarangay(application);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<EDCPCollectionDetail> GetCollectionDetail(String TransNox, String AccountNo, String EntryNox){
        return poSys.GetAccountDetailForTransaction(TransNox, AccountNo, EntryNox);
    }

    public void SaveNewAddress(AddressUpdate foVal, ViewModelCallback callback){
        new SaveAddressTask(callback).execute(foVal);
    }

    private class SaveAddressTask extends AsyncTask<AddressUpdate, Void, Boolean>{

        private final ViewModelCallback callback;

        private String message;

        public SaveAddressTask(ViewModelCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @Override
        protected Boolean doInBackground(AddressUpdate... address) {
            if(!poSys.SaveAddressUpdate(address[0])){
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

    public void SaveNewMobile(MobileUpdate foVal, ViewModelCallback callback){
        new SaveMobileTask(callback).execute(foVal);
    }

    private class SaveMobileTask extends AsyncTask<MobileUpdate, Void, Boolean>{

        private final ViewModelCallback callback;

        private String message;

        public SaveMobileTask(ViewModelCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @Override
        protected Boolean doInBackground(MobileUpdate... mobile) {
            if(!poSys.SaveMobileUpdate(mobile[0])){
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

    public LiveData<List<DMobileUpdate.MobileUpdateInfo>> GetMobileUpdates(String fsVal){
        return poSys.GetMobileUpdates(fsVal);
    }

    public LiveData<List<DAddressUpdate.AddressUpdateInfo>> GetAddressUpdates(String fsVal){
        return poSys.GetAddressUpdates(fsVal);
    }

    public interface OnRemoveDetailCallback{
        void OnSuccess();
        void OnFailed(String message);
    }

    public void RemoveAddress(String fsVal, OnRemoveDetailCallback callback){
        new RemoveAddressTask(callback).execute(fsVal);
    }

    public void RemoveMobile(String fsVal, OnRemoveDetailCallback callback){
        new RemoveMobileTask(callback).execute(fsVal);
    }

    public class RemoveAddressTask extends AsyncTask<String, Void, Boolean>{

        private final OnRemoveDetailCallback callback;

        private String message;

        public RemoveAddressTask(OnRemoveDetailCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... transNo) {
            if(!poSys.DeleteAddressUpdate(transNo[0])){
                message = poSys.getMessage();
                return false;
            }

            return true;
        }


        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnFailed(message);
            } else {
                callback.OnSuccess();
            }
        }
    }

    public class RemoveMobileTask extends AsyncTask<String, Void, Boolean>{

        private final OnRemoveDetailCallback callback;

        private String message;

        public RemoveMobileTask(OnRemoveDetailCallback callback) {
            this.callback = callback;
        }

        @Override
        protected Boolean doInBackground(String... transNo) {
            if(!poSys.DeleteMobileUpdate(transNo[0])){
                message = poSys.getMessage();
                return false;
            }

            return true;
        }


        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnFailed(message);
            } else {
                callback.OnSuccess();
            }
        }
    }

    public void SaveTransaction(CustomerNotAround foVal, ViewModelCallback callback){
        new SaveCNATask(callback).execute(foVal);
    }

    private class SaveCNATask extends AsyncTask<CustomerNotAround, Void, Boolean>{

        private final ViewModelCallback callback;

        private String message;

        public SaveCNATask(ViewModelCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnStartSaving();
        }

        @Override
        protected Boolean doInBackground(CustomerNotAround... cna) {
            if(!poSys.SaveCustomerNotAround(cna[0])){
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

    public void InitCameraLaunch(Activity activity, String TransNox, OnInitializeCameraCallback callback){
        new InitializeCameraTask(activity, TransNox, instance, callback).execute();
    }

    private static class InitializeCameraTask extends AsyncTask<String, Void, Boolean>{

        private final OnInitializeCameraCallback callback;
        private final ImageFileCreator loImage;
        private final LocationRetriever loLrt;

        private Intent loIntent;
        private String[] args = new String[4];
        private String message;

        public InitializeCameraTask(Activity activity, String TransNox, Application instance, OnInitializeCameraCallback callback){
            this.callback = callback;
            this.loImage = new ImageFileCreator(instance, AppConstants.SUB_FOLDER_SELFIE_LOG, TransNox);
            this.loLrt = new LocationRetriever(instance, activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnInit();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!loImage.IsFileCreated()){
                message = loImage.getMessage();
                return false;
            } else {
                if(loLrt.HasLocation()){
                    args[0] = loImage.getFilePath();
                    args[1] = loImage.getFileName();
                    args[2] = loLrt.getLatitude();
                    args[3] = loLrt.getLongitude();
                    loIntent = loImage.getCameraIntent();
                    return true;
                } else {
                    args[0] = loImage.getFilePath();
                    args[1] = loImage.getFileName();
                    args[2] = loLrt.getLatitude();
                    args[3] = loLrt.getLongitude();
                    loIntent = loImage.getCameraIntent();
                    message = loLrt.getMessage();
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccess(loIntent, args);
            } else {
                callback.OnFailed(message, loIntent, args);
            }
        }
    }

    public LiveData<ArrayAdapter<String>> getRequestCodeOptions() {
        MutableLiveData<ArrayAdapter<String>> liveData = new MutableLiveData<>();
        liveData.setValue(DCP_Constants.getAdapter(getApplication(), DCP_Constants.REQUEST_CODE));
        return liveData;
    }

    public LiveData<List<DTownInfo.TownProvinceInfo>> getTownProvinceInfo(){
        return poTown.getTownProvinceInfo();
    }

    public LiveData<List<EBarangayInfo>> GetBarangayList(String fsVal){
        return poBrgy.getAllBarangayFromTown(fsVal);
    }
}