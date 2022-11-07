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

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DEmployeeInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.model.ImportParams;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMCollectionList extends AndroidViewModel {
    private static final String TAG = VMCollectionList.class.getSimpleName();

    private final EmployeeMaster poUser;
    private final LRDcp poSys;
    private final ConnectionUtil poConn;
    private final AppConfigPreference poConfig;

    public interface OnActionCallback {
        void OnLoad();
        void OnSuccess();
        void OnFailed(String message);
    }

    public interface OnCheckDcpForPosting {
        void OnLoad();
        void OnSuccess();
        void OnIncompleteDcp();
        void OnFailed(String message);
    }

    public interface OnDownloadClientList{
        void OnDownload();
        void OnSuccessDownload(List<EDCPCollectionDetail> collectionDetails);
        void OnFailedDownload(String message);
    }

    public VMCollectionList(@NonNull Application application) {
        super(application);
        this.poUser = new EmployeeMaster(application);
        this.poSys = new LRDcp(application);
        this.poConn = new ConnectionUtil(application);
        this.poConfig = AppConfigPreference.getInstance(application);
    }

    public LiveData<DEmployeeInfo.EmployeeBranch> GetUserInfo(){
        return poUser.GetEmployeeBranch();
    }

    public LiveData<List<EDCPCollectionDetail>> GetColletionList(){
        return poSys.GetCollectionList();
    }

    public boolean IsTesting(){
        return poConfig.getTestStatus();
    }

    public void DownloadDCP(ImportParams foVal, OnActionCallback callback){
        new DownloadDcpTask(callback).execute(foVal);
    }

    private class DownloadDcpTask extends AsyncTask<ImportParams, Void, Boolean>{

        private String message;

        private final OnActionCallback callback;

        public DownloadDcpTask(OnActionCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad();
        }

        @Override
        protected Boolean doInBackground(ImportParams... importParams) {
            if(!poConn.isDeviceConnected()){
                message = poSys.getMessage();
                return false;
            }

            if(!poSys.DownloadCollection(importParams[0])){
                message = poSys.getMessage();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean isSuccess) {
            super.onPostExecute(isSuccess);
            if(isSuccess){
                callback.OnSuccess();
            } else {
                callback.OnFailed(message);
            }
        }
    }

    public void SearchClient(String fsVal, OnDownloadClientList callback){
        new SearchClientTask(callback).execute(fsVal);
    }

    private class SearchClientTask extends AsyncTask<String, Void, List<EDCPCollectionDetail>>{

        private final OnDownloadClientList callback;

        private String message;

        public SearchClientTask(OnDownloadClientList callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload();
        }

        @Override
        protected List<EDCPCollectionDetail> doInBackground(String... strings) {
            if(!poConn.isDeviceConnected()){
                message = poConn.getMessage();
                return null;
            }

            List<EDCPCollectionDetail> loList = poSys.GetSearchList(strings[0]);
            if(loList == null){
                message = poSys.getMessage();
                return null;
            }

            return loList;
        }

        @Override
        protected void onPostExecute(List<EDCPCollectionDetail> searchlist) {
            super.onPostExecute(searchlist);
            if(searchlist == null){
                callback.OnFailedDownload(message);
            } else {
                callback.OnSuccessDownload(searchlist);
            }
        }
    }

    public void AddCollection(EDCPCollectionDetail foVal, OnActionCallback callback){
        new AddCollectionTask(callback).execute(foVal);
    }

    private class AddCollectionTask extends AsyncTask<EDCPCollectionDetail, Void, Boolean>{

        private final OnActionCallback callback;

        private String message;

        public AddCollectionTask(OnActionCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad();
        }

        @Override
        protected Boolean doInBackground(EDCPCollectionDetail... dcpDetail) {
            if(!poSys.AddCollection(dcpDetail[0])){
                message = poSys.getMessage();
                return false;
            }
            return null;
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

    public void CheckDcpForPosting(OnCheckDcpForPosting callback){
        new CheckDcpTask(callback).execute();
    }

    private class CheckDcpTask extends AsyncTask<String, Void, Integer>{

        private final OnCheckDcpForPosting callback;

        private String message;

        public CheckDcpTask(OnCheckDcpForPosting callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad();
        }

        @Override
        protected Integer doInBackground(String... strings) {
            if(!poSys.HasRemittedCollection()){
                message = poSys.getMessage();
                return 2;
            }

            if(poSys.HasNotVisitedCollection()){
                message = poSys.getMessage();
                return 0;
            }

            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            switch (result){
                case 0: //Failed
                    callback.OnIncompleteDcp();
                    break;
                case 1: //Success
                    callback.OnSuccess();
                    break;
                default: //Need remittance
                    callback.OnFailed(message);
                    break;

            }
        }
    }

    public void PostCollectionList(String fsVal, OnActionCallback callback){
        new PostCollectionTask(callback).execute(fsVal);
    }

    private class PostCollectionTask extends AsyncTask<String, Void, Boolean>{

        private final OnActionCallback callback;

        private String message;

        public PostCollectionTask(OnActionCallback callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            if(!poConn.isDeviceConnected()){
                message = poConn.getMessage();
                return false;
            }

            String lsResult = poSys.PostCollection(strings[0]);
            if(lsResult == null){
                message = poSys.getMessage();
                return false;
            }

            if(!poSys.PostDcpMaster(lsResult)){
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