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
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EInventoryDetail;
import org.rmj.g3appdriver.dev.Database.GCircle.Entities.EInventoryMaster;
import org.rmj.g3appdriver.lib.integsys.Inventory.RandomStockInventory;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMInventory extends AndroidViewModel {
    private static final String TAG = VMInventory.class.getSimpleName();

    private final RandomStockInventory poSys;
    private final ConnectionUtil poConn;

    private final MutableLiveData<String> psBranch = new MutableLiveData<>();

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

    public interface OnGetBranchListListener{
        void OnLoad();
        void OnRetrieve(List<EBranchInfo> list);
        void OnFailed(String message);
    }

    public VMInventory(@NonNull Application application) {
        super(application);
        this.poSys = new RandomStockInventory(application);
        this.poConn = new ConnectionUtil(application);
        this.psBranch.setValue("");
    }

    public void setBranchCd(String val){
        this.psBranch.setValue(val);
    }

    public LiveData<String> GetBranchCd(){
        return psBranch;
    }

    public LiveData<EBranchInfo> GetBranchInfo(String args){
        return poSys.GetBranchInfo(args);
    }

    public LiveData<EInventoryMaster> GetInventoryMaster(String fsVal){
        return poSys.GetInventoryMaster(fsVal);
    }

    public LiveData<List<EInventoryDetail>> GetInventoryItems(String fsVal){
        return poSys.GetInventoryItems(fsVal);
    }

    public void GetBranchList(OnGetBranchListListener listener){
        new GetBranchTask(listener).execute();
    }

    private class GetBranchTask extends AsyncTask<Void, Void, List<EBranchInfo>>{

        private final OnGetBranchListListener listener;

        private String message;

        public GetBranchTask(OnGetBranchListListener listener) {
            this.listener = listener;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            listener.OnLoad();
        }

        @Override
        protected List<EBranchInfo> doInBackground(Void... voids) {
            List<EBranchInfo> loList = poSys.GetBranchesForInventory();
            if(loList == null){
                message = poSys.getMessage();
                return null;
            }
            return loList;
        }

        @Override
        protected void onPostExecute(List<EBranchInfo> eBranchInfos) {
            super.onPostExecute(eBranchInfos);
            if(eBranchInfos == null){
                listener.OnFailed(message);
            } else {
                listener.OnRetrieve(eBranchInfos);
            }
        }
    }

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

            if(!poSys.ImportInventory(strings[0])){
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