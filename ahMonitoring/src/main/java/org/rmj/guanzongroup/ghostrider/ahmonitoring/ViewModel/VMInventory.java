/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EInventoryMaster;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.RInventoryMaster;
import org.rmj.g3appdriver.lib.integsys.Inventory.RandomStockInventory;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMInventory extends AndroidViewModel {
    private static final String TAG = VMInventory.class.getSimpleName();

    private final RandomStockInventory poSys;

    private final RBranch poBranch;
    private final ConnectionUtil poConn;

    public interface OnCheckLocalRecords{
        void OnCheck();
        void OnProceed();
        void OnInventoryFinished(String message);
    }

    public interface OnDownloadInventory {
        void OnRequest(String title, String message);
        void OnSuccessResult();
        void OnFailed(String message);
    }

    public interface OnSaveInventoryMaster{
        void OnSave();
        void OnSuccess();
        void OnFailed(String message);
    }

    public VMInventory(@NonNull Application application) {
        super(application);
        this.poSys = new RandomStockInventory(application);
        this.poBranch = new RBranch(application);
        this.poConn = new ConnectionUtil(application);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranch.getUserBranchInfo();
    }

    public LiveData<String> getBranchName(String BranchCd){
        return poBranch.getBranchName(BranchCd);
    }

//    public LiveData<List<EInventoryDetail>> GetInventoryDetails(){
//        return poSys.
//    }

    public void CheckBranchInventory(String fsVal, OnCheckLocalRecords callback){
        new CheckLocalRecordsTask(callback).execute(fsVal);
    }

    private class CheckLocalRecordsTask extends AsyncTask<String, Void, Boolean>{

        private final OnCheckLocalRecords callback;

        private String message;

        public CheckLocalRecordsTask(OnCheckLocalRecords callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnCheck();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!poSys.CheckInventoryRecord(strings[0])){
                message = poSys.getMessage();
                return false;
            }

            if(!poConn.isDeviceConnected()){
                message = "Unable to proceed to random stock inventory without connectivity";
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                callback.OnInventoryFinished(message);
            } else {
                callback.OnProceed();
            }
        }
    }

    public void DownloadInventory(String BranchCd, OnDownloadInventory callback){
        new DownloadInventoryTask(callback).execute(BranchCd);
    }

    private class DownloadInventoryTask extends AsyncTask<String, Void, Boolean>{

        private final OnDownloadInventory poCallback;

        private String message;

        public DownloadInventoryTask(OnDownloadInventory poCallback) {
            this.poCallback = poCallback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallback.OnRequest("Random Stock Inventory", "Downloading inventory details. Please wait...");
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!poConn.isDeviceConnected()){
                message = poConn.getMessage();
                return false;
            }

            if(poSys.ImportInventory(strings[0])){
                message = poSys.getMessage();
                return false;
            }

            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(!isSuccess){
                poCallback.OnFailed(message);
            } else {
                poCallback.OnSuccessResult();
            }
        }
    }

    public void PostInventory(String BranchCD, String Remarks, OnSaveInventoryMaster callback){
        new SaveInventoryTask(BranchCD, callback).execute(Remarks);
    }

    private class SaveInventoryTask extends AsyncTask<String, Void, Boolean>{

        private final String BranchCd;
        private final OnSaveInventoryMaster callback;

        private String message;

        public SaveInventoryTask(String BranchCd, OnSaveInventoryMaster callback){
            this.BranchCd = BranchCd;
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnSave();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            String lsResult = poSys.SaveMasterForPosting(BranchCd, strings[0]);
            if(lsResult == null){
                message = poSys.getMessage();
                return false;
            }

            if(!poConn.isDeviceConnected()){
                message = "Your inventory has been save to local device.";
                return false;
            }

            if(!poSys.UploadInventory(BranchCd)){
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
}