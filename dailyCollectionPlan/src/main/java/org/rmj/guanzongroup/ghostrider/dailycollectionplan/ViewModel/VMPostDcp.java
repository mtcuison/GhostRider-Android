/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 10/22/21, 3:14 PM
 * project file last modified : 10/22/21, 3:14 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.json.JSONObject;
import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EAddressUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EClientUpdate;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EMobileUpdate;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranch;
import org.rmj.g3appdriver.GRider.Database.Repositories.RDailyCollectionPlan;
import org.rmj.g3appdriver.GRider.Database.Repositories.RImageInfo;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.GRider.Http.HttpHeaders;
import org.rmj.g3appdriver.GRider.Http.WebClient;
import org.rmj.g3appdriver.dev.Telephony;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.etc.WebFileServer;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.utils.WebApi;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Core.DcpManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VMPostDcp extends AndroidViewModel {
    private static final String TAG = VMPostDcp.class.getSimpleName();
    private final Application instance;
    private final RDailyCollectionPlan poDcpRepo;
    private final RBranch poBranchx;

    public VMPostDcp(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poDcpRepo = new RDailyCollectionPlan(instance);
        this.poBranchx = new RBranch(application);
    }

    public LiveData<EBranchInfo> getUserBranchInfo(){
        return poBranchx.getUserBranchInfo();
    }

    public LiveData<List<EDCPCollectionDetail>> getUnpostedCollectionList() {
        return poDcpRepo.getCollectionDetailLog();
    }

    public void PostLRDCPCollection(OnPostCollection foCallBck) {
        new PostLRDCPCollectionTask(instance, foCallBck).execute();
    }

    public void PostLRDCPTransaction(EDCPCollectionDetail foDcpInfo, OnPostCollection foCallBck) {
        new PostLRDCPTransactionTask(instance, foCallBck).execute(foDcpInfo);
    }

    private static class PostLRDCPCollectionTask extends AsyncTask<Void, Void, String> {
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnPostCollection poCallBck;
        private boolean isSuccess = false;

        private PostLRDCPCollectionTask(Application foAppsxxx, OnPostCollection foCallBck) {
            this.poConnect = new ConnectionUtil(foAppsxxx);
            this.poDcpMngr = new DcpManager(foAppsxxx);
            this.poCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallBck.onLoading();
        }

        @Override
        protected String doInBackground(Void... voids) {
            final String[] lsResult = {""};

            try {
                if(poConnect.isDeviceConnected()) {
                    poDcpMngr.PostLRDCPCollection(new DcpManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            isSuccess = true;
                            lsResult[0] = args;
                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResult[0] = message;
                        }
                    });
                    poDcpMngr.PostDcpMaster(new DcpManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {

                        }

                        @Override
                        public void OnFailed(String message) {

                        }
                    });
                } else {
                    lsResult[0] = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResult[0] = e.getMessage();
            }

            return lsResult[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isSuccess) {
                poCallBck.onSuccess(s);
            } else {
                poCallBck.onFailed(s);
            }
        }

    }

    private static class PostLRDCPTransactionTask extends AsyncTask<EDCPCollectionDetail, Void, String> {
        private final ConnectionUtil poConnect;
        private final DcpManager poDcpMngr;
        private final OnPostCollection poCallBck;
        private boolean isSuccess = false;

        private PostLRDCPTransactionTask(Application foAppsxxx, OnPostCollection foCallBck) {
            this.poConnect = new ConnectionUtil(foAppsxxx);
            this.poDcpMngr = new DcpManager(foAppsxxx);
            this.poCallBck = foCallBck;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            poCallBck.onLoading();
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... edcpCollectionDetails) {
            EDCPCollectionDetail loDcpInfo = edcpCollectionDetails[0];
            final String[] lsResultx = {""};

            try {
                if(poConnect.isDeviceConnected()) {
                    poDcpMngr.PostLRDCPTransaction(loDcpInfo.getTransNox(), loDcpInfo.getAcctNmbr(),
                            new DcpManager.OnActionCallback() {
                        @Override
                        public void OnSuccess(String args) {
                            isSuccess = true;
                            lsResultx[0] = args;
                        }

                        @Override
                        public void OnFailed(String message) {
                            lsResultx[0] = message;
                        }
                    });
                } else {
                    lsResultx[0] = AppConstants.SERVER_NO_RESPONSE();
                }
            } catch (Exception e) {
                e.printStackTrace();
                lsResultx[0] = e.getMessage();
            }

            return lsResultx[0];
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(isSuccess) {
                poCallBck.onSuccess(s);
            } else {
                poCallBck.onFailed(s);
            }
        }

    }

    public interface OnPostCollection {
        void onLoading();
        void onSuccess(String fsMessage);
        void onFailed(String fsMessage);
    }

}
