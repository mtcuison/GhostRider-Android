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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionMaster;
import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCollectionUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.integsys.Dcp.ImportParams;
import org.rmj.g3appdriver.lib.integsys.Dcp.LRDcp;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.DcpManager;
import org.rmj.guanzongroup.ghostrider.notifications.Function.GRiderErrorReport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class VMCollectionList extends AndroidViewModel {
    private static final String TAG = VMCollectionList.class.getSimpleName();

    private final LRDcp poSys;

    public interface OnDownloadCollection{
        void OnDownload();
        void OnSuccessDownload();
        void OnDownloadFailed(String message);
    }

    public interface OnDownloadClientList{
        void OnDownload();
        void OnSuccessDownload(List<EDCPCollectionDetail> collectionDetails);
        void OnFailedDownload(String message);
    }

    public VMCollectionList(@NonNull Application application) {
        super(application);
        this.poSys = new LRDcp(application);
    }

    public LiveData<List<EDCPCollectionDetail>> GetColletionList(){
        return null;
    }

    public void DownloadDCP(ImportParams foVal, OnDownloadCollection callback){
        new DownloadDcpTask(callback).execute(foVal);
    }

    private class DownloadDcpTask extends AsyncTask<ImportParams, Void, Boolean>{

        private String message;

        private final OnDownloadCollection callback;

        public DownloadDcpTask(OnDownloadCollection callback) {
            this.callback = callback;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnDownload();
        }

        @Override
        protected Boolean doInBackground(ImportParams... importParams) {
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
                callback.OnSuccessDownload();
            } else {
                callback.OnDownloadFailed(message);
            }
        }
    }
}